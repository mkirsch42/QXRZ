package org.amityregion5.qxrz.server.world.gameplay;

import org.amityregion5.qxrz.server.world.entity.PickupEntity;

public class Pickup
{

	private int ammoCount;
	private String weaponId;
	private PickupEntity entity;
	private int timeout;
	private long lastPickup;
	private boolean onePickup = false;
	private Upgrade u;

	public Pickup(String wep, int count, int x, int y, int ms)
	{
		entity = new PickupEntity(x, y, this);
		ammoCount = count;
		weaponId = wep;
		timeout = ms;
		lastPickup = -1;
	}

	public Pickup(int healthCount, int x, int y, int ms)
	{
		weaponId = "<3";
		entity = new PickupEntity(x, y, this);
		ammoCount = healthCount;
		timeout = ms;
		lastPickup = -1;
	}

	public Pickup(Upgrade u, int x, int y, int ms)
	{
		weaponId = "WU";
		this.u = u;
		entity = new PickupEntity(x, y, this);
		timeout = ms;
	}

	public int getAmmoCount()
	{
		return ammoCount;
	}

	public String getWeaponId()
	{
		return weaponId;
	}

	public PickupEntity getEntity()
	{
		return entity;
	}

	public boolean canPickup()
	{
		return System.currentTimeMillis() - lastPickup > timeout;
	}

	public void pickup()
	{
		lastPickup = System.currentTimeMillis();
	}

	public void setOnePickup()
	{
		onePickup = true;
	}

	public boolean isOnePickup()
	{
		return onePickup;
	}

	public String getAsset()
	{
		if (!canPickup())
		{
			return "icons/refreshDark";
		}
		if (isHealth())
		{
			return "icons/healthPack";
		}
		for (WeaponTypes t : WeaponTypes.values())
		{
			if (t.text.equals(weaponId))
			{
				return t.asset;
			}
		}
		return "";
	}

	public boolean isHealth()
	{
		return weaponId.equals("<3");
	}

	public boolean isWepUpgrade()
	{
		return weaponId.equals("WU");
	}

	public Upgrade getUp()
	{
		return u;
	}
}
