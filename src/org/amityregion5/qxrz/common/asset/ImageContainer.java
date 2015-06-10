package org.amityregion5.qxrz.common.asset;

import java.awt.image.BufferedImage;

public class ImageContainer
{
	protected BufferedImage img;
	protected int frameID;
	
	public ImageContainer(BufferedImage img)
	{
		this.img = img;
	}
	
	protected final void update(int frameID){
		if (frameID != this.frameID) {
			subUpdate();
			this.frameID = frameID;
		}
	}
	protected void subUpdate(){}
	public BufferedImage getImage(int frameID){
		update(frameID);
		return img;
	}
}
