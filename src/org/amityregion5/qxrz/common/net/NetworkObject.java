package org.amityregion5.qxrz.common.net;

import java.io.Serializable;

// TODO packet numbering stuff

public class NetworkObject implements Serializable
{
	private static final long serialVersionUID = 1L;

	public final transient static int BUFFER_SIZE = 1024 * 5;
	
	private Serializable payload;
	private int packetNumber;
	
	public NetworkObject(Serializable obj, int pn)
	{
		payload = obj;
		packetNumber = pn;
	}
	
	public Serializable getPayload()
	{
		return payload;
	}
	
	public int getPacketNumber()
	{
		return packetNumber;
	}

	
	public String toString()
	{
		return "#" + packetNumber + "\tpayload=" + payload;
	}

	
	
}
