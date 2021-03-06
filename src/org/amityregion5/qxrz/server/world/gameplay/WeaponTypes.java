package org.amityregion5.qxrz.server.world.gameplay;

public enum WeaponTypes
{
	SHOTGUN("sg", "Shotgun", 8, 3, 16, 3, 2, 6, 5000, false, "weapons/shotgun",
			"shotgun"), FIREGUN("fl", "Flamethrower", 100, 2, 0, 6, 0, 35,
			5000, true, "weapons/flamethrower", "flameloop"), REVOLVER("ps",
			"Revolver", 6, 3, 6, 10, 30, 10, 5000, false, "weapons/revolver",
			"revolvershot"), RIFLE("as", "Assult Rifle", 12, 3, 24, 9, 2, 7,
			5000, false, "weapons/rifle", "revolvershot"), ARROWGUN("bo",
			"Bow", 1, 6, 5, 1, 2, 100, 5000, false, "weapons/bow", "arrow"), ROCKETGUN(
			"ro", "Rocket Launcher", 4, 2, 4, 2, 3, 80, 5000, false,
			"weapons/launcher", "rocket"), KNIFE("kn", "Knife", 0, 0, 0, 0, 0,
			0, 0, false, "projectiles/bullet", "footstep");

	public final String text;
	public final String fullName;
	public final int cmaxammo;
	public final int clips;
	public final int reserve;
	public final int rof;
	public final int retime;
	public final int dmg;
	public final int speed;
	public final boolean auto;
	public final String asset;
	public final String sound;

	private WeaponTypes(final String text, final String fullName,
			final int cmaxammo, final int clips, final int reserve,
			final int rof, final int retime, final int dmg, final int speed,
			final boolean auto, final String asset, final String sound)
	{
		this.text = text;
		this.fullName = fullName;
		this.cmaxammo = cmaxammo;
		this.clips = clips;
		this.reserve = reserve;
		this.rof = rof;
		this.retime = retime;
		this.dmg = dmg;
		this.speed = speed;
		this.asset = asset;
		this.auto = auto;
		this.sound = sound;
	}

	public static WeaponTypes getTypeFromString(String str)
	{
		for (WeaponTypes w : values())
		{
			if (w.text.equals(str))
			{
				return w;
			}
		}
		return null;
	}
}