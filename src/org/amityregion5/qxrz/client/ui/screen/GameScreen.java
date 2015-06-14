package org.amityregion5.qxrz.client.ui.screen;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.geom.Point2D;
import java.awt.image.BufferedImage;
import java.util.HashMap;

import javax.sound.sampled.Clip;

import org.amityregion5.qxrz.client.ui.MainGui;
import org.amityregion5.qxrz.client.ui.util.CenterMode;
import org.amityregion5.qxrz.client.ui.util.DoubleReturn;
import org.amityregion5.qxrz.client.ui.util.GameUIHelper;
import org.amityregion5.qxrz.client.ui.util.GuiMath;
import org.amityregion5.qxrz.client.ui.util.GuiUtil;
import org.amityregion5.qxrz.common.asset.AssetManager;
import org.amityregion5.qxrz.common.audio.AudioHelper;
import org.amityregion5.qxrz.common.audio.AudioMessage;
import org.amityregion5.qxrz.common.control.NetworkInputData;
import org.amityregion5.qxrz.common.control.NetworkInputMasks;
import org.amityregion5.qxrz.common.net.ChatMessage;
import org.amityregion5.qxrz.common.ui.NetworkDrawableEntity;
import org.amityregion5.qxrz.common.ui.NetworkDrawablePlayer;
import org.amityregion5.qxrz.common.ui.Viewport;
import org.amityregion5.qxrz.common.util.Colors;
import org.amityregion5.qxrz.common.world.WorldManager;
import org.amityregion5.qxrz.server.world.Obstacle;

public class GameScreen extends AbstractScreen {
	// The current viewport
	private Viewport vp = new Viewport();

	private boolean isChatOpen = false;

	private String text = "";
	private HashMap<KeyEvent, Integer> cooldownKeys = new HashMap<KeyEvent, Integer>();
	private static final int cooldownClearTime = 10;
	private boolean cursorVisible = true;
	private int cursorFlipTime = 0;
	private int scrollOffset = 0;

	/**
	 * Create a game screen
	 * 
	 * @param previous
	 *            the return screen
	 * @param gui
	 *            the MainGui object
	 * @param game
	 *            the Game object
	 */
	public GameScreen(IScreen previous, MainGui gui) {
		super(previous, gui);

		// Set viewport defaults
		vp.xCenter = 0 * 100;
		vp.yCenter = 0 * 100;
		vp.height = 40 * 100 * 1.2;
		vp.width = 60 * 100 * 1.2;
	}

	@Override
	protected void draw(Graphics2D g, WindowData windowData) {
		//Fill the screen with white
		GuiUtil.applyRenderingHints(g, gui);
		g.setColor(Color.WHITE);
		g.fillRect(0,0, windowData.getWidth(), windowData.getHeight());

		drawGame(g, windowData);
		drawHUD(g, windowData);

		for (AudioMessage a : gui.getNetworkDrawablePacket().getPlayables()) {
			if (a.isStarting()) {
				System.out.println("Start sound: " + a.getAsset());
				Clip c = AudioHelper.playCopyClip(a.getAsset());
				AudioHelper.setPercentVolume(c, 1/a.getLocation().distance(vp.xCenter, vp.yCenter));
			}
			if (a.isEnding()) {

			}
		}
	}

