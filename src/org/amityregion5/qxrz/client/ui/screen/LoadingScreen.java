package org.amityregion5.qxrz.client.ui.screen;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import org.amityregion5.qxrz.client.ui.util.CenterMode;
import org.amityregion5.qxrz.client.ui.util.GuiUtil;

public class LoadingScreen implements IScreen
{

	@Override
	public void drawScreen(Graphics2D g, WindowData windowData)
	{
		g.setColor(Color.BLACK);
		g.fillRect(0, 0, windowData.getWidth(), windowData.getHeight());
		
		g.setColor(Color.WHITE);
		GuiUtil.scaleFont("Loading", new Rectangle(10,10,windowData.getWidth()-20, windowData.getHeight()-20), g);
		GuiUtil.drawString(g, "Loading", CenterMode.CENTER, windowData.getWidth()/2, windowData.getHeight()/2);
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
	public void onGameClose() {}
}
