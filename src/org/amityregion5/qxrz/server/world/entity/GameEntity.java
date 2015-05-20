package org.amityregion5.qxrz.server.world.entity;

public abstract class GameEntity
{

	protected double x;
	protected double y;
	protected double vX;
	protected double vY;
	
	public abstract boolean update(double tSinceUpdate);
	
	public GameEntity()
	{
		// TODO Auto-generated constructor stub
	}

}
