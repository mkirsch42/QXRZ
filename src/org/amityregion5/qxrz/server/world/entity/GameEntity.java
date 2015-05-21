package org.amityregion5.qxrz.server.world.entity;

public abstract class GameEntity implements Hitboxed
{
	// Coordinates and velocities
	protected double x;
	protected double y;
	protected double vX;
	protected double vY;
	
	public abstract boolean update(double tSinceUpdate);
	
	public abstract Hitbox getHitbox();
	
	public abstract boolean collide(/*PVector normal*/);
	
	public GameEntity()
	{
		// TODO Auto-generated constructor stub
	}

}
