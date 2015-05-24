package org.amityregion5.qxrz.client.net;

import java.io.Serializable;


public interface ClientEventListener
{
	public void dataReceived(Serializable obj);

}
