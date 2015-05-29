package org.amityregion5.qxrz.server;

import java.io.Serializable;
import java.net.InetAddress;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.amityregion5.qxrz.common.net.NetEventListener;
import org.amityregion5.qxrz.common.net.NetworkNode;
import org.amityregion5.qxrz.server.net.ServerNetworkManager;

public class ServerNetworkTester
{
	public static void main(String[] args) throws Exception
	{
		ServerNetworkManager manager = new ServerNetworkManager(8000);
		//Logger.getGlobal().setLevel(Level.OFF);
		
		manager.attachEventListener(new NetEventListener()
		{
			int i = 1;
			
			@Override
			public void newNode(NetworkNode c)
			{
				manager.sendObject("bounce");
			}
			
			@Override
			public void dataReceived(NetworkNode from, Serializable payload)
			{
				System.out.println("got " + payload + " from " + from.getAddress() + " (#" + i++ + ")");
				manager.sendObject(payload);
			}
		});
		System.out.println(InetAddress.getLocalHost());
		manager.start();
	}
}
