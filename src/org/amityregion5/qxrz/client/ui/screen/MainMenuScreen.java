package org.amityregion5.qxrz.client.ui.screen;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Point;
import org.amityregion5.qxrz.client.ui.MainGui;
import org.amityregion5.qxrz.client.ui.element.ElementRectangle;

public class MainMenuScreen extends AbstractScreen
{
	
	public MainMenuScreen(MainGui gui) {
		super(null, gui);
		elements.add(new ElementRectangle(
				(w)->{return new Point(100, w.getHeight() - 100);},
				(w)->{return new Point(w.getWidth()-200, 50);},
				Color.DARK_GRAY, Color.WHITE, Color.WHITE, "Quit",
				(w)->gui.closeGame()));
	}
	
	@Override
	protected void draw(Graphics2D g, WindowData windowData) {
		g.setColor(Color.black);
		g.fillRect(0,0, windowData.getWidth(), windowData.getHeight());
	}
	
	@Override
	public IScreen getReturnScreen(){return null;}
	
	@Override
	public boolean setReturnScreen(IScreen s){return false;}
}
