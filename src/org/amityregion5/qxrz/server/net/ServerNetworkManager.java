package org.amityregion5.qxrz.server.net;

import java.io.IOException;
import java.io.Serializable;
import java.net.InetSocketAddress;
import java.net.SocketAddress;
import java.util.HashSet;
import java.util.Iterator;
import java.util.logging.Logger;

import org.amityregion5.qxrz.common.net.AbstractNetworkManager;
import org.amityregion5.qxrz.common.net.NetworkNode;
import org.amityregion5.qxrz.common.net.NetworkObject;

public class ServerNetworkManager extends AbstractNetworkManager
{
	// List of client sockets
	private HashSet<NetworkNode> clients = new HashSet<NetworkNode>();

	private Logger l = Logger.getLogger(this.getClass().getName());
	
	public ServerNetworkManager(int p) throws Exception
	{
		super(p);
	}

	public void sendObject(Serializable obj)
	{
		for (NetworkNode c : clients)
		{
			try
			{
				c.send(obj);
			}
			catch (Exception e)
			{
				e.printStackTrace();
			}
		}
	}

	public void removeClient(NetworkNode c)
	{
		clients.remove(c);
	}

	@Override
	public void run()
	{
		while (true)
		{
			try
			{
				NetworkObject netObj = (NetworkObject) inStream.recvObject();
				NetworkNode recvClient = new NetworkNode((InetSocketAddress) inStream.getPacket().getSocketAddress());
				boolean foundClient = false;
				
				// if netObj instanceof DiscoveryQuery don't do anything below
				for (Iterator<NetworkNode> it = clients.iterator(); it.hasNext();)
				{
					NetworkNode c = it.next();
					if (c.equals(recvClient))
					{
						// Found a client
						recvClient = c;
						foundClient = true;
						break;
					}
				}
				
				if(! foundClient)
				{
					clients.add(recvClient);
					callback.newNode(recvClient);
					l.info("New client " + recvClient.getAddress());
				}

				runHelper(recvClient, netObj);

				// eventually callback will do processing + sending, right now just echoing
				sendObject(netObj);

				l.info("Object Received from: " + netObj.toString());
			}
			catch (ClassNotFoundException | IOException e)
			{
				e.printStackTrace();
			}
		}
	}
	
	public SocketAddress getSocket() {
		return sock.getLocalSocketAddress();
	}
	
	public HashSet<NetworkNode> getClients() {
		return clients;
	}
}
