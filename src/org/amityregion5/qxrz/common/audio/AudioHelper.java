package org.amityregion5.qxrz.common.audio;

import javax.sound.sampled.Clip;

public class AudioHelper {
	public static void play(Clip clip) {
		clip.start();
	}
	public static void stop(Clip clip) {
		clip.stop();
	}
}
