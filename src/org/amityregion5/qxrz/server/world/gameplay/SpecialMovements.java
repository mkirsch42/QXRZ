package org.amityregion5.qxrz.server.world.gameplay;

public enum SpecialMovements
{

	DASH("dash", "mvmt/dash"), ROLL("roll", "mvmt/roll"), TELEPORT("tele",
			"mvmt/tele");

	private String type, assetName;

	private SpecialMovements(String type, String assetName)
	{
		this.type = type;
		this.assetName = assetName;
	}

	public String getType()
	{
		return type;
	}

	public String getAssetName()
	{
		return assetName;
	}
}
