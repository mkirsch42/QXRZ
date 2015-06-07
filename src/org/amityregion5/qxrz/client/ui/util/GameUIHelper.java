package org.amityregion5.qxrz.client.ui.util;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.font.FontRenderContext;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

import org.amityregion5.qxrz.client.ui.ChatMessageTime;
import org.amityregion5.qxrz.client.ui.MainGui;
import org.amityregion5.qxrz.client.ui.screen.WindowData;
import org.amityregion5.qxrz.common.asset.AssetManager;
import org.amityregion5.qxrz.common.ui.NetworkDrawableEntity;
import org.amityregion5.qxrz.common.ui.NetworkDrawableObject;
import org.amityregion5.qxrz.common.ui.Viewport;

public class GameUIHelper {
	public static void draw(Graphics2D g, NetworkDrawableEntity nde,
			Viewport vp, WindowData d) {
		for (NetworkDrawableObject ndo : nde.getDrawables()) {
			if (ndo.getAsset().equals("--AABB--")) {
				drawAABB(g, ndo.getBox(), vp, d);
			} else {
				drawAsset(g, ndo, vp, d);
			}
		}
		drawAABB(g, nde.getBox(), vp, d);
		if(nde.getNametag()!="")
		{
			System.out.println(nde.getBox().getWidth());
			drawNT(g, nde.getNametag(), 10F, nde.getBox().getCenterX(), nde.getBox().getMaxY(), vp, d);
		}
	}

	private static void drawNT(Graphics2D g, String nt, float fontSize, double midX, double y, Viewport vp,
			WindowData d) {
		// Do math to determine drawing points
		double xFact = d.getWidth() / vp.width;
		double xOff = vp.getXOff() * xFact;
		double yFact = d.getHeight() / vp.height;
		double yOff = vp.getYOff() * xFact;
		//int width = (int) (box.getWidth() * xFact);
		//int height = (int) (box.getHeight() * yFact);
		g.setFont(g.getFont().deriveFont(fontSize));
		Rectangle2D bounds = GuiMath.getStringBounds(g, nt, 0, 0);
		//Rectangle2D bounds = g.getFont().getStringBounds(nt, new FontRenderContext(null, true, true));
		float w = (float) (bounds.getWidth());
		float h = (float) (bounds.getHeight());
		System.out.println(w+" , "+h);
		double x = midX - w/2;
		// Draw it on a buffered image (to prevent antialiasing)
		BufferedImage buff = ImageModification.createBlankBufferedImage((int)w, (int)h);
		Graphics2D g2 = buff.createGraphics();
		g2.setFont(g.getFont());
		// Set color to black
		g2.setColor(Color.BLACK);
		// Draw the rectangle
		GuiUtil.drawString(g2, nt, CenterMode.CENTER, (int)(w/2), (int)(h/2));
		g2.drawRect(0, 0, (int)w, (int)h);
		// get rid of new graphics object
		g2.dispose();

		// Draw the generated image
		g.drawImage(buff, (int) (x * xFact - xOff), (int) (y * yFact - yOff), (int)w, (int)h, null);
	}
	
	private static void drawAABB(Graphics2D g, Rectangle2D box, Viewport vp,
			WindowData d) {
		// Do math to determine drawing points
		double xFact = d.getWidth() / vp.width;
		double xOff = vp.getXOff() * xFact;
		double yFact = d.getHeight() / vp.height;
		double yOff = vp.getYOff() * xFact;
		int width = (int) (box.getWidth() * xFact);
		int height = (int) (box.getHeight() * yFact);

		// Draw it on a buffered image (to prevent antialiasing)
		BufferedImage buff = ImageModification.createBlankBufferedImage(width,
				height);
		Graphics2D g2 = buff.createGraphics();

		// Set color to black
		g2.setColor(Color.BLACK);
		// Draw the rectangle
		g2.drawRect(0, 0, width - 1, height - 1);

		// get rid of new graphics object
		g2.dispose();

		// Draw the generated image
		g.drawImage(buff, (int) (box.getX() * xFact - xOff), (int) (box.getY()
				* yFact - yOff), width, height, null);
	}

