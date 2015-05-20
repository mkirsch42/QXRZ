package org.amityregion5.qxrz.server.net;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.util.ArrayList;

public class ServerNetworkManager extends Thread
{
	private Thread recvThread;
	private ArrayList<ServerEventListener> listenerList = new ArrayList<ServerEventListener>();
	private UDPInputStream inStream;
	private UDPOutputStream outStream;

	/**
	 * This will initialize a socket that listen on a port
	 * 
	 * @param port
	 *            port that listen to
	 * @throws IOException
	 */
	public ServerNetworkManager(int port) throws IOException
	{
		DatagramSocket ds = new DatagramSocket(port);
		inStream = new UDPInputStream(ds);
		outStream = new UDPOutputStream(ds);
		recvThread = new Thread(this);
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
		outStream.sendObject(netObj);
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
				System.out.println("Object Received!");
				System.out.println(netObj);
			} catch (ClassNotFoundException | IOException e)
			{
				e.printStackTrace();
			}
		}
	}

	// This method is for testing purpose
	public static void main(String[] args) throws Exception
	{
		ServerNetworkManager snm = new ServerNetworkManager(8000);
		snm.start();
		NetworkObject no = new NetworkObject();
		no.type = "Object";
		no.payload = new ArrayList<Integer>();

		DatagramSocket ds = new DatagramSocket();
		ds.connect(InetAddress.getByName("127.0.0.1"), 8000);
		UDPOutputStream uos = new UDPOutputStream(ds);
		ObjectOutputStream oos = new ObjectOutputStream(uos);
		oos.writeObject(no);
		oos.close();
		System.out.println("object sended!");
	}

}
