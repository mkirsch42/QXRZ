package org.amityregion5.qxrz.client.ui.screen;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.geom.Point2D;
import org.amityregion5.qxrz.client.ui.MainGui;
import org.amityregion5.qxrz.common.control.NetworkInputData;
import org.amityregion5.qxrz.common.control.NetworkInputMasks;
import org.amityregion5.qxrz.common.ui.DrawableObject;
import org.amityregion5.qxrz.common.ui.Viewport;
import org.amityregion5.qxrz.server.Game;
import org.amityregion5.qxrz.server.world.Obstacle;
import org.amityregion5.qxrz.server.world.entity.GameEntity;

public class GameScreen extends AbstractScreen
{
	//The current game
	private Game game;
	//The current viewport
	private Viewport vp = new Viewport();

	/**
	 * Create a game screen
	 * 
	 * @param previous the return screen
	 * @param gui the MainGui object
	 * @param game the Game object
	 */
	public GameScreen(IScreen previous, MainGui gui, Game game) {
		super(previous, gui);
		//Set game variable
		this.game = game;
		
		//Set viewport defaults
		vp.xCenter=0 * 100;
		vp.yCenter=0 * 100;
		vp.height=40 * 100;
		vp.width=60 * 100;
		
		//Start game on a new thread
		new Thread(game, "Game loop thread").start();;
	}

	@Override
	protected void draw(Graphics2D g, WindowData windowData) {
		//Fill the screen with white
		g.setColor(Color.WHITE);
		g.fillRect(0,0, windowData.getWidth(), windowData.getHeight());

		//if I is pressed move viewport up
		if (windowData.getKeysDown().stream().anyMatch((k)->k.getKeyCode()==KeyEvent.VK_I)) {
			vp.yCenter-=100;
		}			
		//if J is pressed move viewport left
		if (windowData.getKeysDown().stream().anyMatch((k)->k.getKeyCode()==KeyEvent.VK_J)) {
			vp.xCenter-=100;
		}
		//if K is pressed move viewport down
		if (windowData.getKeysDown().stream().anyMatch((k)->k.getKeyCode()==KeyEvent.VK_K)) {
			vp.yCenter+=100;
		}			
		//if L is pressed move viewport left
		if (windowData.getKeysDown().stream().anyMatch((k)->k.getKeyCode()==KeyEvent.VK_L)) {
			vp.xCenter+=100;
		}
		//if U is pressed zoom viewport out
		if (windowData.getKeysDown().stream().anyMatch((k)->k.getKeyCode()==KeyEvent.VK_U)) {
			vp.height+=(2/1.5) * 100;
			vp.width+=2 * 100;
		}			
		//if O is pressed zoom viewport in
		if (windowData.getKeysDown().stream().anyMatch((k)->k.getKeyCode()==KeyEvent.VK_O) && vp.height > 600) {
			vp.height-=(2/1.5) * 100;
			vp.width-=2 * 100;
		}

		//Loop through all of the obstacles in the world
		for (Obstacle o : game.getWorld().getLandscape().getObstacles()) {
			//If they have drawers (If they can be drawn)
			if (o.getDrawers() != null) {
				//Tell each of the drawers to draw the object
				o.getDrawers().forEach((d)->d.draw(g, o, vp, windowData));
			}
		}
		//Loop through all of the game entities
		for (GameEntity e : game.getWorld().getEntities()) {
			//If that entity is an instance of Drawable object
			if (e instanceof DrawableObject) {
				//Get the as a drawable object
				@SuppressWarnings("unchecked") //Due to the way the DrawableObject interface should be used this should always work
				DrawableObject<GameEntity> d = (DrawableObject<GameEntity>)e;
				//Check if it has drawers
				if (d.getDrawers() != null) {
					//Tell each of the drawers to draw
					d.getDrawers().forEach((d2)->d2.draw(g, e, vp, windowData));
				}
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
		game.close();
	}
}
