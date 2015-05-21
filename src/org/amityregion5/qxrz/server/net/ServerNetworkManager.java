package org.amityregion5.qxrz.server.net;

import java.io.IOException;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.util.ArrayList;

public class ServerNetworkManager extends Thread
{
	private Thread recvThread;
	private ArrayList<ServerEventListener> listenerList = new ArrayList<ServerEventListener>();
	private UDPInputStream inStream;
	private UDPOutputStream outStream;
	private ArrayList<DatagramSocket> clients = new ArrayList<DatagramSocket>();
	/**
	 * This will initialize a socket that listen on a port
	 * 
	 * @param port
	 *            port that listen to
	 * @throws IOException
	 */
	public ServerNetworkManager(int port) throws IOException
	{
		super("Server Manager");
		
		DatagramSocket sock = new DatagramSocket(port);
		inStream = new UDPInputStream(sock);
		outStream = new UDPOutputStream(sock);
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
	 * @param netObj Object that will be sent to all clients
	 * @throws IOException
	 */
	public void sendNetworkObject(NetworkObject netObj) throws IOException
	{
		for(DatagramSocket ds : clients)
		{
			outStream.setSocket(ds);
			outStream.sendObject(netObj);
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
				for (ServerEventListener sel : listenerList)
				{
					sel.dataReceived(netObj);
				}
				DatagramSocket ds = new DatagramSocket(inStream.getDp().getSocketAddress());
				clients.add(ds);
				System.out.println("Object Received from:");
				System.out.println(netObj);
				sendNetworkObject(netObj);
			} catch (ClassNotFoundException | IOException e)
			{
				e.printStackTrace();
			}
		}
	}

	// for testing
	public static void main(String[] args) throws Exception
	{
		ServerNetworkManager snm = new ServerNetworkManager(8000);
		snm.start();
		NetworkObject no = new NetworkObject();
		no.type = "Object";
		no.payload = new ArrayList<Integer>();
		
		DatagramSocket ds = new DatagramSocket();
		
		System.out.println(ds.isConnected());
		ds.connect(InetAddress.getByName("127.0.0.1"), 8000);
		UDPOutputStream uos = new UDPOutputStream(ds);
		uos.sendObject(no);
		UDPInputStream uis = new UDPInputStream(ds);
		NetworkObject recv = uis.recvObject();
		System.out.println("Client received:" + recv);
		System.out.println("object sended!");
	}

}
