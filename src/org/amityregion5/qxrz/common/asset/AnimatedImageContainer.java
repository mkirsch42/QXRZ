package org.amityregion5.qxrz.common.asset;

import java.awt.image.BufferedImage;

public class AnimatedImageContainer extends ImageContainer
{
	private BufferedImage[] images;
	private int index, framesPerChange, framesUntilChange;
	
	public AnimatedImageContainer(BufferedImage[] images, int framesPerChange)
	{
		super(images[0]);
		this.images = images;
		this.framesPerChange = framesPerChange;
	}
	
	@Override
	public void subUpdate(){
		framesUntilChange++;
		if (framesPerChange >= framesUntilChange) {
			index++;
			index%=images.length;
			img = images[index];
			framesUntilChange = 0;
		}
	}
}
