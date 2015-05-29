package org.amityregion5.qxrz.common.ui;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import org.amityregion5.qxrz.client.ui.screen.WindowData;
import org.amityregion5.qxrz.client.ui.util.ImageModification;
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
		int width = (int)(h.getAABB().getWidth() * xFact);
		int height = (int)(h.getAABB().getHeight() * yFact);
		
		BufferedImage buff = ImageModification.createBlankBufferedImage(width, height);
		
		Graphics2D g2 = buff.createGraphics();
		
		g2.drawImage(AssetManager.getImageAssets(assetName)[0], 0, 0,
				width, height, null);
		
		g2.dispose();
		
		g.drawImage(buff, (int)(h.getAABB().getX() * xFact - xOff), (int)(h.getAABB().getY() * yFact - yOff), width, height, null);
	}
}
