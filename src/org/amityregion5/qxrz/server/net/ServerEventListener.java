package org.amityregion5.qxrz.server.net;

import java.net.DatagramSocket;

import org.amityregion5.qxrz.net.NetworkObject;

public interface ServerEventListener
{
	public void dataReceived(NetworkObject netObj);
	
	public void clientConnected(DatagramSocket clientSock);
	
	public void clientDisconnected(DatagramSocket clientSock);
	
	
}
