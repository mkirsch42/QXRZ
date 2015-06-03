package org.amityregion5.qxrz.server.world.gameplay;

import org.amityregion5.qxrz.server.world.entity.PlayerEntity;
import org.amityregion5.qxrz.server.world.entity.ProjectileEntity;

import com.sun.xml.internal.bind.v2.schemagen.xmlschema.List;

public class Player {
	private int health;
	private int speed;
	private Weapon[] guns = new Weapon[2];
	private int equipped; //index for currently equipped weapon
	private Upgrade pupgr; //player upgrade
	private PlayerEntity entity; //physics entity for player
	//constructors
	public Player()
	{
		guns[0] = new Weapon();
		health = 100;
		speed = 100;
		entity = new PlayerEntity();
	}
	public Player(Upgrade u)
	{
		this();
		pupgr = u;
	}
	public Player(PlayerEntity spawn)
	{
		guns[0] = new Weapon();
		health = 100;
		speed = 100;
		entity = spawn;
	}
	public Player(Upgrade u, PlayerEntity spawn)
	{
		this(spawn);
		pupgr = u;
	}
	
	public void damaged(Bullet b)
	{
		if (b.getEntity().getHitBox().intersects(entity.getHitbox()))
		 health -= b.getDamage();
		dead();
	}
	public boolean dead()
	{
		if (!(health <= 0))	
		{
			return false;
		}
		//respawn code to add later
		return true;
	}
	public void setUpgrade(Upgrade u)
	{
		pupgr = u;
	}
	public void equip(int input)
	{
		equipped = input-1;
	}
	public void shoot()
	{
		if (guns[equipped].shoot())
		{
		//Bullet b = new Bullet(new ProjectileEntity(entity), guns[equipped]);
		}
		else {}
	}
	public void useHealthpack()
	{
		if (health >= 100 && health != 100)
			health = 100;
		health += 50;
	}
	public Weapon getEquipped()
	{
		return guns[equipped];
	}
	
	
}
