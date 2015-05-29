package org.amityregion5.qxrz.common.net;

import java.io.Serializable;
import java.net.InetSocketAddress;

public class ServerInfo implements Serializable
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	public InetSocketAddress addr;
	public String name;
	public String description;
	
	public ServerInfo(InetSocketAddress a, String serverName, String serverDescription)
	{
		this.addr = a;
		this.name = serverName;
		this.description = serverDescription;
	}
	
	public String toString()
	{
		return name + ", " + addr.toString();
	}

}
