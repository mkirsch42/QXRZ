package org.amityregion5.qxrz.server.world.entity;

import java.awt.Graphics2D;
import java.awt.geom.Rectangle2D;

import org.amityregion5.qxrz.server.world.vector2d.Vector2D;

public class RectangleHitbox extends Hitbox
{

	private Rectangle2D.Double bounds;
	
	public RectangleHitbox(Rectangle2D.Double r)
	{
		bounds = r;
	}
	
	public Rectangle2D.Double getBounds()
	{
		return bounds;
	}
	
	public boolean intersects(Hitbox h2)
	{
		// TODO get rid of this and stop being hitboxist
		if(!(h2 instanceof RectangleHitbox))
		{
			return false;
		}
		// Cast and use built in functions to check intersection
		RectangleHitbox rhb = (RectangleHitbox)h2;
		return bounds.intersects(rhb.getBounds());
	}

	@Override
	public void debugDraw(Graphics2D g)
	{
		g.draw(bounds);
	}

	@Override
	public Vector2D getNearestNormal(Hitbox h)
	{
		if(! (h instanceof RectangleHitbox))
			return null;
		Rectangle2D.Double r = ((RectangleHitbox)h).getBounds();
		int code = bounds.outcode(r.getMaxX(), r.getMaxY()) &
				bounds.outcode(r.getMaxX(), r.getMinY()) &
				bounds.outcode(r.getMinX(), r.getMaxY()) &
				bounds.outcode(r.getMinX(), r.getMinY());
		System.out.println(code);
		if(code==1)
		{
			return new Vector2D(-1,0);
		}
		if(code==2)
		{
			return new Vector2D(0,-1);
		}
		if(code==4)
		{
			return new Vector2D(1,0);
		}
		if(code==8)
		{
			return new Vector2D(0,1);
		}
		return new Vector2D();
	}
	
}
