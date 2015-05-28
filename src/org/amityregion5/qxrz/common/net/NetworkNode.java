package org.amityregion5.qxrz.common.net;

import java.io.Serializable;
import java.net.DatagramSocket;
import java.util.Arrays;

public class NetworkNode
{
	private DatagramSocket sock;
	private int sentPacketCount;
	private int receivedPacketCount;
	
	public NetworkNode(DatagramSocket ds)
	{
		sock = ds;
		sentPacketCount = 0;
		receivedPacketCount = 0;
	}

	public DatagramSocket getSocket()
	{
		return sock;
	}
	
	public int getReceivedPacketCount()
	{
		return receivedPacketCount;
	}
	
	public void setReceivedPacketCount(int count)
	{
		receivedPacketCount = count;
	}
	
	public int getSentPacketCount()
	{
		return sentPacketCount;
	}
	
	public void send(UDPOutputStream outStream, Serializable obj) throws Exception
	{
		sentPacketCount++;
		outStream.setSocket(sock);
		outStream.sendObject(new NetworkObject(obj, sentPacketCount));
	}
	
	public boolean equals(Object obj)
	{
		System.out.println("NetworkNode.equals()");
		if(!(obj instanceof NetworkNode)) return false;
		
		DatagramSocket sock2 = ((NetworkNode) obj).getSocket();
		
		/* We want to check that they have the same socket... so IP/port. */
		if(sock.getPort() != sock2.getPort()) return false;
		
		return Arrays.equals(sock.getInetAddress().getAddress(), sock2.getInetAddress().getAddress());
	}
}
