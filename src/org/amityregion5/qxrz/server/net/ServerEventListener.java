package org.amityregion5.qxrz.server.net;

public interface ServerEventListener
{
	public void dataReceived(NetworkObject nObj);
	
	public void playerConnected(PlayerInfo pi);
	
	public void playerRemoved(PlayerInfo pi);
	
	
}
