package org.amityregion5.qxrz.common.ui;

import java.awt.Graphics2D;
import java.awt.geom.Point2D;
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
		
		Point2D.Double playerTL = vp.gameToScreen(new Point2D.Double(h.getAABB().getX(), h.getAABB().getY()), d);
		Point2D.Double playerBR = vp.gameToScreen(new Point2D.Double(h.getAABB().getMaxX(), h.getAABB().getMaxY()), d);
		double pW = playerBR.x - playerTL.x;
		double pH = playerBR.y - playerTL.y;
		
		Point2D.Double playerCenter = new Point2D.Double(playerTL.x + pW/2, playerTL.y + pH/2);
		
		//Squarify and rotate(temporary) the image
		BufferedImage before = ImageModification.squarifyImage(AssetManager.getImageAssets(assetName)[0]);
		BufferedImage newImage = ImageModification.rotateImage(before, Math.toRadians(deg++));
		
		int width = (int)(pW * newImage.getWidth()/before.getWidth());
		int height = (int)(pH * newImage.getHeight()/before.getHeight());
		
		//Draw on a buffered image to prevent antialiasing
		BufferedImage buff = ImageModification.createBlankBufferedImage(width, height);
		Graphics2D g2 = buff.createGraphics();
		
		//Draw the image
		g2.drawImage(newImage, 0, 0,
				width-1, height-1, null);
		
		//Dispose of the new graphics object
		g2.dispose();
		
		//Draw the buffer to the screen
		g.drawImage(buff, (int)(playerCenter.x - width/2.0),
				(int)(playerCenter.y - height/2.0), width, height, null);
	}
	
	public static void sdraw(Graphics2D g, NetworkDrawableObject ndo, Viewport vp, WindowData d) {
		//Do math to determine positioning
		
		Point2D.Double playerTL = vp.gameToScreen(new Point2D.Double(ndo.getBox().getX(), ndo.getBox().getY()), d);
		Point2D.Double playerBR = vp.gameToScreen(new Point2D.Double(ndo.getBox().getMaxX(), ndo.getBox().getMaxY()), d);
		double pW = playerBR.x - playerTL.x;
		double pH = playerBR.y - playerTL.y;
		
		Point2D.Double playerCenter = new Point2D.Double(playerTL.x + pW/2, playerTL.y + pH/2);
		
		//Squarify and rotate(temporary) the image
		BufferedImage before = ImageModification.squarifyImage(AssetManager.getImageAssets(ndo.getAsset())[0]);
		BufferedImage newImage = before/*ImageModification.rotateImage(before, Math.toRadians(deg++))*/;
		
		int width = (int)(pW * newImage.getWidth()/before.getWidth());
		int height = (int)(pH * newImage.getHeight()/before.getHeight());
		
		//Draw on a buffered image to prevent antialiasing
		BufferedImage buff = ImageModification.createBlankBufferedImage(width, height);
		Graphics2D g2 = buff.createGraphics();
		
		//Draw the image
		g2.drawImage(newImage, 0, 0,
				width-1, height-1, null);
		
		//Dispose of the new graphics object
		g2.dispose();
		
		//Draw the buffer to the screen
		g.drawImage(buff, (int)(playerCenter.x - width/2.0),
				(int)(playerCenter.y - height/2.0), width, height, null);	}
}
