package org.amityregion5.qxrz.server.net;

import org.amityregion5.qxrz.net.NetworkObject;

//TODO dataReceived handler will have to check for disconnected-notification
public interface ServerEventListener
{
	public void dataReceived(NetworkObject netObj);
	
	public void newClient(Client c);
}
