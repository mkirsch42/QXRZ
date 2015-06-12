package org.amityregion5.qxrz.client.ui.screen;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.RenderingHints;
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
		
		elements.add(ElementSwitcharooney.createSwitharroney(
				(w)->{return new Point(150, 100);}, (w)->{return new Point(w.getWidth()/2-200, 50);},
				()->Color.DARK_GRAY, ()->Color.LIGHT_GRAY, 14f, ()->Color.WHITE, "Render Mode",
				()->{return new Object[]{RenderingHints.VALUE_RENDER_QUALITY, RenderingHints.VALUE_RENDER_SPEED};}, (o)->{
					gui.getSettings().setValue("RenderQuality", o);
				}, gui.getSettings().getValue("RenderQuality")));
		
		elements.add(ElementSwitcharooney.createSwitharroney(
				(w)->{return new Point(w.getWidth()/2+50, 100);}, (w)->{return new Point(w.getWidth()/2-200, 50);},
				()->Color.DARK_GRAY, ()->Color.LIGHT_GRAY, 14f, ()->Color.WHITE, "Render Mode",
				()->{return new Object[]{RenderingHints.VALUE_COLOR_RENDER_QUALITY, RenderingHints.VALUE_COLOR_RENDER_SPEED};}, (o)->{
					
				}, RenderingHints.VALUE_COLOR_RENDER_QUALITY));
	}

	@Override
	protected void draw(Graphics2D g, WindowData windowData) {
		g.setColor(Color.BLACK);
		g.fillRect(0, 0, windowData.getWidth(), windowData.getHeight());
	}
	
	@Override
	public void onScreenChange(boolean leaving)
	{
		if (leaving) {
			gui.getSettings().save();
		}
	}

	@Override
	protected void cleanup(){}
}
