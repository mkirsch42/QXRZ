package org.amityregion5.qxrz.common.ui;

import java.util.List;

/**
 * An interface that defines an object as drawable
  *
 * @param <T> the type of object that it is drawing (it's own type)
 */
public interface DrawableObject<T> {
	/**
	 * Get a list of IObjectDrawers that should be used to draw this object
	 * 
	 * @return a list of IObjectDrawers that should be used to draw this object
	 */
	public List<IObjectDrawer<T>> getDrawers();
}
