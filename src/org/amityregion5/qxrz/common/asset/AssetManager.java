package org.amityregion5.qxrz.common.asset;

import java.awt.Image;
import java.io.IOException;
import java.util.HashMap;
import java.util.stream.Collectors;
import javax.imageio.ImageIO;
import org.amityregion5.qxrz.client.Main;
import org.amityregion5.qxrz.common.util.FileUtil;


public class AssetManager
{
	
	private static HashMap<String, Image> imageAssets = new HashMap<String, Image>();

	public static void loadAssets()
	{
		try
		{
			imageAssets.put("weapons/flamethrower", ImageIO.read(FileUtil.getURLOfResource(Main.class, "weapons/flamethrower.png")));
		}
		catch (IOException e)
		{
			e.printStackTrace();
		}
	}
	
	public static Image[] getImageAssets(String name) {
		return imageAssets.keySet().stream().filter((s)->s.matches(name)).map((k)->imageAssets.get(k)).collect(Collectors.toList()).toArray(new Image[] {});
	}
}
