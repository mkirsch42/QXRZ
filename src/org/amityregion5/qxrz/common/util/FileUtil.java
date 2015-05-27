package org.amityregion5.qxrz.common.util;

import java.net.URL;

public class FileUtil
{
	public static URL getURLOfResource(Class<?> startClass, String string) {
		return startClass.getResource(string);
	}
}
