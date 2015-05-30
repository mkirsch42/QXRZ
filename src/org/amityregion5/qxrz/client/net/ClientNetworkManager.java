package org.amityregion5.qxrz.client.net;

import java.io.IOException;
import java.io.Serializable;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.InterfaceAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.util.Iterator;
import java.util.List;
import java.util.logging.Logger;

import org.amityregion5.qxrz.common.net.AbstractNetworkManager;
import org.amityregion5.qxrz.common.net.BroadcastDiscoveryQuery;
import org.amityregion5.qxrz.common.net.DisconnectNotification;
import org.amityregion5.qxrz.common.net.NetworkNode;
import org.amityregion5.qxrz.common.net.NetworkObject;
import org.amityregion5.qxrz.common.net.ServerInfo;

public class ClientNetworkManager extends AbstractNetworkManager
{
	private NetworkNode server;
	private Logger l = Logger.getGlobal();

	public ClientNetworkManager() throws Exception
	{
		super();
		Runtime.getRuntime().addShutdownHook(new Thread()
		{
			public void run()
			{
				sendObject(new DisconnectNotification());
			}
		});
	}

	/**
	 * Run this when you are looking for servers. Servers will respond with
	 * their information, which you can receive through your dataReceived()
	 * handler.
	 * 
	 * @throws Exception
	 */
	public void broadcastQuery() throws Exception
	{
		NetworkNode broadcast = new NetworkNode(outStream,
				new InetSocketAddress(getBroadcast(), 8000));
		broadcast.send(new BroadcastDiscoveryQuery());
	}

	private static InetAddress getBroadcast() throws SocketException,
			UnknownHostException
	{
		List<InterfaceAddress> addresses = NetworkInterface.getByInetAddress(
				InetAddress.getLocalHost()).getInterfaceAddresses();
		for (Iterator<InterfaceAddress> it = addresses.iterator(); it.hasNext();)
		{
			InterfaceAddress addr = it.next();
			if (addr.getBroadcast() != null)
				return addr.getBroadcast();
		}
		return null; // won't happen on a working computer
	}

	// Once server responds to ping, we can connect
	/**
	 * Connect to a server
	 * 
	 * @param addr
	 *            SocketAddress of the server. This includes ip and port.
	 * @throws SocketException
	 *             Throw if failed to open socket
	 */
	public void connect(InetSocketAddress addr) throws SocketException
	{
		server = new NetworkNode(outStream, addr);
	}

	// this is if user wants to manually connect
	/**
	 * Connect to a server
	 * 
	 * @param host
	 *            Address of the server
	 * @param port
	 *            Port to connect to
	 * @throws SocketException
	 *             Throw if failed to open a socket
	 * @throws UnknownHostException
	 *             If the host is an invalid address
	 */
	public void connect(String host, int port) throws SocketException,
			UnknownHostException
	{
		connect(new InetSocketAddress(InetAddress.getByName(host), port));
	}

	/**
	 * Sends the given object to server
	 * @param s
	 *            Object that needs to be send
	 */
	public void sendObject(Serializable s)
	{
		try
		{
			server.send(s);
		} catch (Exception e)
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

				// Reply to Broadcast query from server!
				if (netObj.getPayload() instanceof ServerInfo)
				{
					ServerInfo info = (ServerInfo) netObj.getPayload();
					info.setAddress((InetSocketAddress) inStream.getPacket().getSocketAddress());
					callback.newNode(info);
				}

				/* If server has not been set yet, ignore all other packets.
				 * Though we probably shouldn't be receiving any packets if we haven't joined the game yet
				 */
				if (server != null)
				{
					runHelper(server, netObj);
				}

			} catch (ClassNotFoundException | IOException e)
			{
				e.printStackTrace();
			}
		}
	}

}