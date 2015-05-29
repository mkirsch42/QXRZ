package org.amityregion5.qxrz.client.ui.screen;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
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
	private Game game;
	Viewport vp = new Viewport();

	public GameScreen(IScreen previous, MainGui gui, Game game) {
		super(previous, gui);
		this.game = game;
		vp.xCenter=20 * 100;
		vp.yCenter=20 * 100;
		vp.height=40 * 100;
		vp.width=60 * 100;
		
		new Thread(game).start();;
	}

	@Override
	protected void draw(Graphics2D g, WindowData windowData) {
		g.setColor(Color.WHITE);
		g.fillRect(0,0, windowData.getWidth(), windowData.getHeight());

		if (windowData.getKeysDown().contains(KeyEvent.VK_I)) {
			vp.yCenter-=100;
		}			
		if (windowData.getKeysDown().contains(KeyEvent.VK_J)) {
			vp.xCenter-=100;
		}
		if (windowData.getKeysDown().contains(KeyEvent.VK_K)) {
			vp.yCenter+=100;
		}			
		if (windowData.getKeysDown().contains(KeyEvent.VK_L)) {
			vp.xCenter+=100;
		}
		if (windowData.getKeysDown().contains(KeyEvent.VK_U)) {
			vp.height+=(2/1.5) * 100;
			vp.width+=2 * 100;
		}			
		if (windowData.getKeysDown().contains(KeyEvent.VK_O) && vp.height > 600) {
			vp.height-=(2/1.5) * 100;
			vp.width-=2 * 100;
		}
		
		//g.setStroke(new BasicStr);
		
		for (Obstacle o : game.getWorld().getLandscape().getObstacles()) {
			if (o.getDrawers() != null) {
				o.getDrawers().forEach((d)->d.draw(g, o, vp, windowData));
			}
		}
		for (GameEntity e : game.getWorld().getEntities()) {
			if (e instanceof DrawableObject) {
				@SuppressWarnings("unchecked") //Due to the way the DrawableObject interface should be used this should always work
				DrawableObject<GameEntity> d = (DrawableObject<GameEntity>)e;
				if (d.getDrawers() != null) {
					d.getDrawers().forEach((d2)->d2.draw(g, e, vp, windowData));
				}
			}
		}
		
		//Do network input data stuff
		{
			NetworkInputData nid = new NetworkInputData();
			
			nid.set(NetworkInputMasks.W, windowData.getKeysDown().contains(KeyEvent.VK_W));
			nid.set(NetworkInputMasks.A, windowData.getKeysDown().contains(KeyEvent.VK_A));
			nid.set(NetworkInputMasks.S, windowData.getKeysDown().contains(KeyEvent.VK_S));
			nid.set(NetworkInputMasks.D, windowData.getKeysDown().contains(KeyEvent.VK_D));
			nid.set(NetworkInputMasks.M1, windowData.getMiceDown().contains(MouseEvent.BUTTON1));
			nid.set(NetworkInputMasks.R, windowData.getKeysDown().contains(KeyEvent.VK_R));
			nid.set(NetworkInputMasks.SPACE, windowData.getKeysDown().contains(KeyEvent.VK_SPACE));
			nid.set(NetworkInputMasks.COMMA, windowData.getKeysDown().contains(KeyEvent.VK_COMMA));
			nid.set(NetworkInputMasks.PERIOD, windowData.getKeysDown().contains(KeyEvent.VK_PERIOD));
			
			//TODO: send somewhere
		}
	}

	@Override
	public IScreen getReturnScreen(){return null;}

	@Override
	public boolean setReturnScreen(IScreen s){return false;}

	@Override
	protected void cleanup() {
	}
}
