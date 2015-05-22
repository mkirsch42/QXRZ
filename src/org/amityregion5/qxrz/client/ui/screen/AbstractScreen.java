package org.amityregion5.qxrz.client.ui.screen;

import java.awt.Graphics2D;
import java.util.ArrayList;
import java.util.List;
import org.amityregion5.qxrz.client.ui.MainGui;
import org.amityregion5.qxrz.client.ui.element.AGuiElement;

public abstract class AbstractScreen implements IScreen {
	
	private IScreen returnScreen;
	protected List<AGuiElement> elements;
	protected MainGui gui;

	/**
	 * @param returnScreen
	 * @param elements
	 */
	public AbstractScreen(IScreen returnScreen, MainGui gui) {
		this.returnScreen = returnScreen;
		this.gui = gui;
		this.elements = new ArrayList<AGuiElement>();
	}

	@Override
	public final void drawScreen(Graphics2D g, WindowData windowData) {
		draw(g, windowData);
		for (AGuiElement ele : elements) {
			ele.drawElement(g, windowData);
		}
	}
	
	protected abstract void draw(Graphics2D g, WindowData windowData);

	@Override
	public IScreen getReturnScreen() {
		return returnScreen;
	}

	@Override
	public boolean setReturnScreen(IScreen s) {
		returnScreen = s;
		return true;
	}

}
