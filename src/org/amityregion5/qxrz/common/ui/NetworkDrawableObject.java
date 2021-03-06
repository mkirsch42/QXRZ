package org.amityregion5.qxrz.common.ui;

import java.awt.Dimension;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.geom.Rectangle2D;
import java.io.Serializable;

public class NetworkDrawableObject implements Serializable
{

	/**
	 * 
	 */
	private static final long serialVersionUID = -4205289169148579464L;
	private String asset;
	private Rectangle2D box;
	private double rot = 0;
	private boolean flipV = false;
	private boolean flipH = false;

	public NetworkDrawableObject(String a, Rectangle2D b)
	{
		asset = a;
		box = b;
	}

	public NetworkDrawableObject(String a, Point p, int w, int h)
	{
		asset = a;
		box = new Rectangle(p, new Dimension(w, h));
	}

	public NetworkDrawableObject(String a, int x, int y, int w, int h)
	{
		asset = a;
		box = new Rectangle(x, y, w, h);
	}

	/**
	 * @return the asset
	 */
	public String getAsset()
	{
		return asset;
	}

	/**
	 * @return the box
	 */
	public Rectangle2D getBox()
	{
		return box;
	}

	public NetworkDrawableObject rotate(double rad)
	{
		rot += rad;
		return this;
	}

	public double getTheta()
	{
		return rot;
	}

	public NetworkDrawableObject flipV()
	{
		flipV = !flipV;
		return this;
	}

	public NetworkDrawableObject flipH()
	{
		flipH = !flipH;
		;
		return this;
	}

	public boolean isFlipV()
	{
		return flipV;
	}

	public boolean isFlipH()
	{
		return flipH;
	}
}
