package org.amityregion5.qxrz.client.ui.util;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.geom.AffineTransform;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.stream.Collectors;

import org.amityregion5.qxrz.client.ui.MainGui;
import org.amityregion5.qxrz.client.ui.screen.WindowData;
import org.amityregion5.qxrz.common.asset.AssetManager;
import org.amityregion5.qxrz.common.asset.ImageContainer;
import org.amityregion5.qxrz.common.net.ChatMessage;
import org.amityregion5.qxrz.common.ui.NetworkDrawableEntity;
import org.amityregion5.qxrz.common.ui.NetworkDrawableObject;
import org.amityregion5.qxrz.common.ui.NetworkDrawablePlayer;
import org.amityregion5.qxrz.common.ui.Viewport;
import org.amityregion5.qxrz.server.util.ColorUtil;

public class GameUIHelper {
	public static void draw(Graphics2D g, NetworkDrawableEntity nde,
			Viewport vp, WindowData d, MainGui gui, boolean drawHealthBarIfNDP) {
		if(nde==null)
		{
			return;
		}
		for (NetworkDrawableObject ndo : nde.getDrawables()) {
			if (ndo.getAsset().equals("--AABB--")) {
				drawAABB(g, ndo.getBox(), vp, d);
			} else if (ndo.getAsset().startsWith("--LINE--")) {
				drawLine(g, ndo.getBox(), vp, d, ColorUtil.stringToColor(ndo.getAsset().substring(8)));
			} else if (ndo.getAsset().startsWith("--STRING--")) {
				drawNT(g, ndo.getAsset().substring(10), 10F, Color.BLACK, false, ndo.getBox().getCenterX(), ndo.getBox().getMaxY() + 40, vp, d);
			} else {
				drawAsset(g, ndo, vp, d, gui);
			}
		}
		if (nde instanceof NetworkDrawablePlayer) {
			NetworkDrawablePlayer ndp = (NetworkDrawablePlayer)nde;
			if(ndp.getNametag()!="")
			{
				drawNT(g, ndp.getNametag(), 10F, ndp.getNameColor(), ndp.isItalics(), nde.getBox().getCenterX(), nde.getBox().getMaxY() + 40, vp, d);

				if (drawHealthBarIfNDP) {
					Point2D.Double playerTL = vp.gameToScreen(new Point2D.Double(ndp.getBox().getX(), ndp.getBox().getY()), d);
					Point2D.Double playerBR = vp.gameToScreen(new Point2D.Double(ndp.getBox().getMaxX(), ndp.getBox().getMaxY()), d);
					double pW = playerBR.x - playerTL.x;
					//double pH = playerBR.y - playerTL.y;
					
					g.setColor(Color.RED);
					g.fillRect((int)playerTL.x, (int)playerTL.y - 6, (int)pW, 5);

					g.setColor(Color.GREEN);
					g.fillRect((int)playerTL.x, (int)playerTL.y - 6,
							(int)Math.round(pW * (double)ndp.getHealth()/ndp.getMaxHealth()), 5);
					
					g.setColor(Color.BLACK);
				}
			}
		}
		//drawAABB(g, nde.getBox(), vp, d);
	}

