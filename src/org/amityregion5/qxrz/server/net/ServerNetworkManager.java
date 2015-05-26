package org.amityregion5.qxrz.server.net;

import java.io.IOException;
import java.io.Serializable;
import java.net.DatagramSocket;
import java.util.HashSet;
import java.util.Iterator;
import java.util.logging.Logger;

import org.amityregion5.qxrz.common.net.NetEventListener;
import org.amityregion5.qxrz.common.net.NetworkNode;
import org.amityregion5.qxrz.common.net.NetworkObject;
import org.amityregion5.qxrz.common.net.UDPInputStream;
import org.amityregion5.qxrz.common.net.UDPOutputStream;

public class ServerNetworkManager extends Thread
{
	private UDPInputStream inStream;
	private UDPOutputStream outStream;
	private NetEventListener callback;

	// List of client sockets
	private HashSet<NetworkNode> clients = new HashSet<NetworkNode>();

	private Logger l = Logger.getLogger(this.getClass().getName());

	// partially abstract
	/**
	 * This will initialize a socket that listens on a port
	 * 
	 * @param port
	 *           port to listen on
	 * @throws IOException
	 */
	public ServerNetworkManager(int port) throws IOException
	{
		// this should be lowercase
		super("servermanager");
		DatagramSocket sock = new DatagramSocket();
		sock.setReuseAddress(true); // don't know if this does anything

		inStream = new UDPInputStream(sock);
		outStream = new UDPOutputStream(sock);
	}

	// abstract
	/**
	 * 
	 * @param sel
	 *           Listener that will be called each time an Object was received
	 */
	public void attachServerEventListener(NetEventListener sel)
	{
		callback = sel;
	}

	// no abstract
	/**
	 * Send an Object over the network
	 * 
	 * @param netObj
	 *           Object that will be sent to all clients
	 * @throws IOException
	 */
	public void sendObject(Serializable obj)
	{
		for (NetworkNode c : clients)
		{
			try
			{
				c.send(outStream, obj);
			}
			catch (Exception e)
			{
				e.printStackTrace();
			}
		}
	}

	// no abstract
	public void removeClient(NetworkNode c)
	{
		clients.remove(c);
	}

	// no abstract
	private NetworkNode getClientBySocket(DatagramSocket sock)
	{
		NetworkNode c2 = new NetworkNode(sock);
		for (Iterator<NetworkNode> it = clients.iterator(); it.hasNext();)
		{
			NetworkNode c = it.next();
			if (c.equals(c2))
			{
				return c;
			}
		}
		return null;
	}

	// partially abstract
	// every except the client hash stuff can be replaced by runHelper
	@Override
	public void run()
	{
		while (true)
		{
			try
			{
				NetworkObject netObj = (NetworkObject) inStream.recvObject();
				DatagramSocket ds = new DatagramSocket();
				NetworkNode c = new NetworkNode(ds);
				ds.connect(inStream.getPacket().getSocketAddress());
				if (!clients.contains(c))
				{
					if (callback != null)
					{
						callback.newNode(c);
					}
					clients.add(c);
				}
				int pn = netObj.getPacketNumber();

				NetworkNode c2 = getClientBySocket(ds);

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
				if (pn <= c2.getReceivedPacketCount())
				{
					continue;
				}
				c2.setReceivedPacketCount(c2.getReceivedPacketCount() + 1);

				callback.dataReceived(c, netObj.getPayload());

				// eventually callback will do processing + sending
				sendObject(netObj);

				l.info("Object Received from:");
				l.info(netObj.toString());

			}
			catch (ClassNotFoundException | IOException e)
			{
				e.printStackTrace();
			}
		}
	}

}
