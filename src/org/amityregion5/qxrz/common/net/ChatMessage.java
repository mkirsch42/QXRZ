package org.amityregion5.qxrz.common.net;

import java.io.Serializable;

public class ChatMessage implements Serializable
{
	private static final long serialVersionUID = 1L;

	private String message;
	private NetworkNode from;

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

	public NetworkNode getSender()
	{
		return from;
	}

	/**
	 * Return the message
	 * @return message itself
	 */
	public String getMessage()
	{
		return message;
	}
}
