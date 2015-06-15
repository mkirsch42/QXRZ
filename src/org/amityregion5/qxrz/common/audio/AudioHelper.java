package org.amityregion5.qxrz.common.audio;

import javax.sound.sampled.Clip;
import javax.sound.sampled.FloatControl;
import javax.sound.sampled.LineEvent.Type;
import org.amityregion5.qxrz.common.asset.AssetManager;

public class AudioHelper
{
	public static void play(Clip clip, boolean loop)
	{
		clip.start();
		clip.loop((loop ? Clip.LOOP_CONTINUOUSLY : 0));
	}

	public static void setVolume(Clip clip, float db)
	{
		FloatControl gainControl = (FloatControl) clip
				.getControl(FloatControl.Type.MASTER_GAIN);
		db = Math.max(Math.min(db, gainControl.getMaximum()),
				gainControl.getMinimum());
		gainControl.setValue(db); // Reduce volume by 10 decibels.
	}

	public static void setPercentVolume(Clip clip, double percent)
	{
		FloatControl gainControl = (FloatControl) clip
				.getControl(FloatControl.Type.MASTER_GAIN);
		setVolume(
				clip,
				gainControl.getMinimum()
						+ (float) (percent * (gainControl.getMaximum() - gainControl
								.getMinimum())));
	}

	public static Clip playCopyClip(String clipName)
	{
		Clip c = AssetManager.getAudioAssets(clipName)[0].getCopy();

		play(c, false);

		c.addLineListener((e) ->
		{
			if (e.getType() == Type.STOP)
			{
				c.close();
				c.flush();
				c.drain();
			}
		});

		return c;
	}

	public static void stop(Clip clip)
	{
		clip.stop();
	}
}
