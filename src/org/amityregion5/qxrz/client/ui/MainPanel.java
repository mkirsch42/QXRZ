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

public class MainPanel extends JPanel implements MouseListener, MouseMotionListener, KeyListener
{
	private static final long serialVersionUID = 4938517709138642833L;

	//The gui object that we come from
	private MainGui gui;
	
	//Keyboard buttons that are currently down
	private List<Integer> keysDown;
	//Mouse buttons that are currently down
	private List<Integer> miceDown;
	//X coordinate relative to the top right of this panel of the mouse
	private int mouseX;
	//Y coordinate relative to the top right of this panel of the mouse
	private int mouseY;

	/**
	 * Create a Main Panel object
	 * 
	 * @param gui the MainGui object that created this panel
	 */
	public MainPanel(MainGui gui)
	{
		//Set instance variables
		this.gui = gui;
		keysDown = new ArrayList<Integer>();
		miceDown = new ArrayList<Integer>();
		
		//Add event listeners
		addMouseListener(this);
		addMouseMotionListener(this);
		//addKeyListener(this);
	}

	@Override
	public void paint(Graphics g)
	{
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
			gui.getCurrentScreen().drawScreen(screenGraphics, new WindowData(getWidth(), getHeight(), keysDown, miceDown, mouseX, mouseY));
			
			//Get rid of the graphics object
			screenGraphics.dispose();
			
			//Draw our image on our screen
			g2.drawImage(scr, null, 0, 0);
		}
	}

	@Override
	public void keyTyped(KeyEvent e){
	}

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
