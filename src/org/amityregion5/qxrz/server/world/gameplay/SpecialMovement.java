package org.amityregion5.qxrz.server.world.gameplay;

import org.amityregion5.qxrz.server.world.Landscape;
import org.amityregion5.qxrz.server.world.World;
import org.amityregion5.qxrz.server.world.entity.PlayerEntity;

public class SpecialMovement {
	private String type;
	private World w;
	private Landscape l = w.getLandscape();
	private int timeused;
	private int tsincelast;
	private SpecMoveStats stat;
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
		
	}
	public void roll(PlayerEntity p)
	{
		
	}
	public void teleport(PlayerEntity p)
	{
		if (tsincelast - timeused < stat.COOLDOWN)
		{
			//nope
		}
		else
		{
			PlayerEntity port = new PlayerEntity(p.getPos().add(p.getVel()),p.getGameModel()); //nonexistent entity to test for the validity of teleport
			if (port.checkCollisions(port.getPos(), l).equals(null))
			{}
			else {
				p = port;
				timeused = (int) System.currentTimeMillis();
			}
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
