package org.amityregion5.qxrz.server.world.entity;

import java.awt.geom.Path2D;
import java.awt.geom.Rectangle2D;

import org.amityregion5.qxrz.server.Game;
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
		vel = new Vector2D(2,1).multiply(1);
		health = 100;
		speed = 100;
	}

	public boolean update(double tSinceUpdate, Landscape surroundings) 
	{
		Obstacle o = checkCollisions(vel.multiply(tSinceUpdate),surroundings);
		if (o!=null)
		{
			System.out.println("Collision!");
			System.out.println(((RectangleHitbox)o.getHitbox()).getBounds());
			collide(o, surroundings);
		}
		else
		{
			pos = pos.add(vel);
		}
		//System.out.println(pos);
		return false;
	}
	
	public RectangleHitbox getHitbox()
	{
		// Create 2x2 square around player
		return new RectangleHitbox(new Rectangle2D.Double((int)pos.getX()-PLAYER_SIZE/2.0, (int)pos.getY()-PLAYER_SIZE/2.0, PLAYER_SIZE, PLAYER_SIZE));
	}

	public Obstacle checkCollisions(Vector2D v, Landscape surroundings)
	{
		Vector2D bak = pos;
		Path2D.Double path = new Path2D.Double();
		Rectangle2D.Double hb = getHitbox().getBounds();
		
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
		pos = bak;
		return o;
	}
	
	public boolean collide(Hitboxed h, Landscape l)
	{
		fixCollisionWithVel(vel, h, l, false);
		return false;
	}

	public Vector2D fixCollisionWithVel(Vector2D v, Hitboxed h, Landscape l, boolean unCollide)
	{
		if (v.length()<2*Game.GAME_UNIT)
		{
			return new Vector2D();
		}
		Vector2D pathTemp = v.multiply(0.5);
		double accuracy = pathTemp.length()*0.5;
		while(accuracy>Game.GAME_UNIT)
		{
			if(checkCollisions(pathTemp, l)!=null)
			{
				pathTemp = pathTemp.subtract(new Vector2D(v.angle()).multiply(accuracy));
			}
			else
			{
				pathTemp = pathTemp.add(new Vector2D(v.angle()).multiply(accuracy));
			}
			accuracy *= 0.5;
			// debug drawing
			Game.debug.draw();
			try
			{
				Thread.sleep(250);
			} catch (InterruptedException e)
			{
				e.printStackTrace();
			}
		}
		if(pathTemp.length()<2*Game.GAME_UNIT)
		{
			pathTemp = new Vector2D();
		}
		v = v.subtract(pathTemp);
		pos = pos.add(pathTemp);
		if(getHitbox().intersects(h.getHitbox()))
		{
			Vector2D t = new Vector2D(v.angle()).multiply(Game.GAME_UNIT*2);
			v = v.add(t);
			pos = pos.subtract(t);
		}
		return v;
	}
	
	public void increaseStat()
	{
		
	}

}
