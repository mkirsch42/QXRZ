package org.amityregion5.qxrz.common.net;

import java.io.Serializable;

public interface NetEventListener
{
	public void dataReceived(NetworkNode from, Serializable payload);
	
	public void newNode(NetworkNode c);
	
}
