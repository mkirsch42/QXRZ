package org.amityregion5.qxrz.server.net;

import java.io.Serializable;

public interface ServerEventListener
{
	public void dataReceived(Client c, Serializable payload);
	
	public void newClient(Client c);
	
}
