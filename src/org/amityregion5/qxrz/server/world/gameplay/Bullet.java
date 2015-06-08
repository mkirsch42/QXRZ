package org.amityregion5.qxrz.server.world.gameplay;

import org.amityregion5.qxrz.server.world.entity.ProjectileEntity;
import org.amityregion5.qxrz.server.world.vector2d.Vector2D;

public class Bullet {
	private String type;
	private int damage;
	private ProjectileEntity entity;
	private int teamId = -1;
	private int sourceId = -1;
	private int speed;
	//constructors
	public Bullet()
	{
		this(new Vector2D(), new Vector2D(), new Weapon("ps"));
	}
	public Bullet(Weapon wep)
	{
		this(new Vector2D(), new Vector2D(), wep);
		damage = wep.getDamage();
		type = wep.getType();
		speed = wep.getSpeed();
		entity = new ProjectileEntity(new Vector2D(), new Vector2D(), this);
	}
	public Bullet(Vector2D pos, Vector2D vel)
	{
		this(pos, vel, new Weapon("ps"));
	}
	public Bullet(Vector2D pos, Vector2D vel, Weapon wep)
	{
		damage = wep.getDamage();
		type = wep.getType();
		speed = wep.getSpeed();
		entity = new ProjectileEntity(pos, new Vector2D(vel.angle()).multiply(wep.getSpeed()), this);
	}
	
	public void setFriendlyFireTeam(Team t)
	{
		teamId = t.getId();
	}
	
	public int friendlyFireTeam()
	{
		return teamId;
	}
	
	public void setFriendlyFirePlayer(Player p)
	{
		sourceId = p.getId();
	}
	
	public int getFriendlyFirePlayer()
	{
		return sourceId;
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
	public int getSpeed()
	{
		return speed;
	}
	
}
