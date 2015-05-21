package org.amityregion5.qxrz.server.world.entity;

import java.awt.Graphics2D;

public abstract class Hitbox
{

	public abstract void debugDraw(Graphics2D g);
	public abstract boolean intersects(Hitbox h2);
	
}
