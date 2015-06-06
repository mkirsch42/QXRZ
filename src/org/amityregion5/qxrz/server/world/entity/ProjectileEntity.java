package org.amityregion5.qxrz.server.world.entity;

import java.awt.Rectangle;

import org.amityregion5.qxrz.common.ui.NetworkDrawableEntity;
import org.amityregion5.qxrz.common.ui.NetworkDrawableObject;
import org.amityregion5.qxrz.server.world.Landscape;
import org.amityregion5.qxrz.server.world.Obstacle;
import org.amityregion5.qxrz.server.world.gameplay.Bullet;
import org.amityregion5.qxrz.server.world.vector2d.Vector2D;

public class ProjectileEntity extends GameEntity
{
	private String asset = "weapons/revolv"; //TODO: change this pls pls pls
	private int projsize; //depending on specific projectile?
	private Bullet b = new Bullet(this);
	
	public ProjectileEntity(PlayerEntity source)
	{
		super();
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
		return new RectangleHitbox(new Rectangle((int)pos.getX() - projsize,(int)pos.getY()- projsize,projsize,projsize));
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
		collide((Hitboxed) o.getHitbox(), surroundings, pos);
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

	@Override
	public NetworkDrawableEntity getNDE() {
		return new NetworkDrawableEntity(new NetworkDrawableObject[] {new NetworkDrawableObject(asset, getHitbox().getAABB())}, getHitbox().getAABB());
	}
}
