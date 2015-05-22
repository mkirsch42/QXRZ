package org.amityregion5.qxrz.server.net;

import java.io.IOException;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.util.ArrayList;
import java.util.HashSet;
import org.amityregion5.qxrz.common.net.NetworkObject;
import org.amityregion5.qxrz.common.net.UDPInputStream;
import org.amityregion5.qxrz.common.net.UDPOutputStream;

public class ServerNetworkManager extends Thread
{
	private Thread recvThread;
	private UDPInputStream inStream;
	private UDPOutputStream outStream;
	// Callback functions
	//private HashSet<ServerEventListener> listenerList = new HashSet<ServerEventListener>();
	private ServerEventListener callback;
	
	// List of client sockets
	private HashSet<Client> clients = new HashSet<Client>();

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
		callback = sel;
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
		for (Client c : clients)
		{
			outStream.setSocket(c.getSocket());
			try
			{
				outStream.sendObject(netObj);
			} catch (IOException e)
			{
				e.printStackTrace();
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
				DatagramSocket ds = new DatagramSocket();
				Client c = new Client(ds);
				ds.connect(inStream.getPacket().getSocketAddress());
				if (! clients.contains(c))
				{
//					for (ServerEventListener sel : listenerList)
//					{
//						sel.clientConnected(ds);
//					}
					callback.newClient(c);
					clients.add(c);
				}

				callback.dataReceived(netObj);
				sendNetworkObject(netObj);
//				for (ServerEventListener sel : listenerList)
//				{
//					sel.dataReceived(netObj);
//				}

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
		NetworkObject no = new NetworkObject(new ArrayList<Integer>(), 0);
		ServerNetworkManager snm = new ServerNetworkManager(8000);
		snm.start();

		DatagramSocket ds = new DatagramSocket();

		System.out.println(ds.isConnected());
		ds.connect(InetAddress.getByName("127.0.0.1"), 8000);
		UDPOutputStream uos = new UDPOutputStream();
		uos.setSocket(ds);
		uos.sendObject(no);
		UDPInputStream uis = new UDPInputStream(ds);
		NetworkObject recv = uis.recvObject();
		System.out.println("Client received:" + recv);
		snm.sendNetworkObject(recv);
		System.out.println("object sended!");
	}

}
