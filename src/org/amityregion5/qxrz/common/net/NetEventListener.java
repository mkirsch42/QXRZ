package org.amityregion5.qxrz.common.net;

import java.io.Serializable;
import java.net.InetSocketAddress;

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
	public void newNode(NetworkNode c);
	
	/**
	 * gets called each time a new server is discovered
	 * @param addr Address of the server
	 * @param name Name of the Server
	 */
	public void serverAdded(InetSocketAddress addr, String name);
	
}
