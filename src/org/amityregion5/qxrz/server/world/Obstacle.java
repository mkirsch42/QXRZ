package org.amityregion5.qxrz.server.world;

import org.amityregion5.qxrz.common.ui.NetworkDrawableEntity;
import org.amityregion5.qxrz.common.ui.NetworkDrawableObject;
import org.amityregion5.qxrz.server.world.entity.Hitbox;
import org.amityregion5.qxrz.server.world.entity.Hitboxed;
import org.amityregion5.qxrz.server.world.vector2d.Vector2D;

public class Obstacle implements Hitboxed
{
	
	Hitbox hb;
	private String assName;
	
	public Hitbox getHitbox()
	{
		return hb;
	}
	
	public Obstacle(Hitbox h, String assetName)
	{
		hb = h;
		assName = assetName;
	}

	public Vector2D getCollidedNormal(Hitboxed h)
	{
		return new Vector2D();
	}
	
	public NetworkDrawableEntity getNDE() {
		return new NetworkDrawableEntity(new NetworkDrawableObject[] {new NetworkDrawableObject(assName, getHitbox().getAABB())}, getHitbox().getAABB());
	}
}
