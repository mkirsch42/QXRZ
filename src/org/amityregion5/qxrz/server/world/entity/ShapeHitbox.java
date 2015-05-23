package org.amityregion5.qxrz.server.world.entity;

import java.awt.Graphics2D;
import java.awt.Shape;

import org.amityregion5.qxrz.server.world.vector2d.Vector2D;

public class ShapeHitbox extends Hitbox
{

	Shape bounds;
	
	public ShapeHitbox(Shape s)
	{
		bounds = s;
	}
	
	@Override
	public void debugDraw(Graphics2D g)
	{
		// TODO Auto-generated method stub
		
	}
	
	public Shape getBounds()
	{
		return bounds;
	}
	
	@Override
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
	public Vector2D getNearestNormal(Hitbox h)
	{
		// TODO Auto-generated method stub
		return null;
	}

	
	
}
