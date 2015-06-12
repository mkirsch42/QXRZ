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
import org.amityregion5.qxrz.client.ui.util.DoubleReturn;
import org.amityregion5.qxrz.common.util.FileUtil;


public class AssetManager
{
	
	private static HashMap<String, ImageContainer> imageAssets = new HashMap<String, ImageContainer>();
	private static HashMap<String, DoubleReturn<Clip, URL>> audioAssets = new HashMap<String, DoubleReturn<Clip, URL>>();

	public static void loadAssets()
	{
		try
		{
			imageAssets.put("players/1/stand", new ImageContainer(ImageIO.read(FileUtil.getURLOfResource(Main.class, "/players/stand.png"))));
			imageAssets.put("players/1/walk", new AnimatedImageContainer(new BufferedImage[] {
					ImageIO.read(FileUtil.getURLOfResource(Main.class, "/players/step0.png")),
					ImageIO.read(FileUtil.getURLOfResource(Main.class, "/players/step1.png"))
			}, 8));
			imageAssets.put("weapons/flamethrower", new ImageContainer(ImageIO.read(FileUtil.getURLOfResource(Main.class, "/weapons/flamethrower.png"))));
			imageAssets.put("weapons/rifle", new ImageContainer(ImageIO.read(FileUtil.getURLOfResource(Main.class, "/weapons/ar.png"))));
			imageAssets.put("weapons/bow", new ImageContainer(ImageIO.read(FileUtil.getURLOfResource(Main.class, "/weapons/bow.png"))));
			imageAssets.put("weapons/revolver", new ImageContainer(ImageIO.read(FileUtil.getURLOfResource(Main.class, "/weapons/revolv.png"))));
			imageAssets.put("weapons/launcher", new ImageContainer(ImageIO.read(FileUtil.getURLOfResource(Main.class, "/weapons/rl.png"))));
			imageAssets.put("weapons/shotgun", new ImageContainer(ImageIO.read(FileUtil.getURLOfResource(Main.class, "/weapons/shot.png"))));
			imageAssets.put("icons/refresh",new ImageContainer( ImageIO.read(FileUtil.getURLOfResource(Main.class, "/icons/refresh.png"))));
			imageAssets.put("icons/refreshDark",new ImageContainer( ImageIO.read(FileUtil.getURLOfResource(Main.class, "/icons/refreshDark.png"))));
			imageAssets.put("icons/cursor",new ImageContainer( ImageIO.read(FileUtil.getURLOfResource(Main.class, "/icons/cursor.png"))));
			imageAssets.put("projectiles/arrow", new ImageContainer(ImageIO.read(FileUtil.getURLOfResource(Main.class, "/projectiles/arrow.png"))));
			imageAssets.put("projectiles/bullet", new ImageContainer(ImageIO.read(FileUtil.getURLOfResource(Main.class, "/projectiles/bullet.png"))));
			imageAssets.put("projectiles/rocket", new ImageContainer(ImageIO.read(FileUtil.getURLOfResource(Main.class, "/projectiles/rocket.png"))));
			imageAssets.put("icons/healthPack", new ImageContainer(ImageIO.read(FileUtil.getURLOfResource(Main.class, "/icons/health.png"))));
			imageAssets.put("tutAnim", new AnimatedImageContainer(new BufferedImage[] {
					null
			}, 10*60));
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
			audioAssets.put("arrow", getAudioClip(FileUtil.getURLOfResource(Main.class, "/audio/arrow.wav")));
			audioAssets.put("explode", getAudioClip(FileUtil.getURLOfResource(Main.class, "/audio/explode.wav")));
			audioAssets.put("flameloop", getAudioClip(FileUtil.getURLOfResource(Main.class, "/audio/flameloop.wav")));
			audioAssets.put("revolvershot", getAudioClip(FileUtil.getURLOfResource(Main.class, "/audio/revolvershot.wav")));
			audioAssets.put("rocket", getAudioClip(FileUtil.getURLOfResource(Main.class, "/audio/rocket.wav")));
			audioAssets.put("shotgun", getAudioClip(FileUtil.getURLOfResource(Main.class, "/audio/shotgun.wav")));
			audioAssets.put("weaponswitch", getAudioClip(FileUtil.getURLOfResource(Main.class, "/audio/weaponswitch.wav")));
		}
		catch (IOException | UnsupportedAudioFileException | LineUnavailableException e)
		{
		}
	}
	
	private static DoubleReturn<Clip, URL> getAudioClip(URL fileURL) throws UnsupportedAudioFileException, IOException, LineUnavailableException {
		AudioInputStream ain = AudioSystem.getAudioInputStream(fileURL);
		Clip c = AudioSystem.getClip();
		c.open(ain);
		return new DoubleReturn<Clip, URL>(c, fileURL);
	}
	
	public static Clip getCopyClip(String assetName) {
		try
		{
			return getAudioClip(audioAssets.get(assetName).b).a;
		}
		catch (UnsupportedAudioFileException | IOException | LineUnavailableException e)
		{
			e.printStackTrace();
		}
		return null;
	}
	
	public static ImageContainer[] getImageAssets(String name) {
		return imageAssets.keySet().stream().sequential().filter((s)->s.matches(regexify(name))).map((k)->imageAssets.get(k)).collect(Collectors.toList()).toArray(new ImageContainer[] {});
	}
	
	public static Clip[] getAudioAssets(String name) {
		return audioAssets.keySet().stream().sequential().filter((s)->s.matches(regexify(name))).map((k)->audioAssets.get(k)).map((dr)->dr.a).collect(Collectors.toList()).toArray(new Clip[] {});
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
