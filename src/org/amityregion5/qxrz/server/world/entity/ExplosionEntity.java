package org.amityregion5.qxrz.server.world.entity;

import java.util.ArrayList;

import org.amityregion5.qxrz.common.ui.NetworkDrawableEntity;
import org.amityregion5.qxrz.common.ui.NetworkDrawableObject;
import org.amityregion5.qxrz.server.world.World;
import org.amityregion5.qxrz.server.world.vector2d.Vector2D;

public class ExplosionEntity extends GameEntity
{

	private final int GROWTHPERUPDATE = 100;
	private final int MAXRADIUS = 1000;
	private final int BASEDMG = 50;
	private final int DMGFALLOFFUNIT = 100;
	private final double DMGFALLOFF = 0.9;
	private final int STARTRADIUS = 100;

	private Vector2D pos;
	private int r;
	private ArrayList<Integer> hitPlayers;

	public ExplosionEntity(int x, int y)
	{
		pos = new Vector2D(x, y);
		r = STARTRADIUS;
		hitPlayers = new ArrayList<Integer>();
	}

	@Override
	public Hitbox getHitbox()
	{
		return new CircleHitbox((int) pos.getX(), (int) pos.getY(), r);
	}

	@Override
	public boolean update(double tSinceUpdate, World w)
	{
		if (r > MAXRADIUS)
			return true;
		r += GROWTHPERUPDATE;
		ArrayList<PlayerEntity> e = w.checkPlayerCollisions(getHitbox(), -1);
		if (e == null)
			return false;
		for (PlayerEntity pe : e)
		{
			if (hitPlayers.contains(new Integer(pe.getId())))
			{
				continue;
			}
			hitPlayers.add(new Integer(pe.getId()));
			Vector2D dist = pos.subtract(pe.getPos());
			double len = dist.length();
			double dmg = BASEDMG * Math.pow(DMGFALLOFF, len / DMGFALLOFFUNIT);
			System.out.println("Explosion caused " + dmg
					+ " damage to player #" + pe.getId());
			pe.getGameModel().explosion((int) (dmg + 0.5));
		}
		return false;
	}

	@Override
	protected boolean collide(Hitboxed h, World w, Vector2D v)
	{
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public NetworkDrawableEntity getNDE()
	{
		return new NetworkDrawableEntity(
				new NetworkDrawableObject[] { new NetworkDrawableObject(
						"explosion/" + Math.min((r * 12) / MAXRADIUS, 12),
						getHitbox().getAABB()) }, getHitbox().getAABB());
	}

}
