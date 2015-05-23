package org.amityregion5.qxrz.server.world.entity;

import java.awt.Graphics2D;
import java.util.ArrayList;

import org.amityregion5.qxrz.server.world.vector2d.Vector2D;

public class CompoundHitbox extends Hitbox
{

	private ArrayList<Hitbox> hbs;
	
	public CompoundHitbox()
	{
		hbs = new ArrayList<Hitbox>();
	}
	
	public CompoundHitbox add(Hitbox h)
	{
		hbs.add(h);
		return this;
	}

	@Override
	public boolean intersects(Hitbox h2)
	{
		for(Hitbox h : hbs)
		{
			if(h.intersects(h2))
			{
				return true;
			}
		}
		return false;
	}

	@Override
	public void debugDraw(Graphics2D g)
	{
		for (Hitbox h : hbs)
		{
			h.debugDraw(g);
		}
	}

	@Override
	public Vector2D getNearestNormal(Hitbox h)
	{
		// TODO Auto-generated method stub
		return new Vector2D();
	}
	
}
