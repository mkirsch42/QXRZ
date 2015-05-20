package org.amityregion5.qxrz.client.ui;

import javax.swing.JFrame;
import javax.swing.WindowConstants;
import org.amityregion5.qxrz.client.ui.screen.IScreen;
import org.amityregion5.qxrz.client.ui.screen.LoadingScreen;

public class MainGui
{
	private JFrame frame;
	private double fps;
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
		new Thread(()->{
			while (frame.isVisible()) {
				frame.repaint();
				try
				{
					Thread.sleep(1000/60);
				}
				catch (Exception e){}
			}
		}).run();
	}
	public void hide()
	{
		frame.setVisible(false);
	}
	
	public IScreen getCurrentScreen()
	{
		return currentScreen;
	}
	
	public void setCurrentScreen(IScreen currentScreen)
	{
		this.currentScreen = currentScreen;
	}

	/**
	 * @return the fps
	 */
	public double getFps()
	{
		return fps;
	}

	/**
	 * @param fps the fps to set
	 */
	public void setFps(double fps)
	{
		this.fps = fps;
	}
	
}
