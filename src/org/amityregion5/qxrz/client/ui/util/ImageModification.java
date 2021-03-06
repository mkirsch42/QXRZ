package org.amityregion5.qxrz.client.ui.util;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.geom.AffineTransform;
import java.awt.geom.Point2D;
import java.awt.image.BufferedImage;
import java.awt.image.WritableRaster;
import org.amityregion5.qxrz.common.util.MathUtil;

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
	
	public static BufferedImage squarifyImage(BufferedImage img) {
		int size = Math.max(img.getHeight(), img.getWidth());
		
		BufferedImage newImage = createBlankBufferedImage(size, size);
		
		Graphics2D g = newImage.createGraphics();
		
		g.drawImage(img, (size-img.getWidth())/2, (size-img.getHeight())/2, img.getWidth(), img.getHeight(), null);
		
		g.dispose();
		
		return newImage;
	}
	
	public static BufferedImage resizeImage(Image image, int width, int height) throws NullPointerException {

		if (image == null) { throw new NullPointerException("No Image sent"); }

		BufferedImage resizedImage = new BufferedImage(width, height, BufferedImage.TYPE_4BYTE_ABGR);

		Graphics2D g = resizedImage.createGraphics();
		g.drawImage(image, 0, 0, width, height, null);
		g.dispose();

		return resizedImage;
	}
	public static BufferedImage rotateImage(BufferedImage image, double radians) throws NullPointerException {

		Point2D.Double center = new Point2D.Double(image.getWidth()/2.0, image.getHeight()/2.0);
		
		Point2D.Double p1 = MathUtil.getPointAtEndOfLine(center, center.distance(0, 0), radians + MathUtil.getAngleBetweenPoints(center, new Point2D.Double(0,0)));
		Point2D.Double p2 = MathUtil.getPointAtEndOfLine(center, center.distance(image.getWidth(), 0), radians + MathUtil.getAngleBetweenPoints(center, new Point2D.Double(image.getWidth(),0)));
		Point2D.Double p3 = MathUtil.getPointAtEndOfLine(center, center.distance(image.getWidth(), image.getHeight()), radians + MathUtil.getAngleBetweenPoints(center, new Point2D.Double(image.getWidth(),image.getHeight())));
		Point2D.Double p4 = MathUtil.getPointAtEndOfLine(center, center.distance(0, image.getHeight()), radians + MathUtil.getAngleBetweenPoints(center, new Point2D.Double(0,image.getHeight())));
		
		double minX = Math.min(Math.min(p1.x, p2.x), Math.min(p3.x, p4.x));
		double minY = Math.min(Math.min(p1.y, p2.y), Math.min(p3.y, p4.y));
		
		double maxX = Math.max(Math.max(p1.x, p2.x), Math.max(p3.x, p4.x));
		double maxY = Math.max(Math.max(p1.y, p2.y), Math.max(p3.y, p4.y));
		
		double width = maxX - minX;
		double height = maxY - minY;
		
		BufferedImage buff = createBlankBufferedImage((int)Math.ceil(width), (int)Math.ceil(height));

		Graphics2D g = buff.createGraphics();

		AffineTransform at = new AffineTransform();
		at.setToIdentity();
		at.translate(width/2, height/2);
		at.rotate(radians);
		g.setTransform(at);

		g.drawImage(image, -image.getWidth() / 2, -image.getHeight() / 2, image.getWidth(), image.getHeight(), null);

		g.dispose();

		return buff;
	}
	public static BufferedImage setImageColor(Image image, Color color, boolean retainAlpha) {
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
