package org.amityregion5.qxrz.server.net;

import java.io.Serializable;
import java.net.DatagramSocket;
import java.net.InetAddress;

import org.amityregion5.qxrz.common.net.NetworkObject;
import org.amityregion5.qxrz.common.net.UDPOutputStream;

// TODO move to common, with new name (NetworkNode)

public class Client
{
	private DatagramSocket sock;
	private int sendedPacketCount;
	private int receivedPacketCount;
	public int getReceivedPacketCount()
	{
		return receivedPacketCount;
	}

	public void setReceivedPacketCount(int receivedPacketCount)
	{
		this.receivedPacketCount = receivedPacketCount;
	}

	public Client(DatagramSocket ds)
	{
		sock = ds;
		sendedPacketCount = 0;
		receivedPacketCount = 0;
	}
	
	public DatagramSocket getSocket()
	{
		return sock;
	}
	
	public int getSendedPacketCount()
	{
		return sendedPacketCount;
	}
	
	public boolean hasSameSocket(DatagramSocket ds)
	{
		InetAddress i1 = sock.getInetAddress();
		InetAddress i2 = ds.getInetAddress();
		return i1.toString().equals(i2.toString());
	}
	
	public void send(UDPOutputStream outStream, Serializable obj) throws Exception
	{
		outStream.setSocket(sock);
		outStream.sendObject(new NetworkObject(obj, sendedPacketCount));
		sendedPacketCount++;
	}
	
	@Override
	public boolean equals(Object obj)
	{
		if(!(obj instanceof Client)) return false;
		InetAddress i1 = sock.getInetAddress();
		InetAddress i2 = ((Client)obj).getSocket().getInetAddress();
		return i1.toString().equals(i2.toString());
	}
}
