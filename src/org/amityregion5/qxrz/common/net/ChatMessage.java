package org.amityregion5.qxrz.common.net;

import java.io.Serializable;
import java.net.DatagramSocket;

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
	
	// For server
	public ChatMessage(String m, NetworkNode f)
	{
		message = m;
		from = f;
	}
	
	public ChatMessage setNode(NetworkNode c)
	{
		from = c;
		return this;
	}
}
