package org.amityregion5.qxrz.common.ui;

import java.awt.Graphics2D;
import org.amityregion5.qxrz.client.ui.screen.WindowData;

/**
 * An interface that defines an object drawer
 *
 * @param <T> the type of object that it can draw
 */
public interface IObjectDrawer<T> {
	/**
	 * Called to draw an object to the screen
	 * 
	 * @param g the graphics object to draw to
	 * @param object the object to draw
	 * @param vp the viewport to use for drawing
	 * @param d the window data
	 */
	public void draw(Graphics2D g, T object, Viewport vp, WindowData d);
}
