package org.amityregion5.qxrz.server.world;

import java.util.ArrayList;
import java.util.List;
import org.amityregion5.qxrz.common.ui.AABBDrawer;
import org.amityregion5.qxrz.common.ui.DrawableObject;
import org.amityregion5.qxrz.common.ui.IObjectDrawer;
import org.amityregion5.qxrz.server.world.entity.Hitbox;
import org.amityregion5.qxrz.server.world.entity.Hitboxed;
import org.amityregion5.qxrz.server.world.vector2d.Vector2D;

public class Obstacle implements Hitboxed, DrawableObject<Obstacle>
{

	private static AABBDrawer<Obstacle> drawer;
	
	Hitbox hb;
	
	public Hitbox getHitbox()
	{
		return hb;
	}
	
	public Obstacle(Hitbox h)
	{
		hb = h;
	}

	public Vector2D getCollidedNormal(Hitboxed h)
	{
		return new Vector2D();
	}

	@Override
	public List<IObjectDrawer<Obstacle>> getDrawers(){
		if (drawer == null) {
			drawer = new AABBDrawer<Obstacle>();
		}
		List<IObjectDrawer<Obstacle>> l = new ArrayList<IObjectDrawer<Obstacle>>();
		l.add(drawer);
		return l;
	}
	
}
