package org.amityregion5.qxrz.client.settings;

import java.awt.RenderingHints;
import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInput;
import java.io.ObjectInputStream;
import java.io.ObjectOutput;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.io.Serializable;
import java.util.Arrays;
import java.util.HashMap;

public class Settings implements Serializable
{
	private static final long serialVersionUID = -777324776814351476L;

	private static HashMap<String, Object> defaultSettings;

	private transient HashMap<String, Object> renderSettings;
	private HashMap<String, Integer> storage;
	private Integer width, height;

	static
	{
		defaultSettings = new HashMap<String, Object>();
		defaultSettings.put("RenderQuality", RenderingHints.VALUE_RENDER_SPEED);
		defaultSettings.put("TextAntialias",
				RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
		defaultSettings.put("Dither", RenderingHints.VALUE_DITHER_ENABLE);
		defaultSettings.put("Antialias", RenderingHints.VALUE_ANTIALIAS_ON);
		defaultSettings.put("FactionalMetrics",
				RenderingHints.VALUE_FRACTIONALMETRICS_ON);
		defaultSettings.put("AlphaInterpolation",
				RenderingHints.VALUE_ALPHA_INTERPOLATION_QUALITY);
		defaultSettings.put("ColorRenderQuality",
				RenderingHints.VALUE_COLOR_RENDER_QUALITY);
		defaultSettings.put("Stroke", RenderingHints.VALUE_STROKE_PURE);
	}

	public Settings()
	{
		load();

		putAllIfAbsent(renderSettings, defaultSettings);
		retainKeys(renderSettings, defaultSettings);
	}

	public void load()
	{
		try
		{
			InputStream file = new FileInputStream("QXRZ_Settings.ini");
			InputStream buffer = new BufferedInputStream(file);
			ObjectInput input = new ObjectInputStream(buffer);

			Settings s = (Settings) input.readObject();

			storage = s.storage;
			width = s.width;
			height = s.height;

			s = null;
			input.close();

			renderSettings = new HashMap<String, Object>();
			readIntToObj(renderSettings, storage, "RenderQuality",
					RenderingHints.VALUE_RENDER_QUALITY,
					RenderingHints.VALUE_RENDER_SPEED);
			readIntToObj(renderSettings, storage, "TextAntialias",
					RenderingHints.VALUE_TEXT_ANTIALIAS_ON,
					RenderingHints.VALUE_TEXT_ANTIALIAS_OFF);
			readIntToObj(renderSettings, storage, "Dither",
					RenderingHints.VALUE_DITHER_ENABLE,
					RenderingHints.VALUE_DITHER_DISABLE);
			readIntToObj(renderSettings, storage, "Antialias",
					RenderingHints.VALUE_ANTIALIAS_ON,
					RenderingHints.VALUE_ANTIALIAS_OFF);
			readIntToObj(renderSettings, storage, "FactionalMetrics",
					RenderingHints.VALUE_FRACTIONALMETRICS_ON,
					RenderingHints.VALUE_FRACTIONALMETRICS_OFF);
			readIntToObj(renderSettings, storage, "AlphaInterpolation",
					RenderingHints.VALUE_ALPHA_INTERPOLATION_QUALITY,
					RenderingHints.VALUE_ALPHA_INTERPOLATION_SPEED);
			readIntToObj(renderSettings, storage, "ColorRenderQuality",
					RenderingHints.VALUE_COLOR_RENDER_QUALITY,
					RenderingHints.VALUE_COLOR_RENDER_SPEED);
			readIntToObj(renderSettings, storage, "Stroke",
					RenderingHints.VALUE_STROKE_PURE,
					RenderingHints.VALUE_STROKE_NORMALIZE);
			storage = null;
		} catch (IOException | ClassNotFoundException e)
		{
			renderSettings = defaultSettings;
			width = 1200;
			height = 800;
		}
	}

	public void readIntToObj(HashMap<String, Object> rS,
			HashMap<String, Integer> so, String key, Object... stuff)
	{
		if (so.get(key) != null)
		{
			rS.put(key, stuff[so.get(key).intValue()]);
		}
	}

	public void writeIntFromObj(HashMap<String, Object> rS,
			HashMap<String, Integer> so, String key, Object... stuff)
	{
		if (rS.get(key) != null)
		{
			so.put(key, Arrays.asList(stuff).indexOf(rS.get(key)));
		}
	}

	public void save()
	{
		try
		{
			OutputStream file = new FileOutputStream("QXRZ_Settings.ini");
			OutputStream buffer = new BufferedOutputStream(file);
			ObjectOutput output = new ObjectOutputStream(buffer);

			storage = new HashMap<String, Integer>();
			writeIntFromObj(renderSettings, storage, "RenderQuality",
					RenderingHints.VALUE_RENDER_QUALITY,
					RenderingHints.VALUE_RENDER_SPEED);
			writeIntFromObj(renderSettings, storage, "TextAntialias",
					RenderingHints.VALUE_TEXT_ANTIALIAS_ON,
					RenderingHints.VALUE_TEXT_ANTIALIAS_OFF);
			writeIntFromObj(renderSettings, storage, "Dither",
					RenderingHints.VALUE_DITHER_ENABLE,
					RenderingHints.VALUE_DITHER_DISABLE);
			writeIntFromObj(renderSettings, storage, "Antialias",
					RenderingHints.VALUE_ANTIALIAS_ON,
					RenderingHints.VALUE_ANTIALIAS_OFF);
			writeIntFromObj(renderSettings, storage, "FactionalMetrics",
					RenderingHints.VALUE_FRACTIONALMETRICS_ON,
					RenderingHints.VALUE_FRACTIONALMETRICS_OFF);
			writeIntFromObj(renderSettings, storage, "AlphaInterpolation",
					RenderingHints.VALUE_ALPHA_INTERPOLATION_QUALITY,
					RenderingHints.VALUE_ALPHA_INTERPOLATION_SPEED);
			writeIntFromObj(renderSettings, storage, "ColorRenderQuality",
					RenderingHints.VALUE_COLOR_RENDER_QUALITY,
					RenderingHints.VALUE_COLOR_RENDER_SPEED);
			writeIntFromObj(renderSettings, storage, "Stroke",
					RenderingHints.VALUE_STROKE_PURE,
					RenderingHints.VALUE_STROKE_NORMALIZE);

			output.writeObject(this);
			output.close();
			storage = null;
		} catch (IOException e)
		{
			e.printStackTrace();
		}
	}

	private static void putAllIfAbsent(HashMap<String, Object> to,
			HashMap<String, Object> from)
	{
		for (String s : from.keySet())
		{
			to.putIfAbsent(s, from.get(s));
		}
	}

	private static void retainKeys(HashMap<String, Object> to,
			HashMap<String, Object> from)
	{
		for (String s : from.keySet())
		{
			if (to.containsKey(s))
			{
				to.remove(to);
			}
		}
	}

	public Object getValue(String key)
	{
		return renderSettings.get(key);
	}

	public void setValue(String key, Object value)
	{
		renderSettings.put(key, value);
	}

	public int getWidth()
	{
		return width;
	}

	public int getHeight()
	{
		return height;
	}
}
