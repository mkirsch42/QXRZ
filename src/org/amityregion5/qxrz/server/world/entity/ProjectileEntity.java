package org.amityregion5.qxrz.server.world.entity;

import java.awt.geom.Rectangle2D;
import org.amityregion5.qxrz.server.world.Landscape;

public abstract class ProjectileEntity extends GameEntity
{
	private final int PROJECTILE_SIZE = 1;
	public ProjectileEntity()
	{
		// TODO Auto-generated constructor stub
	}
	
	public boolean update(double tSinceUpdate, Landscape surroundings)
	{
		return false;
		
	}
	public RectangleHitbox getHitBox()
	{
		return new RectangleHitbox(new Rectangle2D.Double(pos.getX(),pos.getY(),PROJECTILE_SIZE,PROJECTILE_SIZE));
	}

}
