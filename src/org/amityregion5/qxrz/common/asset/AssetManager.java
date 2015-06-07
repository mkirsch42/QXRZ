package org.amityregion5.qxrz.common.asset;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.URL;
import java.util.HashMap;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

import javax.imageio.ImageIO;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;

import org.amityregion5.qxrz.client.Main;
import org.amityregion5.qxrz.common.util.FileUtil;


public class AssetManager
{
	
	private static HashMap<String, BufferedImage> imageAssets = new HashMap<String, BufferedImage>();
	private static HashMap<String, Clip> audioAssets = new HashMap<String, Clip>();

	public static void loadAssets()
	{
		try
		{
			imageAssets.put("players/1", ImageIO.read(FileUtil.getURLOfResource(Main.class, "/players/char1.png")));
			imageAssets.put("weapons/flamethrower", ImageIO.read(FileUtil.getURLOfResource(Main.class, "/weapons/flamethrower.png")));
			imageAssets.put("weapons/rifle", ImageIO.read(FileUtil.getURLOfResource(Main.class, "/weapons/ar.png")));
			imageAssets.put("weapons/bow", ImageIO.read(FileUtil.getURLOfResource(Main.class, "/weapons/bow.png")));
			imageAssets.put("weapons/revolver", ImageIO.read(FileUtil.getURLOfResource(Main.class, "/weapons/revolv.png")));
			imageAssets.put("weapons/launcher", ImageIO.read(FileUtil.getURLOfResource(Main.class, "/weapons/rl.png")));
			imageAssets.put("weapons/shotgun", ImageIO.read(FileUtil.getURLOfResource(Main.class, "/weapons/shot.png")));
			imageAssets.put("icons/refresh", ImageIO.read(FileUtil.getURLOfResource(Main.class, "/icons/refresh.png")));
			imageAssets.put("icons/refreshDark", ImageIO.read(FileUtil.getURLOfResource(Main.class, "/icons/refreshDark.png")));
		}
		catch (IOException e)
		{
			e.printStackTrace();
		}
		
		try
		{
			audioAssets.put("test/BHT", getAudioClip(FileUtil.getURLOfResource(Main.class, "/audio/benny.wav")));
			audioAssets.put("test/elevator", getAudioClip(FileUtil.getURLOfResource(Main.class, "/audio/elevator.wav")));
			audioAssets.put("footstep", getAudioClip(FileUtil.getURLOfResource(Main.class, "/audio/footsteps.wav")));
		}
		catch (IOException | UnsupportedAudioFileException | LineUnavailableException e)
		{
		}
	}
	
	private static Clip getAudioClip(URL fileURL) throws UnsupportedAudioFileException, IOException, LineUnavailableException {
		AudioInputStream ain = AudioSystem.getAudioInputStream(fileURL);
		Clip c = AudioSystem.getClip();
		c.open(ain);
		return c;
	}
	
	public static BufferedImage[] getImageAssets(String name) {
		return imageAssets.keySet().stream().filter((s)->s.matches(regexify(name))).map((k)->imageAssets.get(k)).collect(Collectors.toList()).toArray(new BufferedImage[] {});
	}
	
	public static Clip[] getAudioAssets(String name) {
		return audioAssets.keySet().stream().filter((s)->s.matches(regexify(name))).map((k)->audioAssets.get(k)).collect(Collectors.toList()).toArray(new Clip[] {});
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
