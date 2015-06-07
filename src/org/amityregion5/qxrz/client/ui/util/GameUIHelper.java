package org.amityregion5.qxrz.client.ui.util;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.Rectangle;
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
		//drawAABB(g, nde.getBox(), vp, d);
		if(nde.getNametag()!="")
		{
			drawNT(g, nde.getNametag(), 10F, nde.getNTColor(), nde.isNTItalicized(), nde.getBox().getCenterX(), nde.getBox().getMaxY() + 40, vp, d);
		}
	}

	private static void drawNT(Graphics2D g, String nt, float fontSize, Color ntColor, boolean isItalicized, double x, double y, Viewport vp,
			WindowData d) {
		// Do math to determine drawing points
		Point2D.Double pnt = vp.gameToScreen(new Point2D.Double(x, y), d);

		// Set Font stuffs
		g.setFont(g.getFont().deriveFont(fontSize));
		g.setColor(ntColor);

		if(isItalicized)
		{
			g.setFont(g.getFont().deriveFont(Font.ITALIC));
		}
		
		// Draw the String
		GuiUtil.drawString(g, nt, CenterMode.CENTER, (int)(pnt.x), (int)(pnt.y));
	}
	
	private static void drawAABB(Graphics2D g, Rectangle2D box, Viewport vp,
			WindowData d) {
		// Do math to determine drawing points
		Point2D.Double playerTL = vp.gameToScreen(new Point2D.Double(box.getX(), box.getY()), d);
		Point2D.Double playerBR = vp.gameToScreen(new Point2D.Double(box.getMaxX(), box.getMaxY()), d);
		double pW = playerBR.x - playerTL.x;
		double pH = playerBR.y - playerTL.y;

		// Draw it on a buffered image (to prevent antialiasing)
		BufferedImage buff = ImageModification.createBlankBufferedImage((int)Math.ceil(pW),
				(int)Math.ceil(pH));
		Graphics2D g2 = buff.createGraphics();

		// Set color to black
		g2.setColor(Color.BLACK);
		// Draw the rectangle
		g2.drawRect(0, 0, buff.getWidth() - 1, buff.getHeight() - 1);

		// get rid of new graphics object
		g2.dispose();

		// Draw the generated image
		g.drawImage(buff, (int) (playerTL.x), (int) (playerTL.y), (int)pW, (int)pH, null);
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
