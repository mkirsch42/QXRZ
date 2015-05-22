package org.amityregion5.qxrz.server.net;

import org.amityregion5.qxrz.common.net.NetworkObject;

public interface ServerEventListener
{
	public void dataReceived(Client c, NetworkObject netObj);
	
	public void newClient(Client c);
	
	public void clientRemoved(Client c);
	
}
