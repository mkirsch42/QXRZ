package org.amityregion5.qxrz.client.ui.screen;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Point;
import org.amityregion5.qxrz.client.ui.MainGui;
import org.amityregion5.qxrz.client.ui.element.ElementRectangle;
import org.amityregion5.qxrz.server.Game;

public class MainMenuScreen extends AbstractScreen
{
	
	public MainMenuScreen(MainGui gui) {
		super(null, gui);
		//Debug/Single player mode
		try {
			Game g = new Game();
			elements.add(new ElementRectangle(
					(w)->{return new Point(100, 200);},
					(w)->{return new Point(w.getWidth()-200, 50);},
					Color.DARK_GRAY, Color.WHITE, -20f, Color.WHITE, "Debug game",
					(w)->gui.setCurrentScreen(new GameScreen(this, gui, g))));
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		
		//Quit Button
		elements.add(new ElementRectangle(
				(w)->{return new Point(100, w.getHeight() - 100);},
				(w)->{return new Point(w.getWidth()-200, 50);},
				Color.DARK_GRAY, Color.WHITE, -20f, Color.WHITE, "Quit",
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

	@Override
	protected void cleanup() {
	}
}
