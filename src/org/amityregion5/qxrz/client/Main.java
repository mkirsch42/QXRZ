package org.amityregion5.qxrz.client;

import org.amityregion5.qxrz.client.ui.MainGui;
import org.amityregion5.qxrz.client.ui.screen.MainMenuScreen;
import org.amityregion5.qxrz.common.asset.AssetManager;

public class Main
{
	public static void main(String[] args)
	{
		//Create a gui object
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
