package org.amityregion5.qxrz.server.world.gameplay;

import org.amityregion5.qxrz.common.control.NetworkInputData;
import org.amityregion5.qxrz.server.world.entity.PlayerEntity;
import org.amityregion5.qxrz.server.world.entity.ProjectileEntity;

import com.sun.xml.internal.bind.v2.schemagen.xmlschema.List;

public class Player {
	private int health;
	private int speed;
	private Weapon[] guns = new Weapon[2];
	private int equipped; //index for currently equipped weapon
	private Upgrade pupgr; //player upgrade
	private SpecialMovement pspecmove; //player special movement
	private PlayerEntity entity; //physics entity for player
	//constructors
	public Player() //creates a newly spawned player
	{
		guns[0] = new Weapon();
		health = 100;
		speed = 100;
		entity = new PlayerEntity();
	}
	public Player(Upgrade u) //creates a newly spawned player with a weapon upgrade
	{
		this();
		pupgr = u;
	}
	public Player(PlayerEntity spawn) //spawned at a given entity
	{
		guns[0] = new Weapon();
		health = 100;
		speed = 100;
		entity = spawn;
	}
	public Player(Upgrade u, PlayerEntity spawn) //previous two constructors in one
	{
		this(spawn);
		pupgr = u;
	}
	
	public void damaged(Bullet b) //tests if a given bullet hits player and acts accordingly
	{
		if (b.getEntity().getHitBox().intersects(entity.getHitbox()))
		 health -= b.getDamage();
		dead();
	}
	public boolean dead() //tests for death
	{
		if (!(health <= 0))	
		{
			return false;
		}
		//respawn code to add later
		return true;
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
	public void shoot() //shoots currently equipped weapon and creates a respective bullet
	{
		if (guns[equipped].shoot())
		{
		Bullet b = new Bullet(new ProjectileEntity(entity), guns[equipped]);
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
	public void useSpecMove()
	{
		if(pspecmove.getType().equals("roll"))
		{
			pspecmove.roll(entity);
		}
		else if (pspecmove.getType().equals("teleport"))
		{
			pspecmove.teleport(entity);
		}
		else if (pspecmove.getType().equals("dash"))
		{
			pspecmove.dash(entity);
		}
	}
	public void input(NetworkInputData nid)
	{
		entity.input(nid);
	}
}
