package org.amityregion5.qxrz.client.ui.element;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Point;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Supplier;
import org.amityregion5.qxrz.client.ui.screen.WindowData;

/**
 * 
 * Thanks to Daniel Chen for the name "Switcharroney"
 *
 */
public class ElementSwitcharooney<T> extends ElementRectangle
{
	
	private Supplier<T[]> items;
	private Consumer<T> onChange;
	private int index;
	
	/**
	 * @param topLeftFunction
	 * @param widthHeightFunction
	 * @param background
	 * @param border
	 * @param sizeOrPadding
	 * @param text
	 * @param name
	 * @param onClick
	 */
	public ElementSwitcharooney(Function<WindowData, Point> topLeftFunction,
			Function<WindowData, Point> widthHeightFunction,
			Supplier<Color> background, Supplier<Color> border,
			float sizeOrPadding, Supplier<Color> text,
			Supplier<T[]> supplies)
	{
		super(topLeftFunction, widthHeightFunction, background, border, sizeOrPadding,
				text, null, null);
		this.items = supplies;
	}
	
	public static <T> ElementSwitcharooney<T> createSwitharroney(Function<WindowData, Point> topLeftFunction,
			Function<WindowData, Point> widthHeightFunction,
			Supplier<Color> background, Supplier<Color> border,
			float sizeOrPadding, Supplier<Color> text,
			Supplier<T[]> supplies, Consumer<T> onChange) {
		ElementSwitcharooney<T> es = new ElementSwitcharooney<T>(topLeftFunction, widthHeightFunction, background, border, sizeOrPadding, text, supplies);
		
		es.setName(es::indexName);
		es.setOnChange(onChange);
		es.setClickListener(es::onClick);
		
		return es;
	}
	
	public void setOnChange(Consumer<T> onChange)
	{
		this.onChange = onChange;
	}
	
	public Supplier<T[]> getItems()
	{
		return items;
	}
	
	private String indexName()
	{
		indexify();
		return items.get()[index].toString();
	}
	
	private void onClick(WindowData d) {
		index++;
		indexify();
		onChange.accept(items.get()[index]);
	}
	
	private void indexify() {
		index %= items.get().length;
	}
	
	protected void draw(Graphics2D g, WindowData windowData) {
		indexify();
		super.draw(g, windowData);
	}
}
