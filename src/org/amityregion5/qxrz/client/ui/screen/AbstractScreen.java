package org.amityregion5.qxrz.client.ui.screen;

import java.awt.Graphics2D;
import java.util.ArrayList;
import java.util.List;
import org.amityregion5.qxrz.client.ui.MainGui;
import org.amityregion5.qxrz.client.ui.element.AGuiElement;

public abstract class AbstractScreen implements IScreen
{

	// The screen that this screen will return to
	private IScreen returnScreen;
	// A list of the elements containted by this screen
	protected List<AGuiElement> elements;
	// The main gui
	protected MainGui gui;

	/**
	 * @param returnScreen
	 *            the screen to return to
	 * @param gui
	 *            the MainGui object
	 */
	public AbstractScreen(IScreen returnScreen, MainGui gui)
	{
		// Set variables
		this.returnScreen = returnScreen;
		this.gui = gui;
		this.elements = new ArrayList<AGuiElement>();
	}

	@Override
	public final void drawScreen(Graphics2D g, WindowData windowData)
	{
		// Call the abstract draw method
		draw(g, windowData);
		// Draw each of the elements
		for (AGuiElement ele : elements)
		{
			ele.drawElement(g, windowData);
		}
	}

	/**
	 * Called to draw the contents of this screen (Excluding GuiElements
	 * previously added to it)
	 * 
	 * @param g
	 *            the graphics object
	 * @param windowData
	 *            the window data object
	 */
	protected abstract void draw(Graphics2D g, WindowData windowData);

	@Override
	public IScreen getReturnScreen()
	{
		return returnScreen;
	}

	@Override
	public boolean setReturnScreen(IScreen s)
	{
		returnScreen = s;
		return true;
	}

	@Override
	public final void onGameClose()
	{
		// Close the return screen
		if (returnScreen != null)
			returnScreen.onGameClose();
		// Call clean up
		cleanup();
	}

	/**
	 * Called to cleanup anything that is still open
	 */
	protected abstract void cleanup();

	@Override
	public void onScreenChange(boolean leaving)
	{
	}
}
