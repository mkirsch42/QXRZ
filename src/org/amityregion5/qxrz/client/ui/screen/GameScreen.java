package org.amityregion5.qxrz.client.ui.screen;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.geom.Point2D;

import org.amityregion5.qxrz.client.ui.MainGui;
import org.amityregion5.qxrz.client.ui.util.GameUIHelper;
import org.amityregion5.qxrz.common.control.NetworkInputData;
import org.amityregion5.qxrz.common.control.NetworkInputMasks;
import org.amityregion5.qxrz.common.ui.NetworkDrawableEntity;
import org.amityregion5.qxrz.common.ui.Viewport;
import org.amityregion5.qxrz.common.world.WorldManager;
import org.amityregion5.qxrz.server.world.Obstacle;

public class GameScreen extends AbstractScreen
{
	//The current viewport
	private Viewport vp = new Viewport();

	/**
	 * Create a game screen
	 * 
	 * @param previous the return screen
	 * @param gui the MainGui object
	 * @param game the Game object
	 */
	public GameScreen(IScreen previous, MainGui gui) {
		super(previous, gui);
		
		//Set viewport defaults
		vp.xCenter=0 * 100;
		vp.yCenter=0 * 100;
		vp.height=40 * 100 * 1.2;
		vp.width=60 * 100 * 1.2;
	}

	@Override
	protected void draw(Graphics2D g, WindowData windowData) {
		//Fill the screen with white
		g.setColor(Color.WHITE);
		g.fillRect(0,0, windowData.getWidth(), windowData.getHeight());
		
		if (gui.getNetworkDrawablePacket() != null) {
			if (gui.getNetworkDrawablePacket().getClientIndex() != -1) {
				NetworkDrawableEntity player = gui.getNetworkDrawablePacket().getDrawables().get(gui.getNetworkDrawablePacket().getClientIndex());
				vp.xCenter = player.getBox().getCenterX();
				vp.yCenter = player.getBox().getCenterY();
			}
			
			for (Obstacle o : WorldManager.getWorld(gui.getNetworkDrawablePacket().getCurrentWorld()).getLandscape().getObstacles()) {
				GameUIHelper.draw(g, o.getNDE(), vp, windowData);
			}
			
			for (NetworkDrawableEntity nde : gui.getNetworkDrawablePacket().getDrawables()) {
				GameUIHelper.draw(g, nde, vp, windowData);
			}
		}
		
		//Do network input data stuff
		{
			//Create a network input data object
			NetworkInputData nid = new NetworkInputData();
			
			//Set flags to input data
			nid.set(NetworkInputMasks.W, windowData.getKeysDown().stream().anyMatch((k)->k.getKeyCode()==KeyEvent.VK_W));
			nid.set(NetworkInputMasks.A, windowData.getKeysDown().stream().anyMatch((k)->k.getKeyCode()==KeyEvent.VK_A));
			nid.set(NetworkInputMasks.S, windowData.getKeysDown().stream().anyMatch((k)->k.getKeyCode()==KeyEvent.VK_S));
			nid.set(NetworkInputMasks.D, windowData.getKeysDown().stream().anyMatch((k)->k.getKeyCode()==KeyEvent.VK_D));
			nid.set(NetworkInputMasks.M1, windowData.getMiceDown().contains(MouseEvent.BUTTON1));
			nid.set(NetworkInputMasks.R, windowData.getKeysDown().stream().anyMatch((k)->k.getKeyCode()==KeyEvent.VK_R));
			nid.set(NetworkInputMasks.SPACE, windowData.getKeysDown().stream().anyMatch((k)->k.getKeyCode()==KeyEvent.VK_SPACE));
			nid.set(NetworkInputMasks.COMMA, windowData.getKeysDown().stream().anyMatch((k)->k.getKeyCode()==KeyEvent.VK_COMMA));
			nid.set(NetworkInputMasks.PERIOD, windowData.getKeysDown().stream().anyMatch((k)->k.getKeyCode()==KeyEvent.VK_PERIOD));
			
			//Set mouse coordinate data
			Point2D.Double mc = vp.screenToGame(new Point2D.Double(windowData.getMouseX(), windowData.getMouseY()), windowData);
			nid.setMouseX(mc.x);
			nid.setMouseY(mc.y);
			
			//Send the object
			gui.getNetworkManger().sendObject(nid);
		}
	}

	@Override
	protected void cleanup() {
	}
}
