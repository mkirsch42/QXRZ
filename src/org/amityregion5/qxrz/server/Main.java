package org.amityregion5.qxrz.server;

import java.awt.Rectangle;
import java.io.IOException;

import javax.swing.JApplet;

import org.amityregion5.qxrz.common.net.NetworkObject;
import org.amityregion5.qxrz.server.net.Client;
import org.amityregion5.qxrz.server.net.ServerEventListener;
import org.amityregion5.qxrz.server.net.ServerNetworkManager;
import org.amityregion5.qxrz.server.ui.MainGui;
import org.amityregion5.qxrz.server.world.DebugDraw;
import org.amityregion5.qxrz.server.world.Obstacle;
import org.amityregion5.qxrz.server.world.World;
import org.amityregion5.qxrz.server.world.entity.PlayerEntity;
import org.amityregion5.qxrz.server.world.entity.RectangleHitbox;

public final class Main
{

	private static final int UPDATE_RATE = 20;
	
	public static void main(String[] args) throws InterruptedException, IOException
	{
		new MainGui().show();
		
		// Main game loop, to be moved to separate class
		long lastMs = System.currentTimeMillis();
		// Create world and add test objects
		World w = new World();
		w.add(new PlayerEntity());
		//w.addObstacle(new Obstacle(new RectangleHitbox(new Rectangle(200,90,5,10))));
		
		ServerNetworkManager netManager = new ServerNetworkManager(8000);
		
		netManager.addServerEventListener(new ServerEventListener()
		{
			@Override
			public void newClient(Client c)
			{
				// TODO do stuff for new client (drawing, inventory whatever)
			}
			
			@Override
			public void dataReceived(Client c, NetworkObject netObj)
			{
				if(netObj.getPayload() instanceof PlayerEntity)
				{
					// etc.
				}
				
				/*
				else if(netObj.getPayload() instanceof DisconnectNotification)
				{
					// Remove c from client set
				}*/
				
			}
		});
		JApplet debug = DebugDraw.setup(w);
		
		while(true)
		{
			// Update world entities with proportional time
			w.update( ( System.currentTimeMillis()-lastMs ) / (1000.0/UPDATE_RATE) );
			debug.invalidate();
			debug.repaint();
			
			// Set current time for next update
			lastMs = System.currentTimeMillis();
			// Sleep for next update
			Thread.sleep(1000/UPDATE_RATE);
		}
	}

}
