package org.amityregion5.qxrz.common.ui;

import java.io.Serializable;

import org.amityregion5.qxrz.server.world.gameplay.SpecialMovements;

public class LobbyInformationPacket implements Serializable
{
	private static final long serialVersionUID = 6499520811837167014L;

	private SpecialMovements playerClass;
	private boolean isReady;

	/**
	 * @param playerClass
	 * @param isReady
	 */
	public LobbyInformationPacket(SpecialMovements playerClass, boolean isReady)
	{
		this.playerClass = playerClass;
		this.isReady = isReady;
	}

	/**
	 * @return the playerClass
	 */
	public SpecialMovements getPlayerClass()
	{
		return playerClass;
	}

	/**
	 * @param playerClass
	 *            the playerClass to set
	 */
	public void setPlayerClass(SpecialMovements playerClass)
	{
		this.playerClass = playerClass;
	}

	/**
	 * @return the isReady
	 */
	public boolean isReady()
	{
		return isReady;
	}

	/**
	 * @param isReady
	 *            the isReady to set
	 */
	public void setReady(boolean isReady)
	{
		this.isReady = isReady;
	}

}
