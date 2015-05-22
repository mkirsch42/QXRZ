package org.amityregion5.qxrz.server.world.entity;

import org.amityregion5.qxrz.server.world.Landscape;

public abstract class ProjectileEntity extends GameEntity
{

	public ProjectileEntity(PlayerEntity source)
	{
		pos = source.pos;
		vel = source.vel;
	}
	
	public boolean update(double tSinceUpdate, Landscape surroundings)
	{
		//
		return false;
	}

}
