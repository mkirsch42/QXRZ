package org.amityregion5.qxrz.client.ui.screen;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.image.BufferedImage;
import java.net.SocketException;
import org.amityregion5.qxrz.client.ui.MainGui;
import org.amityregion5.qxrz.client.ui.element.ElementRectangle;
import org.amityregion5.qxrz.client.ui.element.ElementTextBox;
import org.amityregion5.qxrz.client.ui.util.CenterMode;
import org.amityregion5.qxrz.client.ui.util.GuiUtil;
import org.amityregion5.qxrz.client.ui.util.ImageModification;

public class ServerSelectionScreen extends AbstractScreen
{
	private ElementTextBox ipBox, portBox;
	
	/**
	 * Create a join screen
	 * 
	 * @param previous the return screen
	 * @param gui the MainGui object
	 */
	public ServerSelectionScreen(IScreen previous, MainGui gui) {
		super(previous, gui);
		refreshServerList();
		
		elements.add(new ElementRectangle(
				(w)->{return new Point(w.getWidth()-100, 50);},
				(w)->{return new Point(50, 50);},
				Color.LIGHT_GRAY, Color.BLACK, 0, Color.BLACK,
				"Refresh Server List", (w)->refreshServerList()));

		elements.add(new ElementRectangle(
				(w)->{return new Point(50, 50);},
				(w)->{return new Point(50, 50);},
				Color.LIGHT_GRAY, Color.BLACK, -10f, Color.BLACK,
				"<", (w)->{cleanup();gui.setCurrentScreen(getReturnScreen());}));

		
		elements.add(new ElementRectangle(
				(w)->{return new Point(100, 50);},
				(w)->{return new Point(100, 50);},
				Color.WHITE, Color.WHITE, 20f, Color.BLACK,
				"Port:"));
		
		elements.add(portBox = ElementTextBox.createTextBox(
				(w)->{return new Point(250, 50);},
				(w)->{return new Point(w.getWidth()-150-250, 50);},
				Color.DARK_GRAY, Color.BLACK, 20f, Color.WHITE, "8000",
				(k)->Character.isDigit(k)&&
				(portBox.getString().replace("|", "").length()==0 || (1<<16) > Integer.parseInt(portBox.getString().replace("|", "")+((char)k.intValue())))));
		//portBox.setOnTextChangeCallback(()->gui.getNetworkManger().set
		
		elements.add(new ElementRectangle(
				(w)->{return new Point(50, w.getHeight()-100);},
				(w)->{return new Point(50, 50);},
				Color.WHITE, Color.WHITE, 20f, Color.BLACK,
				"IP:"));
		
		elements.add(ipBox = ElementTextBox.createTextBox(
				(w)->{return new Point(150, w.getHeight()-100);},
				(w)->{return new Point(w.getWidth()-350, 50);},
				Color.DARK_GRAY, Color.BLACK, 20f, Color.WHITE,
				(k)->Character.isDigit(k)||((char)k.intValue())=='.'));
		
		//TODO: Figure out how to validate server
		elements.add(new ElementRectangle(
				(w)->{return new Point(w.getWidth()-150, w.getHeight()-100);},
				(w)->{return new Point(100, 50);},
				()->(Color.LIGHT_GRAY), ()->Color.BLACK, 20f, ()->Color.BLACK,
				()->"Join Game", (w)->{
					try {
						gui.getNetworkManger().connect(ipBox.getName(), Integer.parseInt(portBox.getString()));
						//gui.setCurrentScreen(new ServerLobbyScreen());
					} catch (Exception e) {
						e.printStackTrace();
					}}));
		
	}
	
	private void refreshServerList() {
		try {
			gui.getQueryInfo().clear();
			gui.getNetworkManger().broadcastQuery();
		} catch (Exception e) {
			e.printStackTrace();
		}		
	}

	@Override
	protected void draw(Graphics2D g, WindowData windowData) {
		//Fill the screen with white
		g.setColor(Color.WHITE);
		g.fillRect(0,0, windowData.getWidth(), windowData.getHeight());
		
		int serverX = 100;
		int serverY = 150;
		int endWidth = 100;
		int endHeight = 150;
		
		
		//Draw the background
		g.setColor(Color.DARK_GRAY);
		g.fillRect(serverX, serverY, windowData.getWidth()-(serverX + endWidth), windowData.getHeight()-(serverY+endHeight));
		
		//Draw the border
		g.setColor(Color.BLACK);
		g.drawRect(serverX, serverY, windowData.getWidth()-(serverX + endWidth), windowData.getHeight()-(serverY+endHeight));
		
		BufferedImage buff = ImageModification.createBlankBufferedImage(windowData.getWidth()-(serverX + endWidth), windowData.getHeight()-(serverY+endHeight));
		
		Graphics2D gBuff = buff.createGraphics();
		
		GuiUtil.applyRenderingHints(gBuff);
		
		//TODO: Scrolling
		int w = buff.getWidth();
		int h = 75;
		for (int i = 0; i<gui.getQueryInfo().size(); i++) {
			int x = 0;
			int y = h*i;
			
			String ip = gui.getQueryInfo().get(i).getAddress().getAddress().getHostAddress();
			int port = gui.getQueryInfo().get(i).getAddress().getPort();
			
			if (x+h >= 0 && x <= buff.getHeight()) {
				if (ipBox.getName().equals(ip) && portBox.getName().equals("" + port)) {
					gBuff.setColor(Color.GRAY);
				} else {
					gBuff.setColor(Color.LIGHT_GRAY);
				}
				gBuff.fillRect(x, y, w, h);
				
				gBuff.setColor(Color.BLACK);
				gBuff.drawRect(x, y, w, h);
				
				gBuff.setColor(Color.BLACK);
				gBuff.setFont(gBuff.getFont().deriveFont(16f));
				GuiUtil.drawString(gBuff, gui.getQueryInfo().get(i).getName(), CenterMode.LEFT, x + 10, y+h/3);
				GuiUtil.drawString(gBuff, gui.getQueryInfo().get(i).getAddress().getAddress().getHostAddress(), CenterMode.LEFT, x + 10, y+2*h/3);
				
				if (windowData.getMiceDown().size() > 0 && windowData.getMouseX() >= x + serverX && windowData.getMouseX() <= x+w+serverX &&
						windowData.getMouseY() >= y+serverY && windowData.getMouseY() <= y+h+serverY) {
					try
					{
						gui.getNetworkManger().connect(gui.getQueryInfo().get(i).getAddress());
					}
					catch (SocketException e){e.printStackTrace();}
				}
			}
		}
		
		gBuff.dispose();
		
		g.drawImage(buff, null, serverX, serverY);
	}

	@Override
	protected void cleanup() {
	}
}
