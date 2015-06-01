package org.amityregion5.qxrz.server.world.gameplay;

import org.amityregion5.qxrz.server.world.entity.PlayerEntity;
import org.amityregion5.qxrz.server.world.entity.ProjectileEntity;

public class Player {
	//some commit
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
	public void useHealthpack()
	{
		if (health >= 100 && health != 100)
			health = 100;
		health += 50;
	}
	
}
