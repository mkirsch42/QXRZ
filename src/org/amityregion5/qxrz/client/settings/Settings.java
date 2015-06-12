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
import java.util.HashMap;

public class Settings implements Serializable
{
	private static final long serialVersionUID = 2132659756229359129L;

	private HashMap<String, Object> storage;

	@SuppressWarnings("unchecked")
	public Settings()
	{
		try{
			InputStream file = new FileInputStream("QXRZ_Settings.ini");
			InputStream buffer = new BufferedInputStream(file);
			ObjectInput input = new ObjectInputStream (buffer);

			storage = (HashMap<String, Object>)input.readObject();
			input.close();
		}catch (IOException | ClassNotFoundException e){
			storage = new HashMap<String, Object>();
		}

		HashMap<String, Object> defaultStorage = new HashMap<String, Object>();
		defaultStorage.put("RenderQuality", RenderingHints.VALUE_RENDER_SPEED);
		defaultStorage.put("TextAntialias", RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
		defaultStorage.put("Dither", RenderingHints.VALUE_DITHER_ENABLE);
		defaultStorage.put("Antialias", RenderingHints.VALUE_ANTIALIAS_ON);
		defaultStorage.put("FactionalMetrics", RenderingHints.VALUE_FRACTIONALMETRICS_ON);
		defaultStorage.put("AlphaInterpolation", RenderingHints.VALUE_ALPHA_INTERPOLATION_QUALITY);
		defaultStorage.put("ColorRenderQuality", RenderingHints.VALUE_COLOR_RENDER_QUALITY);
		defaultStorage.put("Stroke", RenderingHints.VALUE_STROKE_PURE);

		putAllIfAbsent(storage, defaultStorage);
		retainKeys(storage, defaultStorage);
	}

	public void save() {
		try{
			OutputStream file = new FileOutputStream("QXRZ_Settings.ini");
			OutputStream buffer = new BufferedOutputStream(file);
			ObjectOutput output = new ObjectOutputStream(buffer);
			
			output.writeObject(storage);
			
			output.close();
		}catch (IOException e){
			e.printStackTrace();
		}
	}

	private static void putAllIfAbsent(HashMap<String, Object> to, HashMap<String, Object> from) {
		for (String s : from.keySet()) {
			to.putIfAbsent(s, from.get(s));
		}
	}
	private static void retainKeys(HashMap<String, Object> to, HashMap<String, Object> from) {
		for (String s : from.keySet()) {
			if (to.containsKey(s)) {
				to.remove(to);
			}
		}
	}

	public Object getValue(String key) {
		return storage.get(key);
	}

	public void setValue(String key, Object value) {
		storage.put(key, value);
	}
}
