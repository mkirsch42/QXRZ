package org.amityregion5.qxrz.server.world.gameplay;

public class Upgrade {
	private String type;

	public Upgrade(String t)
	{
		type = t;
	}
	
	public String getType()
	{
		return type;
	}
	public boolean equals(Object obj)
	{
		if ((!(obj instanceof Upgrade)) || obj.equals(null))
			return false;
		else
		{
			if (type.equals(((Upgrade) obj).getType()))
					return true;
			return false;
		}
	}
}
