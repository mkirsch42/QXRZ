package org.amityregion5.qxrz.client.ui.element;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.event.KeyEvent;
import java.awt.geom.Rectangle2D;
import java.util.HashMap;
import java.util.function.Function;
import java.util.function.Predicate;
import org.amityregion5.qxrz.client.ui.screen.WindowData;
import org.amityregion5.qxrz.client.ui.util.CenterMode;
import org.amityregion5.qxrz.client.ui.util.GuiMath;
import org.amityregion5.qxrz.client.ui.util.GuiUtil;

public class ElementTextBox extends ElementRectangle {

	private String text = "";
	private HashMap<KeyEvent, Integer> cooldownKeys = new HashMap<KeyEvent, Integer>();
	private Predicate<Integer> charPred;
	private Runnable onTextChangeCallback, onEnterCallback;
	private static final int cooldownClearTime = 10;
	private boolean selected = false;
	private boolean cursorVisible = true;
	private int cursorFlipTime = 0;

	private ElementTextBox(Function<WindowData, Point> topLeftFunction,
			Function<WindowData, Point> widthHeightFunction, Color background,
			Color border, float sizeOrPadding, Color text,
			Predicate<Integer> characterPredicate) {
		super(topLeftFunction, widthHeightFunction, background, border,
				sizeOrPadding, text, "");
		this.charPred = characterPredicate;
	}

	public static ElementTextBox createTextBox(
			Function<WindowData, Point> topLeftFunction,
			Function<WindowData, Point> widthHeightFunction, Color background,
			Color border, float sizeOrPadding, Color text,
			Predicate<Integer> characterPredicate) {
		return createTextBox(topLeftFunction, widthHeightFunction, background,
				border, sizeOrPadding, text, "", characterPredicate);
	}

	public static ElementTextBox createTextBox(
			Function<WindowData, Point> topLeftFunction,
			Function<WindowData, Point> widthHeightFunction, Color background,
			Color border, float sizeOrPadding, Color text, String defaultText,
			Predicate<Integer> characterPredicate) {
		ElementTextBox box = new ElementTextBox(topLeftFunction,
				widthHeightFunction, background, border, sizeOrPadding, text,
				characterPredicate);

		box.text = defaultText;
		box.setName(box::getString);
		box.setClickListener(box::onClickOn);
		box.setClickOffListener(box::onClickOff);
		box.setWhileKeyDownListener(box::whileKeyDown);

		return box;
	}

	public void setOnTextChangeCallback(Runnable onTextChangeCallback) {
		this.onTextChangeCallback = onTextChangeCallback;
		onTextChangeCallback.run();
	}

	public void setOnEnterCallback(Runnable onEnterCallback) {
		this.onEnterCallback = onEnterCallback;
	}

	@Override
	protected void draw(Graphics2D g, WindowData windowData) {
		// Set the latest window data
		wData = windowData;

		// Draw the background
		g.setColor(background.get());
		g.fillRect(getX(), getY(), getWidth(), getHeight());

		// Draw the border
		g.setColor(border.get());
		g.drawRect(getX(), getY(), getWidth(), getHeight());

		// Set font size
		if (sizeOrPadding > 0) {
			g.setFont(g.getFont().deriveFont(sizeOrPadding));
		} else {
			GuiUtil.scaleFont(name.get(), new Rectangle2D.Double(getX()
					- sizeOrPadding, getY() - sizeOrPadding, getWidth()
					+ sizeOrPadding, getHeight() + sizeOrPadding), g);
		}
		// Set text color
		g.setColor(super.text.get());
		// Draw the text
		GuiUtil.drawString(g, name.get(), CenterMode.LEFT, getX() + 10, getY()
				+ getHeight() / 2);

		cooldownKeys.keySet().removeIf((k) -> cooldownKeys.get(k) <= 0);
		cooldownKeys.replaceAll((k, i) -> i - 1);

		if (cursorFlipTime <= 0) {
			cursorVisible = !cursorVisible;
			cursorFlipTime = cooldownClearTime;
		}
		cursorFlipTime--;

		if (selected && cursorVisible) {
			Rectangle b = GuiMath.getStringBounds(g, text, 0, 0);
			GuiUtil.drawString(g, "|", CenterMode.LEFT, (int) (b.getWidth()
					+ getX() + 10 + 5), getY() + getHeight() / 2);
		}
	}

	public String getString() {
		return text/* + (selected && cursorVisible ? "|" : "") */;
	}

	@Override
	public void setName(String name) {
		text = name;
	}

	protected void onClickOn(WindowData d) {
		selected = true;
	}

	protected void onClickOff(WindowData d) {
		selected = false;
	}

	protected void whileKeyDown(WindowData d) {
		if (selected) {
			d.getKeysDown()
					.stream()
					.sequential()
					.filter((key) -> !cooldownKeys.containsKey(key))
					.forEach(
							(key) -> {
								if (key.getKeyCode() == KeyEvent.VK_ENTER
										&& onEnterCallback != null)
									onEnterCallback.run();
								if (key.isActionKey()
										|| Character
												.isSupplementaryCodePoint(key
														.getKeyChar())
										|| key.getKeyCode() == KeyEvent.VK_SHIFT) {
									return;
								}
								cooldownKeys.put(key, cooldownClearTime);
								if (key.getKeyCode() == KeyEvent.VK_BACK_SPACE
										&& text.length() >= 1) {
									text = text.substring(0, text.length() - 1);
									return;
								}
								if (charPred.test((int) key.getKeyChar())) {
									text += key.getKeyChar();
									if (onTextChangeCallback != null) {
										onTextChangeCallback.run();
									}
								}
							});
		}
	}

	public boolean isSelected() {
		return selected;
	}
}
