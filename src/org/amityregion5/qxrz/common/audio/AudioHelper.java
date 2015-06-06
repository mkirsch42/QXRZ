package org.amityregion5.qxrz.common.audio;

import javax.sound.sampled.Clip;

public class AudioHelper {
	public static void play(Clip clip, boolean loop) {
		clip.start();
		clip.loop((loop ? Clip.LOOP_CONTINUOUSLY : 0));
	}
	public static void stop(Clip clip) {
		clip.stop();
	}
}
