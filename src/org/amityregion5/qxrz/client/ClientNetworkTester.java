package org.amityregion5.qxrz.client;

import java.io.Serializable;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.amityregion5.qxrz.client.net.ClientNetworkManager;
import org.amityregion5.qxrz.common.net.NetEventListener;
import org.amityregion5.qxrz.common.net.NetworkNode;

public class ClientNetworkTester
{
	public static void main(String[] args) throws Exception
	{
		ClientNetworkManager manager = new ClientNetworkManager();
		manager.connect("127.0.0.1", 8000);
		
		Logger.getGlobal().setLevel(Level.OFF);
		
		manager.attachEventListener(new NetEventListener()
		{
			int i = 1;
			
			@Override
			public void newNode(NetworkNode c)
			{
				// leave empty
			}
			
			@Override
			public void dataReceived(NetworkNode from, Serializable payload)
			{
				System.out.println("got " + payload + " from " + from.getAddress() + "\t(#" + i++ + ")");
				manager.sendObject(payload);
			}
		});
		
		manager.start();
		
		manager.sendObject("bounce");
	}
}
