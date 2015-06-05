package org.amityregion5.qxrz.common.net;

import java.io.Serializable;
import java.net.InetSocketAddress;

public class ChatMessage implements Serializable
{
	private static final long serialVersionUID = 1L;

	private String message;
	private InetSocketAddress from;

	// For clients
	/**
	 * Constructs a ChatMessage
	 * 
	 * @param m
	 *            Message itself.
	 */
	public ChatMessage(String m)
	{
		message = m;
	}

	// For server
	/**
	 * Constructs a ChageMessage. This method should only used by the server
	 * 
	 * @param m
	 *            Message itself.
	 * @param f
	 *            sender of the message
	 */
	public ChatMessage(String m, InetSocketAddress addr)
	{
		message = m;
		setFrom(addr);
	}
	

	/**
	 * Return the message
	 * @return message itself
	 */
	public String getMessage()
	{
		return message;
	}

	public InetSocketAddress getFrom()
	{
		return from;
	}

	public ChatMessage setFrom(InetSocketAddress f)
	{
		from = f;
		return this;
	}
}
