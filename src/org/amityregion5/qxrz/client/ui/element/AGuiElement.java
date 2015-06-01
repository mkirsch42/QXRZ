package org.amityregion5.qxrz.client.ui.element;

import java.awt.Graphics2D;

import java.util.function.Consumer;
import org.amityregion5.qxrz.client.ui.screen.WindowData;

public abstract class AGuiElement
{
	public Consumer<WindowData> onClick;
	
	public final void drawElement(Graphics2D g, WindowData windowData) {
		draw(g, windowData);

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
