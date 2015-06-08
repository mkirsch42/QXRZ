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
		/*type = pick;
		if (type.equals("sg"))
		{
			ccamount = 8;
			//cleft = 3;
			cmaxammo = 8;
			maxclips = 3;
			reserve = 16;
			rof = 3;
			retime = 2;
			damage = 6;
		}
		else if (type.equals("ro"))
		{
			ccamount = 4;
			//cleft = 2;
			cmaxammo = 4;
			maxclips = 2;
			reserve = 4;
			rof  = 2;
			retime = 3;
			damage = 80;
			
		}
		else if (type.equals("fl"))
		{
			ccamount = 200;
			cmaxammo = 200;
			rof = 6;
			damage = 35;
		}
		else if (type.equals("ps"))
		{
			ccamount = 6;
			//cleft = 3;
			cmaxammo = 6;
			maxclips = 3;
			reserve = 6;
			rof = 5;
			retime = 1;
			//damage = 13;
			damage=25;
			speed = 1000;
		}
		else if (type.equals("as"))
		{
			ccamount = 12;
			//cleft = 3;
			cmaxammo = 12;
			maxclips = 3;
			reserve = 24;
			rof = 9;
			retime = 2;
			damage = 7;
		}
		else if (type.equals("bo"))
		{
			ccamount = 1;
			//cleft = 6;
			cmaxammo = 1;
			maxclips = 6;
			reserve = 5;
			rof = 1;
			retime = 2; 
			damage = 100;
		}*/

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
		/*if (ccamount<=0)
		{
			if (cleft<=0)
			{
				if (reserve<=0)
				{	
					return false;
				}
				reserve--;
			}
			reload();
		}
		else
			ccamount--;*/
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
		//cleft += 4;
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
