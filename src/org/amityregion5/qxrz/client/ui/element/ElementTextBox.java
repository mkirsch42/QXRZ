package org.amityregion5.qxrz.client.ui.element;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.event.KeyEvent;
import java.util.HashMap;
import java.util.function.Function;
import java.util.function.Predicate;
import org.amityregion5.qxrz.client.ui.screen.WindowData;

public class ElementTextBox extends ElementRectangle {
	
	private String text = "";
	private HashMap<KeyEvent, Integer> cooldownKeys = new HashMap<KeyEvent, Integer>();
	private Predicate<Integer> charPred;
	private Runnable onTextChangeCallback;
	private static final int cooldownClearTime = 30;
	private boolean selected = false;
	private boolean cursorVisible = true;
	private int cursorFlipTime = 0;
	
	private ElementTextBox(Function<WindowData, Point> topLeftFunction,
			Function<WindowData, Point> widthHeightFunction, Color background,
			Color border, float sizeOrPadding, Color text, Predicate<Integer> characterPredicate) {
		super(topLeftFunction, widthHeightFunction, background, border, sizeOrPadding,
				text, "");
		this.charPred = characterPredicate;
	}
	
	public static ElementTextBox createTextBox(Function<WindowData, Point> topLeftFunction,
			Function<WindowData, Point> widthHeightFunction, Color background,
			Color border, float sizeOrPadding, Color text, Predicate<Integer> characterPredicate) {		
		return createTextBox(topLeftFunction, widthHeightFunction, background, border, sizeOrPadding, text, "", characterPredicate);
	}
	public static ElementTextBox createTextBox(Function<WindowData, Point> topLeftFunction,
			Function<WindowData, Point> widthHeightFunction, Color background,
			Color border, float sizeOrPadding, Color text, String defaultText, Predicate<Integer> characterPredicate) {
		ElementTextBox box = new ElementTextBox(topLeftFunction, widthHeightFunction, background, border, sizeOrPadding, text, characterPredicate);
		
		box.text = defaultText;
		box.setName(box::getString);
		box.setClickListener(box::onClickOn);
		box.setClickOffListener(box::onClickOff);
		box.setWhileKeyDownListener(box::whileKeyDown);
		
		return box;
	}
	
	public void setOnTextChangeCallback(Runnable onTextChangeCallback) {
		this.onTextChangeCallback = onTextChangeCallback;
	}
	
	@Override
	protected void draw(Graphics2D g, WindowData windowData) {
		super.draw(g, windowData);
		cooldownKeys.keySet().removeIf((k)->cooldownKeys.get(k)<=0);
		cooldownKeys.replaceAll((k, i)->i-1);
		
		if (cursorFlipTime <= 0) {
			cursorVisible = !cursorVisible;
			cursorFlipTime = cooldownClearTime;
		}
		cursorFlipTime--;
	}
	
	public String getString() {
		return text + (selected && cursorVisible ? "|" : "");
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
			d.getKeysDown().stream().sequential()
			.filter((key)->!cooldownKeys.containsKey(key))
			.forEach((key)->{
				if (key.isActionKey() || Character.isSupplementaryCodePoint(key.getKeyChar()) || key.getKeyCode() == KeyEvent.VK_SHIFT) { return; }
				cooldownKeys.put(key, cooldownClearTime);
				if (key.getKeyCode() == KeyEvent.VK_BACK_SPACE && text.length() >= 1) {
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
