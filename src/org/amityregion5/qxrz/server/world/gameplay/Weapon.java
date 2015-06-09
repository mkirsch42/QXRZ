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
	private int retime; // in updates (approx. 60/sec)
	private int damage;
	private int speed;
	private int recooldown;
	private int firecooldown;
	private boolean reloading;
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
		reloading = false;
		recooldown = retime;
		firecooldown = 0;
	}
	
	public void update()
	{
		if(reloading)
		{
			recooldown--;
			reload();
		}
		if(firecooldown>0)
		{
			firecooldown--;
		}
	}
	
	public boolean cooling()
	{
		return firecooldown>0;
	}
	
	public boolean shoot()
	{
		if(DebugConstants.INFINITEAMMO)
		{
			return true;
		}
		if(firecooldown>0)
		{
			return false;
		}
		if(ccamount<=0)
		{
			if(reserve<=0)
			{
				return false;
			}
			if(!reload())
			{
				return false;
			}
		}
		ccamount--;
		firecooldown = rof;
		return true;
	}
	public boolean reload()
	{
		if(recooldown > 0)
		{
			reloading = true;
			return false;
		}
		reloading = false;
		recooldown = retime;
		if(reserve<=cmaxammo-ccamount)
		{
			ccamount += reserve;
			reserve = 0;
			return true;
		}
		reserve-=cmaxammo-ccamount;
		ccamount = cmaxammo;
		return true;
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
	public WeaponTypes getEnumType()
	{
		for(WeaponTypes w : WeaponTypes.values())
		{
			if(w.text.equals(getType()))
			{
				return w;
			}
		}
		return null;
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
		if(count<=cmaxammo)
		{
			ccamount = count;
			reserve = 0;
			return;
		}
		ccamount = cmaxammo;
		reserve = count-cmaxammo;
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
	public int getInClip()
	{
		return ccamount;
	}
	public int getReserve()
	{
		return reserve;
	}
}
