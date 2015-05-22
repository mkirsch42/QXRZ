package org.amityregion5.qxrz.server.world.entity;

import java.awt.Graphics2D;
import java.awt.geom.Rectangle2D;

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
	
}
