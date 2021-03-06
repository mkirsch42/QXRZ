package org.amityregion5.qxrz.server.world.gameplay;

import java.awt.Color;
import java.util.Random;

import org.amityregion5.qxrz.common.audio.AudioMessage;
import org.amityregion5.qxrz.common.control.NetworkInputData;
import org.amityregion5.qxrz.common.control.NetworkInputMasks;
import org.amityregion5.qxrz.server.world.World;
import org.amityregion5.qxrz.server.world.entity.ExplosionEntity;
import org.amityregion5.qxrz.server.world.entity.PlayerEntity;
import org.amityregion5.qxrz.server.world.vector2d.Vector2D;

public class Player
{
	private static int lastId = 0;
	private int id;
	private int health;
	private int speed;
	private Weapon[] guns = { new Weapon("ps", this), new Weapon(this) };
	private int equipped; // index for currently equipped weapon
	private Upgrade pupgr; // player upgrade
	private SpecialMovement psmove; // player special movement
	private PlayerEntity entity; // physics entity for player
	private World w;
	private String name;
	private Team team;
	private boolean pickingUp = false;
	private boolean dead = false;
	private int score;
	private boolean specmoving;
	private NetworkInputData downs;
	private boolean ready;

	// constructors
	public Player(int forceId)
	{
		id = forceId;
	}

	public Player(World parent, String n) // creates a newly spawned player
	{
		id = lastId++;
		guns[0] = new Weapon("ps", this);
		health = 100;
		speed = 600;
		entity = new PlayerEntity(this);
		w = parent;
		name = n;
		w.say(name + " joined");
		downs = new NetworkInputData();
	}

	public Player(Upgrade u, World parent, String n) // creates a newly spawned
														// player with a weapon
														// upgrade
	{
		this(parent, n);
		pupgr = u;
	}

	public Player(PlayerEntity spawn, World parent, String n) // spawned at a
																// given entity
	{
		guns[0] = new Weapon(this);
		health = 100;
		speed = 600;
		entity = spawn;
		w = parent;
		name = n;
		w.say(name + " joined");
		downs = new NetworkInputData();
	}

	public Player(Upgrade u, PlayerEntity spawn, World parent, String n) // previous
																			// two
																			// constructors
																			// in
																			// one
	{
		this(spawn, parent, n);
		pupgr = u;
	}

	public void joinTeam(Team t)
	{
		t.join(this);
		team = t;
	}

	public void leaveTeam()
	{
		if (team != null)
			team.leave(this);
		team = null;
	}

	public boolean damaged(Bullet b) // tests if a given bullet hits player and
										// acts accordingly
	{
		if (w.getGame().getGM().oneLife && dead)
		{
			return false;
		}
		if (this.isSpecMoving())
		{
			return false;
		}
		if (team != null && b.friendlyFireTeam() == team.getId()
				&& !w.getGame().friendlyFire())
			return false;
		if (b.getFriendlyFirePlayer() == id)
			return false;
		if (b.getEntity().getHitbox().intersects(entity.getHitbox()))
			health -= b.getDamage();
		dead();
		if (isDead())
		{
			b.getSource().getGameModel().addScore();
		} // gives player a point
		// w.win(w.getGame().getGM());
		return true;
	}

	public boolean hurtme(int dmg)
	{
		if (w.getGame().getGM().oneLife && dead)
		{
			return false;
		}
		health -= dmg;
		dead();
		return true;
	}

	public Color getColor()
	{
		if (team == null)
		{
			return Color.GRAY;
		}
		return team.getColor();
	}

	public boolean dead() // tests for death
	{
		if (!(health <= 0))
		{
			return false;
		}
		// respawn code to add later

		dead = true;
		w.say(name + " died");
		respawn(!w.getGame().getGM().oneLife);
		return true;
	}

	public boolean isDead()
	{
		return dead;
	}

	public void respawn(boolean revive)
	{
		if (revive)
		{
			dead = false;
		}
		health = 100;
		w.removeEntity(entity);
		entity = new PlayerEntity(this);
		w.add(entity);
		guns[0] = new Weapon("ps", this);
		guns[1] = new Weapon(this);
		equipped = 0;
	}

