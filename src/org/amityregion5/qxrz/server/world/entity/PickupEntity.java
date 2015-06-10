package org.amityregion5.qxrz.server.world.entity;

import java.awt.Color;
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
	
	public PickupEntity(int x, int y, Pickup p)
	{
		super();
		pos = new Vector2D(x,y);
		parent = p;
		vel = new Vector2D();
	}
	
	public boolean update(double tSinceUpdate, World w)
	{
		PlayerEntity e = checkPlayerCollisions(vel, w, -1);
		if(e!=null)
		{
			if(parent.isOnePickup())
				return e.getGameModel().pickup(parent);
			e.getGameModel().pickup(parent);
		}
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
		NetworkDrawableEntity nde = new NetworkDrawableEntity(new NetworkDrawableObject[] {new NetworkDrawableObject(parent.getAsset(), getHitbox().getAABB())}, getHitbox().getAABB());
		if(parent.canPickup())
			nde.setNametag(""+parent.getAmmoCount(), Color.BLACK);
		return nde;
	}

}
