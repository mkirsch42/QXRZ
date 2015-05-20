package org.amityregion5.qxrz.client.ui.util;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.font.FontRenderContext;
import java.awt.font.GlyphVector;

public class GuiMath {

	public static final int ALPHA = 0;
	public static final int RED = 1;
	public static final int GREEN = 2;
	public static final int BLUE = 3;

	public static final int HUE = 0;
	public static final int SATURATION = 1;
	public static final int BRIGHTNESS = 2;

	public static final int TRANSPARENT = 0;

	public static Rectangle getStringBounds(Graphics2D g2, String str, float x, float y) {
		FontRenderContext frc = g2.getFontRenderContext();
		GlyphVector gv = g2.getFont().createGlyphVector(frc, str);
		return gv.getPixelBounds(frc, x, y);
	}

	public static float[] getHSBArray(int rgb) {
		int[] rgbArr = getRGBArray(rgb);
		return Color.RGBtoHSB(rgbArr[RED], rgbArr[GREEN], rgbArr[BLUE], null);
	}

	public static int getNewPixelRGB(int replacement, int destRGB) {
		float[] destHSB = getHSBArray(destRGB);
		float[] replHSB = getHSBArray(replacement);

		int rgbnew = Color.HSBtoRGB(replHSB[HUE], replHSB[SATURATION], destHSB[BRIGHTNESS]);
		return rgbnew;
	}

	public static int[] getRGBArray(int rgb) {
		return new int[] { rgb >> 24 & 0xff, rgb >> 16 & 0xff, rgb >> 8 & 0xff, rgb & 0xff };
	}

	public static boolean maskMatchesColor(int maskRGB, int destRGB) {
		float[] hsbMask = getHSBArray(maskRGB);
		float[] hsbDest = getHSBArray(destRGB);

		if (hsbMask[HUE] == hsbDest[HUE] && hsbMask[SATURATION] == hsbDest[SATURATION] && getRGBArray(destRGB)[ALPHA] != TRANSPARENT) {
			return true;
		}
		return false;
	}
}
