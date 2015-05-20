package org.amityregion5.qxrz.server.net;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.util.ArrayList;

public class ServerNetworkManager implements Runnable
{
	private Thread recvThread;
	private boolean pause = false;
	private ArrayList<ServerEventListener> list = new ArrayList<ServerEventListener>();
	private UDPInputStream is;
	private UDPOutputStream os;
	
	public ServerNetworkManager(int port) throws IOException
	{
		DatagramSocket ds = new DatagramSocket(port);
		is = new UDPInputStream(ds);
		os = new UDPOutputStream(ds);
		recvThread = new Thread(this);
		recvThread.start();
	}
	
	public void addServerEventListener(ServerEventListener sel)
	{
		list.add(sel);
	}
	
	public void start()
	{
		pause = false;
	}
	
	
	public void stop()
	{
		pause = true;
	}

	public void sendNetworkObject(NetworkObject no) throws IOException
	{
		os.sendObject(no);
	}
	
	@Override
	public void run()
	{
		while(true)
		{
			if(pause)
			{
				try
				{
					Thread.sleep(1000);
				} catch (InterruptedException e)
				{
				}
				continue;
			}
			
			try
			{
				Object obj = is.recvObject();
				NetworkObject no = (NetworkObject)obj;
				for(ServerEventListener sel : list)
				{
					sel.dataReceived(no);
				}
				System.out.println("Object Received!");
				System.out.println(no);
			} catch (ClassNotFoundException | IOException e)
			{
				e.printStackTrace();
			}
		}
	}
	
	
	//This method is for testing purpose
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
