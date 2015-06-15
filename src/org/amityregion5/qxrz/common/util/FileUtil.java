package org.amityregion5.qxrz.common.util;

import java.net.URL;

/**
 * A util class for working with files
 */
public class FileUtil
{
	/**
	 * Used to get the URL of a resource
	 * 
	 * @param startClass
	 *            the Main class
	 * @param string
	 *            the path to the resource
	 * @return the URL to the resource
	 */
	public static URL getURLOfResource(Class<?> startClass, String string)
	{
		return startClass.getResource(string);
	}
}
