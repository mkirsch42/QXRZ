package org.amityregion5.qxrz.server.world.entity;

import java.awt.Rectangle;

public class PlayerEntity extends GameEntity
{
	
	public PlayerEntity()
	{
		x = 0;
		y = 0;
		vX = 2;
		vY = 1;
	}

	public boolean update(double tSinceUpdate)
	{
		x += vX * tSinceUpdate;
		y += vY * tSinceUpdate;
		System.out.println(x + "," + y);
		return false;
	}
	
	public Hitbox getHitbox()
	{
		// Create 2x2 square around player
		return new RectangleHitbox(new Rectangle((int)x-1, (int)y-1, 2, 2));
	}

	@Override
	public boolean collide()
	{
		// TODO Auto-generated method stub
		return false;
	}

}
