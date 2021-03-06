package org.amityregion5.qxrz.server.world.entity;

import java.awt.Rectangle;
import java.util.ArrayList;

import org.amityregion5.qxrz.common.ui.NetworkDrawableEntity;
import org.amityregion5.qxrz.common.ui.NetworkDrawableObject;
import org.amityregion5.qxrz.server.world.World;
import org.amityregion5.qxrz.server.world.gameplay.Pickup;
import org.amityregion5.qxrz.server.world.vector2d.Vector2D;

public class PickupEntity extends GameEntity
{

	private final int HB_SIZE = 250;
	private Pickup parent;

	public PickupEntity(int x, int y, Pickup p)
	{
		super();
		pos = new Vector2D(x, y);
		parent = p;
		vel = new Vector2D();
	}

	public boolean update(double tSinceUpdate, World w)
	{
		boolean ret = false;
		ArrayList<PlayerEntity> e = checkPlayerCollisions(vel, w, -1);
		if (e != null)
		{
			for (PlayerEntity pe : e)
			{
				if (parent.isOnePickup())
					ret = ret | pe.getGameModel().pickup(parent);
				pe.getGameModel().pickup(parent);
			}
		}
		return ret;
	}

	@Override
	public Hitbox getHitbox()
	{
		return new RectangleHitbox(new Rectangle(
				(int) pos.getX() - HB_SIZE / 2, (int) pos.getY() - HB_SIZE / 2,
				HB_SIZE, HB_SIZE));
	}

	@Override
	protected boolean collide(Hitboxed h, World w, Vector2D v)
	{
		if (!(h instanceof PlayerEntity))
			return false;
		PlayerEntity pe = (PlayerEntity) h;
		pe.getGameModel().pickup(parent);
		return false;
	}

	@Override
	public NetworkDrawableEntity getNDE()
	{
		NetworkDrawableObject pickup = new NetworkDrawableObject(
				parent.getAsset(), getHitbox().getAABB());
		NetworkDrawableObject nametag = new NetworkDrawableObject("--STRING--"
				+ parent.getAmmoCount(), getHitbox().getAABB());

		NetworkDrawableObject ndos[] = (parent.canPickup() ? new NetworkDrawableObject[] {
				pickup, nametag }
				: new NetworkDrawableObject[] { pickup });

		NetworkDrawableEntity nde = new NetworkDrawableEntity(ndos, getHitbox()
				.getAABB());
		return nde;
	}

}
