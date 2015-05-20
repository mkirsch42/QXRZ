package org.amityregion5.qxrz.server.world;

import org.amityregion5.qxrz.server.world.entity.Hitbox;
import org.amityregion5.qxrz.server.world.entity.Hitboxed;

public class Obstacle implements Hitboxed
{

	Hitbox hb;
	
	public Hitbox getHitbox()
	{
		return hb;
	}
	
	public Obstacle(Hitbox h)
	{
		hb = h;
	}

}
