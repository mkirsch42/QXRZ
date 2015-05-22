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
	
	public void incrementPacketCount()
	{
		packetCount++;
	}
	
	@Override
	public boolean equals(Object obj)
	{
		if(!(obj instanceof Client)) return false;
		
		return sock.getInetAddress().equals(((Client) obj).getSocket().getInetAddress());
	}
}
