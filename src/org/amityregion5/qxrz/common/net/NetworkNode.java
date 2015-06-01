package org.amityregion5.qxrz.common.net;

import java.io.Serializable;

import java.net.InetSocketAddress;
import java.net.SocketException;

public class NetworkNode extends AbstractNetworkNode
{
	private InetSocketAddress addr;

	private int sentPacketCount;
	private int receivedPacketCount;

	private UDPOutputStream outStream;

	public NetworkNode(UDPOutputStream out, InetSocketAddress a)
			throws SocketException
	{	addr = a;
		sentPacketCount = 0;
		receivedPacketCount = 0;

		outStream = out;
	}
	
	public InetSocketAddress getSocketAddress() {
		return addr;
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

	public void send(Serializable obj) throws Exception
	{
		sentPacketCount++;
		outStream.setAddress(addr);
		outStream.sendObject(new NetworkObject(obj, sentPacketCount));
	}

	public boolean equals(Object obj)
	{
		if (!(obj instanceof NetworkNode))
			return false;

		return addr.equals(((NetworkNode) obj).getAddress());
	}
}
