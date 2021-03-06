package org.amityregion5.qxrz.client;

import java.util.logging.Level;
import java.util.logging.Logger;
import org.amityregion5.qxrz.client.net.ClientNetworkManager;
import org.amityregion5.qxrz.client.settings.Settings;
import org.amityregion5.qxrz.client.ui.MainGui;
import org.amityregion5.qxrz.client.ui.screen.MainMenuScreen;
import org.amityregion5.qxrz.common.asset.AssetManager;

public class Main
{
	public static void main(String[] args) throws Exception
	{
		Logger.getGlobal().setLevel(Level.OFF);

		ClientNetworkManager manager = new ClientNetworkManager();

		MainGui gui = new MainGui(manager);

		// Show the gui
		gui.show();

		// Do loading stuffs
		{
			manager.start();
			manager.broadcastQuery();

			if (args.length > 0)
			{
				manager.setUsername(args[0]);
			}

			gui.setNetworkManager(manager);

			// Called when the JRE closes
			Runtime.getRuntime().addShutdownHook(new Thread(() ->
			{
				System.out.println("Start Shutdown Hook");
				// Clean up gui stuff
					gui.closeGame();

					// manager.close();
				}, "Shutdown Hook thread"));

			AssetManager.loadAssets();

			Settings settings = new Settings();
			settings.save();
			gui.setSettings(settings);
			gui.setSize(settings.getWidth(), settings.getHeight());

			gui.setCursor();
		}

		// Set the screen to the main menu screen
		gui.setCurrentScreen(new MainMenuScreen(gui));

	}
}
