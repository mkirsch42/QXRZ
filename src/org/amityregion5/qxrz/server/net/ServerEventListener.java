package org.amityregion5.qxrz.server.net;

import java.net.InetSocketAddress;

public interface ServerEventListener
{
	public void dataReceived(NetworkObject netObj);
	
	public void clientConnected(InetSocketAddress clientSock);
	
	public void clientDisconnected(InetSocketAddress clientSock);
	
	
}
