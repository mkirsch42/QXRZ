package org.amityregion5.qxrz.client.ui.screen;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.image.BufferedImage;

import org.amityregion5.qxrz.client.ui.MainGui;
import org.amityregion5.qxrz.client.ui.element.ElementImageRectangle;
import org.amityregion5.qxrz.client.ui.element.ElementRectangle;
import org.amityregion5.qxrz.client.ui.element.ElementTextBox;
import org.amityregion5.qxrz.client.ui.util.DoubleReturn;
import org.amityregion5.qxrz.client.ui.util.GameUIHelper;
import org.amityregion5.qxrz.common.game.ChangeClassPacket;
import org.amityregion5.qxrz.common.game.ReadyPacket;
import org.amityregion5.qxrz.common.net.ChatMessage;
import org.amityregion5.qxrz.common.util.Colors;

public class ServerLobbyScreen extends AbstractScreen
{
	private ElementTextBox chatBox;
	private int scrollOffset;


	/**
	 * Create a join screen
	 * 
	 * @param previous the return screen
	 * @param gui the MainGui object
	 */
	public ServerLobbyScreen(IScreen previous, MainGui gui) {
		super(previous, gui);

		gui.getMessages().clear();

		/*
		elements.add(new ElementRectangle(
				(w)->{return new Point(50, 50);},
				(w)->{return new Point(50, 50);},
				Color.LIGHT_GRAY, Color.BLACK, -10f, Color.BLACK,
				"<", (w)->{cleanup();gui.setCurrentScreen(getReturnScreen());}));
		*/
		elements.add(chatBox = ElementTextBox.createTextBox(
				(w)->{return new Point(100, w.getHeight()-100);},
				(w)->{return new Point(w.getWidth()-200, 50);},
				Color.DARK_GRAY, Color.BLACK, 14f, Color.WHITE,
				(k)->true));
		chatBox.setOnEnterCallback(()->{
			gui.getNetworkManger().sendObject(new ChatMessage(chatBox.getString()));
			chatBox.setName("");
		});
		
		elements.add(new ElementRectangle(
				(w)->{return new Point(w.getWidth()/2 + 150, 180);}, 
				(w)->{return new Point(150, 20);}, 
				()->Colors.CLEAR, ()->Colors.CLEAR, 14f, ()->Color.BLACK, ()->gui.getLip().getPlayerClass().toString(),
				(w)->{}));
		
		elements.add(new ElementImageRectangle(
				(w)->{return new Point(w.getWidth()/2 + 175, 200);}, 
				(w)->{return new Point(100, 100);}, 
				()->Colors.CLEAR, ()->Colors.CLEAR, ()->gui.getLip().getPlayerClass().getAssetName(),
				(w)->{}, gui));
		
		elements.add(new ElementRectangle(
				(w)->{return new Point(w.getWidth()/2 + 150, 325);}, 
				(w)->{return new Point(50, 50);}, 
				Color.DARK_GRAY, Color.BLACK, -5f, Color.WHITE, "<",
				(w)->gui.getNetworkManger().sendObject(new ChangeClassPacket(false))));
		elements.add(new ElementRectangle(
				(w)->{return new Point(w.getWidth()/2 + 250, 325);}, 
				(w)->{return new Point(50, 50);}, 
				Color.DARK_GRAY, Color.BLACK, -5f, Color.WHITE, ">",
				(w)->gui.getNetworkManger().sendObject(new ChangeClassPacket(true))));
		
		elements.add(new ElementRectangle(
				(w)->{return new Point(w.getWidth()/2 + 150, 425);}, 
				(w)->{return new Point(200, 50);}, 
				()->Color.DARK_GRAY, ()->Color.BLACK, 14f, ()->Color.WHITE, ()->(gui.getLip().isReady()?"Ready":"Click When Ready"),
				(w)->gui.getNetworkManger().sendObject(new ReadyPacket(!gui.getLip().isReady()))));

	} 

	@Override
	protected void draw(Graphics2D g, WindowData windowData) {
		//Fill the screen with white
		g.setColor(Color.WHITE);
		g.fillRect(0,0, windowData.getWidth(), windowData.getHeight());

		int serverX = 100;
		int serverY = 150;
		int endWidth = windowData.getWidth()/2-50;
		int endHeight = 150;


		//Draw the background
		g.setColor(Color.DARK_GRAY);
		g.fillRect(serverX, serverY, windowData.getWidth()-(serverX + endWidth), windowData.getHeight()-(serverY+endHeight));

		//Draw the border
		g.setColor(Color.BLACK);
		g.drawRect(serverX, serverY, windowData.getWidth()-(serverX + endWidth), windowData.getHeight()-(serverY+endHeight));

		DoubleReturn<BufferedImage, Integer> chat = GameUIHelper.getChatMessagesImage(windowData.getWidth()-(serverX + endWidth), windowData.getHeight()-(serverY+endHeight),
				gui, Color.WHITE, Color.RED, 14, -1, scrollOffset);
		
		scrollOffset -= windowData.getMouseWheel()*5;
		if (scrollOffset < 0) {
			scrollOffset = 0;
		}
		if (scrollOffset > chat.b) {
			scrollOffset = chat.b;
		}

		g.drawImage(chat.a, null, serverX, serverY);
	}

	@Override
	protected void cleanup() {
		//gui.getNetworkManger().sendGoodbye();
	}
}
