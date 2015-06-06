package org.amityregion5.qxrz.common.ui;

import java.awt.geom.Rectangle2D;
import java.io.Serializable;

public class NetworkDrawableEntity implements Serializable{
	private static final long serialVersionUID = 7459980956380613325L;
	
	private NetworkDrawableObject[] drawables;
	private Rectangle2D box;

	/**
	 * @param drawables
	 */
	public NetworkDrawableEntity(NetworkDrawableObject[] drawables, Rectangle2D box) {
		this.drawables = drawables;
		this.box = box;
	}
	public NetworkDrawableEntity() {
	}
	
	public NetworkDrawableObject[] getDrawables() {
		return drawables;
	}
	
	public void setDrawables(NetworkDrawableObject[] drawables) {
		this.drawables = drawables;
	}
	public void setBox(Rectangle2D box) {
		this.box = box;
	}
	
	public Rectangle2D getBox() {
		return box;
	}
}
