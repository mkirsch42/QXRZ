package org.amityregion5.qxrz.common.asset;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.HashMap;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import javax.imageio.ImageIO;
import org.amityregion5.qxrz.client.Main;
import org.amityregion5.qxrz.common.util.FileUtil;


public class AssetManager
{
	
	private static HashMap<String, BufferedImage> imageAssets = new HashMap<String, BufferedImage>();

	public static void loadAssets()
	{
		try
		{
			imageAssets.put("char/char1", ImageIO.read(FileUtil.getURLOfResource(Main.class, "/char/char1.png")));
			imageAssets.put("weapons/flamethrower", ImageIO.read(FileUtil.getURLOfResource(Main.class, "/weapons/flamethrower.png")));
		}
		catch (IOException e)
		{
			e.printStackTrace();
		}
	}
	
	public static BufferedImage[] getImageAssets(String name) {
		return imageAssets.keySet().stream().filter((s)->s.matches(regexify(name))).map((k)->imageAssets.get(k)).collect(Collectors.toList()).toArray(new BufferedImage[] {});
	}
	
	private static String regexify(String str) {
		String finalString = "";
		
		String[] split = str.split(Pattern.quote("**"));
		for (int i = 0; i<split.length; i++) {
			String[] split2 = split[i].split(Pattern.quote("*"));
			for (int i2 = 0; i2<split2.length; i2++) {
				String[] split3 = split2[i].split(Pattern.quote("?"));
				for (int i3 = 0; i3<split3.length; i3++) {
					finalString += split3[i3] + (i3 == split3.length-1  ? "" : "[^/]?");
				}
				finalString += (split2[i2].endsWith("?") ? "[^/]?" : "");
				finalString += (i2 == split2.length-1 ? "" : "[^/]*");
			}
			finalString += (split[i].endsWith("*") ? "[^/]*" : "");
			finalString += (i == split.length-1 ? "" : ".*");
		}
		finalString += (str.endsWith("**") ? ".*" : "");
		
		return finalString;
		//return str.replace("*", "[^/]*").replace("?", "[^/]?");
	}
}
