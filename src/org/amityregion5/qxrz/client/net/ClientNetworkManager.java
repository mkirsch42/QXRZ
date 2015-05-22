package org.amityregion5.qxrz.client.net;

import java.io.IOException;
import java.io.Serializable;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.net.UnknownHostException;

import org.amityregion5.qxrz.common.net.NetworkObject;
import org.amityregion5.qxrz.common.net.UDPInputStream;
import org.amityregion5.qxrz.common.net.UDPOutputStream;

public class ClientNetworkManager implements Runnable
{
	private int packetNumber = 0;
	private UDPOutputStream outStream;
	private UDPInputStream inStream;
	boolean pause = false;
	private Thread recvThread;
	boolean running = true;
	private ClientEventListener callback;

	public void sendObject(Serializable s) throws Exception
	{
		NetworkObject netObj = new NetworkObject(s, packetNumber);
		packetNumber++;
		outStream.sendObject(netObj);
	}

	public void start()
	{
		pause = false;
	}

	/*
	 * Maybe? public void stop() { running = false; }
	 */

	/*
	 * public void pause() { pause = true; }
	 */
	public ClientNetworkManager(String host, int port) throws SocketException,
			UnknownHostException
	{
		DatagramSocket ds = new DatagramSocket();
		ds.setReuseAddress(true);// Just in case?
		ds.connect(InetAddress.getByName(host), port);
		outStream = new UDPOutputStream(ds);
		inStream = new UDPInputStream(ds);
		recvThread = new Thread(this);
		recvThread.start();
	}

	@Override
	public void run()
	{
		while (running)
		{
			while (pause)
			{
				try
				{
					Thread.sleep(1000);
				} catch (InterruptedException e)
				{
				}

			}
			try
			{
				NetworkObject obj = inStream.recvObject();
				if (callback != null)
				{
					callback.dataReceived(obj.getPayload());
				}
			} catch (ClassNotFoundException | IOException e)
			{
				e.printStackTrace();
			}

		}
	}

}
