package org.amityregion5.qxrz.server.net;

import java.io.Serializable;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.SocketAddress;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.logging.Logger;

import org.amityregion5.qxrz.common.net.AbstractNetworkManager;
import org.amityregion5.qxrz.common.net.AbstractNetworkNode;
import org.amityregion5.qxrz.common.net.BroadcastDiscoveryQuery;
import org.amityregion5.qxrz.common.net.Goodbye;
import org.amityregion5.qxrz.common.net.Hello;
import org.amityregion5.qxrz.common.net.NetworkNode;
import org.amityregion5.qxrz.common.net.NetworkObject;

public class ServerNetworkManager extends AbstractNetworkManager
{
	// List of client sockets
	private ArrayList<NetworkNode> clients = new ArrayList<NetworkNode>();

	private Logger l = Logger.getGlobal();
	private AbstractNetworkNode info;

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
		info = new AbstractNetworkNode(name);
		Runtime.getRuntime().addShutdownHook(new Thread()
		{
			public void run()
			{
				synchronized (clients)
				{
					for(int i = 0; i < clients.size(); i ++)
					{
						removeClient(i);
					}
				}
			}
		});
	}

	/**
	 * Sends given object to all clients.
	 * 
	 * @param obj
	 *            Object that needs to be send.
	 */
	public boolean sendObject(Serializable obj)
	{
		for (int i = 0; i < clients.size(); i ++)
		{
			NetworkNode c = clients.get(i);
			try
			{
				c.send(obj);
			} catch (Exception e)
			{
				e.printStackTrace();
			}
		}
		return true;
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
		synchronized (clients)
		{
			clients.remove(c);
		}
		try
		{
			c.send(new Goodbye());
		} catch (Exception e)
		{
			e.printStackTrace();
		}
	}

	public NetworkNode removeClient(int index)
	{
		NetworkNode c = clients.remove(index);
		try
		{
			c.send(new Goodbye());
		} catch (Exception e)
		{
			e.printStackTrace();
		}
		return c;
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
						(InetSocketAddress) inStream.getPacket()
								.getSocketAddress());
				boolean foundClient = false;

				l.info("#" + netObj.getPacketNumber() + " "
						+ netObj.getPayload());

				// if this is a discovery query, echo back at the server
				if (netObj.getPayload() instanceof BroadcastDiscoveryQuery)
				{
					l.info("Query received!");
					// System.out.println("query");
					recvClient.send(info);
				} else
				{
					if (netObj.getPayload() instanceof Hello)
					{
						recvClient.setName(((Hello) netObj.getPayload())
								.getName());
					}
					for (int i = 0; i < clients.size(); i ++)
					{
						NetworkNode c = clients.get(i);
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
						synchronized (clients)
						{
							clients.add(recvClient);
						}
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
	 * 
	 * @return SocketAddress including ip and port
	 */
	public SocketAddress getSocket()
	{
		try
		{
			return new InetSocketAddress(InetAddress.getLocalHost(),
					this.sock.getLocalPort());
		} catch (UnknownHostException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}

	public ArrayList<NetworkNode> getClients()
	{
		return clients;
	}
}
