package org.amityregion5.qxrz.client.net;

import java.io.IOException;
import java.io.Serializable;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.net.UnknownHostException;

import org.amityregion5.qxrz.common.net.NetworkNode;
import org.amityregion5.qxrz.common.net.NetworkObject;
import org.amityregion5.qxrz.common.net.UDPInputStream;
import org.amityregion5.qxrz.common.net.UDPOutputStream;

public class ClientNetworkManager extends Thread
{
	private UDPInputStream inStream;
	private UDPOutputStream outStream;
	private ClientEventListener callback;
	
	private NetworkNode server;

	public ClientNetworkManager(String host, int port) throws SocketException,
			UnknownHostException
	{
		super("clientmanager");
		DatagramSocket sock = new DatagramSocket();
		sock.setReuseAddress(true); // Just in case
		sock.connect(InetAddress.getByName(host), port);
		outStream = new UDPOutputStream(sock);
		inStream = new UDPInputStream(sock);
		
		server = new NetworkNode(sock);
	}
	
	/**
	 * 
	 * @param s
	 *           Object to send
	 * @throws Exception
	 *            Exception will be thrown if having issue with network
	 */
	public void sendObject(Serializable s)
	{
		try
		{
			server.send(outStream, s);
		} catch (Exception e)
		{
			e.printStackTrace();
		}
	}
	
	public void attachClientEventListener(ClientEventListener cel)
	{
		callback = cel;
	}

	@Override
	public void run()
	{
		while (true)
		{
			try
			{
				NetworkObject obj = inStream.recvObject();
				if (callback != null)
				{
					int pn = obj.getPacketNumber();
					if (pn <= server.getReceivedPacketCount())
					{
						continue;
					}
					server.setReceivedPacketCount(server.getReceivedPacketCount() + 1);
					callback.dataReceived(obj.getPayload());
				}
			}
			catch (ClassNotFoundException | IOException e)
			{
				e.printStackTrace();
			}
		}
	}

}
