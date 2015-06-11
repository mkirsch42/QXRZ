package org.amityregion5.qxrz.server.world.gameplay;

import java.awt.Color;
import java.util.Random;

import org.amityregion5.qxrz.common.control.NetworkInputData;
import org.amityregion5.qxrz.common.control.NetworkInputMasks;
import org.amityregion5.qxrz.server.world.World;
import org.amityregion5.qxrz.server.world.entity.PlayerEntity;
import org.amityregion5.qxrz.server.world.vector2d.Vector2D;

public class Player {
	private static int lastId = 0;
	private int id;
	private int health;
	private int speed;
	private Weapon[] guns = {new Weapon("ps"), new Weapon()};
	private int equipped; //index for currently equipped weapon
	private Upgrade pupgr; //player upgrade
	private SpecialMovement pspecmove; //player special movement
	private PlayerEntity entity; //physics entity for player
	private World w;
	private String name;
	private Team team;
	private boolean pickingUp = false;
	private boolean dead = false;
	private int score;
	private NetworkInputData downs;
	//constructors
	public Player(int forceId)
	{
		id = forceId;
	}
	public Player(World parent, String n) //creates a newly spawned player
	{
		id = lastId++;
		guns[0] = new Weapon("ps");
		health = 100;
		speed = 600;
		entity = new PlayerEntity(this);
		w = parent;
		name = n;
		w.say(name + " joined");
		downs = new NetworkInputData();
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
		downs = new NetworkInputData();
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
		if (isDead()) {b.getSource().getGameModel().addScore();} //gives player a point
		//w.win(w.getGame().getGM());
		return true;
	}
	public boolean hurtme(int dmg)
	{
		if(w.getGame().getGM().oneLife && dead)
		{
			return false;
		}
		health -= dmg;
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
		
		dead = true;
		w.say(name + " died");
		respawn(!w.getGame().getGM().oneLife);
		return true;
	}
	public boolean isDead()
	{
		return dead;
	}
	public void respawn(boolean revive)
	{
		if(revive)
		{
			dead = false;
		}
		health = 100;
		w.removeEntity(entity);
		entity = new PlayerEntity(this);
		w.add(entity);
		guns[0] = new Weapon("ps");
		guns[1] = new Weapon();
		equipped = 0;
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
		if(getEquipped().getType().equals(WeaponTypes.KNIFE.text))
		{
			stab(v);
			return;
		}
		if (guns[equipped].shoot())
		{
			if(getEquipped().getType().equals(WeaponTypes.SHOTGUN.text))
			{
				SpecialWeapons.shotgun(w, this, v);
			}
			else if(getEquipped().getType().equals(WeaponTypes.FIREGUN.text))
			{
				
			}
			else
			{
				Bullet b = new Bullet(entity.getPos(), v, guns[equipped]);
				if(team != null)
					b.setFriendlyFireTeam(team);
				b.setFriendlyFirePlayer(this);
				b.setSource(entity);
				w.add(b.getEntity());
			}
		}
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
		if(dead && w.getGame().getGM().oneLife)
			return;
		if(nid.get(NetworkInputMasks.M1) && !getEquipped().cooling() && (getEquipped().getEnumType().auto || !downs.get(NetworkInputMasks.M1)))
		{
			Vector2D v = new Vector2D(nid.getMouseX(), nid.getMouseY()).subtract(entity.getPos());
			shoot(v);
		}
		if(nid.get(NetworkInputMasks.R) && !downs.get(NetworkInputMasks.R))
		{
			getEquipped().reload();
		}
		if(nid.get(NetworkInputMasks.Q) && !downs.get(NetworkInputMasks.Q))
		{
			equipped = (~equipped) & 1; //bc why not
		}
		if(nid.get(NetworkInputMasks.M2) && !downs.get(NetworkInputMasks.M2))
		{
			Vector2D v = new Vector2D(nid.getMouseX(), nid.getMouseY()).subtract(entity.getPos());
			stab(v);
		}
		pickingUp = nid.get(NetworkInputMasks.E);
		downs = nid;
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
		if(isDead())
		{
			return false;
		}
		if(!p.canPickup())
		{
			return false;
		}
		if(p.isHealth())
		{
			if(health>=100)
			{
				health = 100;
				return false;
			}
			health += p.getAmmoCount();
			if(health>=100)
			{
				health=100;
			}
			p.pickup();
			return true;
		}
		String wep = p.getWeaponId();
		if(guns[0]!=null && guns[0].getType().equals(wep))
		{
			if(!guns[0].addAmmo(p.getAmmoCount()))
				return false;
		}
		else if(guns[1]!=null && guns[1].getType().equals(wep))
		{
			if(!guns[1].addAmmo(p.getAmmoCount()))
				return false;
		}
		else if(pickingUp)
		{
			guns[equipped] = new Weapon(wep);
			guns[equipped].setAmmo(p.getAmmoCount());
		}
		else
		{
			return false;
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
	public int getHealth()
	{
		return health;
	}
	public Weapon getEquipped()
	{
		return guns[equipped];
	}
	
	private void stab(Vector2D v)
	{		
		Bullet b = new Bullet(entity.getPos(), v, true);
		if(team != null)
			b.setFriendlyFireTeam(team);
		b.setFriendlyFirePlayer(this);
		b.setSource(entity);
		b.getEntity().update(1,w);
	}
	
	public String getNT()
	{
		if(getEquipped().getType().equals(WeaponTypes.KNIFE.text))
			return getName() + " (" + getHealth() + ")";
		return getName() + " (" + getHealth() + " , " + getEquipped().getType() + ":" + getEquipped().getInClip() + "+" + getEquipped().getReserve() + ")";
	}
	public void randomSpawn()
	{
		w.removeEntity(entity);
		Random r = new Random();
		do
		{
			Vector2D newPos = new Vector2D(r.nextInt((int)w.getBounds().getWidth())+(int)w.getBounds().getMinX(),
					r.nextInt((int)w.getBounds().getHeight())+(int)w.getBounds().getMinY());
			entity = new PlayerEntity(newPos, this);
		} while(w.getLandscape().checkCollisions(entity.getHitbox())!=null);
		w.add(entity);
	}

	public void addScore()
	{
		score++;
	}
	public void equipWep(String string)
	{
		guns[equipped] = new Weapon(string);

	}
	public boolean isFlipped()
	{
		return downs!=null && Math.abs(new Vector2D(downs.getMouseX(), downs.getMouseY()).subtract(entity.getPos()).angle())<Math.PI/2;
	}
	public double rotatedAngle()
	{
		if(downs==null)
		{
			return 0;
		}
		return new Vector2D(downs.getMouseX(), downs.getMouseY()).subtract(entity.getPos()).angle();
	}
}
