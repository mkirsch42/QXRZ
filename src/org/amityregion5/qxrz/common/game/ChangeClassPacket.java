package org.amityregion5.qxrz.common.game;

import java.io.Serializable;

public class ChangeClassPacket implements Serializable
{
	private static final long serialVersionUID = -1603916390206557665L;

	private boolean right;

	/**
	 * @param right
	 */
	public ChangeClassPacket(boolean right)
	{
		this.right = right;
	}

	/**
	 * @return the right
	 */
	public boolean isRight()
	{
		return right;
	}

	/**
	 * @param right
	 *            the right to set
	 */
	public void setRight(boolean right)
	{
		this.right = right;
	}

}
