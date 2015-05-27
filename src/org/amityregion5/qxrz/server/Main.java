package org.amityregion5.qxrz.server;

import java.io.IOException;
import java.io.Serializable;

import org.amityregion5.qxrz.common.net.ChatMessage;
import org.amityregion5.qxrz.common.net.DisconnectNotification;
import org.amityregion5.qxrz.common.net.NetEventListener;
import org.amityregion5.qxrz.common.net.NetworkNode;
import org.amityregion5.qxrz.server.net.ServerNetworkManager;
import org.amityregion5.qxrz.server.ui.MainGui;
import org.amityregion5.qxrz.server.world.DebugDraw;
import org.amityregion5.qxrz.server.world.entity.PlayerEntity;
import org.amityregion5.qxrz.server.world.entity.ProjectileEntity;
import org.amityregion5.qxrz.server.world.entity.RectangleHitbox;

public final class Main
{
	public static void main(String[] args) throws InterruptedException, IOException
	{
		
		ServerNetworkManager netManager = new ServerNetworkManager(8000);
		
		netManager.attachEventListener(new NetEventListener()
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
					RectangleHitbox uhb = u.getHitbox();
					int health = u.getHealth();
				}
				
				else if(netObj instanceof ProjectileEntity)
				{
					ProjectileEntity u = (ProjectileEntity) netObj;
					RectangleHitbox uhb = u.getHitBox();
				}
				
				else if(netObj instanceof DisconnectNotification)
				{
					// also stop drawing player and stuff
					netManager.removeClient(c);
				}
				else if(netObj instanceof ChatMessage)
				{
					// echo it back out
					netManager.sendObject(((ChatMessage) netObj).setNode(c));
				}
			}
		});
		
		netManager.start();
		// How to send things to all clients:
		// netManager.sendObject(whatever);
		
		new MainGui().show();
		Game g = new Game();
		if(DebugConstants.DEBUG_GUI)
		{
			Game.debug = DebugDraw.setup(g.getWorld());
		}
		g.run();
	}

}
