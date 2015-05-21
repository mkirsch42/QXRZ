package org.amityregion5.qxrz.server.world.entity;

import java.awt.Rectangle;

import org.amityregion5.qxrz.server.world.Landscape;
import org.amityregion5.qxrz.server.world.vector2d.Vector2D;

public class PlayerEntity extends GameEntity
{
	
	public PlayerEntity()
	{
		pos = new Vector2D(0,0);
		vel = new Vector2D(2,1);
	}

	public boolean update(double tSinceUpdate, Landscape surroundings)
	{
		Vector2D bak = pos;
		pos = pos.add(vel.multiply(tSinceUpdate));
		Object o = surroundings.checkCollisions(this);
		if (o!=null)
		{
			pos = bak;
			System.out.println("Collision!");
		}
		System.out.println(pos);
		return false;
	}
	
	public Hitbox getHitbox()
	{
		// Create 2x2 square around player
		return new RectangleHitbox(new Rectangle((int)pos.getX()-1, (int)pos.getY()-1, 2, 2));
	}

	@Override
	public boolean collide()
	{
		// TODO Auto-generated method stub
		return false;
	}

}