	private void drawGame(Graphics2D g, WindowData windowData) {
		if (gui.getNetworkDrawablePacket() != null) {
			if (gui.getNetworkDrawablePacket().getClientIndex() != -1) {
				NetworkDrawableEntity player = gui.getNetworkDrawablePacket().getDrawables().get(gui.getNetworkDrawablePacket().getClientIndex());
				vp.xCenter = player.getBox().getCenterX();
				vp.yCenter = player.getBox().getCenterY();
			}

			for (Obstacle o : WorldManager.getWorld(gui.getNetworkDrawablePacket().getCurrentWorld()).getLandscape().getObstacles()) {
				GameUIHelper.drawObstacle(g, o.getNDE(), vp, windowData, gui, true);
			}

			for(int i=0;i<gui.getNetworkDrawablePacket().getDrawables().size();i++)
			{
				if(i!=gui.getNetworkDrawablePacket().getClientIndex())
				{
					GameUIHelper.draw(g, gui.getNetworkDrawablePacket().getDrawables().get(i), vp, windowData, gui, true);
				}
			}

			GameUIHelper.draw(g, gui.getNetworkDrawablePacket().getClientObject(), vp, windowData, gui, false);
		}

		DoubleReturn<BufferedImage, Integer> chat = GameUIHelper.getChatMessagesImage(windowData.getWidth()/2 - 20, windowData.getHeight() - 200, gui, Color.BLACK, Color.RED, 12f, (isChatOpen ? -1 : 10000), scrollOffset);
		g.drawImage(chat.a, null, 10, 100);

		if (!isChatOpen && windowData.getKeysDown().stream().anyMatch((k)->k.getKeyCode()==KeyEvent.VK_T)) {
			isChatOpen = true;
			cooldownKeys.put(windowData.getKeysDown().stream().filter((k)->k.getKeyCode()==KeyEvent.VK_T).findAny().get(), cooldownClearTime);
			text = "";
			scrollOffset = 0;
		}

		if (!isChatOpen && windowData.getKeysDown().stream().anyMatch((k)->k.getKeyCode()==KeyEvent.VK_SLASH)) {
			isChatOpen = true;
			cooldownKeys.put(windowData.getKeysDown().stream().filter((k)->k.getKeyCode()==KeyEvent.VK_SLASH).findAny().get(), cooldownClearTime);
			text = "/";
			scrollOffset = 0;
		}

		//Do network input data stuff
		if (isChatOpen) {
			scrollOffset -= windowData.getMouseWheel()*5;
			if (scrollOffset < 0) {
				scrollOffset = 0;
			}
			if (scrollOffset > chat.b) {
				scrollOffset = chat.b;
			}

			cooldownKeys.keySet().removeIf((k)->cooldownKeys.get(k)<=0);
			cooldownKeys.replaceAll((k, i)->i-1);

			if (cursorFlipTime <= 0) {
				cursorVisible = !cursorVisible;
				cursorFlipTime = cooldownClearTime;
			}
			cursorFlipTime--;

			windowData.getKeysDown().stream().sequential()
			.filter((key)->!cooldownKeys.containsKey(key))
			.forEach((key)->{
				if (key.getKeyCode() == KeyEvent.VK_ESCAPE) {
					text = "";
					isChatOpen = false;
				} else {
					if (key.getKeyCode() == KeyEvent.VK_ENTER && text.length()>0) {
						gui.getNetworkManger().sendObject(new ChatMessage(text));
						text = "";
						isChatOpen = false;
						return;
					} else {
						if (GuiUtil.isTextCharacter(key)) {
							cooldownKeys.put(key, cooldownClearTime);
							if (key.getKeyCode() == KeyEvent.VK_BACK_SPACE && text.length() >= 1) {
								text = text.substring(0, text.length() - 1);
							} else {
								text += key.getKeyChar();
							}
						}
					}
				}
			});

			g.setColor(Color.LIGHT_GRAY);
			g.fillRect(20, windowData.getHeight()-88, windowData.getWidth()/2-40, 16);

			g.setColor(Color.BLACK);
			g.setFont(g.getFont().deriveFont(14f));
			GuiUtil.drawString(g, text , CenterMode.LEFT, 20 + 5, windowData.getHeight() - 80);

			if (cursorVisible) {
				Rectangle b = GuiMath.getStringBounds(g, text, 0, 0);
				GuiUtil.drawString(g, "|", CenterMode.LEFT, (int)(b.getWidth() + 20 + 5), windowData.getHeight() - 80);
			}

			if (gui.getNetworkManger().isConnected()) {
				NetworkInputData nid = new NetworkInputData();

				Point2D.Double mc = vp.screenToGame(new Point2D.Double(windowData.getMouseX(), windowData.getMouseY()), windowData);
				nid.setMouseX(mc.x);
				nid.setMouseY(mc.y);

				//Send the object
				gui.getNetworkManger().sendObject(nid);
			}
		} else if (gui.getNetworkManger().isConnected()) {
			//Create a network input data object
			NetworkInputData nid = new NetworkInputData();

			//Set flags to input data
			nid.set(NetworkInputMasks.W, windowData.getKeysDown().stream().anyMatch((k)->k.getKeyCode()==KeyEvent.VK_W));
			nid.set(NetworkInputMasks.A, windowData.getKeysDown().stream().anyMatch((k)->k.getKeyCode()==KeyEvent.VK_A));
			nid.set(NetworkInputMasks.S, windowData.getKeysDown().stream().anyMatch((k)->k.getKeyCode()==KeyEvent.VK_S));
			nid.set(NetworkInputMasks.D, windowData.getKeysDown().stream().anyMatch((k)->k.getKeyCode()==KeyEvent.VK_D));
			nid.set(NetworkInputMasks.E, windowData.getKeysDown().stream().anyMatch((k)->k.getKeyCode()==KeyEvent.VK_E));
			nid.set(NetworkInputMasks.Q, windowData.getKeysDown().stream().anyMatch((k)->k.getKeyCode()==KeyEvent.VK_Q));
			nid.set(NetworkInputMasks.M1, windowData.getMiceDown().contains(MouseEvent.BUTTON1));
			nid.set(NetworkInputMasks.M2, windowData.getMiceDown().contains(MouseEvent.BUTTON3));
			nid.set(NetworkInputMasks.R, windowData.getKeysDown().stream().anyMatch((k)->k.getKeyCode()==KeyEvent.VK_R));
			nid.set(NetworkInputMasks.SPACE, windowData.getKeysDown().stream().anyMatch((k)->k.getKeyCode()==KeyEvent.VK_SPACE));
			nid.set(NetworkInputMasks.COMMA, windowData.getKeysDown().stream().anyMatch((k)->k.getKeyCode()==KeyEvent.VK_COMMA));
			nid.set(NetworkInputMasks.PERIOD, windowData.getKeysDown().stream().anyMatch((k)->k.getKeyCode()==KeyEvent.VK_PERIOD));
			if (nid.get(NetworkInputMasks.W) || nid.get(NetworkInputMasks.A) || nid.get(NetworkInputMasks.S) || nid.get(NetworkInputMasks.D)) {
				AudioHelper.play(AssetManager.getAudioAssets("footstep")[0].getMaster(), true);
			} else {
				AudioHelper.stop(AssetManager.getAudioAssets("footstep")[0].getMaster());
			}

			//Set mouse coordinate data
			Point2D.Double mc = vp.screenToGame(new Point2D.Double(windowData.getMouseX(), windowData.getMouseY()), windowData);
			nid.setMouseX(mc.x);
			nid.setMouseY(mc.y);

			//Send the object
			gui.getNetworkManger().sendObject(nid);
		}
	}

