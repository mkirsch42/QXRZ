package org.amityregion5.qxrz.server.world.gameplay;

import java.awt.geom.Path2D;

import org.amityregion5.qxrz.server.world.Landscape;
import org.amityregion5.qxrz.server.world.World;
import org.amityregion5.qxrz.server.world.entity.PlayerEntity;
import org.amityregion5.qxrz.server.world.vector2d.Vector2D;

public class SpecialMovement {
	private String type;
	private Landscape la;
	private int timeused;
	private static final int TELEPORTTIME = 8;
	private static final int DASHTIME = 6;
	private static final int ROLLTIME = 4;
	
	public SpecialMovement(String t, World wo)
	{
		type = t;
		la = wo.getLandscape();
	}
	
	public void dash(PlayerEntity p, int call)
	{
		//maybe some animation
		if (call - timeused < DASHTIME)
		{	/*invalid*/ }
		else
		{
			PlayerEntity port = new PlayerEntity(p.getPos().add(p.getVel().subtract(new Vector2D(3,3))),p.getGameModel()); //nonexistent entity to test for the validity of dash
																														   //distance will be 2 units lower than teleport distance												
			if (port.checkCollisions(port.getPos(), la).equals(null)) 
			{
				//dash assets?
				p.getGameModel().setSpecMoving(true);
				Path2D.Double pa = new Path2D.Double();				   //
				pa.moveTo(port.getPos().getX(), port.getPos().getY()); //i don't know
				p.getGameModel().setSpecMoving(false);				   //
				timeused = (int) System.currentTimeMillis();
			}
			else {/*invalid*/}
		}
	}
	
	public void roll(PlayerEntity p, int call)
	{
		if (call - timeused < ROLLTIME)
		{	/*invalid*/ }
		else
		{
			PlayerEntity port = new PlayerEntity(p.getPos().add(p.getVel().subtract(new Vector2D(3,3))),p.getGameModel()); //nonexistent entity to test for the validity of roll
																														   //distance will be 3 units lower than teleport distance												
			if (port.checkCollisions(port.getPos(), la).equals(null)) 
			{
				//roll assets?
				p.getGameModel().setSpecMoving(true);
				Path2D.Double pa = new Path2D.Double();				   //
				pa.moveTo(port.getPos().getX(), port.getPos().getY()); //i don't know
				timeused = (int) System.currentTimeMillis();		   //
				p.getGameModel().setSpecMoving(false);
			}
			else {/*invalid*/}
		}
	}
	public void teleport(PlayerEntity p, int call)
	{
		//no animation for this movement
		if (call - timeused < TELEPORTTIME)
		{	/*invalid*/ }
		else
		{
			PlayerEntity port = new PlayerEntity(p.getPos().add(p.getVel()),p.getGameModel()); //nonexistent entity to test for the validity of teleport
																							   //teleport distance will be the max distance for all special moves
			if (port.checkCollisions(port.getPos(), la).equals(null))
			{
				p.getGameModel().setSpecMoving(true);
				p = port;
				p.getGameModel().setSpecMoving(false);
				timeused = (int) System.currentTimeMillis();
			}
			else {/*invalid*/}
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
