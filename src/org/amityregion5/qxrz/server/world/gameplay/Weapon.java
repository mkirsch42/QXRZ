package org.amityregion5.qxrz.server.world.gameplay;

public class Weapon {
	private String type;
	private int ccamount; //current clip amount
	private int cleft;	  //clips left
	private int cmaxammo; //maximum ammo per clip
	private int maxclips; //maximum clips to hold
	private int reserve; //reserve ammo;
	private int rof; //per second
	public Weapon() //constructor intended for player spawn				
	{
		type = "ps";
		ccamount = 10;
		cleft = 6;
		cmaxammo = 10;
		maxclips = 6;
		reserve = 0;
		rof = 8;
	}
	public Weapon(String pick)
	{
		type = pick;
		if (type.equals("sg"))
		{
			ccamount = 6;
			cleft = 5;
			cmaxammo = 6;
			maxclips = 5;
			reserve = 0;
			rof = 2;
		}
		else if (type.equals("ro"))
		{
			ccamount = 2;
			cleft = 4;
			cmaxammo = 2;
			maxclips = 4;
			reserve = 0;
			rof  = 1;
			
		}
		else if (type.equals("fl"))
		{
			ccamount = 20;
			cleft = 10;
			cmaxammo = 20;
			maxclips = 10;
			reserve = 0;
			rof = 5;
		}
		else if (type.equals("ps"))
		{
			ccamount = 10;
			cleft = 6;
			cmaxammo = 10;
			maxclips = 6;
			reserve = 0;
			rof = 8;
		}
	}
	
	public boolean shoot()
	{
		if (ccamount==0)
		{
			if (cleft==0)
			{
				if (reserve==0)
				{	
					return false;
				}
				reserve--;
			}
			reload();
		}
		ccamount--;
		return true;
	}
	public void reload()
	{
		if (ccamount==0)
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
		}
	}
}
