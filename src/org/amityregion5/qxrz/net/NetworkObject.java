package org.amityregion5.qxrz.net;

import java.io.Serializable;

// TODO we have to write object casting methods... enums?

public class NetworkObject implements Serializable
{
	private static final long serialVersionUID = 1L;
	
	public String type;
	public Serializable payload;
	public int packetNumber = 0;
	public NetworkObject()
	{
		// can't do it this way, NEEDS to be synchronized clock time
	}
	
	
	
	public String toString()
	{
		return "type=" + type + ", " + "payload=" + payload;
	}
	
	
}
