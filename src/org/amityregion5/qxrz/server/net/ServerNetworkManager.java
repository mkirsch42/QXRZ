package org.amityregion5.qxrz.server.net;

import java.io.IOException;
import java.io.Serializable;
import java.net.DatagramSocket;
import java.util.HashSet;
import java.util.Iterator;
import java.util.logging.Logger;

import org.amityregion5.qxrz.common.net.NetworkObject;
import org.amityregion5.qxrz.common.net.UDPInputStream;
import org.amityregion5.qxrz.common.net.UDPOutputStream;

public class ServerNetworkManager extends Thread
{
	private Thread recvThread;
	private UDPInputStream inStream;
	private UDPOutputStream outStream;
	private Logger l = Logger.getLogger("Global");
	// Callback functions
	// private HashSet<ServerEventListener> listenerList = new HashSet<ServerEventListener>();
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
		// this should be lowercase
		super("servermanager");
		DatagramSocket sock = new DatagramSocket();
		sock.setReuseAddress(true); // don't know if this does anything

		inStream = new UDPInputStream(sock);
		outStream = new UDPOutputStream(null);
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
	public void sendObject(Serializable obj)
	{
		for (Client c : clients)
		{
			try
			{
				c.send(outStream, obj);
			} catch (Exception e)
			{
				e.printStackTrace();
			}
		}
	}

	public void removeClient(Client c)
	{
		clients.remove(c);
	}
	
	private Client getClientBySocket(DatagramSocket sock)
	{
		Client c2 = new Client(sock);
		for(Iterator<Client> it = clients.iterator(); it.hasNext(); )
		{
			Client c = it.next();
			if(c.equals(c2))
			{
				return c;
			}
		}
		return null;
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
					if (callback != null)
					{
						callback.newClient(c);
					}
					clients.add(c);
				}
				int pn = netObj.getPacketNumber();
				
				Client c2 = getClientBySocket(ds);
				if(pn < c2.getReceivedPacketCount())
				{
					continue;
				}
				c2.incrementReceivedPacketCount();
				
				if (callback != null)
				{
					callback.dataReceived(c, netObj.getPayload());
				}
				sendObject(netObj);

				l.info("Object Received from:");
				l.info(netObj.toString());

			} catch (ClassNotFoundException | IOException e)
			{
				e.printStackTrace();
			}
		}
	}

	// for testing
	// public static void main(String[] args) throws Exception
	// {
	// NetworkObject no = new NetworkObject(new ArrayList<Integer>());
	// ServerNetworkManager snm = new ServerNetworkManager(8000);
	// snm.start();
	//
	// DatagramSocket ds = new DatagramSocket();
	//
	// l.info(ds.isConnected());
	// ds.connect(InetAddress.getByName("127.0.0.1"), 8000);
	// UDPOutputStream uos = new UDPOutputStream();
	// uos.setSocket(ds);
	// uos.sendObject(no);
	// UDPInputStream uis = new UDPInputStream(ds);
	// NetworkObject recv = uis.recvObject();
	// l.info("Client received:" + recv);
	// snm.sendObject(recv);
	// l.info("object sended!");
	// }

}
