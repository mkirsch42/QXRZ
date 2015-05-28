package org.amityregion5.qxrz.client;

import java.io.Serializable;

import org.amityregion5.qxrz.client.net.ClientNetworkManager;
import org.amityregion5.qxrz.common.net.NetEventListener;
import org.amityregion5.qxrz.common.net.NetworkNode;

public class ClientNetworkTester
{
	public static void main(String[] args) throws Exception
	{
		ClientNetworkManager manager = new ClientNetworkManager();
		manager.connect("127.0.0.1", 8000);
		
		manager.attachEventListener(new NetEventListener()
		{
			
			@Override
			public void newNode(NetworkNode c)
			{
				// leave empty
			}
			
			@Override
			public void dataReceived(NetworkNode from, Serializable payload)
			{
				System.out.println("got " + payload + " from " + from);
				
			}
		});
		
		manager.start();
		
		manager.sendObject("hello from client");
	}
}
