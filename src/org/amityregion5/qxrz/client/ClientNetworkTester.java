package org.amityregion5.qxrz.client;

import java.io.Serializable;
import java.net.InetSocketAddress;

import org.amityregion5.qxrz.client.net.ClientNetworkManager;
import org.amityregion5.qxrz.common.net.AbstractNetworkNode;
import org.amityregion5.qxrz.common.net.NetEventListener;
import org.amityregion5.qxrz.common.net.NetworkNode;

public class ClientNetworkTester
{
	public static void main(String[] args) throws Exception
	{
		ClientNetworkManager manager = new ClientNetworkManager();
//		manager.connect(, 8000);
//		System.out.println(ClientNetworkManager.getBroadcast());
//		Logger.getGlobal().setLevel(Level.OFF);
		
		manager.attachEventListener(new NetEventListener()
		{
//			int i = 1;
			
			@Override
			public void newNode(AbstractNetworkNode c)
			{
				// This will b
			}
			
			@Override
			public void dataReceived(NetworkNode from, Serializable payload)
			{
				
			}
		});
		
		manager.start();
		manager.broadcastQuery();
		System.out.println("Query sent!");
	}
}
