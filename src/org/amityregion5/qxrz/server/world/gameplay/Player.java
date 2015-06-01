package org.amityregion5.qxrz.server.world.gameplay;

import org.amityregion5.qxrz.server.world.entity.PlayerEntity;
import org.amityregion5.qxrz.server.world.entity.ProjectileEntity;

public class Player {
	private int health;
	private int speed;
	private Weapon[] guns = new Weapon[2];
	private int equipped; //index for currently equipped weapon
	private Upgrade pupgr; //player upgrade
	private PlayerEntity entity; //physics entity for player
	
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
	public void damaged(Bullet b)
	{
		if (b.getEntity().getHitBox().intersects(entity.getHitbox()))
		 health -= b.getDamage();
		dead();
	}
	public void setUpgrade(Upgrade u)
	{
		pupgr = u;
	}
	public void useHealthpack()
	{
		if (health >= 100 && health != 100)
			health = 100;
		health += 50;
	}
	public boolean dead()
	{
		if (!(health <= 0))	
		{
			return false;
		}
		new Player();
		return true;
	}
	
}
