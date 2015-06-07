package org.amityregion5.qxrz.server.world.gameplay;

import org.amityregion5.qxrz.server.world.entity.PickupEntity;

public class Pickup
{

	private int ammoCount;
	private String weaponId;
	private PickupEntity entity;
	private int timeout;
	private long lastPickup;
	
	public Pickup(String wep, int count, int x, int y, int ms)
	{
		entity = new PickupEntity(x, y, this);
		ammoCount = count;
		weaponId = wep;
		timeout = ms;
		lastPickup = -1;
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
		return System.currentTimeMillis()-lastPickup>timeout;
	}

	public void pickup()
	{
		lastPickup = System.currentTimeMillis();
	}
}
