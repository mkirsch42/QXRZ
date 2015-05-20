/**
 * 
 */
package org.amityregion5.qxrz.client.ui;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import javax.swing.JPanel;
import org.amityregion5.qxrz.client.ui.screen.WindowData;
import org.amityregion5.qxrz.client.ui.util.GuiUtil;
import org.amityregion5.qxrz.client.ui.util.ImageModification;

/**
 * @author savelyevse17
 *
 */
public class MainPanel extends JPanel
{
	private static final long serialVersionUID = 4938517709138642833L;

	private MainGui gui;
	private long lastRepaint;

	public MainPanel(MainGui gui)
	{
		this.gui = gui;
		lastRepaint = System.currentTimeMillis();
	}

	@Override
	public void paint(Graphics g)
	{
		long elapsed = System.currentTimeMillis() - lastRepaint;
		lastRepaint = System.currentTimeMillis();
		
		gui.setFps(1000.0/elapsed);
		
		Graphics2D g2 = (Graphics2D)g;
		GuiUtil.applyRenderingHints(g2);
		
		if (gui.getCurrentScreen() != null) {
			BufferedImage scr = ImageModification.createBlankBufferedImage(getWidth(), getHeight());
			Graphics2D screenGraphics = scr.createGraphics();
			GuiUtil.applyRenderingHints(screenGraphics);

			gui.getCurrentScreen().drawScreen(screenGraphics, new WindowData(getWidth(), getHeight()));
			
			screenGraphics.dispose();
			
			g2.drawImage(scr, null, 0, 0);
		}
	}
	

}
