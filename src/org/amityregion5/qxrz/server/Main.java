package org.amityregion5.qxrz.server;

import java.io.Serializable;

import org.amityregion5.qxrz.common.control.NetworkInputData;
import org.amityregion5.qxrz.common.net.AbstractNetworkNode;
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
import org.amityregion5.qxrz.server.world.gameplay.Player;
//github.com/mkirsch42/QXRZ.gitimport org.amityregion5.qxrz.common.net.ChatMessage;

public final class Main
{
	private static Game g;
	
	public static void main(String[] args) throws Exception
	{
		
		ServerNetworkManager netManager = new ServerNetworkManager("Sergey Server", 8000);
		//TODO maybe all the manager stuff should be created within the GUI
		netManager.attachEventListener(new NetEventListener()
		{
			@Override
			public void newNode(AbstractNetworkNode c)
			{
				Player p = new Player();
				g.addPlayer((NetworkNode) c, p);
				// TODO do stuff for new client (drawing, inventory whatever)
				// You should cast c to a NetworkNode before using
			}
			
			@Override
			public void dataReceived(NetworkNode c, Serializable netObj)
			{
				if(netObj instanceof NetworkInputData)
				{
					Player from = g.findPlayer(c);
					from.input((NetworkInputData)netObj);
					System.out.println("I got data!");
				}
				
				if(netObj instanceof PlayerEntity) 
				{
					PlayerEntity u = (PlayerEntity) netObj;
					RectangleHitbox uhb = u.getHitbox();
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
					netManager.sendObject(((ChatMessage) netObj)/*.setNode(c)*/);
				}
			}
		});
		
		netManager.start();
		

		//new MainGui().show();

		new MainGui(netManager).show();
		g = new Game(); //TODO game needs access to network, too...
		//TODO server panel should show actual IP, not 0.0.0.0
		if(DebugConstants.DEBUG_GUI)
		{
			Game.debug = DebugDraw.setup(g.getWorld());
		}
		g.run();
	}

}