	private void drawHUD(Graphics2D g, WindowData windowData) {

		if (gui.getNetworkDrawablePacket() == null) {
			return;
		}
		NetworkDrawablePlayer client = gui.getNetworkDrawablePacket().getClientObject();
		if (client != null) {
			int width = 300;
			int height = 150;
			int x = windowData.getWidth() - width;
			int y = windowData.getHeight() - height;

			g.setColor(Colors.TRANS_GRAY);
			g.fillRect(x, y, width, height);

			g.setColor(Color.WHITE);
			g.setFont(g.getFont().deriveFont(14F));
			GuiUtil.drawString(g, gui.getUsername(), CenterMode.LEFT, x + 10, y + 10);

			g.setColor(Color.RED);
			g.fillRect(x + 10, y + 10 + 8, 200, 25);

			g.setColor(Color.GREEN);
			g.fillRect(x + 10, y + 10 + 8, (int)Math.round(200 * (double)client.getHealth()/client.getMaxHealth()), 25);

			g.setXORMode(Color.RED);
			GuiUtil.drawString(g, client.getHealth() + "/" + client.getMaxHealth(), CenterMode.CENTER, x + 10 + 100, y + 10 + 9 + 25/2);

			g.setPaintMode();
			g.setColor(Color.WHITE);
			GuiUtil.drawString(g, client.getGun(), CenterMode.LEFT, x + 10, y + 10 + 14*3);
			GuiUtil.drawString(g, client.getAmmo() + " + " + client.getTotalAmmo(), CenterMode.LEFT, x + 10, y + 10 + 14*4);
		}
	}
	
	@Override
	public void onScreenChange(boolean leaving) {
		if (leaving) {
			AudioHelper.stop(AssetManager.getAudioAssets("test/BHT")[0].getMaster());
			AudioHelper.setPercentVolume(AssetManager.getAudioAssets("test/BHT")[0].getMaster(), 1);
		} else {
			AudioHelper.setPercentVolume(AssetManager.getAudioAssets("test/BHT")[0].getMaster(), 0.6);
			AudioHelper.play(AssetManager.getAudioAssets("test/BHT")[0].getMaster(), true);
		}
	}

	@Override
	protected void cleanup() {
		AudioHelper.stop(AssetManager.getAudioAssets("footstep")[0].getMaster());
		//gui.getNetworkManger().sendGoodbye();
	}
}
