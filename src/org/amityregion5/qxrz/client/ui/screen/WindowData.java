package org.amityregion5.qxrz.client.ui.screen;

import java.util.List;

/**
 * A utility/data class to store input data and screen size data
 */
public class WindowData
{
	//Width of window
	private int width;
	//Height of window
	private int height;
	//List of keys that are down
	private List<Integer> keysDown;
	//List of mouse buttons that are down
	private List<Integer> miceDown;
	//Mouse X Coordinate
	private int mouseX;
	//Mouse Y Coordinate
	private int mouseY;
	
	/**
	 * @param width the width of the screen
	 * @param height the height of the screen
	 * @param keysDown a list of keys that are down
	 * @param miceDown a list of mouse buttons that are down
	 * @param mouseX the mouse X coordinate
	 * @param mouseY the mouse Y coordinate
	 */
	public WindowData(int width, int height, List<Integer> keysDown,
			List<Integer> miceDown, int mouseX, int mouseY)
	{
		//Set variables
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
