package org.amityregion5.qxrz.server.world.gameplay;

import org.amityregion5.qxrz.server.world.entity.PlayerEntity;
import org.amityregion5.qxrz.server.world.entity.ProjectileEntity;
import org.amityregion5.qxrz.server.world.vector2d.Vector2D;

public class Bullet
{
	private String type;
	private int damage;
	private ProjectileEntity entity;
	private PlayerEntity source; // tracking score
	private int teamId = -1;
	private int sourceId = -1;
	private int speed;

	// constructors
	public Bullet(PlayerEntity source)
	{
		this(new Vector2D(), new Vector2D(), new Weapon("ps",
				source.getGameModel()), source);
	}

	public Bullet(Weapon wep, PlayerEntity source)
	{
		this(new Vector2D(), new Vector2D(), wep, source);
		damage = wep.getDamage();
		type = wep.getType();
		speed = wep.getSpeed();
		entity = new ProjectileEntity(new Vector2D(), new Vector2D(), this);
	}

	public Bullet(Vector2D pos, Vector2D vel, boolean isKnife,
			PlayerEntity source)
	{
		this(pos, vel, new Weapon("ps", source.getGameModel()), source);
		if (isKnife)
		{
			damage = 50;
			type = "kn";
			speed = PlayerEntity.PLAYER_SIZE;
			entity = new ProjectileEntity(pos,
					new Vector2D(vel.angle()).multiply(speed), this);
		}
	}

	public Bullet(Vector2D pos, Vector2D vel, Weapon wep, PlayerEntity source)
	{
		damage = wep.getDamage();
		type = wep.getType();
		speed = wep.getSpeed();
		entity = new ProjectileEntity(pos,
				new Vector2D(vel.angle()).multiply(wep.getSpeed()), this);
	}

	public void setFriendlyFireTeam(Team t)
	{
		teamId = t.getId();
	}

	public int friendlyFireTeam()
	{
		return teamId;
	}

	public void setFriendlyFirePlayer(Player p)
	{
		sourceId = p.getId();
	}

	public int getFriendlyFirePlayer()
	{
		return sourceId;
	}

	public int getDamage()
	{
		return damage;
	}

	public String getType()
	{
		return type;
	}

	public ProjectileEntity getEntity()
	{
		return entity;
	}

	public int getSpeed()
	{
		return speed;
	}

	public PlayerEntity getSource()
	{
		return source;
	}

	public void setSource(PlayerEntity p)
	{
		source = p;
	}
}
