package org.amityregion5.qxrz.client.ui.screen;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Point;
import org.amityregion5.qxrz.client.ui.MainGui;
import org.amityregion5.qxrz.client.ui.element.ElementRectangle;
import org.amityregion5.qxrz.client.ui.element.ElementSwitcharooney;

/**
 * The Main Menu Screen
 */
public class OptionsScreen extends AbstractScreen
{

	public OptionsScreen(IScreen s, MainGui gui) {
		super(s, gui);
		
		elements.add(new ElementRectangle((w) -> {return new Point(50, 50);},
				(w) -> {return new Point(50, 50);},
				Color.LIGHT_GRAY, Color.BLACK, -10f, Color.BLACK, "<", 
				(w) -> {cleanup();gui.setCurrentScreen(getReturnScreen());}));
		
		//elements.add(ElementSwitcharooney<>)
	}

	@Override
	protected void draw(Graphics2D g, WindowData windowData) {
		g.setColor(Color.BLACK);
		g.fillRect(0, 0, windowData.getWidth(), windowData.getHeight());
	}

	@Override
	protected void cleanup(){}
}
