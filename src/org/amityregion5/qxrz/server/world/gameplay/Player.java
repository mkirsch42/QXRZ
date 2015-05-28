package org.amityregion5.qxrz.server.world.gameplay;

import org.amityregion5.qxrz.server.world.entity.PlayerEntity;
import org.amityregion5.qxrz.server.world.entity.ProjectileEntity;

public class Player {
	private int health;
	private final int speed = 100; //speed will not be upgradable
	private Weapon[] guns = new Weapon[2];
	private int equipped; //index for currently equipped weapon
	private Upgrade pupgr; //player upgrade
	private PlayerEntity entity;
	
	public Player()
	{
		guns[0] = new Weapon();
		health = 100;
	}
	public void damaged(Bullet b)
	{
		if (b.getEntity().getHitBox().intersects(entity.getHitbox()))
		 health -= b.getDamage();
	}
	public void setUpgrade(Upgrade u)
	{
		pupgr = u;
	}
}
