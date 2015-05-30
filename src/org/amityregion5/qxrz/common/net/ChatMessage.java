package org.amityregion5.qxrz.common.net;

import java.io.Serializable;

public class ChatMessage implements Serializable
{
	private static final long serialVersionUID = 1L;
	
	private String message;
	private NetworkNode from;
	
	// For clients
	public ChatMessage(String m)
	{
		message = m;
	}
	
	public ChatMessage setNode(NetworkNode c)
	{
		from = c;
		return this;
	}
	
	public NetworkNode getSender()
	{
		return from;
	}
	
	public String getMessage()
	{
		return message;
	}
}
