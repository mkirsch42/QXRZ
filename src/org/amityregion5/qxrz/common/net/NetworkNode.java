package org.amityregion5.qxrz.common.net;

import java.io.Serializable;

import java.net.InetSocketAddress;
import java.net.SocketException;
import java.util.logging.Logger;

public class NetworkNode extends AbstractNetworkNode
{

	/**
	 * 
	 */
	private static final long serialVersionUID = -5035976919498974857L;
	private int sentPacketCount;
	private int receivedPacketCount;
	private static Logger l = Logger.getGlobal();
	private UDPOutputStream outStream;

	public NetworkNode(UDPOutputStream out, InetSocketAddress a)
			throws SocketException
	{	addr = a;
		sentPacketCount = 0;
		receivedPacketCount = 0;
		setAddress(a);
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
}
