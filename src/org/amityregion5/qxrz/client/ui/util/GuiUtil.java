package org.amityregion5.qxrz.client.ui.util;

import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.RenderingHints;
import java.awt.event.KeyEvent;
import java.awt.geom.Rectangle2D;

/**
 * 
 * Utility methods for Gui stuffs
 *
 */
public class GuiUtil
{
	/**
	 * Draw a string on your screen
	 * 
	 * @param g the Graphics2D object
	 * @param str the String to draw
	 * @param mode the center mod to draw the string in
	 * @param x the x Coordinate to draw at
	 * @param y the y Coordinate to draw at
	 */
	public static void drawString(Graphics2D g, String str, CenterMode mode,
			int x, int y)
	{
		//Get the bounds of the string
		Rectangle bounds = GuiMath.getStringBounds(g, str, 0, 0);

		//Subtract the bounds
		x -= bounds.x;
		y -= bounds.y;

		//Subtract some more of the bounds depending on how it should be
		if (mode == CenterMode.RIGHT)
		{
			x -= bounds.width;
		} else if (mode == CenterMode.CENTER)
		{
			x -= bounds.width / 2;
		}
		//Move half way up
		y -= bounds.height / 2;
		
		//Draw the string
		g.drawString(str, x, y);
	}

	/**
	 * Scale a font to fit in a rectangle
	 * 
	 * @param text the string to scale
	 * @param rect the rectangle to fit it into
	 * @param g the graphics object
	 * @return the new Font to use
	 */
	public static Font scaleFont(String text, Rectangle2D rect, Graphics2D g)
	{
		//The size of our new font
		float newSize = 1000f;

		//Get a font at that size (1000)
		Font tempFont = g.getFont().deriveFont(newSize);

		//Set our font to that
		g.setFont(tempFont);

		//Calculate the new size using a ratio with the width
		newSize *= rect.getWidth()
				/ GuiMath.getStringBounds(g, text, 0, 0).getWidth();

		//Get a new font at the new size
		tempFont = g.getFont().deriveFont(newSize);
		//Set our font to that
		g.setFont(tempFont);

		//If it is too tall
		if (GuiMath.getStringBounds(g, text, 0, 0).getHeight() > rect
				.getHeight())
		{
			//Do the same calculation as earlier but with the height
			newSize *= rect.getHeight()
					/ GuiMath.getStringBounds(g, text, 0, 0).getHeight();
			tempFont = g.getFont().deriveFont(newSize);
			g.setFont(tempFont);
		}

		//Return the new font (Really not necessary)
		return g.getFont().deriveFont(newSize);
	}

	/**
	 * Set rendering hints to make it look better
	 * 
	 * @param g the graphics object
	 */
	public static void applyRenderingHints(Graphics2D g)
	{
		g.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING,
				RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
		g.setRenderingHint(RenderingHints.KEY_DITHERING,
				RenderingHints.VALUE_DITHER_ENABLE);
		g.setRenderingHint(RenderingHints.KEY_RENDERING,
				RenderingHints.VALUE_RENDER_SPEED);
		g.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
				RenderingHints.VALUE_ANTIALIAS_ON);
		g.setRenderingHint(RenderingHints.KEY_TEXT_LCD_CONTRAST, 100);
		g.setRenderingHint(RenderingHints.KEY_FRACTIONALMETRICS,
				RenderingHints.VALUE_FRACTIONALMETRICS_ON);
		g.setRenderingHint(RenderingHints.KEY_ALPHA_INTERPOLATION,
				RenderingHints.VALUE_ALPHA_INTERPOLATION_QUALITY);
		g.setRenderingHint(RenderingHints.KEY_COLOR_RENDERING,
				RenderingHints.VALUE_COLOR_RENDER_QUALITY);
		g.setRenderingHint(RenderingHints.KEY_STROKE_CONTROL,
				RenderingHints.VALUE_STROKE_PURE);
	}

	/**
	 * Scale a font to fit in a rectangle
	 * @param text
	 * @param rect
	 * @param g
	 * @param maxSize
	 * @return
	 */
	public static Font scaleFont(String text, Rectangle2D rect, Graphics2D g,
			float maxSize)
	{
		Font fnt = scaleFont(text, rect, g);

		if (fnt.getSize2D() > maxSize)
		{
			fnt = fnt.deriveFont(maxSize);
		}

		return fnt;
	}
	
	public static boolean isTextCharacter(KeyEvent k) {
		if (k.isActionKey() || k.getKeyChar() == KeyEvent.CHAR_UNDEFINED) {
			return false;
		}
		return true;
	}
}
