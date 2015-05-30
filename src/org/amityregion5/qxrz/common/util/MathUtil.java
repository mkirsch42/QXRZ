package org.amityregion5.qxrz.common.util;

import java.awt.geom.Point2D;

/**
 * A math util class
 */
public class MathUtil
{
	/**
	 * Used to get the angle between 2 points (in radians)
	 * 
	 * @param start the starting point
	 * @param end the ending point
	 * @return the angle between the points in radians
	 */
	public static double getAngleBetweenPoints(Point2D.Double start, Point2D.Double end) {
		return Math.atan2(end.getY() - start.getY(), end.getX() - start.getX());
	}
	/**
	 * Used to get the point at the end of a line
	 * 
	 * @param start the start point
	 * @param len the length of the line
	 * @param rad the radian measure of the line from standard position
	 * @return the point at the end of this line
	 */
	public static Point2D.Double getPointAtEndOfLine(Point2D.Double start, double len, double rad) {
		return new Point2D.Double(start.getX() + len * Math.cos(rad), start.getY() + len * Math.sin(rad));
	}

}
