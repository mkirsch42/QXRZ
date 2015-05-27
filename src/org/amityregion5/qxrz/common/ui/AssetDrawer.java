package org.amityregion5.qxrz.common.ui;

import java.awt.Color;
import java.awt.Graphics2D;
import org.amityregion5.qxrz.client.ui.screen.WindowData;
import org.amityregion5.qxrz.common.asset.AssetManager;
import org.amityregion5.qxrz.server.world.entity.Hitbox;
import org.amityregion5.qxrz.server.world.entity.Hitboxed;

public class AssetDrawer<T extends Hitboxed> implements IObjectDrawer<T> {
	
	private String assetName;
	
	public AssetDrawer(String assetName)
	{
		this.assetName = assetName;
	}
	
	@Override
	public void draw(Graphics2D g, T object, Viewport vp, WindowData d) {
		g.setColor(Color.BLACK);
		Hitbox h = object.getHitbox();
		double xFact = d.getWidth()/vp.width;
		double xOff = vp.getXOff() * xFact;
		double yFact = d.getHeight()/vp.height;
		double yOff = vp.getYOff() * xFact;
		
		g.drawImage(AssetManager.getImageAssets(assetName)[0], (int)(h.getAABB().getX() * xFact - xOff), (int)(h.getAABB().getY() * yFact - yOff),
				(int)(h.getAABB().getWidth() * xFact), (int)(h.getAABB().getHeight() * yFact), null);
	}
}
