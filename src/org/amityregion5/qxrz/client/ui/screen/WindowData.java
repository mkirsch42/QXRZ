package org.amityregion5.qxrz.client.ui.screen;

import java.util.List;

public class WindowData
{
	private int width;
	private int height;
	private List<Integer> keysDown;
	private List<Integer> miceDown;
	private int mouseX;
	private int mouseY;
	
	/**
	 * @param width
	 * @param height
	 * @param keysDown
	 * @param miceDown
	 * @param mouseX
	 * @param mouseY
	 */
	public WindowData(int width, int height, List<Integer> keysDown,
			List<Integer> miceDown, int mouseX, int mouseY)
	{
		this.width = width;
		this.height = height;
		this.keysDown = keysDown;
		this.miceDown = miceDown;
		this.mouseX = mouseX;
		this.mouseY = mouseY;
	}
	/**
	 * @return the width
	 */
	public int getWidth()
	{
		return width;
	}
	/**
	 * @param width the width to set
	 */
	public void setWidth(int width)
	{
		this.width = width;
	}
	/**
	 * @return the height
	 */
	public int getHeight()
	{
		return height;
	}
	/**
	 * @param height the height to set
	 */
	public void setHeight(int height)
	{
		this.height = height;
	}
	/**
	 * @return the keysDown
	 */
	public List<Integer> getKeysDown()
	{
		return keysDown;
	}
	/**
	 * @param keysDown the keysDown to set
	 */
	public void setKeysDown(List<Integer> keysDown)
	{
		this.keysDown = keysDown;
	}
	/**
	 * @return the miceDown
	 */
	public List<Integer> getMiceDown()
	{
		return miceDown;
	}
	/**
	 * @param miceDown the miceDown to set
	 */
	public void setMiceDown(List<Integer> miceDown)
	{
		this.miceDown = miceDown;
	}
	/**
	 * @return the mouseX
	 */
	public int getMouseX()
	{
		return mouseX;
	}
	/**
	 * @param mouseX the mouseX to set
	 */
	public void setMouseX(int mouseX)
	{
		this.mouseX = mouseX;
	}
	/**
	 * @return the mouseY
	 */
	public int getMouseY()
	{
		return mouseY;
	}
	/**
	 * @param mouseY the mouseY to set
	 */
	public void setMouseY(int mouseY)
	{
		this.mouseY = mouseY;
	}
	
}
