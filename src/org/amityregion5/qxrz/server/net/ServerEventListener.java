package org.amityregion5.qxrz.server.net;

public interface ServerEventListener
{
	public void dataReceived(NetworkObject nObj);
	
	public void clientConnected(ClientInfo pi);
	
	public void clientDisconnected(ClientInfo pi);
	
	
}
