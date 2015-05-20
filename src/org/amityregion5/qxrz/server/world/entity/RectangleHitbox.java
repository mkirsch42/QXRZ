package org.amityregion5.qxrz.server.world.entity;

import java.awt.Rectangle;

public class RectangleHitbox extends Hitbox
{

	private Rectangle bounds;
	
	public RectangleHitbox(Rectangle r)
	{
		bounds = r;
	}
	
	public Rectangle getBounds()
	{
		return bounds;
	}
	
	public boolean intersects(Hitbox h2)
	{
		if(!(h2 instanceof RectangleHitbox))
		{
			return false;
		}
		RectangleHitbox rhb = (RectangleHitbox)h2;
		return bounds.intersects(rhb.getBounds());
	}
	
}
