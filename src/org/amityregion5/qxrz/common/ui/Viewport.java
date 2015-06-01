package org.amityregion5.qxrz.common.ui;

/**
 * A data/util class for storing the viewport
 */
public class Viewport {
	//The width of the viewport (not screen width)
	public double width;
	//The height of the viewport (not screen height)
	public double height;
	//The x coordinate of the center of the viewport
	public double xCenter;
	//The y coordinate of the center of the viewport
	public double yCenter;
	
	/**
	 * Get the xOffset of the viewport (the left most x coordinate)
	 * 
	 * @return the xOffset of the viewport
	 */
	public double getXOff() {
		return xCenter - width/2;
	}
	
	/**
	 * Get the yOffset of the viewport (the upper most y coordinate)
	 * 
	 * @return the yOffset of the viewport
	 */
	public double getYOff() {
		return yCenter - height/2;
	}
}