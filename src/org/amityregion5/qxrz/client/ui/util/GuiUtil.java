package org.amityregion5.qxrz.client.ui.util;

import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.RenderingHints;

public class GuiUtil
{
	public static void drawString(Graphics2D g, String str, CenterMode mode,
			int x, int y)
	{
		Rectangle bounds = GuiMath.getStringBounds(g, str, 0, 0);

		x -= bounds.x;
		y -= bounds.y;

		if (mode == CenterMode.LEFT)
		{
			x -= bounds.width;
		} else if (mode == CenterMode.CENTER)
		{
			x -= bounds.width / 2;
		}
		y -= bounds.height / 2;
		g.drawString(str, x, y);
	}

	public static Font scaleFont(String text, Rectangle rect, Graphics2D g)
	{
		float newSize = 1000f;

		Font tempFont = g.getFont().deriveFont(newSize);

		g.setFont(tempFont);

		newSize *= rect.getWidth()
				/ GuiMath.getStringBounds(g, text, 0, 0).getWidth();

		tempFont = g.getFont().deriveFont(newSize);
		g.setFont(tempFont);

		if (GuiMath.getStringBounds(g, text, 0, 0).getHeight() > rect
				.getHeight())
		{
			newSize *= rect.getHeight()
					/ GuiMath.getStringBounds(g, text, 0, 0).getHeight();
			tempFont = g.getFont().deriveFont(newSize);
			g.setFont(tempFont);
		}

		return g.getFont().deriveFont(newSize);
	}

	public static void applyRenderingHints(Graphics2D g)
	{
		g.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING,
				RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
		g.setRenderingHint(RenderingHints.KEY_DITHERING,
				RenderingHints.VALUE_DITHER_ENABLE);
		g.setRenderingHint(RenderingHints.KEY_RENDERING,
				RenderingHints.VALUE_RENDER_QUALITY);
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

	public static Font scaleFont(String text, Rectangle rect, Graphics2D g,
			float maxSize)
	{
		Font fnt = scaleFont(text, rect, g);

		if (fnt.getSize2D() > maxSize)
		{
			fnt = fnt.deriveFont(maxSize);
		}

		return fnt;
	}
}
