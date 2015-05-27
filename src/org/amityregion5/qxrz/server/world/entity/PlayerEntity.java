package org.amityregion5.qxrz.server.world.entity;

import java.awt.Rectangle;
import java.awt.geom.Path2D;
import java.awt.geom.Rectangle2D;
import java.util.ArrayList;
import java.util.List;
import org.amityregion5.qxrz.common.ui.AABBDrawer;
import org.amityregion5.qxrz.common.ui.AssetDrawer;
import org.amityregion5.qxrz.common.ui.DrawableObject;
import org.amityregion5.qxrz.common.ui.IObjectDrawer;
import org.amityregion5.qxrz.server.DebugConstants;
import org.amityregion5.qxrz.server.Game;
import org.amityregion5.qxrz.server.world.DebugDraw;
import org.amityregion5.qxrz.server.world.Landscape;
import org.amityregion5.qxrz.server.world.Obstacle;
import org.amityregion5.qxrz.server.world.vector2d.Vector2D;

public class PlayerEntity extends GameEntity implements
		DrawableObject<PlayerEntity>
{

	private static List<IObjectDrawer<PlayerEntity>> drawers;

<<<<<<< HEAD
	private final double PLAYER_SIZE = 4;
=======
	private final int PLAYER_SIZE = 400;
	private Weapon[] guns = new Weapon[2];
	private int health;
	private int speed;
>>>>>>> branch 'master' of https://github.com/mkirsch42/QXRZ.git

	public PlayerEntity() // creates player vector
	{
<<<<<<< HEAD
		pos = new Vector2D(0, 0);
		vel = new Vector2D(2, 1).multiply(DebugConstants.PATH_LEN);
=======
		//pos = new Vector2D(1500, 2500);
		pos = new Vector2D(0,0);
		vel = new Vector2D(200, 100).multiply(DebugConstants.PATH_LEN);
		health = 100;
		speed = 100;
>>>>>>> branch 'master' of https://github.com/mkirsch42/QXRZ.git
	}

	public boolean update(double tSinceUpdate, Landscape surroundings)
	{
		// System.out.println(pos);
		Obstacle o = checkCollisions(vel.multiply(tSinceUpdate), surroundings);
		if (o != null)
		{
			if (DebugConstants.DEBUG_PATH)
			{
				Game.debug.buffer.add(((RectangleHitbox) o.getHitbox())
						.getBounds());
			}
			collide(o, surroundings, vel.multiply(tSinceUpdate));
		}
		else
		{
			pos = pos.add(vel.multiply(tSinceUpdate));
		}
		// System.out.println(pos);
		return false;
	}

	public RectangleHitbox getHitbox()
	{
		// Create 2x2 square around player
		return new RectangleHitbox(new Rectangle((int) pos.getX() - PLAYER_SIZE
				/ 2, (int) pos.getY() - PLAYER_SIZE / 2, PLAYER_SIZE,
				PLAYER_SIZE));
	}

	public Obstacle checkCollisions(Vector2D v, Landscape surroundings)
	{
		Vector2D bak = pos;
		Path2D.Double path = new Path2D.Double();
		Rectangle hb = getHitbox().getBounds();

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
		path.append(getHitbox().getBounds(), false);
		if (DebugConstants.DEBUG_PATH)
			DebugDraw.buffer.add(path);
		Obstacle o = surroundings.checkCollisions(new ShapeHitbox(path));
		pos = bak;
		return o;
	}

	public boolean collide(Hitboxed h, Landscape l, Vector2D v)
	{
		// The stuff left after fully reaching the obstacle
		Vector2D rem = fixCollisionWithVel(v, h, l, false);
		// Obstacle normal
		Vector2D norm = h.getHitbox().getNearestNormal(getHitbox());
		// Go a bit into the obstacle
		pos = pos.subtract(norm.multiply(5 * Game.GAME_UNIT));
		// Get the amount you can move along the side
		Vector2D move = rem.project(norm.rotateQuad(1)).snap();
		rem = fixCollisionWithVel(move, h, l, true);

		// Get back out of obstacle
		pos = pos.add(norm.multiply(5 * Game.GAME_UNIT));
		// If no more velocity, don't try to spend any more
		if (rem.equals(new Vector2D()))
			return false;

		// Backup velocity
		Vector2D bak = vel;
		// Use remaining velocity for this update in the usual direction
		vel = new Vector2D(vel.angle()).multiply(rem.length());
		// Recursively update until there is no more velocity
		update(1, l);
		// Restore velocity
		vel = bak;
		return false;
	}

	public Vector2D fixCollisionWithVel(Vector2D v, Hitboxed h, Landscape l,
			boolean unCollide)
	{
		// if(unCollide)
		// System.out.println(v);
		pos = pos.add(v);
		if (unCollide && getHitbox().intersects(h.getHitbox()))
		{
			return new Vector2D();
		}
		pos = pos.subtract(v);
		if (unCollide)
		{
			// System.out.println("I can do it!");
			// System.out.println("Yes you can!");

		}
		if (v.length() < 2 * Game.GAME_UNIT)
		{
			return new Vector2D();
		}
		Vector2D pathTemp = v.multiply(0.5);
		double accuracy = pathTemp.length() * 0.5;
		while (accuracy > Game.GAME_UNIT)
		{
			if (unCollide)
			{
				if (DebugConstants.DEBUG_PATH)
				{
					checkCollisions(pathTemp, l);
				}
				Vector2D b = pos;
				pos = pos.add(pathTemp);
				if (getHitbox().intersects(h.getHitbox()))
				{
					pathTemp = pathTemp.add(new Vector2D(v.angle())
							.multiply(accuracy));
				}
				else
				{
					pathTemp = pathTemp.subtract(new Vector2D(v.angle())
							.multiply(accuracy));
				}
				pos = b;
			}
			else
			{
				if (!unCollide && checkCollisions(pathTemp, l) != null)
				{
					pathTemp = pathTemp.subtract(new Vector2D(v.angle())
							.multiply(accuracy));
				}
				else
				{
					pathTemp = pathTemp.add(new Vector2D(v.angle())
							.multiply(accuracy));
				}
			}
			accuracy *= 0.5;
			if (DebugConstants.DEBUG_PATH)
			{
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
		}
		if (pathTemp.length() < 2 * Game.GAME_UNIT)
		{
			pathTemp = new Vector2D();
		}
		v = v.subtract(pathTemp);
		pos = pos.add(pathTemp);
		while (getHitbox().intersects(h.getHitbox()))
		{
			if (unCollide)
			{
				Vector2D t = new Vector2D(v.angle()).multiply(Game.GAME_UNIT);
				v = v.subtract(t);
				pos = pos.add(t);
			}
			else
			{
				Vector2D t = new Vector2D(v.angle()).multiply(Game.GAME_UNIT);
				v = v.add(t);
				pos = pos.subtract(t);
			}
		}
		if (unCollide)
		{
			Vector2D t = new Vector2D(v.angle()).multiply(Game.GAME_UNIT);
			v = v.subtract(t);
			pos = pos.add(t);
		}
		else
		{
			Vector2D t = new Vector2D(v.angle()).multiply(Game.GAME_UNIT);
			v = v.add(t);
			pos = pos.subtract(t);
		}
		return v;
	}

	public int getHealth()
	{
		return health;
	}

	public void damaged(ProjectileEntity bullet)
	{
		if (this.getHitbox().intersects(bullet.getHitBox()))
			health -= bullet.getDamage();
	}

	@Override
	public List<IObjectDrawer<PlayerEntity>> getDrawers()
	{
		if (drawers == null)
		{
			drawers = new ArrayList<IObjectDrawer<PlayerEntity>>();
			drawers.add(new AssetDrawer<PlayerEntity>("weapons/*"));
			drawers.add(new AABBDrawer<PlayerEntity>());
		}
		return drawers;
	}
}
