package org.amityregion5.qxrz.client;

import java.io.Serializable;

import org.amityregion5.qxrz.client.net.ClientNetworkManager;
import org.amityregion5.qxrz.common.net.AbstractNetworkNode;
import org.amityregion5.qxrz.common.net.ChatMessage;
import org.amityregion5.qxrz.common.net.NetEventListener;
import org.amityregion5.qxrz.common.net.NetworkNode;
import org.amityregion5.qxrz.common.net.ServerInfo;

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
				System.out.println("Server Founded!" + c.getAddress() + " name: " + ((ServerInfo) c).getName());
			}
			
			@Override
			public void dataReceived(NetworkNode from, Serializable payload)
			{
				if(payload instanceof ChatMessage)
				{
					ChatMessage cm = (ChatMessage)payload;
					String s = cm.getMessage();
				}
			}
		});
		
		manager.start();
		manager.broadcastQuery();
		System.out.println("Query sent!");
	}
}
