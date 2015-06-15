package org.amityregion5.qxrz.server.world.entity;

import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Rectangle2D;

import org.amityregion5.qxrz.server.world.vector2d.Vector2D;

public class CircleHitbox extends Hitbox
{

	int cX;
	int cY;
	int radius;

	public CircleHitbox(int x, int y, int r)
	{
		cX = x;
		cY = y;
		radius = r;
	}

	public Ellipse2D getBounds()
	{
		return new Ellipse2D.Double(cX - radius, cY - radius, 2 * radius,
				2 * radius);
	}

	@Override
	public void debugDraw(Graphics2D g)
	{
		// TODO Auto-generated method stub

	}

	@Override
	public boolean intersects(Hitbox h2)
	{
		return h2.intersects(new RectangleHitbox((Rectangle) getAABB()));
	}

	@Override
	public Vector2D getNearestNormal(Hitbox h)
	{
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Rectangle2D getAABB()
	{
		return new Rectangle(cX - radius, cY - radius, 2 * radius, 2 * radius);
	}

}
