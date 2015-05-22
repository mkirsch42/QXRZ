package org.amityregion5.qxrz.server.net;

import java.net.DatagramSocket;

public class Client
{
	private DatagramSocket sock;
	private int packetCount;
	
	public Client(DatagramSocket ds)
	{
		sock = ds;
		packetCount = 0;
	}
	
	public DatagramSocket getSocket()
	{
		return sock;
	}
	
	public int getPacketCount()
	{
		return packetCount;
	}
}
