package org.amityregion5.qxrz.server.world.entity;

import java.awt.Graphics2D;
import java.awt.geom.Rectangle2D;
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

	@Override
	public Rectangle2D getAABB() {
		return hbs.stream().map((h)->h.getAABB())
				.reduce(hbs.get(0).getAABB(),
						(c, n)->{return new Rectangle2D.Double(Math.min(c.getX(), n.getX()),Math.min(c.getY(), n.getY()),
								Math.max(c.getMaxX(), n.getMaxX()) - Math.min(c.getX(), n.getX()), 
								Math.max(c.getMaxY(), n.getMaxY()) - Math.min(c.getY(), n.getY()));},
						(c, n)->{return new Rectangle2D.Double(Math.min(c.getX(), n.getX()),Math.min(c.getY(), n.getY()),
								Math.max(c.getMaxX(), n.getMaxX()) - Math.min(c.getX(), n.getX()), 
								Math.max(c.getMaxY(), n.getMaxY()) - Math.min(c.getY(), n.getY()));});
	}

}
