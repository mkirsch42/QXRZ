package org.amityregion5.qxrz.common.audio;

import javax.sound.sampled.Clip;
import javax.sound.sampled.FloatControl;
import javax.sound.sampled.LineEvent.Type;
import org.amityregion5.qxrz.common.asset.AssetManager;

public class AudioHelper {
	public static void play(Clip clip, boolean loop) {
		clip.start();
		clip.loop((loop ? Clip.LOOP_CONTINUOUSLY : 0));
	}
	
	public static void setVolume(Clip clip, float db) {
		
		Clip c = AssetManager.getAudioAssets("test/BHT")[0];
		FloatControl gainControl = 
			    (FloatControl) c.getControl(FloatControl.Type.MASTER_GAIN);
		db = Math.max(Math.min(db, gainControl.getMaximum()), gainControl.getMinimum());
		gainControl.setValue(db); // Reduce volume by 10 decibels.


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
