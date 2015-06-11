package org.amityregion5.qxrz.client.ui.screen;

import java.awt.Graphics2D;
import org.amityregion5.qxrz.client.ui.MainGui;
import org.amityregion5.qxrz.common.asset.AnimatedImageContainer;
import org.amityregion5.qxrz.common.asset.AssetManager;

/**
 * The Main Menu Screen
 */
public class TutorialScreen extends AbstractScreen
{

	public TutorialScreen(IScreen s, MainGui gui) {
		super(s, gui);
		AnimatedImageContainer aic = (AnimatedImageContainer) AssetManager.getImageAssets("tutAnim")[0];
		aic.setIndex(0);
	}

	@Override
	protected void draw(Graphics2D g, WindowData windowData) {
		AnimatedImageContainer aic = (AnimatedImageContainer) AssetManager.getImageAssets("tutAnim")[0];
		if (aic.getIndex() >= aic.getFrames() - 1) {
			gui.setCurrentScreen(getReturnScreen());
		} else {
			g.drawImage(aic.getImage(gui.getFrameID()), 0, 0, windowData.getWidth(), windowData.getHeight(), null);
		}
	}

	@Override
	protected void cleanup(){}
}
