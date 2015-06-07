package org.amityregion5.qxrz.server.world.entity;

import java.awt.geom.Path2D;
import java.awt.geom.Rectangle2D;

import org.amityregion5.qxrz.common.ui.NetworkDrawableEntity;
import org.amityregion5.qxrz.server.DebugConstants;
import org.amityregion5.qxrz.server.Game;
import org.amityregion5.qxrz.server.world.DebugDraw;
import org.amityregion5.qxrz.server.world.Landscape;
import org.amityregion5.qxrz.server.world.Obstacle;
import org.amityregion5.qxrz.server.world.World;
import org.amityregion5.qxrz.server.world.vector2d.Vector2D;

public abstract class GameEntity implements Hitboxed
{
	// Coordinates and velocities
	protected Vector2D pos;
	protected Vector2D vel;
	private int id;
	private static int lastId=0;
	
	public GameEntity()
	{
		id = lastId++;
	}
	
	public boolean update(double tSinceUpdate, World w)
	{
		Landscape surroundings = w.getLandscape();
		// System.out.println(pos);
		Obstacle o = checkCollisions(vel.multiply(tSinceUpdate), surroundings);
		if (o != null)
		{
			System.out.println("Entity #" + id + " collided");
			if (DebugConstants.DEBUG_PATH)
			{
				DebugDraw.buffer.add(((RectangleHitbox) o.getHitbox())
						.getBounds());
			}
			return collide(o, w, vel.multiply(tSinceUpdate));
		}
		else
		{
			pos = pos.add(vel.multiply(tSinceUpdate));
		}
		// System.out.println(pos);
		Game.debug.pos(pos);
		return false;
	}
	
	protected abstract boolean collide(Hitboxed h, World w, Vector2D v);

	public Obstacle checkCollisions(Vector2D v, Landscape surroundings)
	{
		Vector2D bak = pos;
		Path2D.Double path = new Path2D.Double();
		Rectangle2D hb = getHitbox().getAABB();

		Vector2D p1 = new Vector2D(hb.getMinX(), hb.getMinY());
		Vector2D p2 = new Vector2D(hb.getMinX(), hb.getMaxY());
		Vector2D p3 = new Vector2D(hb.getMaxX(), hb.getMinY());
		Vector2D p4 = new Vector2D(hb.getMaxX(), hb.getMaxY());

		path.moveTo(p1.getX(), p1.getY());
		path.lineTo(p1.add(v).getX(), p1.add(v).getY());

		path.moveTo(p2.getX(), p2.getY());
		path.lineTo(p2.add(v).getX(), p2.add(v).getY());

		path.moveTo(p3.getX(), p3.getY());
		path.lineTo(p3.add(v).getX(), p3.add(v).getY());

		path.moveTo(p4.getX(), p4.getY());
		path.lineTo(p4.add(v).getX(), p4.add(v).getY());

		path.append(hb, false);
		pos = pos.add(v);
		path.append(getHitbox().getAABB(), false);
		if (DebugConstants.DEBUG_PATH)
			DebugDraw.buffer.add(path);
		Obstacle o = surroundings.checkCollisions(new ShapeHitbox(path));
		pos = bak;
		return o;
	}

	public GameEntity checkEntityCollisions(Vector2D v, World surroundings)
	{
		Vector2D bak = pos;
		Path2D.Double path = new Path2D.Double();
		Rectangle2D hb = getHitbox().getAABB();

		Vector2D p1 = new Vector2D(hb.getMinX(), hb.getMinY());
		Vector2D p2 = new Vector2D(hb.getMinX(), hb.getMaxY());
		Vector2D p3 = new Vector2D(hb.getMaxX(), hb.getMinY());
		Vector2D p4 = new Vector2D(hb.getMaxX(), hb.getMaxY());

		path.moveTo(p1.getX(), p1.getY());
		path.lineTo(p1.add(v).getX(), p1.add(v).getY());

		path.moveTo(p2.getX(), p2.getY());
		path.lineTo(p2.add(v).getX(), p2.add(v).getY());

		path.moveTo(p3.getX(), p3.getY());
		path.lineTo(p3.add(v).getX(), p3.add(v).getY());

		path.moveTo(p4.getX(), p4.getY());
		path.lineTo(p4.add(v).getX(), p4.add(v).getY());

		path.append(hb, false);
		pos = pos.add(v);
		path.append(getHitbox().getAABB(), false);
		if (DebugConstants.DEBUG_PATH)
			DebugDraw.buffer.add(path);
		GameEntity o = surroundings.checkEntityCollisions(new ShapeHitbox(path),id);
		pos = bak;
		return o;
	}
	
	public PlayerEntity checkPlayerCollisions(Vector2D v, World surroundings)
	{
		Vector2D bak = pos;
		Path2D.Double path = new Path2D.Double();
		Rectangle2D hb = getHitbox().getAABB();

		Vector2D p1 = new Vector2D(hb.getMinX(), hb.getMinY());
		Vector2D p2 = new Vector2D(hb.getMinX(), hb.getMaxY());
		Vector2D p3 = new Vector2D(hb.getMaxX(), hb.getMinY());
		Vector2D p4 = new Vector2D(hb.getMaxX(), hb.getMaxY());

		path.moveTo(p1.getX(), p1.getY());
		path.lineTo(p1.add(v).getX(), p1.add(v).getY());

		path.moveTo(p2.getX(), p2.getY());
		path.lineTo(p2.add(v).getX(), p2.add(v).getY());

		path.moveTo(p3.getX(), p3.getY());
		path.lineTo(p3.add(v).getX(), p3.add(v).getY());

		path.moveTo(p4.getX(), p4.getY());
		path.lineTo(p4.add(v).getX(), p4.add(v).getY());

		path.append(hb, false);
		pos = pos.add(v);
		path.append(getHitbox().getAABB(), false);
		if (DebugConstants.DEBUG_PATH)
			DebugDraw.buffer.add(path);
		PlayerEntity o = surroundings.checkPlayerCollisions(new ShapeHitbox(path),id);
		pos = bak;
		return o;
	}
	
	public Vector2D getPos()
	{
		return pos;
	}
	
	public Vector2D getVel()
	{
		return vel;
	}
	
	public int getId()
	{
		return id;
	}
	
	public boolean equals(Object o)
	{
		if(!(o instanceof GameEntity))
		{
			return false;
		}
		return ((GameEntity)o).getId() == id;
	}
	
	public void remove(){}

	public abstract NetworkDrawableEntity getNDE();
}
