package org.amityregion5.qxrz.client.ui.element;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Point;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Supplier;
import org.amityregion5.qxrz.client.ui.MainGui;
import org.amityregion5.qxrz.client.ui.screen.WindowData;
import org.amityregion5.qxrz.common.asset.AssetManager;

public class ElementImageRectangle extends ElementRectangle
{
	private MainGui gui;

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
	public ElementImageRectangle(Function<WindowData, Point> topLeftFunction,
			Function<WindowData, Point> widthHeightFunction, Color background,
			Color border, String imageName, Consumer<WindowData> onClick,
			MainGui gui)
	{
		super(topLeftFunction, widthHeightFunction, background, border, 0,
				null, imageName, onClick);
		this.gui = gui;
		// TODO Auto-generated constructor stub
	}

	/**
	 * @param topLeftFunction
	 * @param widthHeightFunction
	 * @param background
	 * @param border
	 * @param sizeOrPadding
	 * @param text
	 * @param name
	 */
	public ElementImageRectangle(Function<WindowData, Point> topLeftFunction,
			Function<WindowData, Point> widthHeightFunction, Color background,
			Color border, String imageName, MainGui gui)
	{
		super(topLeftFunction, widthHeightFunction, background, border, 0,
				null, imageName);
		this.gui = gui;
		// TODO Auto-generated constructor stub
	}

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
	public ElementImageRectangle(Function<WindowData, Point> topLeftFunction,
			Function<WindowData, Point> widthHeightFunction,
			Supplier<Color> background, Supplier<Color> border,
			Supplier<String> imageName, Consumer<WindowData> onClick,
			MainGui gui)
	{
		super(topLeftFunction, widthHeightFunction, background, border, 0,
				null, imageName, onClick);
		this.gui = gui;
	}

	@Override
	protected void draw(Graphics2D g, WindowData windowData)
	{
		// Set the latest window data
		wData = windowData;

		// Draw the background
		g.setColor(background.get());
		g.fillRect(getX(), getY(), getWidth(), getHeight());

		// Draw the border
		g.setColor(border.get());
		g.drawRect(getX(), getY(), getWidth(), getHeight());

		g.drawImage(AssetManager.getImageAssets(getName())[0].getImage(gui
				.getFrameID()), getX(), getY(), getWidth(), getHeight(), null);
	}
}
