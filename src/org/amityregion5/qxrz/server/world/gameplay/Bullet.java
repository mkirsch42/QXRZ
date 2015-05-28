package org.amityregion5.qxrz.server.world.gameplay;

import org.amityregion5.qxrz.server.world.entity.ProjectileEntity;

public class Bullet {
	private String type;
	private int damage;
	private ProjectileEntity entity;
	
	public Bullet()
	{
		this("ps"); //pistol is default
	}
	public Bullet(String wtype)
	{
		type = wtype;
		if (type.equals("ps"))
			damage = 5;
		else if (type.equals("sg"))
			damage = 10;
		else if (type.equals("ro"))
			damage = 95;
		else if (type.equals("fl"))
			damage = 15;
	}
	public int getDamage()
	{
		return damage;
	}
	public String getType()
	{
		return type;
	}
	public ProjectileEntity getEntity()
	{
		return entity;
	}
	
}
