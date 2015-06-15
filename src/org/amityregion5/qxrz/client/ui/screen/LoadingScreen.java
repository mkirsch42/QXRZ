package org.amityregion5.qxrz.client.ui.screen;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import org.amityregion5.qxrz.client.ui.util.CenterMode;
import org.amityregion5.qxrz.client.ui.util.GuiUtil;

/**
 * A simple loading screen (Lightweight due to the fact that it doesnt extend
 * AbstractScreen)
 */
public class LoadingScreen implements IScreen
{

	@Override
	public void drawScreen(Graphics2D g, WindowData windowData)
	{
		// Fill background with black
		g.setColor(Color.BLACK);
		g.fillRect(0, 0, windowData.getWidth(), windowData.getHeight());

		// Write the text "Loading" in big letters on the screen
		g.setColor(Color.WHITE);
		GuiUtil.scaleFont(
				"Loading",
				new Rectangle(10, 10, windowData.getWidth() - 20, windowData
						.getHeight() - 20), g);
		GuiUtil.drawString(g, "Loading", CenterMode.CENTER,
				windowData.getWidth() / 2, windowData.getHeight() / 2);
	}

	@Override
	public IScreen getReturnScreen()
	{
		return null;
	}

	@Override
	public boolean setReturnScreen(IScreen s)
	{
		return false;
	}

	@Override
	public void onGameClose()
	{
	}

	@Override
	public void onScreenChange(boolean leaving)
	{
	}
}
