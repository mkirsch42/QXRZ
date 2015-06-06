package org.amityregion5.qxrz.client.ui.screen;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.image.BufferedImage;
import java.net.SocketException;

import org.amityregion5.qxrz.client.ui.MainGui;
import org.amityregion5.qxrz.client.ui.element.ElementImageRectangle;
import org.amityregion5.qxrz.client.ui.element.ElementRectangle;
import org.amityregion5.qxrz.client.ui.element.ElementTextBox;
import org.amityregion5.qxrz.client.ui.util.CenterMode;
import org.amityregion5.qxrz.client.ui.util.GuiUtil;
import org.amityregion5.qxrz.client.ui.util.ImageModification;
import org.amityregion5.qxrz.common.asset.AssetManager;
import org.amityregion5.qxrz.common.audio.AudioHelper;
import org.amityregion5.qxrz.common.util.Colors;

public class ServerSelectionScreen extends AbstractScreen {
	private ElementTextBox ipBox, portBox;
	private int selectedServer, lastMouseButtonsDown;

	/**
	 * Create a join screen
	 * 
	 * @param previous
	 *            the return screen
	 * @param gui
	 *            the MainGui object
	 */
	public ServerSelectionScreen(IScreen previous, MainGui gui) {
		super(previous, gui);
		refreshServerList();

		elements.add(new ElementImageRectangle((w) -> {
			return new Point(w.getWidth() - 100, 50);
		}, (w) -> {
			return new Point(50, 50);
		}, Color.LIGHT_GRAY, Color.BLACK, "icons/refresh",
		(w) -> refreshServerList()));

		elements.add(new ElementRectangle((w) -> {
			return new Point(50, 50);
		}, (w) -> {
			return new Point(50, 50);
		}, Color.LIGHT_GRAY, Color.BLACK, -10f, Color.BLACK, "<", (w) -> {
			cleanup();
			gui.setCurrentScreen(getReturnScreen());
		}));

		elements.add(new ElementRectangle((w) -> {
			return new Point(125, 50);
		}, (w) -> {
			return new Point(100, 50);
		}, Colors.CLEAR, Colors.CLEAR, 20f, Color.BLACK, "Username:"));

		elements.add(portBox = ElementTextBox.createTextBox((w) -> {
			return new Point(250, 50);
		}, (w) -> {
			return new Point(w.getWidth() - 150 - 250, 50);
		}, Color.DARK_GRAY, Color.BLACK, 20f, Color.WHITE, gui.getUsername(), (
				k) -> true));
		portBox.setOnTextChangeCallback(() -> {
			gui.setUsername(portBox.getString());
		});
		/*
		 * portBox.setOnTextChangeCallback(()->{ int boxInt =
		 * Integer.parseInt(portBox.getString()); if (boxInt >= 65536) {
		 * portBox.setName(Integer.parseInt(portBox.getString())/10 + ""); }
		 * refreshServerList(); });
		 */

		elements.add(new ElementRectangle((w) -> {
			return new Point(50, w.getHeight() - 100);
		}, (w) -> {
			return new Point(50, 50);
		}, Color.WHITE, Color.WHITE, 20f, Color.BLACK, "IP:"));

		elements.add(ipBox = ElementTextBox.createTextBox((w) -> {
			return new Point(150, w.getHeight() - 100);
		}, (w) -> {
			return new Point(w.getWidth() - 350, 50);
		}, Color.DARK_GRAY, Color.BLACK, 20f, Color.WHITE, (k) -> true));

		// TODO: Figure out how to validate server
		elements.add(new ElementRectangle((w) -> {
			return new Point(w.getWidth() - 150, w.getHeight() - 100);
		}, (w) -> {return new Point(100, 50);
		}, () -> (Color.LIGHT_GRAY), () -> Color.BLACK, 20f, () -> Color.BLACK,
		() -> "Join Game", (w) -> {
			try {
				gui.getNetworkManger().connect(ipBox.getName(), 8000);
				if (gui.getNetworkManger().isConnected()) {
					gui.setCurrentScreen(new GameScreen(this, gui));
				}
				// gui.setCurrentScreen(new ServerLobbyScreen(this,
				// gui));
			} catch (Exception e) {
				e.printStackTrace();
			}
		}));

	}

