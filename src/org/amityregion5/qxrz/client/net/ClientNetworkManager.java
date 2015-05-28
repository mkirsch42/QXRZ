package org.amityregion5.qxrz.client.net;

import java.io.IOException;
import java.io.Serializable;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.SocketException;
import java.net.UnknownHostException;

import org.amityregion5.qxrz.common.net.AbstractNetworkManager;
import org.amityregion5.qxrz.common.net.DisconnectNotification;
import org.amityregion5.qxrz.common.net.NetworkNode;
import org.amityregion5.qxrz.common.net.NetworkObject;

public class ClientNetworkManager extends AbstractNetworkManager
{
	private NetworkNode server;
	
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
	
	public void broadcastQuery()
	{
		// do fancy broadcast stuff here
		// then wait on the callback
		// MAKE SURE TO ATTACH CALLBACK BEFORE LISTENING duh
	}
	
	// Once server responds to ping, we can connect
	public void connect(InetSocketAddress addr) throws SocketException
	{
		server = new NetworkNode(outStream, addr);
	}
	
	// this is if user wants to manually connect
	public void connect(String host, int port) throws SocketException, UnknownHostException
	{
		connect(new InetSocketAddress(InetAddress.getByName(host), port));
	}

	public void sendObject(Serializable s)
	{
		try
		{
			server.send(s);
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
				System.out.println("client received something");
				runHelper(server, netObj);
			}
			catch (ClassNotFoundException | IOException e)
			{
				e.printStackTrace();
			}
		}
	}

}