package org.amityregion5.qxrz.server.net;

import java.net.InetSocketAddress;

import org.amityregion5.qxrz.net.NetworkObject;

public interface ServerEventListener
{
	public void dataReceived(NetworkObject netObj);
	
	public void clientConnected(InetSocketAddress clientSock);
	
	public void clientDisconnected(InetSocketAddress clientSock);
	
	
}
