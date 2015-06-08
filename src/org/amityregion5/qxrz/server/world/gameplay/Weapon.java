package org.amityregion5.qxrz.server.world.gameplay;

import org.amityregion5.qxrz.server.DebugConstants;

public class Weapon {
	private String type;
	private int ccamount; //current clip amount
	//private int cleft;	  //clips left
	private int cmaxammo; //maximum ammo per clip
	private int maxclips; //maximum clips to hold
	private int reserve; //reserve ammo;
	private int rof; //per half second
	private int retime; //per half second
	private int damage;
	private int speed;
	//constructors
	public Weapon()
	{
		this("ps");
	}
	public Weapon(String pick)
	{
		for(WeaponTypes w : WeaponTypes.values())
		{
			if(w.text.equals(pick))
			{
				type=w.text;
				ccamount = w.cmaxammo;
				cmaxammo = w.cmaxammo;
				maxclips = w.clips;
				reserve = w.reserve;
				rof = w.rof;
				retime = w.retime;
				damage = w.dmg;
				speed = w.speed;
				break;
			}
		}

	}
	
	public boolean shoot()
	{
		if(DebugConstants.INFINITEAMMO)
		{
			return true;
		}
		if(ccamount<=0)
		{
			if(reserve<=0)
			{
				return false;
			}
			reload();
		}
		ccamount--;
		return true;
	}
	public void reload()
	{
		if(reserve<=cmaxammo-ccamount)
		{
			ccamount += reserve;
			reserve = 0;
			return;
		}
		reserve-=cmaxammo-ccamount;
		ccamount = cmaxammo;
		
		/*if (ccamount==0)
		{
			if (cleft != 0)
			{
				ccamount = cmaxammo;
				cleft--;
			}
		}
		else //still ammo left in clip
		{
			reserve = ccamount;
			ccamount += cmaxammo-ccamount; //clip fills up
			cleft--;
		}*/
	}
	public int getDamage()
	{
		return damage;
	}
	public String getType()
	{
		return type;
	}
	public int getSpeed()
	{
		return speed;
	}
	public void changeROF()
	{
		rof += 4;
	}
	public void changeMaxAmmo()
	{
		maxclips += 4;
	}
	public void changeCMax()
	{
		cmaxammo += 4;
	}
	public void setAmmo(int count)
	{
		if(count>maxclips*cmaxammo)
		{
			count = maxclips*cmaxammo;
		}
		reserve = count;
		reload();
	}
	public boolean addAmmo(int count)
	{
		if(reserve==(maxclips-1)*cmaxammo)
			return false;
		reserve += count;
		if(reserve>(maxclips-1)*cmaxammo)
		{
			reserve=(maxclips-1)*cmaxammo;
		}
		return true;
	}
}