	private void refreshServerList() {
		try {
			selectedServer = -1;
			gui.getQueryInfo().clear();
			gui.getNetworkManger().broadcastQuery();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	protected void draw(Graphics2D g, WindowData windowData) {
		// Fill the screen with white
		g.setColor(Color.WHITE);
		g.fillRect(0, 0, windowData.getWidth(), windowData.getHeight());

		int serverX = 100;
		int serverY = 150;
		int endWidth = 100;
		int endHeight = 150;

		// Draw the background
		g.setColor(Color.DARK_GRAY);
		g.fillRect(serverX, serverY, windowData.getWidth()
				- (serverX + endWidth), windowData.getHeight()
				- (serverY + endHeight));

		// Draw the border
		g.setColor(Color.BLACK);
		g.drawRect(serverX, serverY, windowData.getWidth()
				- (serverX + endWidth), windowData.getHeight()
				- (serverY + endHeight));

		BufferedImage buff = ImageModification.createBlankBufferedImage(
				windowData.getWidth() - (serverX + endWidth),
				windowData.getHeight() - (serverY + endHeight));

		Graphics2D gBuff = buff.createGraphics();

		GuiUtil.applyRenderingHints(gBuff);

		// TODO: Scrolling
		int w = buff.getWidth();
		int h = 75;
		for (int i = 0; i < gui.getQueryInfo().size(); i++) {
			int x = 0;
			int y = h * i;

			// String ip =
			// gui.getQueryInfo().get(i).getAddress().getAddress().getHostAddress();
			// int port = gui.getQueryInfo().get(i).getAddress().getPort();

			if (x + h >= 0 && x <= buff.getHeight()) {
				if (i == selectedServer) {
					gBuff.setColor(Color.GRAY);
				} else {
					gBuff.setColor(Color.LIGHT_GRAY);
				}
				gBuff.fillRect(x, y, w, h);

				gBuff.setColor(Color.BLACK);
				gBuff.drawRect(x, y, w, h);

				gBuff.setColor(Color.BLACK);
				gBuff.setFont(gBuff.getFont().deriveFont(16f));
				GuiUtil.drawString(gBuff, gui.getQueryInfo().get(i).getName(),
						CenterMode.LEFT, x + 10, y + h / 3);
				GuiUtil.drawString(gBuff, gui.getQueryInfo().get(i)
						.getAddress().getAddress().getHostAddress(),
						CenterMode.LEFT, x + 10, y + 2 * h / 3);

				if (i == selectedServer) {
					gBuff.setColor(Color.WHITE);
					GuiUtil.drawString(gBuff, "Click to Connect",
							CenterMode.RIGHT, x + w - 10, y + h / 2);
				}

				if (windowData.getMiceDown().size() == 0
						&& lastMouseButtonsDown > 0
						&& windowData.getMouseX() >= x + serverX
						&& windowData.getMouseX() <= x + w + serverX
						&& windowData.getMouseY() >= y + serverY
						&& windowData.getMouseY() <= y + h + serverY) {
					if (i == selectedServer) {
						try {
							gui.setUsername(portBox.getString());
							gui.getNetworkManger().connect(
									gui.getQueryInfo().get(i).getAddress());
							if (gui.getNetworkManger().isConnectedTo(
									gui.getQueryInfo().get(i).getAddress())) {
								// gui.setCurrentScreen(new
								// ServerLobbyScreen(this, gui));
								gui.setCurrentScreen(new GameScreen(this, gui));
							}
						} catch (SocketException e) {
							e.printStackTrace();
						}
					} else {
						selectedServer = i;
					}
				}
			}
		}
		gBuff.dispose();

		g.drawImage(buff, null, serverX, serverY);

		lastMouseButtonsDown = windowData.getMiceDown().size();
	}

	@Override
	public void onScreenChange(boolean leaving) {
		if (leaving) {
			AudioHelper.stop(AssetManager.getAudioAssets("test/elevator")[0]);
		} else {
			AudioHelper.play(AssetManager.getAudioAssets("test/elevator")[0],
					true);
		}
	}

	@Override
	protected void cleanup() {
		gui.getNetworkManger().sendDisconnectNotification();
	}
}
