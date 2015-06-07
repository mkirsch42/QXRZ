package org.amityregion5.qxrz.server.world.entity;

import java.awt.Rectangle;

import org.amityregion5.qxrz.common.ui.NetworkDrawableEntity;
import org.amityregion5.qxrz.common.ui.NetworkDrawableObject;
import org.amityregion5.qxrz.server.world.World;
import org.amityregion5.qxrz.server.world.gameplay.Pickup;
import org.amityregion5.qxrz.server.world.vector2d.Vector2D;

public class PickupEntity extends GameEntity
{

	private final int HB_SIZE = 250;
	private Pickup parent;
	private String assetReload = "icons/refreshDark";
	private String assetReady = "weapons/rifle";
	
	public PickupEntity(int x, int y, Pickup p)
	{
		super();
		pos = new Vector2D(x,y);
		parent = p;
		vel = new Vector2D();
	}
	
	public boolean update(double tSinceUpdate, World w)
	{
		PlayerEntity e = checkPlayerCollisions(vel, w);
		if(e!=null)
			e.getGameModel().pickup(parent);
		return false;
	}
	
	@Override
	public Hitbox getHitbox()
	{
		return new RectangleHitbox(new Rectangle((int) pos.getX() - HB_SIZE
				/ 2, (int) pos.getY() - HB_SIZE / 2, HB_SIZE,
				HB_SIZE));
	}

	@Override
	protected boolean collide(Hitboxed h, World w, Vector2D v)
	{
		if(!(h instanceof PlayerEntity))
			return false;
		PlayerEntity pe = (PlayerEntity)h;
		pe.getGameModel().pickup(parent);
		return false;
	}

	@Override
	public NetworkDrawableEntity getNDE()
	{
		if(parent.canPickup())
			return new NetworkDrawableEntity(new NetworkDrawableObject[] {new NetworkDrawableObject(assetReady, getHitbox().getAABB())}, getHitbox().getAABB());
		return new NetworkDrawableEntity(new NetworkDrawableObject[] {new NetworkDrawableObject(assetReload, getHitbox().getAABB())}, getHitbox().getAABB());
	}

}
