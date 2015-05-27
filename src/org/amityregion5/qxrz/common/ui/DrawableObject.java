package org.amityregion5.qxrz.common.ui;

import java.util.List;

public interface DrawableObject<T> {
	public List<IObjectDrawer<T>> getDrawers();
}
