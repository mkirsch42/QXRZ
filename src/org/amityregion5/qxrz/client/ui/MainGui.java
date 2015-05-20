package org.amityregion5.qxrz.client.ui;

import javax.swing.JFrame;
import javax.swing.WindowConstants;
import org.amityregion5.qxrz.client.ui.screen.IScreen;
import org.amityregion5.qxrz.client.ui.screen.LoadingScreen;

public class MainGui
{
	private JFrame frame;
	
	private IScreen currentScreen;
	
	public MainGui()
	{
		frame = new JFrame("QXRZ");
		
		frame.add(new MainPanel(this));
		
		frame.setSize(1200, 800);
		
		frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
		
		setCurrentScreen(new LoadingScreen());
	}

	public void show()
	{
		frame.setVisible(true);
	}
	public void hide()
	{
	}
	
	public IScreen getCurrentScreen()
	{
		return currentScreen;
	}
	
	public void setCurrentScreen(IScreen currentScreen)
	{
		this.currentScreen = currentScreen;
	}
}
