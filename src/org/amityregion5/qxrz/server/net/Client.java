package org.amityregion5.qxrz.server.net;

import java.io.Serializable;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.util.Arrays;

import org.amityregion5.qxrz.common.net.NetworkObject;
import org.amityregion5.qxrz.common.net.UDPOutputStream;

// TODO move to common, with new name (NetworkNode)

public class Client
{
	private DatagramSocket sock;
	private int sentPacketCount;
	private int receivedPacketCount;
	
	public Client(DatagramSocket ds)
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
	
	public void incrementReceivedPacketCount()
	{
		receivedPacketCount++;
	}
	
	public int getSentPacketCount()
	{
		return sentPacketCount;
	}
	
	public void send(UDPOutputStream outStream, Serializable obj) throws Exception
	{
		outStream.setSocket(sock);
		outStream.sendObject(new NetworkObject(obj, sentPacketCount));
		sentPacketCount++;
	}
	
	public boolean equals(Object obj)
	{
		if(!(obj instanceof Client)) return false;
		
		DatagramSocket sock2 = ((Client) obj).getSocket();
		
		/* We want to check that they have the same socket... so IP/port. */
		if(sock.getPort() != sock2.getPort()) return false;
		
		return Arrays.equals(sock.getInetAddress().getAddress(), sock2.getInetAddress().getAddress());
	}
}
