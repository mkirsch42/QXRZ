package org.amityregion5.qxrz.server.world.gameplay;

public class Upgrade {
	private Player pl;
	private String type;
	public Upgrade()
	{
		
	}
	public Upgrade(String type)
	{
		if (type.equals("rof"))
		{
			
		}
		else if (type.equals("cmax"))
		{
			
		}
		else if (type.equals("maxammo"))
		{
			pl.getEquip();
		}
	}
}
