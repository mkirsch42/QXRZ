package org.amityregion5.qxrz.common.ui;

public class Viewport {
	public double width, height, xCenter, yCenter;
	
	public double getXOff() {
		return xCenter - width/2;
	}
	
	public double getYOff() {
		return yCenter - height/2;
	}
}