package org.amityregion5.qxrz.server;

import java.io.Serializable;

import org.amityregion5.qxrz.common.net.NetEventListener;
import org.amityregion5.qxrz.common.net.NetworkNode;
import org.amityregion5.qxrz.server.net.ServerNetworkManager;

public class ServerNetworkTester
{
	public static void main(String[] args) throws Exception
	{
		ServerNetworkManager manager = new ServerNetworkManager(8000);
		
		manager.attachEventListener(new NetEventListener()
		{
			@Override
			public void newNode(NetworkNode c)
			{

			}
			
			@Override
			public void dataReceived(NetworkNode from, Serializable payload)
			{
				System.out.println("got " + payload + " from " + from);
				manager.sendObject("everyone say hello to " + from.getAddress());
			}
		});
		
		manager.start();
	}
}
