package org.amityregion5.qxrz.common.net;

import java.io.Serializable;

/* This is what servers reply to Broadcast Queries with.
 * 
 */
public class ServerInfo implements Serializable
{
	private static final long serialVersionUID = 1L;
	
	private String name;
	
	public ServerInfo(String serverName)
	{
		name = serverName;
	}
	
	public String getName()
	{
		return name;
	}
}
