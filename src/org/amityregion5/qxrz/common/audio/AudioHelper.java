package org.amityregion5.qxrz.common.audio;

import javax.sound.sampled.Clip;
import javax.sound.sampled.LineEvent.Type;
import org.amityregion5.qxrz.common.asset.AssetManager;

public class AudioHelper {
	public static void play(Clip clip, boolean loop) {
		clip.start();
		clip.loop((loop ? Clip.LOOP_CONTINUOUSLY : 0));
	}
	
	public static Clip playCopyClip(String clipName) {
		Clip c = AssetManager.getCopyClip(clipName);
		
		play(c, false);
		
		c.addLineListener((e)->{
			if (e.getType() == Type.STOP) {
				c.close();
				c.flush();
				c.drain();
			}
		});
		
		return c;
	}

	
	public static void stop(Clip clip) {
		clip.stop();
	}
}
