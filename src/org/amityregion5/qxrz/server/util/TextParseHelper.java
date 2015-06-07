package org.amityregion5.qxrz.server.util;

public final class TextParseHelper
{

	public static String boolToOnOff(boolean in)
	{
		if(in)
		{
			return "on";
		}
		return "off";
	}
	
	public static boolean onOffToBool(String in)
	{
		if(in.equalsIgnoreCase("on"))
		{
			return true;
		}
		if(in.equalsIgnoreCase("off"))
		{
			return false;
		}
		throw new IllegalArgumentException("Input is not on or off");
	}
	
}
