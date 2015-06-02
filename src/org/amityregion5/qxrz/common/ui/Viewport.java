package org.amityregion5.qxrz.common.ui;

import java.awt.geom.Point2D;
import org.amityregion5.qxrz.client.ui.screen.WindowData;

/**
 * A data/util class for storing the viewport
 */
public class Viewport
{
	// The width of the viewport (not screen width)
	public double width;
	// The height of the viewport (not screen height)
	public double height;
	// The x coordinate of the center of the viewport
	public double xCenter;
	// The y coordinate of the center of the viewport
	public double yCenter;

	/**
	 * Get the xOffset of the viewport (the left most x coordinate)
	 * 
	 * @return the xOffset of the viewport
	 */
	public double getXOff()
	{
		return xCenter - width / 2;
	}

	/**
	 * Get the yOffset of the viewport (the upper most y coordinate)
	 * 
	 * @return the yOffset of the viewport
	 */
	public double getYOff()
	{
		return yCenter - height / 2;
	}

	public Point2D.Double screenToGame(Point2D.Double point,
			WindowData windowData)
	{
		return new Point2D.Double((point.x + getXOff()) / windowData.getWidth()
				* width, (point.y + getYOff()) / windowData.getHeight()
				* height);
	}

	public Point2D.Double gameToScreen(Point2D.Double point,
			WindowData windowData)
	{
		double xFact = windowData.getWidth()/width;
		double xOff = getXOff() * xFact;
		double yFact = windowData.getHeight()/height;
		double yOff = getYOff() * xFact;

		return new Point2D.Double(point.x * xFact - xOff, point.y * yFact - yOff);
	}
}