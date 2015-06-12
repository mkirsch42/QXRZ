package org.amityregion5.qxrz.client.ui.screen;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.RenderingHints;
import org.amityregion5.qxrz.client.ui.MainGui;
import org.amityregion5.qxrz.client.ui.element.ElementRectangle;
import org.amityregion5.qxrz.client.ui.element.ElementSwitcharooney;

/**
 * The Main Menu Screen
 */
public class OptionsScreen extends AbstractScreen
{

	public OptionsScreen(IScreen s, MainGui gui) {
		super(s, gui);
		
		elements.add(new ElementRectangle((w) -> {return new Point(50, 50);},
				(w) -> {return new Point(50, 50);},
				Color.LIGHT_GRAY, Color.BLACK, -10f, Color.BLACK, "<", 
				(w) -> {cleanup();gui.setCurrentScreen(getReturnScreen());}));
		
		//Render Quality
		elements.add(ElementSwitcharooney.createSwitharroney(
				(w)->{return new Point(150, 100);}, (w)->{return new Point(w.getWidth()/2-200, 50);},
				()->Color.DARK_GRAY, ()->Color.LIGHT_GRAY, 14f, ()->Color.WHITE, "Render Mode",
				()->{return new Object[]{RenderingHints.VALUE_RENDER_QUALITY, RenderingHints.VALUE_RENDER_SPEED};}, (o)->{
					gui.getSettings().setValue("RenderQuality", o);
				}, gui.getSettings().getValue("RenderQuality")));
		
		//Text Antialias
		elements.add(ElementSwitcharooney.createSwitharroney(
				(w)->{return new Point(w.getWidth()/2+50, 100);}, (w)->{return new Point(w.getWidth()/2-200, 50);},
				()->Color.DARK_GRAY, ()->Color.LIGHT_GRAY, 14f, ()->Color.WHITE, "Text Antialias",
				()->{return new Object[]{RenderingHints.VALUE_TEXT_ANTIALIAS_ON, RenderingHints.VALUE_TEXT_ANTIALIAS_OFF};}, (o)->{
					gui.getSettings().setValue("TextAntialias", o);
				}, gui.getSettings().getValue("TextAntialias")));
		
		//Dither
		elements.add(ElementSwitcharooney.createSwitharroney(
				(w)->{return new Point(150, 200);}, (w)->{return new Point(w.getWidth()/2-200, 50);},
				()->Color.DARK_GRAY, ()->Color.LIGHT_GRAY, 14f, ()->Color.WHITE, "Dither",
				()->{return new Object[]{RenderingHints.VALUE_DITHER_ENABLE, RenderingHints.VALUE_DITHER_DISABLE};}, (o)->{
					gui.getSettings().setValue("Dither", o);
				}, gui.getSettings().getValue("Dither")));
		
		//Antialias
		elements.add(ElementSwitcharooney.createSwitharroney(
				(w)->{return new Point(w.getWidth()/2+50, 200);}, (w)->{return new Point(w.getWidth()/2-200, 50);},
				()->Color.DARK_GRAY, ()->Color.LIGHT_GRAY, 14f, ()->Color.WHITE, "Antialias",
				()->{return new Object[]{RenderingHints.VALUE_ANTIALIAS_ON, RenderingHints.VALUE_ANTIALIAS_OFF};}, (o)->{
					gui.getSettings().setValue("Antialias", o);
				}, gui.getSettings().getValue("Antialias")));
		
		//FactionalMetrics
		elements.add(ElementSwitcharooney.createSwitharroney(
				(w)->{return new Point(150, 300);}, (w)->{return new Point(w.getWidth()/2-200, 50);},
				()->Color.DARK_GRAY, ()->Color.LIGHT_GRAY, 14f, ()->Color.WHITE, "Fractional Metrics",
				()->{return new Object[]{RenderingHints.VALUE_FRACTIONALMETRICS_ON, RenderingHints.VALUE_FRACTIONALMETRICS_OFF};}, (o)->{
					gui.getSettings().setValue("FactionalMetrics", o);
				}, gui.getSettings().getValue("FactionalMetrics")));
		
		//AlphaInterpolation
		elements.add(ElementSwitcharooney.createSwitharroney(
				(w)->{return new Point(w.getWidth()/2+50, 300);}, (w)->{return new Point(w.getWidth()/2-200, 50);},
				()->Color.DARK_GRAY, ()->Color.LIGHT_GRAY, 14f, ()->Color.WHITE, "Alpha Interpolation",
				()->{return new Object[]{RenderingHints.VALUE_ALPHA_INTERPOLATION_QUALITY, RenderingHints.VALUE_ALPHA_INTERPOLATION_SPEED};}, (o)->{
					gui.getSettings().setValue("AlphaInterpolation", o);
				}, gui.getSettings().getValue("AlphaInterpolation")));
		
		//ColorRenderQuality
		elements.add(ElementSwitcharooney.createSwitharroney(
				(w)->{return new Point(150, 400);}, (w)->{return new Point(w.getWidth()/2-200, 50);},
				()->Color.DARK_GRAY, ()->Color.LIGHT_GRAY, 14f, ()->Color.WHITE, "Color Render",
				()->{return new Object[]{RenderingHints.VALUE_COLOR_RENDER_QUALITY, RenderingHints.VALUE_COLOR_RENDER_SPEED};}, (o)->{
					gui.getSettings().setValue("ColorRenderQuality", o);
				}, gui.getSettings().getValue("ColorRenderQuality")));
		
		//Stroke
		elements.add(ElementSwitcharooney.createSwitharroney(
				(w)->{return new Point(w.getWidth()/2+50, 400);}, (w)->{return new Point(w.getWidth()/2-200, 50);},
				()->Color.DARK_GRAY, ()->Color.LIGHT_GRAY, 14f, ()->Color.WHITE, "Stoke Mode",
				()->{return new Object[]{RenderingHints.VALUE_STROKE_PURE, RenderingHints.VALUE_STROKE_NORMALIZE};}, (o)->{
					gui.getSettings().setValue("Stroke", o);
				}, gui.getSettings().getValue("Stroke")));
	}

	@Override
	protected void draw(Graphics2D g, WindowData windowData) {
		g.setColor(Color.BLACK);
		g.fillRect(0, 0, windowData.getWidth(), windowData.getHeight());
	}
	
	@Override
	public void onScreenChange(boolean leaving)
	{
		if (leaving) {
			gui.getSettings().save();
		}
	}

	@Override
	protected void cleanup(){}
}
