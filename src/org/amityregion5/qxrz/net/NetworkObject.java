package org.amityregion5.qxrz.net;

import java.io.Serializable;

public class NetworkObject implements Serializable
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	public String type;
	public Serializable payload;
	private long timeStamp;
	
	public NetworkObject()
	{
		timeStamp = System.currentTimeMillis();
	}
	public long getTimeStamp()
	{
		return timeStamp;
	}
	
	public String toString()
	{
		return "type=" + type + ", " + "payload=" + payload;
	}
}
