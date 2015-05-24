package org.amityregion5.qxrz.client.ui.screen;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;
import org.amityregion5.qxrz.client.ui.MainGui;
import org.amityregion5.qxrz.client.ui.ViewPort;
import org.amityregion5.qxrz.client.ui.util.ImageModification;
import org.amityregion5.qxrz.common.ui.DrawableObject;
import org.amityregion5.qxrz.server.Game;
import org.amityregion5.qxrz.server.world.Obstacle;
import org.amityregion5.qxrz.server.world.entity.GameEntity;

public class GameScreen extends AbstractScreen
{
	private Game game;
	ViewPort vp = new ViewPort();

	public GameScreen(IScreen previous, MainGui gui, Game game) {
		super(previous, gui);
		this.game = game;
		vp.xOff=-10;
		vp.yOff=-10;
		vp.height=40;
		vp.width=60;
		
		new Thread(game).start();;
	}

	@Override
	protected void draw(Graphics2D g, WindowData windowData) {
		g.setColor(Color.WHITE);
		g.fillRect(0,0, windowData.getWidth(), windowData.getHeight());

		if (windowData.getKeysDown().contains(KeyEvent.VK_W)) {
			vp.yOff--;
		}			
		if (windowData.getKeysDown().contains(KeyEvent.VK_A)) {
			vp.xOff--;
		}
		if (windowData.getKeysDown().contains(KeyEvent.VK_S)) {
			vp.yOff++;
		}			
		if (windowData.getKeysDown().contains(KeyEvent.VK_D)) {
			vp.xOff++;
		}
		if (windowData.getKeysDown().contains(KeyEvent.VK_Q)) {
			vp.height+=(2/1.5);
			vp.width+=2;
			vp.xOff-=(1/1.5);
			vp.yOff--;
		}			
		if (windowData.getKeysDown().contains(KeyEvent.VK_E) && vp.height > 6) {
			vp.height-=(2/1.5);
			vp.width-=2;
			vp.xOff+=(1/1.5);
			vp.yOff++;
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
