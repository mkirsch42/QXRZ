package org.amityregion5.qxrz.server.net;

import java.io.Serializable;

import org.amityregion5.qxrz.common.net.NetworkNode;

public interface ServerEventListener
{
	public void dataReceived(NetworkNode c, Serializable payload);
	
	public void newClient(NetworkNode c);
	
}
