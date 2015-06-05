package org.amityregion5.qxrz.server.world.gameplay;

import org.amityregion5.qxrz.server.world.entity.ProjectileEntity;

public class Bullet {
	private String type;
	private int damage;
	private ProjectileEntity entity;
	//constructors
	public Bullet()
	{
		this(new Weapon("ps")); //pistol is default
	}
	public Bullet(Weapon wep)
	{
		damage = wep.getDamage();
		type = wep.getType();
	}
	public Bullet(ProjectileEntity source)
	{
		entity = source;
	}
	public Bullet(ProjectileEntity source, Weapon wep)
	{
		this(wep);
		entity = source;
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
