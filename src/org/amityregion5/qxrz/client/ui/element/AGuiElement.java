package org.amityregion5.qxrz.client.ui.element;

import java.awt.Graphics2D;
import java.util.function.Consumer;
import org.amityregion5.qxrz.client.ui.screen.WindowData;

/**
 * An abstract class that represents any and all gui elements
 */
public abstract class AGuiElement
{
	//An on click listener/event handler
	protected Consumer<WindowData> onClick;
	
	/**
	 * This method should be called to draw this element
	 * 
	 * @param g the graphics object to draw on
	 * @param windowData the window data
	 */
	public final void drawElement(Graphics2D g, WindowData windowData) {
		//Call abstract draw method
		draw(g, windowData);

		//Do click listener stuff
		if (onClick != null && windowData.getMiceDown().size() > 0 && isPointInElement(windowData.getMouseX(), windowData.getMouseY())){
			onClick.accept(windowData);
		}
	}
	
	protected abstract void draw(Graphics2D g, WindowData windowData);
	public abstract boolean isPointInElement(int x, int y);
	
	public void setClickListener(Consumer<WindowData> onClick) {
		this.onClick = onClick;
	}
}
