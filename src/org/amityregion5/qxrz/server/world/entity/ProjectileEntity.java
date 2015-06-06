package org.amityregion5.qxrz.server.world.entity;

import java.awt.Rectangle;

import org.amityregion5.qxrz.common.ui.NetworkDrawableEntity;
import org.amityregion5.qxrz.common.ui.NetworkDrawableObject;
import org.amityregion5.qxrz.server.world.World;
import org.amityregion5.qxrz.server.world.gameplay.Bullet;
import org.amityregion5.qxrz.server.world.vector2d.Vector2D;

public class ProjectileEntity extends GameEntity
{
	private Bullet gameModel;
	private String asset = "weapons/revolver"; // TODO: change this pls pls pls
	private int projsize = 100; // depending on specific projectile?

	public ProjectileEntity(Vector2D p, Vector2D v, Bullet b)
	{
		super();
		pos = p;
		vel = v;
		gameModel = b;
	}

	public Hitbox getHitbox()
	{
		return new RectangleHitbox(new Rectangle((int) pos.getX() - projsize/2,
				(int) pos.getY() - projsize/2, projsize, projsize));
	}

	@Override
	protected boolean collide(Hitboxed h, World l, Vector2D v)
	{
		return true;
	}

	@Override
	public NetworkDrawableEntity getNDE() {
		return new NetworkDrawableEntity(new NetworkDrawableObject[] {new NetworkDrawableObject(asset, getHitbox().getAABB())}, getHitbox().getAABB());
	}
}
