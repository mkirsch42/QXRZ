package org.amityregion5.qxrz.server.net;

import org.amityregion5.qxrz.net.NetworkObject;

public interface ServerEventListener
{
	public void dataReceived(NetworkObject netObj);
	
	public void clientConnected(Client c);
	
	public void clientDisconnected(Client c);
}
