package org.amityregion5.qxrz.common.ui;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import org.amityregion5.qxrz.client.ui.screen.WindowData;
import org.amityregion5.qxrz.client.ui.util.ImageModification;
import org.amityregion5.qxrz.server.world.entity.Hitbox;
import org.amityregion5.qxrz.server.world.entity.Hitboxed;

/**
 * 
 * In case you don't know AABB stands for Axis Aligned Bounding Box
 * 
 */
public class AABBDrawer<T extends Hitboxed> implements IObjectDrawer<T> {
	@Override
	public void draw(Graphics2D g, T object, Viewport vp, WindowData d) {
		//Get the hitbox
		Hitbox h = object.getHitbox();
		
		//Do math to determine drawing points
		double xFact = d.getWidth()/vp.width;
		double xOff = vp.getXOff() * xFact;
		double yFact = d.getHeight()/vp.height;
		double yOff = vp.getYOff() * xFact;
		int width = (int)(h.getAABB().getWidth() * xFact);
		int height = (int)(h.getAABB().getHeight() * yFact);
		
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
		g.drawImage(buff, (int)(h.getAABB().getX() * xFact - xOff), (int)(h.getAABB().getY() * yFact - yOff), width, height, null);
	}
}
