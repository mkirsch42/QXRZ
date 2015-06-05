package org.amityregion5.qxrz.common.ui;

import java.awt.Dimension;
import java.awt.Point;
import java.awt.Rectangle;
import java.io.Serializable;

public class NetworkDrawableObject implements Serializable
{

	/**
	 * 
	 */
	private static final long serialVersionUID = -4205289169148579464L;
	private String asset;
	private Rectangle box;
	
	public NetworkDrawableObject(String a, Rectangle b)
	{
		asset = a;
		box = b;
	}
	
	public NetworkDrawableObject(String a, Point p, int w, int h)
	{
		asset = a;
		box = new Rectangle(p,new Dimension(w,h));
	}
	
	public NetworkDrawableObject(String a, int x, int y, int w, int h)
	{
		asset = a;
		box = new Rectangle(x,y,w,h);
	}
}
