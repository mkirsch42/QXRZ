/**
 * 
 */
package org.amityregion5.qxrz.client.ui;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JPanel;
import org.amityregion5.qxrz.client.ui.screen.WindowData;
import org.amityregion5.qxrz.client.ui.util.GuiUtil;
import org.amityregion5.qxrz.client.ui.util.ImageModification;

/**
 * @author savelyevse17
 *
 */
public class MainPanel extends JPanel implements MouseListener, MouseMotionListener, KeyListener
{
	private static final long serialVersionUID = 4938517709138642833L;

	private MainGui gui;
	
	private List<Integer> keysDown;
	private List<Integer> miceDown;
	private int mouseX;
	private int mouseY;

	public MainPanel(MainGui gui)
	{
		this.gui = gui;
		keysDown = new ArrayList<Integer>();
		miceDown = new ArrayList<Integer>();
		
		addMouseListener(this);
		addMouseMotionListener(this);
		addKeyListener(this);
	}

	@Override
	public void paint(Graphics g)
	{
		
		Graphics2D g2 = (Graphics2D)g;
		GuiUtil.applyRenderingHints(g2);
		
		if (gui.getCurrentScreen() != null) {
			BufferedImage scr = ImageModification.createBlankBufferedImage(getWidth(), getHeight());
			Graphics2D screenGraphics = scr.createGraphics();
			GuiUtil.applyRenderingHints(screenGraphics);

			gui.getCurrentScreen().drawScreen(screenGraphics, new WindowData(getWidth(), getHeight(), keysDown, miceDown, mouseX, mouseY));
			
			screenGraphics.dispose();
			
			g2.drawImage(scr, null, 0, 0);
		}
	}

	@Override
	public void keyTyped(KeyEvent e){}

	@Override
	public void keyPressed(KeyEvent e)
	{
		if (!keysDown.contains(e.getExtendedKeyCode())) {
			keysDown.add(e.getExtendedKeyCode());
		}
	}

	@Override
	public void keyReleased(KeyEvent e)
	{
		if (keysDown.contains(e.getExtendedKeyCode())) {
			keysDown.remove(keysDown.indexOf(e.getExtendedKeyCode()));
		}
	}

	@Override
	public void mouseDragged(MouseEvent e)
	{
		mouseX = e.getX();
		mouseY = e.getY();
	}

	@Override
	public void mouseMoved(MouseEvent e)
	{
		mouseX = e.getX();
		mouseY = e.getY();
	}

	@Override
	public void mouseClicked(MouseEvent e){}

	@Override
	public void mousePressed(MouseEvent e)
	{
		if (!miceDown.contains(e.getButton())) {
			miceDown.add(e.getButton());
		}
	}

	@Override
	public void mouseReleased(MouseEvent e)
	{
		if (miceDown.contains(e.getButton())) {
			miceDown.remove(miceDown.indexOf(e.getButton()));
		}
	}

	@Override
	public void mouseEntered(MouseEvent e){}

	@Override
	public void mouseExited(MouseEvent e){}
}
