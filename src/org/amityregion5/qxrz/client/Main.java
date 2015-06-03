package org.amityregion5.qxrz.client;

import java.io.Serializable;
import org.amityregion5.qxrz.client.net.ClientNetworkManager;
import org.amityregion5.qxrz.client.ui.MainGui;
import org.amityregion5.qxrz.client.ui.screen.MainMenuScreen;
import org.amityregion5.qxrz.common.asset.AssetManager;
import org.amityregion5.qxrz.common.net.AbstractNetworkNode;
//github.com/mkirsch42/QXRZ.gitimport org.amityregion5.qxrz.common.net.AbstractNetworkNode;
import org.amityregion5.qxrz.common.net.DisconnectNotification;
import org.amityregion5.qxrz.common.net.NetEventListener;
import org.amityregion5.qxrz.common.net.NetworkNode;
import org.amityregion5.qxrz.common.net.ServerInfo;

public class Main
{
	public static void main(String[] args) throws Exception
	{
		ClientNetworkManager manager = new ClientNetworkManager();

		manager.attachEventListener(new NetEventListener()
		{

			@Override
			public void newNode(AbstractNetworkNode server)
			{
				// This will be called when a new server makes itself known.

				// You should cast the AbstractNetworkNode to a ServerInfo before using
			}

			@Override
			public void dataReceived(NetworkNode from, Serializable payload)
			{
				System.out.println(from.getSocketAddress());
				if(payload instanceof ServerInfo)
				{
					/* call gui functions here
					 * like gui.addServer(from, ServerInfo) or something
					 * and then addServer() would add that server to the menu
					 */
				}
			}
		});
		manager.start();
		manager.broadcastQuery();
		//Create a gui object
		/* TODO this should take the manager in its constructor
		 * then you can call
		 * manager.broadcastQuery()
		 * and
		 * manager.connect(address) when the user chooses a server.
		 */
		MainGui gui = new MainGui(manager); 

		//Called when the JRE closes
		Runtime.getRuntime().addShutdownHook(new Thread(()->
		{
			//Send a disconnect notification packet to the server
			manager.sendObject(new DisconnectNotification());
			
			//Clean up gui stuff
			gui.closeGame();

			manager.close();
		}, "Shutdown Hook thread"));


		//Show the gui
		gui.show();

		//Do loading stuffs
		{
			AssetManager.loadAssets();
		}

		//Set the screen to the main menu screen
		gui.setCurrentScreen(new MainMenuScreen(gui));
	}
}
