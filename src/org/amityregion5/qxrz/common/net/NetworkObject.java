package org.amityregion5.qxrz.common.net;

import java.io.Serializable;

// TODO we have to write object casting methods... enums?
// TODO needs methods for adding objects... auto type string (have a list of types -> strings)
// TODO packet numbering stuff

public class NetworkObject implements Serializable
{
	private static final long serialVersionUID = 1L;
	
	private Serializable payload;
	private int packetNumber;
	
	public NetworkObject(Serializable obj, int packetNum)
	{
		payload = obj;
		packetNumber = packetNum;
	}
	//TODO on the receiving end we can use type.cast(payload)
	
	public String toString()
	{
		return "payload=" + payload;
	}

	
	
}