	public void setWepUpgrade(Upgrade u) // sets weapon upgrades from a given
											// upgrade pickup
	{
		if (!(u.equals(null)))
		{
			if (pupgr.equals(null) || !pupgr.equals(u))
			{
				pupgr = u;
				switch (u.getType())
				{
				case "maxclips":
					guns[equipped].upMaxClips();
				case "rof":
					guns[equipped].upROF();
				case "cmax":
					guns[equipped].upCMax();
				}
			}
		} else
		{
		}
		;
	}

	public void equip(int input) // changes currently equipped weapon
	{
		equipped = input - 1; // assuming weapon keys for user will be 1 and 2
								// respectively
	}

	public void shoot(Vector2D v) // shoots currently equipped weapon and
									// creates a respective bullet
	{
		if (getEquipped().getType().equals(WeaponTypes.KNIFE.text)
				|| getEquipped().getInClip() + getEquipped().getReserve() == 0)
		{
			stab(v);
			return;
		}
		if (guns[equipped].shoot())
		{
			w.addSound(new AudioMessage(
					entity.getPos().toIntPoint(),
					WeaponTypes.getTypeFromString(getEquipped().getType()).sound,
					true));
			if (getEquipped().getType().equals(WeaponTypes.SHOTGUN.text))
			{
				SpecialWeapons.shotgun(w, this, v);
			} else if (getEquipped().getType().equals(WeaponTypes.FIREGUN.text))
			{
				SpecialWeapons.firegun(w, this, v);
			} else
			{
				Bullet b = new Bullet(entity.getPos(), v, guns[equipped],
						entity);
				if (team != null)
					b.setFriendlyFireTeam(team);
				b.setFriendlyFirePlayer(this);
				b.setSource(entity);
				if (guns[equipped].getEnumType() == WeaponTypes.ROCKETGUN)
				{
					b.getEntity()
							.setOnHitCallback(
									() ->
									{
										int x = b.getEntity().getPos()
												.toIntPoint().x;
										int y = b.getEntity().getPos()
												.toIntPoint().y;
										w.addSound(new AudioMessage(b
												.getEntity().getPos()
												.toIntPoint(), "explode", true));
										w.add(new ExplosionEntity(x, y));
									});
				}
				w.add(b.getEntity());
			}
		}
	}

	public void useHealthpack() // increase health by using health pack
	{
		if (health >= 50 && health != 100)
			health = 100;
		health += 50;
	}

	public void setSpecMove(SpecialMovement sm)
	{
		psmove = sm;
	}

	public SpecialMovement getSpecMove()
	{
		if (psmove == null)
		{
			setSpecMove(new SpecialMovement(SpecialMovements.DASH, getParent()));
		}
		return psmove;
	}

	public void useSpecMove()
	{
		switch (psmove.getType())
		{
		case ROLL:
			psmove.roll(entity, System.currentTimeMillis());
			break;
		case TELEPORT:
			psmove.teleport(entity, System.currentTimeMillis());
			break;
		case DASH:
			psmove.dash(entity, System.currentTimeMillis());
			break;
		}
	}

	public void input(NetworkInputData nid)
	{
		entity.input(nid);
		if (dead && w.getGame().getGM().oneLife)
			return;
		if (nid.get(NetworkInputMasks.M1)
				&& !getEquipped().cooling()
				&& (getEquipped().getEnumType().auto || !downs
						.get(NetworkInputMasks.M1)))
		{
			Vector2D v = new Vector2D(nid.getMouseX(), nid.getMouseY())
					.subtract(entity.getPos());
			shoot(v);
		}
		if (nid.get(NetworkInputMasks.R) && !downs.get(NetworkInputMasks.R))
		{
			getEquipped().reload();
		}
		if (nid.get(NetworkInputMasks.Q) && !downs.get(NetworkInputMasks.Q))
		{
			equipped = (~equipped) & 1; // bc why not
			getParent().addSound(
					new AudioMessage(getEntity().getPos().toIntPoint(),
							"weaponswitch", true));
		}
		if (nid.get(NetworkInputMasks.M2) && !downs.get(NetworkInputMasks.M2))
		{
			Vector2D v = new Vector2D(nid.getMouseX(), nid.getMouseY())
					.subtract(entity.getPos());
			stab(v);
		}
		if (nid.get(NetworkInputMasks.SPACE))
		{
			useSpecMove();
		}
		pickingUp = nid.get(NetworkInputMasks.E);
		downs = nid;
	}

