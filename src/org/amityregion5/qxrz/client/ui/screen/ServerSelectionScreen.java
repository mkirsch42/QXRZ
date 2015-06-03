package org.amityregion5.qxrz.client.ui.screen;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Point;
import org.amityregion5.qxrz.client.ui.MainGui;
import org.amityregion5.qxrz.client.ui.element.ElementRectangle;
import org.amityregion5.qxrz.client.ui.element.ElementTextBox;

public class ServerSelectionScreen extends AbstractScreen
{

	/**
	 * Create a join screen
	 * 
	 * @param previous the return screen
	 * @param gui the MainGui object
	 */
	public ServerSelectionScreen(IScreen previous, MainGui gui) {
		super(previous, gui);
		try {
			gui.getNetworkManger().broadcastQuery();
		} catch (Exception e) {e.printStackTrace();}
		
		elements.add(new ElementRectangle(
				(w)->{return new Point(100, 25);},
				(w)->{return new Point(w.getWidth()-200, 50);},
				Color.WHITE, Color.WHITE, 20f, Color.BLACK,
				"Input IP and port"));
		
		elements.add(ElementTextBox.createTextBox(
				(w)->{return new Point(50, 75);},
				(w)->{return new Point(w.getWidth()/2-100, 50);},
				Color.DARK_GRAY, Color.BLACK, 20f, Color.WHITE,
				(k)->Character.isDigit(k)||((char)k.intValue())=='.'));
		elements.add(ElementTextBox.createTextBox(
				(w)->{return new Point(w.getWidth()/2+50, 75);},
				(w)->{return new Point(w.getWidth()/2-100, 50);},
				Color.DARK_GRAY, Color.BLACK, 20f, Color.WHITE,
				(k)->Character.isDigit(k)));
	}

	@Override
	protected void draw(Graphics2D g, WindowData windowData) {
		//Fill the screen with white
		g.setColor(Color.WHITE);
		g.fillRect(0,0, windowData.getWidth(), windowData.getHeight());
		
		//Draw the background
		g.setColor(Color.DARK_GRAY);
		g.fillRect(100, 150, windowData.getWidth()-200, windowData.getHeight()-250);
		
		//Draw the border
		g.setColor(Color.BLACK);
		g.drawRect(100, 150, windowData.getWidth()-200, windowData.getHeight()-250);
	}

	@Override
	public IScreen getReturnScreen(){return null;}

	@Override
	public boolean setReturnScreen(IScreen s){return false;}

	@Override
	protected void cleanup() {
	}
}
