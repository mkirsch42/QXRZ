package org.amityregion5.qxrz.client.ui.util;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.font.FontRenderContext;
import java.awt.font.GlyphVector;

/**
 * A static class for Gui Math
 */
public class GuiMath {

	public static final int ALPHA = 0;
	public static final int RED = 1;
	public static final int GREEN = 2;
	public static final int BLUE = 3;

	public static final int HUE = 0;
	public static final int SATURATION = 1;
	public static final int BRIGHTNESS = 2;

	public static final int TRANSPARENT = 0;

	/**
	 * Get a rectangle that will fully surround a string
	 * 
	 * @param g2 your current graphics object
	 * @param str the string to get the size of
	 * @param x use 0
	 * @param y use 0
	 * @return a rectangle representing the bounds of the string
	 */
	public static Rectangle getStringBounds(Graphics2D g2, String str, float x, float y) {
		FontRenderContext frc = g2.getFontRenderContext();
		GlyphVector gv = g2.getFont().createGlyphVector(frc, str.replace(' ', '.'));
		return gv.getPixelBounds(frc, x, y);
	}

	/**
	 * Convert and RGB integer to an array with an H, S, and B channel
	 * 
	 * @param rgb an integer representing an RGB value (A channel ignored)
	 * @return an array representing an HSB color
	 */
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

	/**
	 * Convert an RGBA number to an array containing the R, G, B, and A channels
	 * @param rgb the RGBA number
	 * @return an array containing the R, G, B, and A channels
	 */
	public static int[] getRGBArray(int rgb) {
		//Uses bit manipulation
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
