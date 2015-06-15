package org.amityregion5.qxrz.client.net;

import java.io.IOException;
import java.io.Serializable;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.InterfaceAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.util.Enumeration;
import java.util.logging.Logger;

import org.amityregion5.qxrz.common.net.AbstractNetworkManager;
import org.amityregion5.qxrz.common.net.AbstractNetworkNode;
import org.amityregion5.qxrz.common.net.BroadcastDiscoveryQuery;
import org.amityregion5.qxrz.common.net.ChatMessage;
import org.amityregion5.qxrz.common.net.Goodbye;
import org.amityregion5.qxrz.common.net.Hello;
import org.amityregion5.qxrz.common.net.NetworkNode;
import org.amityregion5.qxrz.common.net.NetworkObject;

public class ClientNetworkManager extends AbstractNetworkManager
{
	private NetworkNode server;
	private static Logger l = Logger.getGlobal();
	private boolean running = true;
	private boolean isConnected = false;
	private String username;

	public ClientNetworkManager() throws Exception
	{
		super();
	}

	public boolean isConnectedTo(InetSocketAddress address)
	{
		if (server == null || !isConnected)
		{
			return false;
		}
		return address.equals(server.getAddress());
	}

	/**
	 * Send a chat message to the server
	 * 
	 * @param message
	 *            String message itself
	 */
	public void sendChatMessage(String message)
	{
		ChatMessage cm = new ChatMessage(message, server.getAddress());
		sendObject(cm);
	}

	/**
	 * Send a disconnect notification to the server. This will cause the client
	 * to disconnect from the server.
	 * 
	 */
	public void sendGoodbye()
	{
		sendObject(new Goodbye());
		isConnected = false;
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
		l.info(getBroadcast().toString());
		NetworkNode broadcast = new NetworkNode(outStream,
				new InetSocketAddress(getBroadcast(), 8000));
		l.info(broadcast.getAddress().toString());
		broadcast.send(new BroadcastDiscoveryQuery());
	}

	private static InetAddress getBroadcast() throws SocketException,
			UnknownHostException
	{
		Enumeration<NetworkInterface> addresses = NetworkInterface
				.getNetworkInterfaces();
		while (addresses.hasMoreElements())
		{
			NetworkInterface networkInterface = addresses.nextElement();
			if (!networkInterface.isLoopback())
			{
				for (InterfaceAddress interfaceAddress : networkInterface
						.getInterfaceAddresses())
				{
					InetAddress bcast = interfaceAddress.getBroadcast();
					if (bcast != null)
					{
						return bcast;
					}
				}
			}
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
		isConnected = sendObject(new Hello(username));
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
	 * 
	 * @param s
	 *            Object that needs to be send
	 * @return did it send somewhere/did it work?
	 */
	public boolean sendObject(Serializable s)
	{
		try
		{
			if (server != null)
			{
				server.send(s);
				return true;
			}
		} catch (Exception e)
		{
			e.printStackTrace();
		}
		return false;
	}

	@Override
	public void run()
	{
		while (running)
		{
			try
			{
				NetworkObject netObj = (NetworkObject) inStream.recvObject();

				// Reply to Broadcast query from server!
				if (netObj.getPayload() instanceof AbstractNetworkNode)
				{
					AbstractNetworkNode info = (AbstractNetworkNode) netObj
							.getPayload();
					info.setAddress((InetSocketAddress) inStream.getPacket()
							.getSocketAddress());
					callback.newNode(info);
				}

				/*
				 * If server has not been set yet, ignore all other packets.
				 * Though we probably shouldn't be receiving any packets if we
				 * haven't joined the game yet
				 */
				if (server != null)
				{
					// System.out.println(netObj.getPacketNumber() + "/" +
					// server.getReceivedPacketCount());
					// System.out.println(netObj.getPayload().getClass().getSimpleName());
					runHelper(server, netObj);
				}

			} catch (ClassNotFoundException | IOException e)
			{
				e.printStackTrace();
			}
		}
	}

	public void close()
	{
		running = false;
	}

	public boolean isConnected()
	{
		return isConnected;
	}

	public String getUsername()
	{
		return username;
	}

	public void setUsername(String username)
	{
		this.username = username;
	}
}
