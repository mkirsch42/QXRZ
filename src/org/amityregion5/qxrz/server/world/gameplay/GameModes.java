package org.amityregion5.qxrz.server.world.gameplay;

public enum GameModes
{
	ENDLESS(0, false),
	LASTMAN(1, true);
	
	public final int id;
	public final boolean oneLife;
	
	private GameModes(final int value, boolean oneLife)
	{
		id = value;
		this.oneLife = oneLife;
	}
	
	public boolean equals(GameModes gm)
	{
		return id==gm.id;
	}
}
