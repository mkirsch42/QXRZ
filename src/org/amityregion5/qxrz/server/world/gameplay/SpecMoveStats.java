package org.amityregion5.qxrz.server.world.gameplay;

public enum SpecMoveStats {
	DASH(6),
	ROLL(4),
	TELEPORT(8);
	
	public final int COOLDOWN;
	private SpecMoveStats(final int COOL)
	{
		COOLDOWN = COOL;
	}
}
