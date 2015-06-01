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
import org.amityregion5.qxrz.common.net.ServerInfo;

public class ServerNetworkManager extends AbstractNetworkManager
{
	// List of client sockets
	private HashSet<NetworkNode> clients = new HashSet<NetworkNode>();

	private Logger l = Logger.getGlobal();
	private ServerInfo info;
	
	private boolean running = true;

	/**
	 * To construct a server, pass in a name and a port to listen on
	 * 
	 * @param name
	 *            Name of the server.
	 * @param p
	 *            port to listen to
	 * @throws Exception
	 *             Throw exception if failed to open a socket.
	 */
	public ServerNetworkManager(String name, int p) throws Exception
	{
		super(p);
		info = new ServerInfo(name);
		Runtime.getRuntime().addShutdownHook(new Thread()
		{
			public void run()
			{
				for (Iterator<NetworkNode> it = clients.iterator(); it
						.hasNext();)
				{
					NetworkNode n = it.next();
					try
					{
						removeClient(n);
					} catch (Exception e)
					{
						e.printStackTrace();
					}
				}
				System.exit(0);
			}
		});
	}

	/**
	 * Sends given object to all clients.
	 * 
	 * @param obj
	 *            Object that needs to be send.
	 */
	public void sendObject(Serializable obj)
	{
		for (NetworkNode c : clients)
		{
			try
			{
				c.send(obj);
			} catch (Exception e)
			{
				e.printStackTrace();
			}
		}
	}

	/**
	 * Remove a client from the list. This function will tell the client it has
	 * been disconnected so it will stop pestering the server. Note that when a
	 * client disconnects, it will be automatically removed.
	 * 
	 * @param c
	 *            Client that need to be removed
	 */
	public void removeClient(NetworkNode c)
	{
		clients.remove(c);
		try
		{
			c.send(new DisconnectNotification());
		} catch (Exception e)
		{
			e.printStackTrace();
		}
	}

	@Override
	public void run()
	{
		while (running)
		{
			try
			{
				NetworkObject netObj = (NetworkObject) inStream.recvObject();
				NetworkNode recvClient = new NetworkNode(outStream,
						(InetSocketAddress) inStream.getPacket()
								.getSocketAddress());
				boolean foundClient = false;

				l.info("#" + netObj.getPacketNumber() + " "
						+ netObj.getPayload());

				// if this is a discovery query, echo back at the server
				if (netObj.getPayload() instanceof BroadcastDiscoveryQuery)
				{
					l.info("Query received!");
					recvClient.send(info);
				} else
				{
					for (Iterator<NetworkNode> it = clients.iterator(); it
							.hasNext();)
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
				}

				l.info("Object Received from: " + netObj.toString());
			} catch (Exception e)
			{
				e.printStackTrace();
			}
		}
	}
	/**
	 * return the IP and port of this server
	 * @return SocketAddress including ip and port
	 */
	public SocketAddress getSocket()
	{
		return sock.getLocalSocketAddress();
	}

	public HashSet<NetworkNode> getClients()
	{
		return clients;
	}
	
	public void close()
	{
		running = false;
	}
}
