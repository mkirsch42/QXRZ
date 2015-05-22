package org.amityregion5.qxrz.client;

import org.amityregion5.qxrz.client.ui.MainGui;
import org.amityregion5.qxrz.client.ui.screen.MainMenuScreen;

public class Main
{
	public static void main(String[] args)
	{
		MainGui gui = new MainGui();
		
		gui.show();
		
		{
			//TODO: Loading?
		}

		gui.setCurrentScreen(new MainMenuScreen(gui));
	}
}
