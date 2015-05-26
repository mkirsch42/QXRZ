package org.amityregion5.qxrz.client.ui.element;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.geom.Rectangle2D;
import java.util.function.Consumer;
import java.util.function.Function;
import org.amityregion5.qxrz.client.ui.screen.WindowData;
import org.amityregion5.qxrz.client.ui.util.CenterMode;
import org.amityregion5.qxrz.client.ui.util.GuiUtil;

public class ElementRectangle extends AGuiElement
{
	protected Function<WindowData, Point> topLeftFunction;
	protected Function<WindowData, Point> widthHeightFunction;
	protected Color background, border, text;
	protected String name;
	protected WindowData wData;
	protected float sizeOrPadding;

	/**
	 * @param topLeftFunction
	 * @param widthHeightFunction
	 * @param attachWidthToRight
	 * @param background
	 * @param border
	 * @param text
	 * @param name
	 */
	public ElementRectangle(Function<WindowData, Point> topLeftFunction,
			Function<WindowData, Point> widthHeightFunction,
			Color background, Color border, float sizeOrPadding,
			Color text, String name) {
		this.topLeftFunction = topLeftFunction;
		this.widthHeightFunction = widthHeightFunction;
		this.background = background;
		this.border = border;
		this.text = text;
		this.name = name;
		this.sizeOrPadding = sizeOrPadding;
	}

	/**
	 * @param topLeftFunction
	 * @param widthHeightFunction
	 * @param attachWidthToRight
	 * @param background
	 * @param border
	 * @param text
	 * @param name
	 */
	public ElementRectangle(Function<WindowData, Point> topLeftFunction,
			Function<WindowData, Point> widthHeightFunction,
			Color background, Color border, float sizeOrPadding,
			Color text, String name, Consumer<WindowData> onClick) {
		this(topLeftFunction, widthHeightFunction, background, border, sizeOrPadding, text, name);
		setClickListener(onClick);
	}

	@Override
	protected void draw(Graphics2D g, WindowData windowData)
	{
		wData = windowData;
		
		g.setColor(background);
		g.fillRect(getX(), getY(), getWidth(), getHeight());
		
		g.setColor(border);
		g.drawRect(getX(), getY(), getWidth(), getHeight());
		
		if (sizeOrPadding > 0) {
			g.setFont(g.getFont().deriveFont(sizeOrPadding));
		} else {
			GuiUtil.scaleFont(name, new Rectangle2D.Double(getX() - sizeOrPadding, getY() - sizeOrPadding, getWidth() + sizeOrPadding, getHeight() + sizeOrPadding), g);
		}
		g.setColor(text);
		GuiUtil.drawString(g, name, CenterMode.CENTER, getX() + getWidth()/2, getY() + getHeight()/2);
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
		return background;
	}

	/**
	 * @param background the background to set
	 */
	public void setBackground(Color background)
	{
		this.background = background;
	}

	/**
	 * @return the border
	 */
	public Color getBorder()
	{
		return border;
	}

	/**
	 * @param border the border to set
	 */
	public void setBorder(Color border)
	{
		this.border = border;
	}

	/**
	 * @return the text
	 */
	public Color getText()
	{
		return text;
	}

	/**
	 * @param text the text to set
	 */
	public void setText(Color text)
	{
		this.text = text;
	}

	/**
	 * @return the name
	 */
	public String getName()
	{
		return name;
	}

	/**
	 * @param name the name to set
	 */
	public void setName(String name)
	{
		this.name = name;
	}
	
}
