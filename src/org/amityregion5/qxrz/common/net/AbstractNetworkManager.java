package org.amityregion5.qxrz.common.net;

import java.io.IOException;
import java.io.Serializable;
import java.net.DatagramSocket;
import java.net.SocketException;


// eventually Server/Client should just extend this
public abstract class AbstractNetworkManager extends Thread
{
	protected UDPInputStream inStream;
	protected UDPOutputStream outStream;
	protected NetEventListener callback;
	
	protected DatagramSocket sock;
	
	// call super(sock) from subclasses
	public AbstractNetworkManager() throws SocketException
	{
		this(0);
	}
	
	public AbstractNetworkManager(int port) throws SocketException
	{
		super("network manager");
		
		sock = new DatagramSocket(port);
		sock.setReuseAddress(true); // Just in case
		
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
	protected void runHelper(NetworkNode node, NetworkObject netObj) throws ClassNotFoundException, IOException
	{
		/*
		 * The packet count should be always-increasing.
		 * 
		 * If we get an out of order packet (pn < packetCount) or duplicate
		 * packet (pn == packetCount) ignore the packet. Note that duplicate
		 * packets are extremely rare.
		 * 
		 * Lost packets are ignored; hopefully communications are not
		 * drastically affected by this rather rare occurrence.
		 * 
		 * If they are, we may need to work in some sort of handshake
		 */
		if(netObj.getPacketNumber() <= node.getReceivedPacketCount())
		{
			// should this fail silently?
			throw new IOException("Out-of-order or duplicate packet received.");
		}
		
		// We received an in-order packet!
		node.setReceivedPacketCount(node.getReceivedPacketCount() + 1);
		callback.dataReceived(node, netObj.getPayload());
	}
	
}
