package org.amityregion5.qxrz.server.world.gameplay;

import org.amityregion5.qxrz.server.world.Landscape;
import org.amityregion5.qxrz.server.world.World;
import org.amityregion5.qxrz.server.world.entity.PlayerEntity;
import org.amityregion5.qxrz.server.world.vector2d.Vector2D;

public class SpecialMovement {
	private String type;
	private Landscape la;
	private int timeused;
	private static final int TTELEPORT = 8;
	private static final int TDASH = 6;
	private static final int TROLL = 4;
	public SpecialMovement(String t, World wo)
	{
		type = t;
		la = wo.getLandscape();
	}
	
	public void dash(PlayerEntity p, int call)
	{
		//maybe some animation
		if (call - timeused < TDASH)
		{
			//invalid
		}
		else
		{
			PlayerEntity port = new PlayerEntity(p.getPos().add(p.getVel().subtract(new Vector2D(3,3))),p.getGameModel()); //nonexistent entity to test for the validity of dash
																														   //distance will be 2 units lower than teleport distance												
			if (port.checkCollisions(port.getPos(), la).equals(null)) 
			{
				while (!(p.getPos().equals(port.getPos()))) //until dashed at location
				{
					p.getPos().add(new Vector2D(2,2)); //placeholder
				}
				timeused = (int) System.currentTimeMillis();
			}
			else {
				//invalid
			}
		}
	}
	
	public void roll(PlayerEntity p, int call)
	{
		//maybe some animation
		if (call - timeused < TROLL)
		{
			//invalid
		}
		else
		{
			PlayerEntity port = new PlayerEntity(p.getPos().add(p.getVel().subtract(new Vector2D(3,3))),p.getGameModel()); //nonexistent entity to test for the validity of roll
																														   //distance will be 3 units lower than teleport distance												
			if (port.checkCollisions(port.getPos(), la).equals(null)) 
			{
				while (!(p.getPos().equals(port.getPos()))) //until rolled at location
				{
					p.getPos().add(new Vector2D(1,1)); //placeholder
				}
				timeused = (int) System.currentTimeMillis();
			}
			else {
				//invalid
			}
		}
	}
	public void teleport(PlayerEntity p, int call)
	{
		//no animation for this movement
		if (call - timeused < TTELEPORT)
		{
			//invalid
		}
		else
		{
			PlayerEntity port = new PlayerEntity(p.getPos().add(p.getVel()),p.getGameModel()); //nonexistent entity to test for the validity of teleport
																							   //teleport distance will be the max distance for all special moves
			if (port.checkCollisions(port.getPos(), la).equals(null))
			{
				p = port;
				timeused = (int) System.currentTimeMillis();
			}
			else {
				//invalid
			}
		}	
	}
	
	public String getType()
	{
		return type;
	}
	public boolean equals(SpecialMovement o)
	{
		if (this.getType().equals(o.getType()))
		{
			return true;
		}
		return false;
	}
	public int getTimeUsed()
	{
		return timeused;
	}
}
