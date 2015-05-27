package org.amityregion5.qxrz.server.net;

import java.io.IOException;
import java.io.Serializable;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketAddress;
import java.net.SocketException;
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
	
	public ServerNetworkManager(int p) throws SocketException
	{
		super(p);
	}

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

	public void removeClient(NetworkNode c)
	{
		clients.remove(c);
	}

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

				runHelper(getClientBySocket(ds), netObj);

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
	
	public SocketAddress getSocket() {
		return sock.getLocalSocketAddress();
	}
	
	public HashSet<NetworkNode> getClients() {
		return clients;
	}
}
