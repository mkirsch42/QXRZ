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
	private Runnable onHitCallback;
	public static int projsize = 100; // depending on specific projectile?

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

	public boolean update(double tSinceUpdate, World w)
	{
		if (gameModel.getType().equals("fl")) {
			setVel(getVel().multiply(0.9));
			if (getVel().equals(new Vector2D())) {
				gameModel.getSource().getGameModel().getParent().removeEntity(this);
			}
		}
		PlayerEntity e = checkPlayerCollisions(vel.multiply(tSinceUpdate), w, gameModel.getFriendlyFirePlayer());
		if(e==null)
			return super.update(tSinceUpdate, w);
		//System.out.println("ProjectileEntity #" + getId() + " collided with Entity #" + e.getId());
		boolean ret = super.update(tSinceUpdate, w);
		return ((PlayerEntity)e).getGameModel().damaged(gameModel) || ret;
	}
	
	@Override
	protected boolean collide(Hitboxed h, World l, Vector2D v)
	{
		if (onHitCallback != null) onHitCallback.run();
		return true;
	}

	@Override
	public NetworkDrawableEntity getNDE() {
		String asset = "projectiles/bullet";
		if(gameModel.getType().equals("ro"))
		{
			asset = "projectiles/rocket";
		}
		if(gameModel.getType().equals("bo"))
		{
			asset = "projectiles/arrow";
		}
		if(gameModel.getType().equals("fl")) {
			asset = "projectiles/fire";
		}
		return new NetworkDrawableEntity(new NetworkDrawableObject[] {new NetworkDrawableObject(asset, getHitbox().getAABB()).rotate(vel.angle())}, getHitbox().getAABB());
	}
	
	
	public void setOnHitCallback(Runnable onHitCallback) {
		this.onHitCallback = onHitCallback;
	}
}
