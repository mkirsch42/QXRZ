package org.amityregion5.qxrz.client.ui;

import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.swing.JFrame;
import javax.swing.WindowConstants;

import org.amityregion5.qxrz.client.net.ClientNetworkManager;
import org.amityregion5.qxrz.client.ui.screen.IScreen;
import org.amityregion5.qxrz.client.ui.screen.LoadingScreen;
import org.amityregion5.qxrz.common.net.AbstractNetworkNode;
import org.amityregion5.qxrz.common.net.ChatMessage;
import org.amityregion5.qxrz.common.net.NetEventListener;
import org.amityregion5.qxrz.common.net.NetworkNode;
import org.amityregion5.qxrz.common.net.ServerInfo;
import org.amityregion5.qxrz.common.ui.NetworkDrawablePacket;
import org.amityregion5.qxrz.common.util.RNG;

public class MainGui
{
	//The frame
	private JFrame frame;
	private boolean setFrameInvisible = true;
	private MainPanel panel;
	//The previous few FPS values
	private double[] fps;
	//The current screen
	private IScreen currentScreen;
	//The time since the last repaint
	private long lastRepaint;
	private List<ServerInfo> queryInfo;
	private List<ChatMessage> messages;
	private NetworkDrawablePacket ndp;
	private String username;

	private Thread renderThread;

	private ClientNetworkManager networkManger;

	/**
	 * Create a new MainGui object
	 * @param manager the network manager to use
	 * @param chatMessages 
	 */
	public MainGui(ClientNetworkManager manager)
	{
		username = "Player " + RNG.r.nextInt(10000);
		
		setNetworkManager(manager);
		this.queryInfo = new ArrayList<ServerInfo>();
		this.messages = new ArrayList<ChatMessage>();

		//Store 10 fps values
		fps = new double[10];

		//Create the frame
		frame = new JFrame("QXRZ");

		//Add a main panel to the frame
		frame.add(panel = new MainPanel(this));

		//Set the size of the frame
		frame.setSize(1200, 800);

		//Set the default close operation of the frame
		frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

		//Add a listener for when the window is closed and close the game
		frame.addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent e) {
				setFrameInvisible = false;
				Runtime.getRuntime().exit(0);
			}
		});

		frame.addKeyListener(panel);

		//Set the screen to the loading screen
		setCurrentScreen(new LoadingScreen());
	}

	/**
	 * Show the frame and data represented by this Main Gui Object
	 */
	public void show()
	{
		if (!frame.isVisible()) {
			//Set the last repaint value
			lastRepaint = System.currentTimeMillis();

			//Set the frame as visible
			frame.setVisible(true);

			//Start a new thread which contains the repaint method (Render loop)
			renderThread = new Thread(()->{
				//Stopping condition: when the frame is hidden
				while (frame.isVisible()) {
					//Move the fps values down by 1
					for (int i=0; i<fps.length-1; i++) {
						fps[i] = fps[i+1];
					}
					//Set the newest FPS value
					fps[fps.length-1] = 1000.0/(System.currentTimeMillis() - lastRepaint);
					//Set the last repaint time
					lastRepaint = System.currentTimeMillis();

					//Repaint the screen
					frame.repaint();
					
					//Wait enough time to make it 60 fps
					try{
						//The 900 here is chosen because it makes it the closest to 60 FPS
						Thread.sleep((900/60-(System.currentTimeMillis()-lastRepaint)));
					}catch (Exception e){}
				}
				System.out.println("MainGui.show()");
			}, "Gui Refresh Thread");
			renderThread.start();
		}
	}

	/**
	 * Hide the frame and data represented by this MainGui object
	 */
	public void hide()
	{
		//Hide the frame (will also stop the render thread)
		if (setFrameInvisible) {
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
	 * @param currentScreen new current screen
	 */
	public synchronized void setCurrentScreen(IScreen currentScreen)
	{
		this.currentScreen = currentScreen;
	}

	/**
	 * @return the fps
	 */
	public double getFps()
	{
		//Get the average of the FPS values
		return Arrays.stream(fps).average().orElse(0);
	}

	/**
	 * Called to safely close the game (Should cut any networking communication)
	 */
	public void closeGame() {
		//hide();
		
		currentScreen.onGameClose();
		
		getNetworkManger().close();
	}

	/**
	 * @return the networkManger
	 */
	public ClientNetworkManager getNetworkManger() {
		return networkManger;
	}

	/**
	 * @param networkManger the networkManger to set
	 */
	public void setNetworkManager(ClientNetworkManager networkManger) {
		this.networkManger = networkManger;
		networkManger.attachEventListener(new NetEventListener()
		{

			@Override
			public void newNode(AbstractNetworkNode server)
			{
				if (queryInfo.contains(server))
				{
					queryInfo.set(queryInfo.indexOf((ServerInfo) server),
							(ServerInfo) server);
				} else
				{
					queryInfo.add((ServerInfo) server);
				}
			}

			@Override
			public void dataReceived(NetworkNode from, Serializable payload)
			{
				if (payload instanceof ChatMessage)
				{
					messages.add(0, (ChatMessage) payload);
				} else if (payload instanceof NetworkDrawablePacket) {
					ndp = (NetworkDrawablePacket)payload;
				}
			}
		});
	}

	/**
	 * @return the queryInfo
	 */
	public List<ServerInfo> getQueryInfo() {
		return queryInfo;
	}

	/**
	 * @return the messages
	 */
	public List<ChatMessage> getMessages() {
		return messages;
	}

	/**
	 * @return the NetworkDrawablePacket
	 */
	public NetworkDrawablePacket getNetworkDrawablePacket() {
		return ndp;
	}

	/**
	 * @return the username
	 */
	public String getUsername() {
		return username;
	}

	/**
	 * @param username the username to set
	 */
	public void setUsername(String username) {
		this.username = username;
	}
}
