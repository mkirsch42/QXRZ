package org.amityregion5.qxrz.server.world.entity;

import java.io.Serializable;

import org.amityregion5.qxrz.server.world.Landscape;
import org.amityregion5.qxrz.server.world.vector2d.Vector2D;

public abstract class GameEntity implements Hitboxed
{
	// Coordinates and velocities
	protected Vector2D pos;
	protected Vector2D vel;
	
	public abstract boolean update(double tSinceUpdate, Landscape surroundings);
	
	public abstract Hitbox getHitbox();
	
	protected abstract boolean collide(Hitboxed h, Landscape l);
	
	public GameEntity()
	{
		// TODO Auto-generated constructor stub
	}

}
