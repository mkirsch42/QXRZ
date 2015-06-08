package org.amityregion5.qxrz.server.world.gameplay;

import java.awt.Color;

import javafx.scene.Parent;

import org.amityregion5.qxrz.common.control.NetworkInputData;
import org.amityregion5.qxrz.common.control.NetworkInputMasks;
import org.amityregion5.qxrz.server.world.World;
import org.amityregion5.qxrz.server.world.entity.PlayerEntity;
import org.amityregion5.qxrz.server.world.entity.ProjectileEntity;
import org.amityregion5.qxrz.server.world.vector2d.Vector2D;

public class Player {
	private static int lastId = 0;
	private int id;
	private int health;
	private int speed;
	private Weapon[] guns = new Weapon[2];
	private int equipped; //index for currently equipped weapon
	private Upgrade pupgr; //player upgrade
	private SpecialMovement pspecmove; //player special movement
	private PlayerEntity entity; //physics entity for player
	private World w;
	private boolean hasShot = false;
	private String name;
	private Team team;
	private boolean pickingUp = false;
	private boolean dead = false;
	private int score;
	//constructors
	public Player(int forceId)
	{
		id = forceId;
	}
	public Player(World parent, String n) //creates a newly spawned player
	{
		id = lastId++;
		guns[0] = new Weapon();
		health = 100;
		speed = 600;
		entity = new PlayerEntity(this);
		w = parent;
		name = n;
		w.say(name + " joined");
	}
	public Player(Upgrade u, World parent, String n) //creates a newly spawned player with a weapon upgrade
	{
		this(parent, n);
		pupgr = u;
	}
	public Player(PlayerEntity spawn, World parent, String n) //spawned at a given entity
	{
		guns[0] = new Weapon();
		health = 100;
		speed = 600;
		entity = spawn;
		w = parent;
		name = n;
		w.say(name + " joined");
	}
	public Player(Upgrade u, PlayerEntity spawn, World parent, String n) //previous two constructors in one
	{
		this(spawn, parent, n);
		pupgr = u;
	}
	
	public void joinTeam(Team t)
	{
		t.join(this);
		team = t;
	}
	
	public void leaveTeam()
	{
		if(team!=null)
			team.leave(this);
		team = null;
	}
	
	public boolean damaged(Bullet b) //tests if a given bullet hits player and acts accordingly
	{
		if(w.getGame().getGM().oneLife && dead)
		{
			return false;
		}
		if(team!=null && b.friendlyFireTeam()==team.getId() && !w.getGame().friendlyFire())
			return false;
		if(b.getFriendlyFirePlayer()==id)
			return false;
		if (b.getEntity().getHitbox().intersects(entity.getHitbox()))
		 health -= b.getDamage();
		dead();
		return true;
	}
	
	public Color getColor()
	{
		if(team==null)
		{
			return Color.GRAY; 
		}
		return team.getColor();
	}
	
	public boolean dead() //tests for death
	{
		if (!(health <= 0))	
		{
			return false;
		}
		//respawn code to add later
		health = 100;
		w.removeEntity(entity);
		entity = new PlayerEntity(this);
		w.add(entity);
		w.say(name + " died");
		dead = true;
		return true;
	}
	public boolean isDead()
	{
		return dead;
	}
	public void setUpgrade(Upgrade u) //sets weapon upgrades from a given upgrade pickup
	{
		pupgr = u;
		if (!(pupgr.getType().equals(null)))
		{
		
		switch (pupgr.getType())
		{
		case "maxclips": guns[0].changeMaxAmmo();
						 guns[1].changeMaxAmmo();
		case "rof":		 guns[0].changeROF();
						 guns[1].changeROF();
		case "cmax":	 guns[0].changeCMax();
						 guns[1].changeCMax();
		}
		}
		else {};
	}
	public void equip(int input) //changes currently equipped weapon
	{
		equipped = input-1; //assuming weapon keys for user will be 1 and 2 respectively
	}
	public void shoot(Vector2D v) //shoots currently equipped weapon and creates a respective bullet
	{
		if (guns[equipped].shoot())
		{
			int len = entity.PLAYER_SIZE/2 + ProjectileEntity.projsize/2;
			Vector2D normVel = new Vector2D(v.snap().angle()).multiply(len).snap();
			Vector2D pos;
			if(normVel.getX()==0)
			{
				pos = entity.getPos().add(new Vector2D(v.angle()).multiply(1+Math.abs(len/Math.sin(v.angle()))).multiply(0.5));
			}
			else
			{
				pos = entity.getPos().add(new Vector2D(v.angle()).multiply(1+Math.abs(len/Math.cos(v.angle()))).multiply(0.5));
			}
			
			Bullet b = new Bullet(pos, v, guns[equipped]);
			if(team != null)
				b.setFriendlyFireTeam(team);
			b.setFriendlyFirePlayer(this);
			w.add(b.getEntity());
		}
		else {}
	}
	public void useHealthpack() //increase health by using health pack
	{
		if (health >= 50 && health != 100)
			health = 100;
		health += 50;
	}
	public void setSpecMove(SpecialMovement sm)
	{
		pspecmove = sm;
		
	}
	public void useSpecMove() //suggested buttons are f or e
	{
		if (!(pspecmove.equals(null)))
		{
			switch (pspecmove.getType())
			{
			case "roll":	pspecmove.roll(entity);
			case "tele":	pspecmove.teleport(entity);
			case "dash":	pspecmove.dash(entity);
			}
		}
		else {}
	}
	public void input(NetworkInputData nid)
	{
		entity.input(nid);
		if(nid.get(NetworkInputMasks.M1) && !hasShot)
		{
			hasShot = true;
			Vector2D v = new Vector2D(nid.getMouseX(), nid.getMouseY()).subtract(entity.getPos());
			shoot(v);
		}
		if(!nid.get(NetworkInputMasks.M1))
		{
			hasShot = false;
		}
		pickingUp = nid.get(NetworkInputMasks.E);
	}
	public PlayerEntity getEntity()
	{
		return entity;
	}
	public int getId()
	{
		return id;
	}
	public boolean equals(Object obj)
	{
		if(!(obj instanceof Player))
		{
			return false;
		}
		return ((Player)obj).getId()==id;
	}
	public String getName()
	{
		return name;
	}
	
	public Team getTeam()
	{
		return team;
	}
	
	public boolean pickup(Pickup p)
	{
		if(!p.canPickup())
		{
			return false;
		}
		String wep = p.getWeaponId();
		if(guns[0]!=null && guns[0]
				.getType()
				.equals(wep))
		{
			if(!guns[0].addAmmo(p.getAmmoCount()))
				return false;
		}
		else if(guns[1]!=null && guns[1].getType().equals(wep))
		{
			if(!guns[1].addAmmo(p.getAmmoCount()))
				return false;
		}
		if(pickingUp)
		{
			guns[equipped] = new Weapon(wep);
			guns[equipped].setAmmo(p.getAmmoCount());
		}
		p.pickup();
		return true;
	}
	public double getSpeed()
	{
		return speed;
	}
	
	public World getParent()
	{
		return w;
	}
	public int getScore()
	{
		return score;
	}
}
