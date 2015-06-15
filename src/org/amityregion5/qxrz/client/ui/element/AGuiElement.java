package org.amityregion5.qxrz.client.ui.element;

import java.awt.Graphics2D;

import java.util.function.Consumer;
import org.amityregion5.qxrz.client.ui.screen.WindowData;

/**
 * An abstract class that represents any and all gui elements
 */
public abstract class AGuiElement
{
	// An on click listener/event handler
	protected Consumer<WindowData> onClickOn, onClickOff, whileKeyDown;
	private int lastMiceDown = 0;

	/**
	 * This method should be called to draw this element
	 * 
	 * @param g
	 *            the graphics object to draw on
	 * @param windowData
	 *            the window data
	 */
	public final void drawElement(Graphics2D g, WindowData windowData)
	{
		// Call abstract draw method
		draw(g, windowData);

		// Do click listener stuff
		if (windowData.getMiceDown().size() == 0 && lastMiceDown > 0)
		{
			if (isPointInElement(windowData.getMouseX(), windowData.getMouseY()))
			{
				if (onClickOn != null)
				{
					onClickOn.accept(windowData);
				}
			} else
			{
				if (onClickOff != null)
				{
					onClickOff.accept(windowData);
				}
			}
		}
		lastMiceDown = windowData.getMiceDown().size();
		if (windowData.getKeysDown().size() > 0 && whileKeyDown != null)
		{
			whileKeyDown.accept(windowData);
		}
	}

	protected abstract void draw(Graphics2D g, WindowData windowData);

	public abstract boolean isPointInElement(int x, int y);

	public void setClickListener(Consumer<WindowData> onClick)
	{
		this.onClickOn = onClick;
	}

	public void setClickOffListener(Consumer<WindowData> onClick)
	{
		this.onClickOff = onClick;
	}

	public void setWhileKeyDownListener(Consumer<WindowData> listener)
	{
		this.whileKeyDown = listener;
	}
}
