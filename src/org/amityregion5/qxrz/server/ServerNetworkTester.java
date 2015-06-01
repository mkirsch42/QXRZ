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
		
		System.out.println(InetAddress.getLocalHost());
		manager.start();
	}
}
