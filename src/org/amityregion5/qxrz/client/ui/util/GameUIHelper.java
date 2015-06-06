package org.amityregion5.qxrz.client.ui.util;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;

import org.amityregion5.qxrz.client.ui.screen.WindowData;
import org.amityregion5.qxrz.common.asset.AssetManager;
import org.amityregion5.qxrz.common.ui.NetworkDrawableEntity;
import org.amityregion5.qxrz.common.ui.NetworkDrawableObject;
import org.amityregion5.qxrz.common.ui.Viewport;

public class GameUIHelper {
	public static void draw(Graphics2D g, NetworkDrawableEntity nde, Viewport vp, WindowData d) {
		for (NetworkDrawableObject ndo : nde.getDrawables()) {
			if (ndo.getAsset().equals("--AABB--")) {
				drawAABB(g, ndo.getBox(), vp, d);
			} else {
				drawAsset(g, ndo, vp, d);
			}
		}
	}
	
	private static void drawAABB(Graphics2D g, Rectangle2D box, Viewport vp, WindowData d) {
		//Do math to determine drawing points
		double xFact = d.getWidth()/vp.width;
		double xOff = vp.getXOff() * xFact;
		double yFact = d.getHeight()/vp.height;
		double yOff = vp.getYOff() * xFact;
		int width = (int)(box.getWidth() * xFact);
		int height = (int)(box.getHeight() * yFact);
		
		//Draw it on a buffered image (to prevent antialiasing)
		BufferedImage buff = ImageModification.createBlankBufferedImage(width, height);
		Graphics2D g2 = buff.createGraphics();
		
		//Set color to black
		g2.setColor(Color.BLACK);
		//Draw the rectangle
		g2.drawRect(0, 0, width-1, height-1);
		
		//get rid of new graphics object
		g2.dispose();
		
		//Draw the generated image
		g.drawImage(buff, (int)(box.getX() * xFact - xOff), (int)(box.getY() * yFact - yOff), width, height, null);
	}
	
	private static void drawAsset(Graphics2D g, NetworkDrawableObject ndo, Viewport vp, WindowData d) {
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
				(int)(playerCenter.y - height/2.0), width, height, null);
	}
}
