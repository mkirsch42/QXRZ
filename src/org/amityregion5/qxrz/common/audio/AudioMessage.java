package org.amityregion5.qxrz.common.audio;

import java.awt.Point;
import java.io.Serializable;

public class AudioMessage implements Serializable
{

	/**
	 * 
	 */
	private static final long serialVersionUID = 2165375561208124385L;

	private boolean start = true;
	
	private String asset = "";
	
	private Point loc = new Point(0,0);
	
	public AudioMessage(Point p, String a, boolean s)
	{
		loc = p;
		asset = a;
		start = s;
	}
	
	public Point getLocation()
	{
		return loc;
	}
	
	public boolean isStarting()
	{
		return start;
	}
	
	public boolean isEnding()
	{
		return !start;
	}
	
	public String getAsset()
	{
		return asset;
	}
}
