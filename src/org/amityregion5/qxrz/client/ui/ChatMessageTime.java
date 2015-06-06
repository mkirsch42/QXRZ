package org.amityregion5.qxrz.client.ui;

import org.amityregion5.qxrz.common.net.ChatMessage;

public class ChatMessageTime {
	public ChatMessage msg;
	public long time;
	/**
	 * @param msg
	 * @param time
	 */
	public ChatMessageTime(ChatMessage msg, long time) {
		this.msg = msg;
		this.time = time;
	}
	
	
}
