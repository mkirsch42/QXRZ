package org.amityregion5.qxrz.server;

import java.net.InetAddress;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.amityregion5.qxrz.server.net.ServerNetworkManager;

public class ServerNetworkTester
{
	public static void main(String[] args) throws Exception
	{
		ServerNetworkManager manager = new ServerNetworkManager("test server", 8000);
		Logger.getGlobal().setLevel(Level.OFF);
		
		System.out.println(InetAddress.getLocalHost());
		manager.start();
	}
}
