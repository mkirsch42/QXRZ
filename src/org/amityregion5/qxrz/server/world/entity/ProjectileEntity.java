package org.amityregion5.qxrz.server.world.entity;

import java.awt.geom.Path2D;
import java.awt.geom.Rectangle2D;

import java.awt.Rectangle;


import org.amityregion5.qxrz.server.world.Landscape;
import org.amityregion5.qxrz.server.world.Obstacle;
import org.amityregion5.qxrz.server.world.vector2d.Vector2D;

public class ProjectileEntity extends GameEntity
{

	private int projectilesize; //depending on specific projectile?
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
		return new RectangleHitbox(new Rectangle((int)pos.getX(),(int)pos.getY(),projectilesize,projectilesize));
	}
	public Obstacle checkCollisions(Vector2D v, Landscape surroundings)
	{
		Obstacle o = new Obstacle(null);
		for (Obstacle obj: surroundings.getObstacles())
		{
			if (obj.getHitbox().intersects(this.getHitbox()))
			{
				o = obj;
				break;
			}
		}
		return o;
	}

	@Override
	public Hitbox getHitbox() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	protected boolean collide(Hitboxed h, Landscape l, Vector2D v) {
		
		return false;
	}

}