	public PlayerEntity getEntity()
	{
		return entity;
	}

	public int getId()
	{
		return id;
	}

	public boolean equals(Object obj)
	{
		if (!(obj instanceof Player))
		{
			return false;
		}
		return ((Player) obj).getId() == id;
	}

	public String getName()
	{
		return name;
	}

	public Team getTeam()
	{
		return team;
	}

	public boolean pickup(Pickup p)
	{
		if (isDead())
		{
			return false;
		}
		if (!p.canPickup())
		{
			return false;
		}
		if (p.isHealth())
		{
			if (health >= 100)
			{
				health = 100;
				return false;
			}
			health += p.getAmmoCount();
			if (health >= 100)
			{
				health = 100;
			}
			p.pickup();
			return true;
		}
		if (p.isWepUpgrade())
		{
			setWepUpgrade(p.getUp());
		}
		String wep = p.getWeaponId();
		if (guns[0] != null && guns[0].getType().equals(wep))
		{
			System.out.println("Adding ammo to gun 0");
			if (!guns[0].addAmmo(p.getAmmoCount()))
				return false;
		} else if (guns[1] != null && guns[1].getType().equals(wep))
		{
			if (!guns[1].addAmmo(p.getAmmoCount()))
				return false;
		} else if (pickingUp)
		{
			guns[equipped] = new Weapon(wep, this);
			guns[equipped].setAmmo(p.getAmmoCount());
		} else
		{
			return false;
		}
		p.pickup();
		return true;
	}

	public double getSpeed()
	{
		return speed;
	}

	public World getParent()
	{
		return w;
	}

	public int getScore()
	{
		return score;
	}

	public int getHealth()
	{
		return health;
	}

	public Weapon getEquipped()
	{
		return guns[equipped];
	}

	private void stab(Vector2D v)
	{
		Bullet b = new Bullet(entity.getPos(), v, true, entity);
		if (team != null)
			b.setFriendlyFireTeam(team);
		b.setFriendlyFirePlayer(this);
		b.setSource(entity);
		b.getEntity().update(1, w);
	}

	public String getNT()
	{
		if (getEquipped().getType().equals(WeaponTypes.KNIFE.text))
			return getName() + " (" + getHealth() + ")";
		return getName() + " (" + getHealth() + " , " + getEquipped().getType()
				+ ":" + getEquipped().getInClip() + "+"
				+ getEquipped().getReserve() + ")";
	}

	public void randomSpawn()
	{
		w.removeEntity(entity);
		Random r = new Random();
		do
		{
			Vector2D newPos = new Vector2D(r.nextInt((int) w.getBounds()
					.getWidth()) + (int) w.getBounds().getX(),
					r.nextInt((int) w.getBounds().getHeight())
							+ (int) w.getBounds().getY());
			entity = new PlayerEntity(newPos, this);
		} while (w.getLandscape().checkCollisions(entity.getHitbox()) != null);
		w.add(entity);
	}

	public void addScore()
	{
		score++;
	}

	public void equipWep(String string)
	{
		guns[equipped] = new Weapon(string, this);

	}

	public boolean isFlipped()
	{
		return downs != null
				&& Math.abs(new Vector2D(downs.getMouseX(), downs.getMouseY())
						.subtract(entity.getPos()).angle()) < Math.PI / 2;
	}

	public double rotatedAngle()
	{
		if (downs == null)
		{
			return 0;
		}
		return new Vector2D(downs.getMouseX(), downs.getMouseY()).subtract(
				entity.getPos()).angle();
	}

	public void setSpecMoving(boolean on)
	{
		specmoving = on;
	}

	public boolean isSpecMoving()
	{
		return specmoving;
	}

	public void setReady(boolean ready)
	{
		this.ready = ready;
	}

	public boolean isReady()
	{
		return ready;
	}

	public NetworkInputData getDowns()
	{
		return downs;
	}

	public boolean explosion(int dmg)
	{
		if (w.getGame().getGM().oneLife && dead)
		{
			return false;
		}
		if (this.isSpecMoving())
		{
			return false;
		}
		health -= dmg;
		dead();
		return true;
		// TODO: move backwards
	}
}
