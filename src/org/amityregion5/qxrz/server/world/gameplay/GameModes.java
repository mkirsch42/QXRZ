package org.amityregion5.qxrz.server.world.gameplay;

public enum GameModes
{
	//ENDLESS(0, false),
	//LASTMAN(1, true);
	
	public final int id;
	public final boolean oneLife;
	public final boolean teams;
	
	private GameModes(final int value, boolean oneLife, boolean t)
	{
		id = value;
		this.oneLife = oneLife;
		teams = t;
	}
	
	public boolean equals(GameModes gm)
	{
		return id==gm.id;
	}
}
