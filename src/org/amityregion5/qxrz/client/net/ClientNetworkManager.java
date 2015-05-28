package org.amityregion5.qxrz.client.net;

import java.io.IOException;
import java.io.Serializable;
import java.net.InetAddress;

import org.amityregion5.qxrz.common.net.AbstractNetworkManager;
import org.amityregion5.qxrz.common.net.NetworkNode;
import org.amityregion5.qxrz.common.net.NetworkObject;

public class ClientNetworkManager extends AbstractNetworkManager
{
	private NetworkNode server;
	public ClientNetworkManager(String host, int port) throws Exception
	{
		super();
		
		sock.connect(InetAddress.getByName(host), port);
		
		server = new NetworkNode(sock);
	}

	public void sendObject(Serializable s)
	{
		try
		{
			server.send(outStream, s);
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
				runHelper(server, netObj);
			}
			catch (ClassNotFoundException | IOException e)
			{
				e.printStackTrace();
			}
		}
	}

}
