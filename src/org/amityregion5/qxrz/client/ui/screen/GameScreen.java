package org.amityregion5.qxrz.client.ui.screen;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.event.KeyEvent;
import org.amityregion5.qxrz.client.ui.MainGui;
import org.amityregion5.qxrz.client.ui.Viewport;
import org.amityregion5.qxrz.common.ui.DrawableObject;
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
		vp.xCenter=20;
		vp.yCenter=20;
		vp.height=40;
		vp.width=60;
		
		new Thread(game).start();;
	}

	@Override
	protected void draw(Graphics2D g, WindowData windowData) {
		g.setColor(Color.WHITE);
		g.fillRect(0,0, windowData.getWidth(), windowData.getHeight());

		if (windowData.getKeysDown().contains(KeyEvent.VK_W)) {
			vp.yCenter--;
		}			
		if (windowData.getKeysDown().contains(KeyEvent.VK_A)) {
			vp.xCenter--;
		}
		if (windowData.getKeysDown().contains(KeyEvent.VK_S)) {
			vp.yCenter++;
		}			
		if (windowData.getKeysDown().contains(KeyEvent.VK_D)) {
			vp.xCenter++;
		}
		if (windowData.getKeysDown().contains(KeyEvent.VK_Q)) {
			vp.height+=(2/1.5);
			vp.width+=2;
		}			
		if (windowData.getKeysDown().contains(KeyEvent.VK_E) && vp.height > 6) {
			vp.height-=(2/1.5);
			vp.width-=2;
		}
		
		//g.setStroke(new BasicStr);
		
		for (Obstacle o : game.getWorld().getLandscape().getObstacles()) {
			if (o.getDrawer() != null) {
				o.getDrawer().draw(g, o, vp, windowData);
			}
		}
		for (GameEntity e : game.getWorld().getEntities()) {
			if (e instanceof DrawableObject) {
				@SuppressWarnings("unchecked") //Due to the way the DrawableObject interface should be used this should always work
				DrawableObject<GameEntity> d = (DrawableObject<GameEntity>)e;
				if (d.getDrawer() != null) {
					d.getDrawer().draw(g, e, vp, windowData);
				}
			}
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
