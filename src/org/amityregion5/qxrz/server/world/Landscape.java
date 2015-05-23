package org.amityregion5.qxrz.server.world;

import java.awt.Graphics2D;
import java.util.ArrayList;

import org.amityregion5.qxrz.server.world.entity.Hitbox;
import org.amityregion5.qxrz.server.world.entity.Hitboxed;

public class Landscape
{

	ArrayList<Obstacle> obstacles;
	
	public Landscape()
	{
		obstacles = new ArrayList<Obstacle>();
	}
	
	public void add(Obstacle o)
	{
		obstacles.add(o);
	}
	
	public Obstacle checkCollisions(Hitbox e)
	{
		// Check each obstacle for collision
		for(Obstacle o : obstacles)
		{
			if(o.getHitbox().intersects(e))
			{
				return o;
			}
		}
		return null;
	}

	public void draw(Graphics2D g2)
	{
		for (Obstacle o : obstacles)
		{
			o.getHitbox().debugDraw(g2);
		}
	}

}