	private static void drawAsset(Graphics2D g, NetworkDrawableObject ndo,
			Viewport vp, WindowData d) {
		// Do math to determine positioning
		Point2D.Double playerTL = vp.gameToScreen(new Point2D.Double(ndo
				.getBox().getX(), ndo.getBox().getY()), d);
		Point2D.Double playerBR = vp.gameToScreen(new Point2D.Double(ndo
				.getBox().getMaxX(), ndo.getBox().getMaxY()), d);
		double pW = playerBR.x - playerTL.x;
		double pH = playerBR.y - playerTL.y;

		Point2D.Double playerCenter = new Point2D.Double(playerTL.x + pW / 2,
				playerTL.y + pH / 2);

		// Squarify and rotate(temporary) the image
		BufferedImage before = ImageModification.squarifyImage(AssetManager
				.getImageAssets(ndo.getAsset())[0]);
		BufferedImage newImage = before/*
										 * ImageModification.rotateImage(before,
										 * Math.toRadians(deg++))
										 */;

		int width = (int) (pW * newImage.getWidth() / before.getWidth());
		int height = (int) (pH * newImage.getHeight() / before.getHeight());

		// Draw on a buffered image to prevent antialiasing
		BufferedImage buff = ImageModification.createBlankBufferedImage(width,
				height);
		Graphics2D g2 = buff.createGraphics();

		// Draw the image
		g2.drawImage(newImage, 0, 0, width - 1, height - 1, null);

		// Dispose of the new graphics object
		g2.dispose();

		// Draw the buffer to the screen
		g.drawImage(buff, (int) (playerCenter.x - width / 2.0),
				(int) (playerCenter.y - height / 2.0), width, height, null);
	}

	public static BufferedImage getChatMessagesImage(int width, int height,
			MainGui gui, Color textColor, Color fromServerTextColor, float textSize) {
		BufferedImage buff = ImageModification.createBlankBufferedImage(width,
				height);

		Graphics2D gBuff = buff.createGraphics();

		GuiUtil.applyRenderingHints(gBuff);

		gBuff.setFont(gBuff.getFont().deriveFont(textSize));
		Font noBold = gBuff.getFont();
		gBuff.setColor(textColor);

		int totalYTrans = 0;
		for (ChatMessageTime c : gui.getMessages()) {
			if(c.msg.isFromServer())
			{
				gBuff.setFont(gBuff.getFont().deriveFont(Font.BOLD));
				gBuff.setColor(fromServerTextColor);
			}
			else
			{
				gBuff.setFont(noBold);
				gBuff.setColor(textColor);
			}
			int x = 0;
			int subIndex = 0;
			int endIndex = c.msg.getMessage().length();

			ArrayList<String> lines = new ArrayList<String>();

			while (subIndex < endIndex) {
				Rectangle r = GuiMath.getStringBounds(gBuff, c.msg.getMessage()
						.substring(subIndex, endIndex), 0, 0);
				if (r.width >= buff.getWidth() - 20) {
					endIndex = (int) ((buff.getWidth() - 20) / (double) r.width * (endIndex - subIndex));
					r = GuiMath.getStringBounds(gBuff, c.msg.getMessage()
							.substring(subIndex, endIndex), 0, 0);
					while (r.width >= buff.getWidth() - 20) {
						endIndex--;
						r = GuiMath.getStringBounds(gBuff, c.msg.getMessage()
								.substring(subIndex, endIndex), 0, 0);
					}
				}
				/*
				 * while (r.width >= buff.getWidth() - 20) { if (r.width/2 >=
				 * buff.getWidth()-20 && subIndex > 0) { subIndex*=2; } else {
				 * subIndex++; }
				 * 
				 * }
				 */

				totalYTrans += r.height + 2;

				lines.add(c.msg.getMessage().substring(subIndex, endIndex));

				subIndex = endIndex;
				endIndex = c.msg.getMessage().length();
			}

			for (int i = lines.size() - 1; i >= 0; i--) {
				String str = lines.get(i);

				Rectangle r = GuiMath.getStringBounds(gBuff, str, 0, 0);

				gBuff.translate(0, -(r.height + 2));
				GuiUtil.drawString(gBuff, str, CenterMode.LEFT, x + 10,
						(buff.getHeight() - (r.height + 2)) + r.height / 2);
			}

			if (totalYTrans > buff.getHeight()) {
				break;
			}
		}

		gBuff.dispose();

		return buff;
	}
}
