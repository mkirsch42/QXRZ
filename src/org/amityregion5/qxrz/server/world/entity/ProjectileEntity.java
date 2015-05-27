package org.amityregion5.qxrz.server.world.entity;

import java.awt.geom.Path2D;
import java.awt.geom.Rectangle2D;

import org.amityregion5.qxrz.server.world.Landscape;
import org.amityregion5.qxrz.server.world.Obstacle;
import org.amityregion5.qxrz.server.world.vector2d.Vector2D;

public abstract class ProjectileEntity extends GameEntity
{

	private final int PROJECTILE_SIZE = 1;
	private final int DAMAGE = 10;
	public ProjectileEntity(PlayerEntity source)

	{
		pos = source.pos;
		vel = source.vel;
	}
	
	public boolean update(double tSinceUpdate, Landscape surroundings)
	{
		//
		return false;
	}
	
	public RectangleHitbox getHitBox()
	{
		return new RectangleHitbox(new Rectangle2D.Double(pos.getX(),pos.getY(),PROJECTILE_SIZE,PROJECTILE_SIZE));
	}
	public int getDamage()
	{
		return DAMAGE;
	}
	public Obstacle checkCollisions(Vector2D v, Landscape surroundings)
	{
		//stuff will be added
		Vector2D back = pos;
		Rectangle2D.Double hbox = this.getHitBox().getBounds();
		Path2D.Double path = new Path2D.Double();
		
		return new Obstacle(null);
	}

}
