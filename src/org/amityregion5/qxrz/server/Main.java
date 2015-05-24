package org.amityregion5.qxrz.server;

import java.io.IOException;
import java.io.Serializable;

import org.amityregion5.qxrz.common.net.DisconnectNotification;
import org.amityregion5.qxrz.common.net.NetEventListener;
import org.amityregion5.qxrz.common.net.NetworkNode;
import org.amityregion5.qxrz.server.net.ServerNetworkManager;
import org.amityregion5.qxrz.server.ui.MainGui;
import org.amityregion5.qxrz.server.world.entity.PlayerEntity;
import org.amityregion5.qxrz.server.world.entity.ProjectileEntity;

public final class Main
{
	public static void main(String[] args) throws InterruptedException, IOException
	{
		
		ServerNetworkManager netManager = new ServerNetworkManager(8000);
		
		netManager.attachServerEventListener(new NetEventListener()
		{
			@Override
			public void newNode(NetworkNode c)
			{
				// TODO do stuff for new client (drawing, inventory whatever)
			}
			
			@Override
			public void dataReceived(NetworkNode c, Serializable netObj)
			{
				if(netObj instanceof PlayerEntity) 
				{
					PlayerEntity u = (PlayerEntity) netObj;
//					netManager.sendObject(u);
				}
				
				else if(netObj instanceof ProjectileEntity)
				{
					ProjectileEntity u = (ProjectileEntity) netObj;
//					netManager.sendObject(u);
				}
				
				else if(netObj instanceof DisconnectNotification)
				{
					// also stop drawing player and stuff
					netManager.removeClient(c);
				}
				
			}
		});
		// How to send things to all clients:
		// netManager.sendObject(whatever);
		
		new MainGui().show();
		new Game().run();;
	}

}
