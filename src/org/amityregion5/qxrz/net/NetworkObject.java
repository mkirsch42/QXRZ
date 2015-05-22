package org.amityregion5.qxrz.net;

import java.io.Serializable;

// TODO we have to write object casting methods... enums?
// TODO needs methods for adding objects... auto type string (have a list of types -> strings)

public class NetworkObject implements Serializable
{
	private static final long serialVersionUID = 1L;
	
	public String type;
	public Serializable payload;
	/*private long timeStamp;
	
	public static long getNetworkTime() throws IOException
	{
		URL timeAPI = new URL("http://www.google.com");
		
		long start = System.currentTimeMillis();
		URLConnection connection = timeAPI.openConnection();
		long ends = System.currentTimeMillis();
		
		return connection.getDate() - (ends - start) >> 1;
	}*/
	
	public NetworkObject()
	{
		// can't do it this way, NEEDS to be synchronized clock time
		//timeStamp = System.currentTimeMillis();
	}
	
	/*
	// MAYBE will be implemented.
	public long getTimeStamp()
	{
		return timeStamp;
	}
	
	public void setTimeStamp(long l)
	{
		this.timeStamp = l;
	}*/
	
	public String toString()
	{
		return "type=" + type + ", " + "payload=" + payload;
	}

	public static long getNetworkTime()
	{
		// TODO Auto-generated method stub
		return 0;
	}
	
	
}
