package org.amityregion5.qxrz.server.world.gameplay;

public enum GameModes
{

	//ENDLESS(0, false),
	//LASTMAN(1, true);

	ENDLESS(0, false, true),
	LASTMAN(1, true, false);

	
	public final int id;
	public final boolean oneLife;


	public final boolean hasTeams;

	

	private GameModes(final int value, boolean oneLife, boolean hasTeams)
	{
		id = value;
		this.oneLife = oneLife;
		this.hasTeams = hasTeams;

	}
	
	public boolean equals(GameModes gm)
	{
		return id==gm.id;
	}
}
