package org.amityregion5.qxrz.common.ui;

import java.awt.Graphics2D;
import org.amityregion5.qxrz.client.ui.ViewPort;
import org.amityregion5.qxrz.client.ui.screen.WindowData;

public interface IObjectDrawer<T> {
	public void draw(Graphics2D g, T object, ViewPort vp, WindowData d);
}
