package org.amityregion5.qxrz.common.game;

import java.io.Serializable;

public class ReadyPacket implements Serializable
{
	private static final long serialVersionUID = -5930003421058686080L;
	private boolean ready;

	/**
	 * @param ready
	 */
	public ReadyPacket(boolean ready)
	{
		this.ready = ready;
	}

	/**
	 * @return the ready
	 */
	public boolean isReady()
	{
		return ready;
	}

	/**
	 * @param ready
	 *            the ready to set
	 */
	public void setReady(boolean ready)
	{
		this.ready = ready;
	}

}
