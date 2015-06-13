package org.amityregion5.qxrz.common.net;

import java.io.Serializable;

public interface NetEventListener
{
	/**
	 * callback method. This methods will gets called each time the manager receive an object.
	 * @param from Sender of this object
	 * @param payload Actual data received.
	 */
	public void dataReceived(NetworkNode from, Serializable payload);
	
	/**
	 * this method gets called each time a new node connected to the manager 
	 * @param c node that just connected
	 */
	public void newNode(AbstractNetworkNode c);
	
	public void nodeRemoved(int i);

}
