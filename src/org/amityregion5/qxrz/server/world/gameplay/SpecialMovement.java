package org.amityregion5.qxrz.server.world.gameplay;

import org.amityregion5.qxrz.server.world.entity.PlayerEntity;

public class SpecialMovement {
	private String type;
	private static final int TIME = 3; //time for all special moves though cooldown will vary
	private static final int ROLL_LEN = 4;
	private static final int DASH_LEN = 6;	//arbitrary values that are subject to change
	private static final int TELE_LEN = 6;
	public SpecialMovement(String t)
	{
		type = t;
	}
	public String getType()
	{
		return type;
	}
	public void dash(PlayerEntity p)
	{
		//code
	}
	public void roll(PlayerEntity p)
	{
		//code
	}
	public void teleport(PlayerEntity p)
	{
		//code
	}
}
