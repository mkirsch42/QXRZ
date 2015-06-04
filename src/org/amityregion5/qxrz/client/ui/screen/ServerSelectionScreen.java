package org.amityregion5.qxrz.client.ui.screen;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.image.BufferedImage;
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
				(w)->{return new Point(100, 25);},
				(w)->{return new Point(w.getWidth()-200, 50);},
				Color.WHITE, Color.WHITE, 20f, Color.BLACK,
				"Input IP and port"));
		
		elements.add(ipBox = ElementTextBox.createTextBox(
				(w)->{return new Point(50, 75);},
				(w)->{return new Point(w.getWidth()/2-100, 50);},
				Color.DARK_GRAY, Color.BLACK, 20f, Color.WHITE,
				(k)->Character.isDigit(k)||((char)k.intValue())=='.'));
		elements.add(portBox = ElementTextBox.createTextBox(
				(w)->{return new Point(w.getWidth()/2+50, 75);},
				(w)->{return new Point(w.getWidth()/2-100, 50);},
				Color.DARK_GRAY, Color.BLACK, 20f, Color.WHITE,
				(k)->Character.isDigit(k)));
		
		//TODO: Figure out how to validate server
		elements.add(new ElementRectangle(
				(w)->{return new Point(150, 150);},
				(w)->{return new Point(w.getWidth()/2-200, 50);},
				()->(Color.LIGHT_GRAY), ()->Color.BLACK, 20f, ()->Color.BLACK,
				()->"Join Game", (w)->{
					try {
						gui.getNetworkManger().connect(ipBox.getName(), Integer.parseInt(portBox.getString()));
						//gui.setCurrentScreen(new ServerLobbyScreen());
					} catch (Exception e) {
						e.printStackTrace();
					}}));
		
		elements.add(new ElementRectangle(
				(w)->{return new Point(w.getWidth()/2+50, 150);},
				(w)->{return new Point(w.getWidth()/2-200, 50);},
				Color.LIGHT_GRAY, Color.BLACK, 20f, Color.BLACK,
				"Refresh Server List", (w)->refreshServerList()));
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
		
		//Draw the background
		g.setColor(Color.DARK_GRAY);
		g.fillRect(100, 225, windowData.getWidth()-200, windowData.getHeight()-250);
		
		//Draw the border
		g.setColor(Color.BLACK);
		g.drawRect(100, 225, windowData.getWidth()-200, windowData.getHeight()-250);
		
		BufferedImage buff = ImageModification.createBlankBufferedImage(windowData.getWidth()-200, windowData.getHeight()-375);
		
		Graphics2D gBuff = buff.createGraphics();
		
		GuiUtil.applyRenderingHints(gBuff);
		
		//TODO: Scrolling
		int w = buff.getWidth();
		int h = 75;
		int xBuff = 100;
		int yBuff = 225;
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
				GuiUtil.drawString(gBuff, gui.getQueryInfo().get(i).getName(), CenterMode.CENTER, x+w/2, y+h/2);
				
				if (windowData.getMiceDown().size() > 0 && windowData.getMouseX() >= x + xBuff && windowData.getMouseX() <= x+w+xBuff &&
						windowData.getMouseY() >= y+yBuff && windowData.getMouseY() <= y+h+yBuff) {
					ipBox.setName(ip);
					portBox.setName("" + port);
				}
			}
		}
		
		gBuff.dispose();
		
		g.drawImage(buff, null, xBuff, yBuff);
	}

	@Override
	public IScreen getReturnScreen(){return null;}

	@Override
	public boolean setReturnScreen(IScreen s){return false;}

	@Override
	protected void cleanup() {
	}
}
