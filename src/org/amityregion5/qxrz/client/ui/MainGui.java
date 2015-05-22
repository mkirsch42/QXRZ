package org.amityregion5.qxrz.client.ui;

import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import javax.swing.JFrame;
import javax.swing.WindowConstants;
import org.amityregion5.qxrz.client.ui.screen.IScreen;
import org.amityregion5.qxrz.client.ui.screen.LoadingScreen;

public class MainGui
{
	private JFrame frame;
	private double fps;
	private IScreen currentScreen;
	private long lastRepaint;
	
	public MainGui()
	{
		frame = new JFrame("QXRZ");
		
		frame.add(new MainPanel(this));
		
		frame.setSize(1200, 800);
		
		frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
		
		frame.addWindowListener(new WindowListener() {
			@Override
			public void windowOpened(WindowEvent e) {}
			@Override
			public void windowClosing(WindowEvent e) {}
			@Override
			public void windowClosed(WindowEvent e) {
				closeGame();
			}
			@Override
			public void windowIconified(WindowEvent e) {}
			@Override
			public void windowDeiconified(WindowEvent e) {}
			@Override
			public void windowActivated(WindowEvent e) {}
			@Override
			public void windowDeactivated(WindowEvent e) {}
		});
		
		setCurrentScreen(new LoadingScreen());
	}

	public void show()
	{
		lastRepaint = System.currentTimeMillis();
		
		frame.setVisible(true);
		new Thread(()->{
			while (frame.isVisible()) {
				long elapsed = System.currentTimeMillis() - lastRepaint;
				fps = 1000.0/elapsed;
				
				lastRepaint = System.currentTimeMillis();
				
				frame.repaint();
				try
				{
					Thread.sleep(1000/60);
				}
				catch (Exception e){}
			}
		}).start();
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
	public void closeGame() {
		hide();
		System.exit(0);
	}
}
