package org.amityregion5.qxrz.client.ui.screen;

import java.awt.Color;
import java.awt.Graphics2D;

public class LoadingScreen implements IScreen
{

	@Override
	public void drawScreen(Graphics2D g, WindowData windowData)
	{
		g.setColor(Color.BLACK);
		g.fillRect(0, 0, windowData.getWidth(), windowData.getHeight());
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
}
