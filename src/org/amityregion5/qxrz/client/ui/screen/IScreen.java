package org.amityregion5.qxrz.client.ui.screen;

import java.awt.Graphics2D;

public interface IScreen
{
	/**
	 * Called to draw the screen
	 * 
	 * @param g the Graphics2D object to draw on
	 */
	public void drawScreen(Graphics2D g, WindowData windowData);
	/**
	 * Get the screen that this screen came from or null if it does not exist
	 * 
	 * @return the return screen or null
	 */
	public IScreen getReturnScreen();
	/**
	 * Set the return screen for this screen
	 * 
	 * @param s the return screen for this screen
	 * @return did it work?
	 */
	public boolean setReturnScreen(IScreen s);
}
