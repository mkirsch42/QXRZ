package org.amityregion5.qxrz.server.net;

import java.io.IOException;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;

import org.amityregion5.qxrz.net.NetworkObject;
import org.amityregion5.qxrz.net.UDPInputStream;
import org.amityregion5.qxrz.net.UDPOutputStream;

public class ServerNetworkManager extends Thread
{
	private Thread recvThread;
	private UDPInputStream inStream;
	private UDPOutputStream outStream;
	private long timeOffset;
	// Callback functions
	private HashSet<ServerEventListener> listenerList = new HashSet<ServerEventListener>();

	// List of client sockets
	private HashSet<DatagramSocket> clients = new HashSet<DatagramSocket>();

	/**
	 * This will initialize a socket that listens on a port
	 * 
	 * @param port
	 *            port to listen on
	 * @throws IOException
	 */
	public ServerNetworkManager(int port) throws IOException
	{
		super("Server Manager");
		timeOffset = NetworkObject.getNetworkTime()
				- System.currentTimeMillis();
		DatagramSocket sock = new DatagramSocket(port);
		inStream = new UDPInputStream(sock);
		outStream = new UDPOutputStream();
		recvThread = new Thread();
		recvThread.start();
	}

	/**
	 * 
	 * @param sel
	 *            Listener that will be called each time an Object was received
	 */
	public void addServerEventListener(ServerEventListener sel)
	{
		listenerList.add(sel);
	}

	/**
	 * Send an Object over the network
	 * 
	 * @param netObj
	 *            Object that will be sent to all clients
	 * @throws IOException
	 */
	public void sendNetworkObject(NetworkObject netObj)
	{
		netObj.setTimeStamp(System.currentTimeMillis() + timeOffset);
		for (DatagramSocket ds : clients)
		{
			outStream.setSocket(ds);
			try
			{
				outStream.sendObject(netObj);
			} catch (IOException e)
			{
				// client disconnected! uh oh.
				for (ServerEventListener sel : listenerList)
				{
					sel.clientDisconnected(ds);
				}
			}
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

				// Time stamp verification
				if (Math.abs(netObj.getTimeStamp() - System.currentTimeMillis()
						- timeOffset) > 1000 * 30)
				{

				}
				DatagramSocket ds = new DatagramSocket();
				ds.connect(inStream.getPacket().getSocketAddress());
				if (!clients.contains(ds))
				{
					for (ServerEventListener sel : listenerList)
					{
						sel.clientConnected(ds);
					}
					clients.add(ds);
				}
				for (ServerEventListener sel : listenerList)
				{
					sel.dataReceived(netObj);
				}
				sendNetworkObject(netObj);

				System.out.println("Object Received from:");
				System.out.println(netObj);

			} catch (ClassNotFoundException | IOException e)
			{
				e.printStackTrace();
			}
		}
	}

	// for testing
	public static void main(String[] args) throws Exception
	{
		NetworkObject no = new NetworkObject();
		System.out.println(new Date(NetworkObject.getNetworkTime()));
		ServerNetworkManager snm = new ServerNetworkManager(8000);
		snm.start();
		no.type = "Object";
		no.payload = new ArrayList<Integer>();

		DatagramSocket ds = new DatagramSocket();

		System.out.println(ds.isConnected());
		ds.connect(InetAddress.getByName("127.0.0.1"), 8000);
		UDPOutputStream uos = new UDPOutputStream();
		uos.setSocket(ds);
		uos.sendObject(no);
		UDPInputStream uis = new UDPInputStream(ds);
		NetworkObject recv = uis.recvObject();
		System.out.println("Client received:" + recv);
		System.out.println("object sended!");
	}

}
