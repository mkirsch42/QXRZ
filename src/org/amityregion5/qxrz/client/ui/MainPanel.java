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
import java.awt.event.MouseWheelEvent;
import java.awt.event.MouseWheelListener;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JPanel;

import org.amityregion5.qxrz.client.ui.screen.WindowData;
import org.amityregion5.qxrz.client.ui.util.GuiUtil;
import org.amityregion5.qxrz.client.ui.util.ImageModification;

public class MainPanel extends JPanel implements MouseListener, MouseMotionListener, KeyListener, MouseWheelListener
{
	private static final long serialVersionUID = 4938517709138642833L;

	//The gui object that we come from
	private MainGui gui;

	//Keyboard buttons that are currently down
	private List<KeyEvent> keysDown;
	//Mouse buttons that are currently down
	private List<Integer> miceDown;
	//X coordinate relative to the top right of this panel of the mouse
	private int mouseX;
	//Y coordinate relative to the top right of this panel of the mouse
	private int mouseY;
	private int mouseWheel;

	/**
	 * Create a Main Panel object
	 * 
	 * @param gui the MainGui object that created this panel
	 */
	public MainPanel(MainGui gui)
	{
		//Set instance variables
		this.gui = gui;
		keysDown = new ArrayList<KeyEvent>();
		miceDown = new ArrayList<Integer>();

		//Add event listeners
		addMouseListener(this);
		addMouseMotionListener(this);
		addMouseWheelListener(this);
		//addKeyListener(this);
	}

	@Override
	public void paint(Graphics g)
	{
		try {
			//Convert our graphics object to a Graphics2D object
			Graphics2D g2 = (Graphics2D)g;
			//Make everything look better
			GuiUtil.applyRenderingHints(g2);

			//If we have something to draw
			if (gui.getCurrentScreen() != null) {
				//Create an image the size of the screen
				BufferedImage scr = ImageModification.createBlankBufferedImage(getWidth(), getHeight());
				//Get it's graphics object
				Graphics2D screenGraphics = scr.createGraphics();
				//Make it look better
				GuiUtil.applyRenderingHints(screenGraphics);

				//Get the current screen to draw on it
				gui.getCurrentScreen().drawScreen(screenGraphics, new WindowData(getWidth(), getHeight(), keysDown, miceDown, mouseX, mouseY, mouseWheel));
				mouseWheel = 0;

				//Get rid of the graphics object
				screenGraphics.dispose();

				//Draw our image on our screen
				g2.drawImage(scr, null, 0, 0);
			}
		} catch(Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public void keyTyped(KeyEvent e){
	}

	@Override
	public void keyPressed(KeyEvent e)
	{
		if (!isKeyDown(e)) {
			keysDown.add(e);
		}
	}

	@Override
	public void keyReleased(KeyEvent e)
	{
		for (KeyEvent k : keysDown) {
			if (k.getKeyCode() == e.getKeyCode()) {
				keysDown.remove(k);
				return;
			}
		}
	}

	private boolean isKeyDown(KeyEvent e) {
		for (KeyEvent k : keysDown) {
			if (k.getKeyCode() == e.getKeyCode()) {
				return true;
			}
		}
		return false;
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

	@Override
	public void mouseWheelMoved(MouseWheelEvent e) {
		mouseWheel = e.getUnitsToScroll();
	}
}