	public static void drawObstacle(Graphics2D g, NetworkDrawableEntity nde,
			Viewport vp, WindowData windowData, MainGui gui, boolean b) {
		draw(g, nde, vp, windowData, gui, b);
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

		if(isItalicized)
		{
			g.setFont(g.getFont().deriveFont(Font.PLAIN));
		}
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

	private static void drawLine(Graphics2D g, Rectangle2D box, Viewport vp,
			WindowData d, Color c) {
		// Do math to determine drawing points
		Point2D.Double playerTL = vp.gameToScreen(new Point2D.Double(box.getX(), box.getY()), d);
		Point2D.Double playerBR = vp.gameToScreen(new Point2D.Double(box.getMaxX(), box.getMaxY()), d);

		g.setColor(c);
		g.drawLine((int)playerTL.x, (int)playerTL.y, (int) playerBR.x, (int)playerBR.y);
		//g.drawImage(buff, (int) (playerTL.x), (int) (playerTL.y), (int)pW, (int)pH, null);
	}

	private static void drawAsset(Graphics2D g, NetworkDrawableObject ndo,
			Viewport vp, WindowData d, MainGui gui) {
		// Squarify and rotate(temporary) the image
		ImageContainer[] assets = AssetManager.getImageAssets(ndo.getAsset());

		if (assets == null || assets.length == 0) {
			drawAABB(g, ndo.getBox(), vp, d);
			System.err.println("MISSING ASSET: " + ndo.getAsset());
		} else {
			ImageContainer asset = assets[0];
			// Do math to determine positioning
			Point2D.Double playerTL = vp.gameToScreen(new Point2D.Double(ndo
					.getBox().getX(), ndo.getBox().getY()), d);
			Point2D.Double playerBR = vp.gameToScreen(new Point2D.Double(ndo
					.getBox().getMaxX(), ndo.getBox().getMaxY()), d);
			double pW = playerBR.x - playerTL.x;
			double pH = playerBR.y - playerTL.y;

			BufferedImage img = ImageModification.squarifyImage(asset.getImage(gui.getFrameID()));

			AffineTransform old = g.getTransform();

			AffineTransform at = new AffineTransform();
			at.setToIdentity();
			at.translate(playerTL.x + pW/2, playerTL.y + pH/2);
			at.rotate(ndo.getTheta());
			at.scale(ndo.isFlipH() ? -1 : 1, ndo.isFlipV() ? -1 : 1);
			g.setTransform(at);

			// Draw the buffer to the screen
			g.drawImage(img, -(int)pW/2, -(int)pH/2, (int)pW, (int)pH, null);

			g.setTransform(old);
		}
	}

	public static DoubleReturn<BufferedImage, Integer> getChatMessagesImage(int width, int height,
			MainGui gui, Color textColor, Color fromServerTextColor, float textSize, long deltaMilli, int offset) {
		BufferedImage buff = ImageModification.createBlankBufferedImage(width,
				height);

		Graphics2D gBuff = buff.createGraphics();

		GuiUtil.applyRenderingHints(gBuff, gui);

		gBuff.setFont(gBuff.getFont().deriveFont(textSize));
		Font noBold = gBuff.getFont();
		gBuff.setColor(textColor);
		gBuff.translate(0, offset);

		int totalYTrans = -offset;
		for (ChatMessage c : gui.getMessages().stream().filter((c)->(deltaMilli == -1 || System.currentTimeMillis() - c.getTimestamp() < deltaMilli)).collect(Collectors.toList())) {
			if(c.isFromServer())
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
			int endIndex = c.getMessage().length();

			ArrayList<String> lines = new ArrayList<String>();

			while (subIndex < endIndex) {
				Rectangle r = GuiMath.getStringBounds(gBuff, c.getMessage()
						.substring(subIndex, endIndex), 0, 0);
				if (r.width >= buff.getWidth() - 20) {
					endIndex = (int) ((buff.getWidth() - 20) / (double) r.width * (endIndex - subIndex));
					r = GuiMath.getStringBounds(gBuff, c.getMessage()
							.substring(subIndex, endIndex), 0, 0);
					while (r.width >= buff.getWidth() - 20) {
						endIndex--;
						r = GuiMath.getStringBounds(gBuff, c.getMessage()
								.substring(subIndex, endIndex), 0, 0);
					}
				}

				r.height=(int) textSize;
				totalYTrans += r.height + 2;

				lines.add(c.getMessage().substring(subIndex, endIndex));

				subIndex = endIndex;
				endIndex = c.getMessage().length();
			}

			for (int i = lines.size() - 1; i >= 0; i--) {
				String str = lines.get(i);

				Rectangle r = GuiMath.getStringBounds(gBuff, str, 0, 0);
				r.height=(int) textSize;

				gBuff.translate(0, -(r.height + 2));
				GuiUtil.drawString(gBuff, str, CenterMode.LEFT, x + 10,
						(buff.getHeight() - (r.height + 2)) + r.height / 2);
			}

			if (totalYTrans > buff.getHeight()) {
				break;
			}
		}

		gBuff.dispose();

		DoubleReturn<BufferedImage, Integer> br = new DoubleReturn<BufferedImage, Integer>();

		br.a = buff;
		br.b = Math.max(offset + totalYTrans - buff.getHeight() + 14,0)/*totalYTrans - buff.getHeight(),0)*/;

		return br;
	}
}
