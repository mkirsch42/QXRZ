package org.amityregion5.qxrz.client.ui;

import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JFrame;
import javax.swing.WindowConstants;

import org.amityregion5.qxrz.client.net.ClientNetworkManager;
import org.amityregion5.qxrz.client.ui.screen.IScreen;
import org.amityregion5.qxrz.client.ui.screen.LoadingScreen;
import org.amityregion5.qxrz.common.net.AbstractNetworkNode;
import org.amityregion5.qxrz.common.net.ChatMessage;
import org.amityregion5.qxrz.common.net.Goodbye;
import org.amityregion5.qxrz.common.net.NetEventListener;
import org.amityregion5.qxrz.common.net.NetworkNode;
import org.amityregion5.qxrz.common.ui.NetworkDrawablePacket;

public class MainGui
{
	// The frame
	private JFrame frame;
	private boolean setFrameInvisible = true;
	private MainPanel panel;
	// The previous few FPS values
	private int fps;
	// The current screen
	private IScreen currentScreen;
	// The time since the last repaint
//	private long lastRepaint;
	private List<AbstractNetworkNode> queryInfo;
	private List<ChatMessage> messages;
	private NetworkDrawablePacket ndp;
	private int frameID;

	private Thread renderThread;

	private ClientNetworkManager networkManger;

	/**
	 * Create a new MainGui object
	 * 
	 * @param manager
	 *            the network manager to use
	 * @param chatMessages
	 */
	public MainGui(ClientNetworkManager manager)
	{
		setNetworkManager(manager);
		setUsername(System.getProperty("user.name"));

		this.queryInfo = new ArrayList<AbstractNetworkNode>();
		this.messages = new ArrayList<ChatMessage>();

		// Create the frame
		frame = new JFrame("QXRZ");

		// Add a main panel to the frame
		frame.add(panel = new MainPanel(this));

		// Set the size of the frame
		frame.setSize(1200, 800);

		// Set the default close operation of the frame
		frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

		// Add a listener for when the window is closed and close the game
		frame.addWindowListener(new WindowAdapter()
		{
			@Override
			public void windowClosing(WindowEvent e)
			{
				setFrameInvisible = false;
				System.exit(0);
			}
		});

		frame.addKeyListener(panel);

		// Set the screen to the loading screen
		setCurrentScreen(new LoadingScreen());
	}

	/**
	 * Show the frame and data represented by this Main Gui Object
	 */
	public void show()
	{
		if (!frame.isVisible())
		{
			// Set the last repaint value
//			lastRepaint = System.currentTimeMillis();

			// Set the frame as visible
			frame.setVisible(true);

			// Start a new thread which contains the repaint method (Render
			// loop)
			renderThread = new Thread(
					() ->
					{
						// Stopping condition: when the frame is hidden
						@SuppressWarnings("unused")
						int fps = 0;
						@SuppressWarnings("unused")
						int update = 0;
						//int update = 0;
						long fpsTimer = System.currentTimeMillis();
						int targetFPS = 60;
						double nsPerUpdate = 1000000000.0 / targetFPS;
						// last update

						double then = System.nanoTime();
						double unprocessed = 0;
						boolean shouldRender = false;

						while (frame.isVisible())
						{
							double now = System.nanoTime();
							unprocessed += (now - then) / nsPerUpdate;
							then = now;
							// update
							while (unprocessed >= 1)
							{
								//update++;
//								update();
								unprocessed--;
								shouldRender = true;
							}

							if (shouldRender)
							{
								fps++;
								frame.repaint();
								frameID++;
								frameID%=60;
								shouldRender = false;
							} else
							{
								try
								{
									Thread.sleep(1);
								} catch (InterruptedException e)
								{
									e.printStackTrace();
								}
							}
							
							if(System.currentTimeMillis() - fpsTimer > 1000)
							{
//								System.out.println("Update=" + update);
//								System.out.println("FPS=" + fps);
								
								//put code for processing fps data here!!!!
								
								
								fps = 0;
								//update = 0;
								fpsTimer = System.currentTimeMillis();
							}
						}						System.out.println("MainGui.show()");
					}, "Gui Refresh Thread");
			renderThread.start();
		}
	}

	/**
	 * Hide the frame and data represented by this MainGui object
	 */
	public void hide()
	{
		// Hide the frame (will also stop the render thread)
		if (setFrameInvisible)
		{
			frame.setVisible(false);
		}
	}

	/**
	 * Get the current screen
	 * 
	 * @return the current screen
	 */
	public synchronized IScreen getCurrentScreen()
	{
		return currentScreen;
	}

	/**
	 * Set the current screen
	 * 
	 * @param currentScreen
	 *            new current screen
	 */
	public synchronized void setCurrentScreen(IScreen currentScreen)
	{
		if (this.currentScreen != null)
		{
			this.currentScreen.onScreenChange(true);
		}
		this.currentScreen = currentScreen;
		if (this.currentScreen != null)
		{
			this.currentScreen.onScreenChange(false);
		}
	}

	/**
	 * @return the fps
	 */
	public int getFps()
	{
		// Get the average of the FPS values
		return fps;
	}

	/**
	 * Called to safely close the game (Should cut any networking communication)
	 */
	public void closeGame()
	{
		// hide();

		currentScreen.onGameClose();

		getNetworkManger().close();
	}

	/**
	 * @return the networkManger
	 */
	public ClientNetworkManager getNetworkManger()
	{
		return networkManger;
	}

	/**
	 * @param networkManger
	 *            the networkManger to set
	 */
	public void setNetworkManager(ClientNetworkManager networkManger)
	{
		this.networkManger = networkManger;
		networkManger.attachEventListener(new NetEventListener()
		{

			@Override
			public void newNode(AbstractNetworkNode server)
			{
				if (queryInfo.contains(server))
				{
					queryInfo.set(queryInfo.indexOf(server), server);
				} else
				{
					queryInfo.add(server);
				}
			}

			@Override
			public void dataReceived(NetworkNode from, Serializable payload)
			{
				if (payload instanceof ChatMessage)
				{
					messages.add(0, ((ChatMessage) payload).setTimestamp());
				} else if (payload instanceof NetworkDrawablePacket)
				{
					ndp = (NetworkDrawablePacket) payload;
				} else if (payload instanceof Goodbye) {
					System.err.println("YOU HAVE BEEN KICKED");
					System.exit(0);
				}
			}
		});
	}

	/**
	 * @return the queryInfo
	 */
	public List<AbstractNetworkNode> getQueryInfo()
	{
		return queryInfo;
	}

	/**
	 * @return the messages
	 */
	public List<ChatMessage> getMessages()
	{
		return messages;
	}

	/**
	 * @return the NetworkDrawablePacket
	 */
	public NetworkDrawablePacket getNetworkDrawablePacket()
	{
		return ndp;
	}

	/**
	 * @return the username
	 */
	public String getUsername()
	{
		return getNetworkManger().getUsername();
	}

	/**
	 * @param username
	 *            the username to set
	 */
	public void setUsername(String username)
	{
		getNetworkManger().setUsername(username);
	}
	
	public int getFrameID(){
		return frameID;
	}
}
