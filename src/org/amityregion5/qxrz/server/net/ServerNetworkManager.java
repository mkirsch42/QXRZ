package org.amityregion5.qxrz.server.net;

import java.io.Serializable;
import java.net.InetSocketAddress;
import java.net.SocketAddress;
import java.util.HashSet;
import java.util.Iterator;
import java.util.logging.Logger;

import org.amityregion5.qxrz.common.net.AbstractNetworkManager;
import org.amityregion5.qxrz.common.net.BroadcastDiscoveryQuery;
import org.amityregion5.qxrz.common.net.DisconnectNotification;
import org.amityregion5.qxrz.common.net.NetworkNode;
import org.amityregion5.qxrz.common.net.NetworkObject;

public class ServerNetworkManager extends AbstractNetworkManager
{
	// List of client sockets
	private HashSet<NetworkNode> clients = new HashSet<NetworkNode>();

	private Logger l = Logger.getGlobal();

	public ServerNetworkManager(int p) throws Exception
	{
		super(p);
		Runtime.getRuntime().addShutdownHook(new Thread()
		{
			public void run()
			{
				for (Iterator<NetworkNode> it = clients.iterator(); it.hasNext();)
				{
					NetworkNode n = it.next();
					try
					{
						removeClient(n);
					}
					catch (Exception e)
					{
						e.printStackTrace();
					}
				}
				System.exit(0);
			}
		});
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
		try
		{
			c.send(new DisconnectNotification());
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
	}

	@Override
	public void run()
	{
		while (true)
		{
			try
			{
				NetworkObject netObj = (NetworkObject) inStream.recvObject();
				NetworkNode recvClient = new NetworkNode(outStream,
						(InetSocketAddress) inStream.getPacket().getSocketAddress());
				boolean foundClient = false;
				
				l.info("#" + netObj.getPacketNumber() + " " + netObj.getPayload());

				// if this is a discovery query, echo back at the server
				if (netObj.getPayload() instanceof BroadcastDiscoveryQuery)
				{
					recvClient.send(netObj);
				}
				else
				{
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

					if (!foundClient)
					{
						clients.add(recvClient);
						callback.newNode(recvClient);
						l.info("New client " + recvClient.getAddress());
					}

					runHelper(recvClient, netObj);
					if (netObj.getPayload() instanceof DisconnectNotification)
					{
						removeClient(recvClient);
					}
				}

				l.info("Object Received from: " + netObj.toString());
			}
			catch (Exception e)
			{
				e.printStackTrace();
			}
		}
	}

	public SocketAddress getSocket()
	{
		return sock.getLocalSocketAddress();
	}

	public HashSet<NetworkNode> getClients()
	{
		return clients;
	}
}
