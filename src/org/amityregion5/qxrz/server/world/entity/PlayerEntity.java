package org.amityregion5.qxrz.server.world.entity;

public class PlayerEntity extends GameEntity
{
	
	public PlayerEntity()
	{
		x = 0;
		y = 0;
		vX = 2;
		vY = 1;
	}

	@Override
	public boolean update(double tSinceUpdate)
	{
		x += vX * tSinceUpdate;
		y += vY * tSinceUpdate;
		System.out.println(x + "," + y);
		return false;
	}

}
