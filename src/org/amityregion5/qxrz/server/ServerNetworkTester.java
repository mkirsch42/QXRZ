package org.amityregion5.qxrz.server;

import java.io.Serializable;
import java.net.InetAddress;
import java.net.InetSocketAddress;

import org.amityregion5.qxrz.common.net.NetEventListener;
import org.amityregion5.qxrz.common.net.NetworkNode;
import org.amityregion5.qxrz.server.net.ServerNetworkManager;

public class ServerNetworkTester
{
	public static void main(String[] args) throws Exception
	{
		ServerNetworkManager manager = new ServerNetworkManager("test server", 8000);
//		Logger.getGlobal().setLevel(Level.OFF);
		
		manager.attachEventListener(new NetEventListener()
		{
//			int i = 1;
			
			@Override
			public void newNode(NetworkNode c)
			{
				
			}
			
			@Override
			public void dataReceived(NetworkNode from, Serializable payload)
			{
				
			}

			@Override
			public void serverAdded(InetSocketAddress addr, String name)
			{
				// TODO Auto-generated method stub
				
			}
		});
		System.out.println(InetAddress.getLocalHost());
		manager.start();
	}
}
