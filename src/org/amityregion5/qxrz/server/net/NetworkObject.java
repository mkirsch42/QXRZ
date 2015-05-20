package org.amityregion5.qxrz.server.net;

import java.io.Serializable;

public class NetworkObject implements Serializable
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	String type;
	Serializable payload;
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
