package org.amityregion5.qxrz.client.ui.util;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import java.awt.image.WritableRaster;

public class ImageModification {
	public static BufferedImage createBufferedImage(Image image) {
		BufferedImage buff = new BufferedImage(image.getWidth(null), image.getHeight(null), BufferedImage.TYPE_4BYTE_ABGR);

		Graphics2D g = buff.createGraphics();

		g.drawImage(image, 0, 0, image.getWidth(null), image.getHeight(null), null);

		g.dispose();

		return buff;
	}
	public static BufferedImage createBlankBufferedImage(int width, int height) {
		return new BufferedImage(width, height, BufferedImage.TYPE_4BYTE_ABGR);
	}
	public static Graphics2D getImageGraphics(Image image) {
		return createBufferedImage(image).createGraphics();
	}
	public static Graphics2D getImageGraphics(BufferedImage image) {
		return image.createGraphics();
	}
	public static Image resizeImage(Image image, int width, int height) throws NullPointerException {

		if (image == null) { throw new NullPointerException("No Image sent"); }

		BufferedImage resizedImage = new BufferedImage(width, height, BufferedImage.TYPE_4BYTE_ABGR);

		Graphics2D g = resizedImage.createGraphics();
		g.drawImage(image, 0, 0, width, height, null);
		g.dispose();

		return resizedImage;
	}
	public static Image rotateImage(Image image, double degrees, int extraBorder) throws NullPointerException {

		if (image == null) { throw new NullPointerException("No Image sent"); }

		BufferedImage buff = new BufferedImage(image.getWidth(null) + extraBorder * 2, image.getHeight(null) + extraBorder * 2, BufferedImage.TYPE_4BYTE_ABGR);

		Graphics2D g = buff.createGraphics();

		AffineTransform at = new AffineTransform();
		at.setToIdentity();
		at.translate(image.getWidth(null) / 2 + extraBorder, image.getHeight(null) / 2 + extraBorder);
		at.rotate(Math.toRadians(degrees));
		g.setTransform(at);

		g.drawImage(image, -image.getWidth(null) / 2, -image.getHeight(null) / 2, image.getWidth(null), image.getHeight(null), null);

		g.dispose();

		return buff;
	}
	public static Image setImageColor(Image image, Color color, boolean retainAlpha) {
		BufferedImage buff = createBufferedImage(image);

		int width = buff.getWidth();
		int height = buff.getHeight();
		WritableRaster raster = buff.getRaster();

		for (int xx = 0; xx < width; xx++) {
			for (int yy = 0; yy < height; yy++) {
				int[] pixels = raster.getPixel(xx, yy, (int[]) null);
				pixels[0] = color.getRed();
				pixels[1] = color.getGreen();
				pixels[2] = color.getBlue();

				if (pixels[3] != 0 && !retainAlpha) {
					pixels[3] = color.getAlpha();

				}

				raster.setPixel(xx, yy, pixels);
			}
		}
		return buff;
	}
	public BufferedImage changeColor(BufferedImage image, Color mask, Color replacement) {
		BufferedImage destImage = new BufferedImage(image.getWidth(), image.getHeight(), BufferedImage.TYPE_INT_ARGB);

		Graphics2D g = destImage.createGraphics();
		g.drawImage(image, null, 0, 0);
		g.dispose();

		for (int i = 0; i < destImage.getWidth(); i++) {
			for (int j = 0; j < destImage.getHeight(); j++) {

				int destRGB = destImage.getRGB(i, j);

				if (GuiMath.maskMatchesColor(mask.getRGB(), destRGB)) {
					int rgbnew = GuiMath.getNewPixelRGB(replacement.getRGB(), destRGB);
					destImage.setRGB(i, j, rgbnew);
				}
			}
		}

		return destImage;
	}
}
