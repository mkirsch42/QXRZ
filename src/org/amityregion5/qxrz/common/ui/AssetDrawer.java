package org.amityregion5.qxrz.common.ui;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import org.amityregion5.qxrz.client.ui.screen.WindowData;
import org.amityregion5.qxrz.client.ui.util.ImageModification;
import org.amityregion5.qxrz.common.asset.AssetManager;
import org.amityregion5.qxrz.server.world.entity.Hitbox;
import org.amityregion5.qxrz.server.world.entity.Hitboxed;

public class AssetDrawer<T extends Hitboxed> implements IObjectDrawer<T> {
	
	//The asset to draw
	private String assetName;
	
	//TODO: get rid of this
	//Temporary for testing
	private double deg;
	
	public AssetDrawer(String assetName)
	{
		//Set the asset name
		this.assetName = assetName;
	}
	
	@Override
	public void draw(Graphics2D g, T object, Viewport vp, WindowData d) {
		//Get hitbox
		Hitbox h = object.getHitbox();
		//Do math to determine positioning
		double xFact = d.getWidth()/vp.width;
		double xOff = vp.getXOff() * xFact;
		double yFact = d.getHeight()/vp.height;
		double yOff = vp.getYOff() * xFact;
		int width = (int)(h.getAABB().getWidth() * xFact);
		int height = (int)(h.getAABB().getHeight() * yFact);
		
		//Draw on a buffered image to prevent antialiasing
		BufferedImage buff = ImageModification.createBlankBufferedImage(width, height);
		Graphics2D g2 = buff.createGraphics();
		
		//Squarify and rotate(temporary) the image
		BufferedImage newImage = ImageModification.squarifyImage(ImageModification.rotateImage(AssetManager.getImageAssets(assetName)[0], Math.toRadians(deg++)));
		
		//Draw the image
		g2.drawImage(newImage, 0, 0,
				width-1, height-1, null);
		
		//Dispose of the new graphics object
		g2.dispose();
		
		//Draw the buffer to the screen
		g.drawImage(buff, (int)(h.getAABB().getX() * xFact - xOff), (int)(h.getAABB().getY() * yFact - yOff), width, height, null);
	}
}
