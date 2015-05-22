package org.amityregion5.qxrz.server.net;

import java.io.Serializable;
import java.net.DatagramSocket;

import org.amityregion5.qxrz.common.net.NetworkObject;
import org.amityregion5.qxrz.common.net.UDPOutputStream;

// TODO move to common, with new name (NetworkNode)

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
	
	public void send(UDPOutputStream outStream, Serializable obj) throws Exception
	{
		outStream.setSocket(sock);
		outStream.sendObject(new NetworkObject(obj, packetCount));
		packetCount++;
	}
	
	@Override
	public boolean equals(Object obj)
	{
		if(!(obj instanceof Client)) return false;
		
		return sock.getInetAddress().equals(((Client) obj).getSocket().getInetAddress());
	}
}
