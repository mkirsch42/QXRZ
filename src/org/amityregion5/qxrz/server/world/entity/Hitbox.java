package org.amityregion5.qxrz.server.world.entity;

import java.awt.Graphics2D;
import java.awt.geom.Rectangle2D;
import org.amityregion5.qxrz.server.world.vector2d.Vector2D;

public abstract class Hitbox
{

	public abstract void debugDraw(Graphics2D g);
	public abstract boolean intersects(Hitbox h2);
	public abstract Vector2D getNearestNormal(Hitbox h);
	public abstract Rectangle2D getAABB();
	
}
