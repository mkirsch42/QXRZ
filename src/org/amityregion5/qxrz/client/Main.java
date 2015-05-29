package org.amityregion5.qxrz.client;

import java.io.Serializable;

import org.amityregion5.qxrz.client.net.ClientNetworkManager;
import org.amityregion5.qxrz.client.ui.MainGui;
import org.amityregion5.qxrz.client.ui.screen.MainMenuScreen;
import org.amityregion5.qxrz.common.asset.AssetManager;
import org.amityregion5.qxrz.common.net.NetEventListener;
import org.amityregion5.qxrz.common.net.NetworkNode;

import com.sun.org.apache.bcel.internal.generic.NEWARRAY;

public class Main
{
	public static void main(String[] args) throws Exception
	{
		ClientNetworkManager manager = new ClientNetworkManager();
		
		//TODO maybe all the manager stuff should be created within the GUI
		manager.attachEventListener(new NetEventListener()
		{
			
			@Override
			public void newNode(NetworkNode c)
			{
				// leave empty
			}
			
			@Override
			public void dataReceived(NetworkNode from, Serializable payload)
			{
				
			}
		});
		manager.start();
		
		
		//Create a gui object
		/*TODO this should take the manager in its constructor
		 * then you can call
		 * manager.broadcastQuery()
		 * and
		 * manager.connect(address) when the user chooses a server.
		 */
		MainGui gui = new MainGui(); 
		
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
