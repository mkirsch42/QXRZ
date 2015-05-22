package org.amityregion5.qxrz.net;

import java.io.Serializable;

// TODO we have to write object casting methods... enums?
// TODO needs methods for adding objects... auto type string (have a list of types -> strings)
// TODO packet numbering stuff

public class NetworkObject implements Serializable
{
	private static final long serialVersionUID = 1L;
	
	public String type;
	public Serializable payload;
	public int packetNumber;
	
	/*public NetworkObject(Serializable obj, int packetNum)
	{
		payload = obj;
		packetNumber = packetNum;
	}*/
	
	public String toString()
	{
		return "type=" + type + ", " + "payload=" + payload;
	}

	
	
}
