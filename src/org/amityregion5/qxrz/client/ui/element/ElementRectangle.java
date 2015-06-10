package org.amityregion5.qxrz.client.ui.element;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.geom.Rectangle2D;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Supplier;

import org.amityregion5.qxrz.client.ui.screen.WindowData;
import org.amityregion5.qxrz.client.ui.util.CenterMode;
import org.amityregion5.qxrz.client.ui.util.GuiUtil;

/**
 * A class representing a rectangle (Could be a button or a label)
 */
public class ElementRectangle extends AGuiElement
{
	//A function to determine to top left corner of the rectangle
	protected Function<WindowData, Point> topLeftFunction;
	//A function to determine the width and height of the rectangle
	protected Function<WindowData, Point> widthHeightFunction;
	//A supplier to determine the name of the rectangle
	protected Supplier<String> name;
	//The three colors of the rectangle
	protected Supplier<Color> background, border, text;
	//Last known window data of the rectangle
	protected WindowData wData;
	//The size/Padding of the rectangle's text
	protected float sizeOrPadding;

	/**
	 * @param topLeftFunction the function to determine to top left corner of the rectangle
	 * @param widthHeightFunction the function to determine the width and height of the rectangle
	 * @param background the background color
	 * @param border the border color
	 * @param sizeOrPadding the size of the text or if it is a negative number to padding for the resize
	 * @param text the text color
	 * @param name the text in the rectangle
	 */
	public ElementRectangle(Function<WindowData, Point> topLeftFunction,
			Function<WindowData, Point> widthHeightFunction,
			Color background, Color border, float sizeOrPadding,
			Color text, String name) {
		this(topLeftFunction, widthHeightFunction, ()->background, ()->border, sizeOrPadding, ()->text, ()->name, null);
	}

	/**
	 * @param topLeftFunction the function to determine to top left corner of the rectangle
	 * @param widthHeightFunction the function to determine the width and height of the rectangle
	 * @param background the background color
	 * @param border the border color
	 * @param sizeOrPadding the size of the text or if it is a negative number to padding for the resize
	 * @param text the text color
	 * @param name the text in the rectangle
	 * @param onClick the click listener for the rectangle
	 */
	public ElementRectangle(Function<WindowData, Point> topLeftFunction,
			Function<WindowData, Point> widthHeightFunction,
			Color background, Color border, float sizeOrPadding,
			Color text, String name, Consumer<WindowData> onClick) {
		this(topLeftFunction, widthHeightFunction, ()->background, ()->border, sizeOrPadding, ()->text, ()->name, onClick);
	}
	
	/**
	 * @param topLeftFunction the function to determine to top left corner of the rectangle
	 * @param widthHeightFunction the function to determine the width and height of the rectangle
	 * @param background the background color
	 * @param border the border color
	 * @param sizeOrPadding the size of the text or if it is a negative number to padding for the resize
	 * @param text the text color
	 * @param name the string supplier for the text in the rectangle
	 */
	public ElementRectangle(Function<WindowData, Point> topLeftFunction,
			Function<WindowData, Point> widthHeightFunction,
			Supplier<Color> background, Supplier<Color> border, float sizeOrPadding,
			Supplier<Color> text, Supplier<String> name, Consumer<WindowData> onClick) {
		this.name = name;
		this.topLeftFunction = topLeftFunction;
		this.widthHeightFunction = widthHeightFunction;
		this.background = background;
		this.border = border;
		this.text = text;
		setClickListener(onClick);
		this.sizeOrPadding = sizeOrPadding;
	}
	
	

	@Override
	protected void draw(Graphics2D g, WindowData windowData)
	{
		//Set the latest window data
		wData = windowData;
		
		//Draw the background
		g.setColor(background.get());
		g.fillRect(getX(), getY(), getWidth(), getHeight());
		
		//Draw the border
		g.setColor(border.get());
		g.drawRect(getX(), getY(), getWidth(), getHeight());
		
		//Set font size
		if (sizeOrPadding > 0) {
			g.setFont(g.getFont().deriveFont(sizeOrPadding));
		} else {
			GuiUtil.scaleFont(name.get(), new Rectangle2D.Double(getX() - sizeOrPadding, getY() - sizeOrPadding, getWidth() + sizeOrPadding, getHeight() + sizeOrPadding), g);
		}
		//Set text color
		g.setColor(text.get());
		//Draw the text
		GuiUtil.drawString(g, name.get(), CenterMode.CENTER, getX() + getWidth()/2, getY() + getHeight()/2);
	}

	@Override
	public boolean isPointInElement(int x, int y)
	{
		return x >= getX() && x <= getX() + getWidth() && y >= getY() && y <= getY() + getHeight();
	}

	/**
	 * @return the x
	 */
	public int getX()
	{
		return topLeftFunction.apply(wData).x;
	}

	/**
	 * @return the y
	 */
	public int getY()
	{
		return topLeftFunction.apply(wData).y;
	}

	/**
	 * @return the width
	 */
	public int getWidth()
	{
		return widthHeightFunction.apply(wData).x;
	}

	/**
	 * @return the height
	 */
	public int getHeight()
	{
		return widthHeightFunction.apply(wData).y;
	}

	/**
	 * @return the topLeftFunction
	 */
	public Function<WindowData, Point> getTopLeftFunction() {
		return topLeftFunction;
	}

	/**
	 * @param topLeftFunction the topLeftFunction to set
	 */
	public void setTopLeftFunction(Function<WindowData, Point> topLeftFunction) {
		this.topLeftFunction = topLeftFunction;
	}

	/**
	 * @return the widthHeightFunction
	 */
	public Function<WindowData, Point> getWidthHeightFunction() {
		return widthHeightFunction;
	}

	/**
	 * @param widthHeightFunction the widthHeightFunction to set
	 */
	public void setWidthHeightFunction(
			Function<WindowData, Point> widthHeightFunction) {
		this.widthHeightFunction = widthHeightFunction;
	}

	/**
	 * @return the background
	 */
	public Color getBackground()
	{
		return background.get();
	}

	/**
	 * @param background the background to set
	 */
	public void setBackground(Supplier<Color> background)
	{
		this.background = background;
	}

	/**
	 * @return the border
	 */
	public Color getBorder()
	{
		return border.get();
	}

	/**
	 * @param border the border to set
	 */
	public void setBorder(Supplier<Color> border)
	{
		this.border = border;
	}

	/**
	 * @return the text
	 */
	public Color getText()
	{
		return text.get();
	}

	/**
	 * @param text the text to set
	 */
	public void setText(Supplier<Color> text)
	{
		this.text = text;
	}

	/**
	 * @return the name
	 */
	public String getName()
	{
		return name.get();
	}

	/**
	 * @param name the name to set
	 */
	public void setName(String name)
	{
		this.name = ()->name;
	}

	/**
	 * @param name the name to set
	 */
	public void setName(Supplier<String> name)
	{
		this.name = name;
	}
	
}
