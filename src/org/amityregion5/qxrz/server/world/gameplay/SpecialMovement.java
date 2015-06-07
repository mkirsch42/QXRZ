package org.amityregion5.qxrz.server.world.gameplay;

import org.amityregion5.qxrz.server.world.Landscape;
import org.amityregion5.qxrz.server.world.Obstacle;
import org.amityregion5.qxrz.server.world.World;
import org.amityregion5.qxrz.server.world.entity.PlayerEntity;
import org.amityregion5.qxrz.server.world.vector2d.Vector2D;

public class SpecialMovement {
	private String type;
	private static final int TIME = 3; //time for all special moves though cooldown will vary
	private World w;
	private Landscape l = w.getLandscape();
	public SpecialMovement(String t)
	{
		type = t;
	}
	public String getType()
	{
		return type;
	}
	public void dash(PlayerEntity p)
	{
		//some animation for this
		Vector2D dir = p.getVel();

	}
	public void roll(PlayerEntity p)
	{
		//some animation for this
		Vector2D dir = p.getVel();
	}
	public void teleport(PlayerEntity p)
	{
		//some animation for this;
		Vector2D port = p.getVel();
		for (Obstacle o: l.getObstacles())
		{
			//i don't know
		}
	}
	public boolean equals(SpecialMovement o)
	{
		if (this.getType().equals(o.getType()))
		{
			return true;
		}
		return false;
	}
}
