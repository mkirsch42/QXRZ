package org.amityregion5.qxrz.server.world.entity;

import java.awt.geom.Path2D;
import java.awt.geom.Rectangle2D;

import org.amityregion5.qxrz.server.world.DebugDraw;
import org.amityregion5.qxrz.server.world.Landscape;
import org.amityregion5.qxrz.server.world.Obstacle;
import org.amityregion5.qxrz.server.world.vector2d.Vector2D;

public class PlayerEntity extends GameEntity
{
	
	private final double PLAYER_SIZE = 4;
	private Weapon[] guns = new Weapon[2];
	private int health;
	private int speed;
	
	public PlayerEntity() //creates player vector
	{
		pos = new Vector2D(0,0);
		vel = new Vector2D(2,1).multiply(10);
		health = 100;
		speed = 100;
	}

	public boolean update(double tSinceUpdate, Landscape surroundings) 
	{
		Vector2D bak = pos;
		Path2D.Double path = new Path2D.Double();
		Rectangle2D.Double hb = getHitbox().getBounds();
		
		Vector2D v = vel.multiply(tSinceUpdate);
		
		Vector2D p1 = new Vector2D(hb.getMinX(), hb.getMinY());
		Vector2D p2 = new Vector2D(hb.getMinX(), hb.getMaxY());
		Vector2D p3 = new Vector2D(hb.getMaxX(), hb.getMinY());
		Vector2D p4 = new Vector2D(hb.getMaxX(), hb.getMaxY());
		
		path.moveTo(p1.getX(),p1.getY());
		path.lineTo(p1.add(v).getX(), p1.add(v).getY());
		
		path.moveTo(p2.getX(),p2.getY());
		path.lineTo(p2.add(v).getX(), p2.add(v).getY());
		
		path.moveTo(p3.getX(),p3.getY());
		path.lineTo(p3.add(v).getX(), p3.add(v).getY());
		
		path.moveTo(p4.getX(),p4.getY());
		path.lineTo(p4.add(v).getX(), p4.add(v).getY());
		
		path.append(hb, false);
		pos = pos.add(v);
		path.append(getHitbox().getBounds(), false);
		DebugDraw.buffer.add(path);
		Obstacle o = surroundings.checkCollisions(new ShapeHitbox(path));
		if (o!=null)
		{
			pos = bak;
			System.out.println("Collision!");
			collide(o);
		}
		System.out.println(pos);
		return false;
	}
	
	public RectangleHitbox getHitbox()
	{
		// Create 2x2 square around player
		return new RectangleHitbox(new Rectangle2D.Double((int)pos.getX()-PLAYER_SIZE/2.0, (int)pos.getY()-PLAYER_SIZE/2.0, PLAYER_SIZE, PLAYER_SIZE));
	}

	public boolean collide(Hitboxed h)
	{
		
		return false;
	}
	
	public void increaseStat()
	{
		
	}

}
