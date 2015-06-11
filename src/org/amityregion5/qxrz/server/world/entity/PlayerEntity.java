package org.amityregion5.qxrz.server.world.entity;

import java.awt.Rectangle;
import java.awt.geom.Rectangle2D;

import org.amityregion5.qxrz.common.control.NetworkInputData;
import org.amityregion5.qxrz.common.control.NetworkInputMasks;
import org.amityregion5.qxrz.common.ui.NetworkDrawableEntity;
import org.amityregion5.qxrz.common.ui.NetworkDrawableObject;
import org.amityregion5.qxrz.common.ui.NetworkDrawablePlayer;
import org.amityregion5.qxrz.server.DebugConstants;
import org.amityregion5.qxrz.server.Game;
import org.amityregion5.qxrz.server.world.Landscape;
import org.amityregion5.qxrz.server.world.World;
import org.amityregion5.qxrz.server.world.gameplay.Player;
import org.amityregion5.qxrz.server.world.gameplay.WeaponTypes;
import org.amityregion5.qxrz.server.world.vector2d.Vector2D;

public class PlayerEntity extends GameEntity
{

	private Vector2D inputVel = new Vector2D();
	
	//private int updateStackSize = 0;
	
	private Player parent;
	
	private String asset = "players/1/walk";
	private String standing = "players/1/stand";

	public final static int PLAYER_SIZE = 400;
	
	public PlayerEntity(Player p) // creates player vector
	{
		super();
		pos = new Vector2D(0, 0);
		vel = new Vector2D(0, 0);
		parent = p;
		//pos = new Vector2D(1500, 2500);
		//pos = new Vector2D(0,0);
		//vel = new Vector2D(200, 100).multiply(DebugConstants.PATH_LEN);
	}

	public PlayerEntity(Vector2D spawn, Player p)
	{
		super();
		pos = spawn;
		vel = new Vector2D(0, 0);
		parent = p;
	}
	
	public boolean update(double tSinceUpdate, World w)
	{
		if(parent.isDead() && w.getGame().getGM().oneLife)
		{
			vel = inputVel;
			pos = pos.add(vel.multiply(tSinceUpdate*2));
			return false;
		}
		parent.getEquipped().update();
		//updateStackSize++;
		boolean ret = super.update(tSinceUpdate, w);
		//updateStackSize--;
		//if(updateStackSize==0)
		{
			vel = inputVel;
		}
		return ret;
	}
	
	public boolean input(NetworkInputData nid)
	{
		int vX = 0;
		int vY = 0;
		if(nid.get(NetworkInputMasks.W))
		{
			vY = -100;
		}
		if(nid.get(NetworkInputMasks.S))
		{
			vY = 100;
		}
		if(nid.get(NetworkInputMasks.A))
		{
			vX = -100;
		}
		if(nid.get(NetworkInputMasks.D))
		{
			vX = 100;
		}
		if(vX==0 && vY==0)
		{
			inputVel = new Vector2D();
		}
		else
		{
			inputVel = new Vector2D(new Vector2D(vX, vY).angle()).multiply(parent.getSpeed());
		}
		vel = inputVel;
		return false;
	}
	
	public RectangleHitbox getHitbox()
	{
		// Create 2x2 square around player
		return new RectangleHitbox(new Rectangle((int) pos.getX() - PLAYER_SIZE
				/ 2, (int) pos.getY() - PLAYER_SIZE / 2, PLAYER_SIZE,
				PLAYER_SIZE));
	}

	public boolean collide(Hitboxed h, World w, Vector2D v)
	{
		Landscape l = w.getLandscape();
		// The stuff left after fully reaching the obstacle
		Vector2D rem = fixCollisionWithVel(v, h, l, false);
		// Obstacle normal
		Vector2D norm = h.getHitbox().getNearestNormal(getHitbox());
		System.out.println(norm);
		// Go a bit into the obstacle
		pos = pos.subtract(norm.multiply(5 * Game.GAME_UNIT));
		// Get the amount you can move along the side
		Vector2D move = rem.project(norm.rotateQuad(1));
		rem = fixCollisionWithVel(move, h, l, true);

		// Get back out of obstacle
		pos = pos.add(norm.multiply(5 * Game.GAME_UNIT));
		// If no more velocity, don't try to spend any more
		if (rem.equals(new Vector2D()))
		{
			pos = pos.subtract(move);
			Vector2D bak = vel;
			vel = move;
			update(1, w);
			vel = bak;
			return false;
		}

		// Backup velocity
		Vector2D bak = vel;
		// Use remaining velocity for this update in the usual direction
		vel = new Vector2D(vel.angle()).multiply(rem.length());
		// Recursively update until there is no more velocity
		update(1, w);
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
		/*if (v.length() < 2 * Game.GAME_UNIT)
		{
			return new Vector2D();
		}*/
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
				//Vector2D b = pos;
				//pos = pos.add(pathTemp);
				//if (getHitbox().intersects(h.getHitbox()))
				//if(l.checkCollisions(getHitbox())!=null)
				if(checkCollisions(pathTemp, l)!=null)
				{
					pathTemp = pathTemp.add(new Vector2D(v.angle())
							.multiply(accuracy));
				}
				else
				{
					pathTemp = pathTemp.subtract(new Vector2D(v.angle())
							.multiply(accuracy));
				}
				//pos = b;
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
		//while (getHitbox().intersects(h.getHitbox()))
		while(!h.getHitbox().canGetNormal(getHitbox()) || getHitbox().intersects(h.getHitbox()))
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
		/*if (unCollide)
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
		}*/
		return v;
	}

	public Player getGameModel()
	{
		return parent;
	}
	
	@Override
	public NetworkDrawableEntity getNDE() {
		if(parent.isDead() && parent.getParent().getGame().getGM().oneLife)
		{
			return new NetworkDrawableEntity(new NetworkDrawableObject[]{},getHitbox().getAABB());
		}
		
		NetworkDrawableObject playerNDO = new NetworkDrawableObject((vel.equals(new Vector2D()) ? standing : asset), getHitbox().getAABB());
		if(parent.isFlipped())
		{
			playerNDO.flipH();
		}
		Rectangle2D r2d = getHitbox().getAABB();
		NetworkDrawableObject gun = new NetworkDrawableObject(WeaponTypes.getTypeFromString(parent.getEquipped().getType()).asset, new Rectangle2D.Double(r2d.getCenterX() - PLAYER_SIZE/4.0, r2d.getCenterY() - PLAYER_SIZE/4.0, PLAYER_SIZE/2.0, PLAYER_SIZE/2.0)).rotate(parent.rotatedAngle());
		double aRef = parent.rotatedAngle()%(Math.PI*2);
		if (Math.abs(aRef) > Math.PI/2) {
			gun.flipV();
		}
		NetworkDrawablePlayer player= new NetworkDrawablePlayer(new NetworkDrawableObject[] {playerNDO, gun}, getHitbox().getAABB());
		player.setNametag(parent.getName());
		player.setAmmo(parent.getEquipped().getInClip());
		player.setGun(parent.getEquipped().getEnumType().fullName);
		player.setTotalAmmo(parent.getEquipped().getReserve());
		player.setHealth(parent.getHealth());
		player.setMaxHealth(100); //TODO: REMOVE MAGIC NUMBER
		
		if(parent.getTeam()==null){
			player.setItalics(true);
		}else {
			player.setNameColor(parent.getTeam().getColor());
		}
		return player;
	}
}
