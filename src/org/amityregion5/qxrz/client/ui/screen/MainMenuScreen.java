package org.amityregion5.qxrz.client.ui.screen;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Point;
import org.amityregion5.qxrz.client.ui.MainGui;
import org.amityregion5.qxrz.client.ui.element.ElementRectangle;
import org.amityregion5.qxrz.common.asset.AssetManager;
import org.amityregion5.qxrz.common.audio.AudioHelper;

/**
 * The Main Menu Screen
 */
public class MainMenuScreen extends AbstractScreen
{

	public MainMenuScreen(MainGui gui)
	{
		super(null, gui);

		// Add the title
		elements.add(new ElementRectangle((w) ->
		{
			return new Point(0, 10);
		}, (w) ->
		{
			return new Point(w.getWidth(), 190);
		}, Color.BLACK, Color.BLACK, -40f, Color.WHITE, "QXRZ"));

		// Debug/Single player mode

		elements.add(new ElementRectangle((w) ->
		{
			return new Point(100, 200);
		}, (w) ->
		{
			return new Point(w.getWidth() - 200, 50);
		}, Color.DARK_GRAY, Color.WHITE, -20f, Color.WHITE, "Join game",
				(w) -> gui
						.setCurrentScreen(new ServerSelectionScreen(this, gui))));

		elements.add(new ElementRectangle((w) ->
		{
			return new Point(100, 300);
		}, (w) ->
		{
			return new Point(w.getWidth() - 200, 50);
		}, Color.DARK_GRAY, Color.WHITE, -20f, Color.WHITE, "View Tutorial",
				(w) -> gui.setCurrentScreen(new TutorialScreen(this, gui))));

		elements.add(new ElementRectangle((w) ->
		{
			return new Point(100, 400);
		}, (w) ->
		{
			return new Point(w.getWidth() - 200, 50);
		}, Color.DARK_GRAY, Color.WHITE, -20f, Color.WHITE, "Settings",
				(w) -> gui.setCurrentScreen(new OptionsScreen(this, gui))));

		// Quit Button
		elements.add(new ElementRectangle((w) ->
		{
			return new Point(100, w.getHeight() - 100);
		}, (w) ->
		{
			return new Point(w.getWidth() - 200, 50);
		}, Color.DARK_GRAY, Color.WHITE, -20f, Color.WHITE, "Quit", (w) ->
		{
			System.exit(0);
		}));
	}

	@Override
	protected void draw(Graphics2D g, WindowData windowData)
	{
		// Fill the background with black
		g.setColor(Color.black);
		g.fillRect(0, 0, windowData.getWidth(), windowData.getHeight());
	}

	@Override
	public void onScreenChange(boolean leaving)
	{
		if (leaving)
		{
			AudioHelper.stop(AssetManager.getAudioAssets("test/BHT")[0]
					.getMaster());
		} else
		{
			AudioHelper.play(
					AssetManager.getAudioAssets("test/BHT")[0].getMaster(),
					true);
		}
	}

	@Override
	public IScreen getReturnScreen()
	{
		return new LoadingScreen();
	}

	@Override
	public boolean setReturnScreen(IScreen s)
	{
		return false;
	}

	@Override
	protected void cleanup()
	{
	}
}
