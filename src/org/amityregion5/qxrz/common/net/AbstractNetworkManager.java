package org.amityregion5.qxrz.common.net;

import java.io.IOException;
import java.io.Serializable;
import java.net.DatagramSocket;


// eventually Server/Client should just extend this
public abstract class AbstractNetworkManager extends Thread
{
	protected UDPInputStream inStream;
	protected UDPOutputStream outStream;
	protected NetEventListener callback;
	
	// call super(sock) from subclasses
	public AbstractNetworkManager(DatagramSocket sock)
	{
		outStream = new UDPOutputStream(sock);
		inStream = new UDPInputStream(sock);
	}
	
	/*
	 * Client only sends to server, while server sends to all clients.
	 */
	public abstract void sendObject(Serializable obj);
	
	public void attachEventListener(NetEventListener listener)
	{
		callback = listener;
	}
	
	// For thread
	public abstract void run();
	
	// subclasses will call this helper for each node
	protected boolean runHelper(NetworkNode node) throws ClassNotFoundException, IOException
	{
		NetworkObject netObj = (NetworkObject) inStream.recvObject();
		
		/*
		 * The packet count should be always-increasing.
		 * 
		 * If we get an out of order packet (pn < packetCount) or duplicate
		 * packet (pn == packetCount) ignore the packet. Note that duplicate
		 * packets are quite rare.
		 * 
		 * Lost packets are ignored; hopefully communications are not
		 * drastically affected by this rather rare occurrence.
		 */
		if(netObj.getPacketNumber() <= node.getReceivedPacketCount())
		{
			return false;	
		}
		node.setReceivedPacketCount(node.getReceivedPacketCount() + 1);
		callback.dataReceived(node, netObj.getPayload());
		return true;
	}
	
}
