package org.amityregion5.qxrz.common.asset;

import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.LineUnavailableException;

public class AudioData
{
	private Clip master;
	private byte[] data;
	private AudioFormat format;

	/**
	 * @param data
	 * @param format
	 */
	public AudioData(byte[] data, AudioFormat format)
	{
		this.data = data;
		this.format = format;
	}

	public Clip getMaster()
	{
		if (master == null)
		{
			try
			{
				master = AudioSystem.getClip();
				master.open(format, data, 0, data.length);
			} catch (LineUnavailableException e)
			{
			}
		}
		return master;
	}

	public Clip getCopy()
	{
		try
		{
			Clip c = AudioSystem.getClip();
			c.open(format, data, 0, data.length);
			return c;
		} catch (LineUnavailableException e)
		{
			e.printStackTrace();
		}
		return null;
	}
}
