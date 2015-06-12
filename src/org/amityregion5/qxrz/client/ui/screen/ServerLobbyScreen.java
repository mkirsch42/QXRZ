package org.amityregion5.qxrz.client.ui.screen;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

import org.amityregion5.qxrz.client.ui.MainGui;
import org.amityregion5.qxrz.client.ui.element.ElementTextBox;
import org.amityregion5.qxrz.client.ui.util.CenterMode;
import org.amityregion5.qxrz.client.ui.util.GuiMath;
import org.amityregion5.qxrz.client.ui.util.GuiUtil;
import org.amityregion5.qxrz.client.ui.util.ImageModification;
import org.amityregion5.qxrz.common.net.ChatMessage;

public class ServerLobbyScreen extends AbstractScreen
{
	private ElementTextBox chatBox;



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

		BufferedImage buff = ImageModification.createBlankBufferedImage(windowData.getWidth()-(serverX + endWidth), windowData.getHeight()-(serverY+endHeight));

		Graphics2D gBuff = buff.createGraphics();

		GuiUtil.applyRenderingHints(gBuff, gui);

		gBuff.setFont(gBuff.getFont().deriveFont(14f));
		gBuff.setColor(Color.WHITE);
		
		int totalYTrans = 0;
		for (ChatMessage c : gui.getMessages()) {
			int x = 0;
			int subIndex = 0;
			int endIndex = c.getMessage().length();
			
			ArrayList<String> lines = new ArrayList<String>();

			while (subIndex < endIndex) {
				Rectangle r = GuiMath.getStringBounds(gBuff, c.getMessage().substring(subIndex, endIndex), 0, 0);
				if (r.width >= buff.getWidth() - 20) {
					endIndex = (int)((buff.getWidth()-20)/(double)r.width * (endIndex-subIndex));
					r = GuiMath.getStringBounds(gBuff, c.getMessage().substring(subIndex, endIndex), 0, 0);
					while (r.width >= buff.getWidth() - 20) {
						endIndex--;
						r = GuiMath.getStringBounds(gBuff, c.getMessage().substring(subIndex, endIndex), 0, 0);
					}
				}
				/*
				while (r.width >= buff.getWidth() - 20) {
					if (r.width/2 >= buff.getWidth()-20 && subIndex > 0) {
						subIndex*=2;
					} else {
						subIndex++;
					}
					
				}*/

				totalYTrans += r.height + 2;
				
				lines.add(c.getMessage().substring(subIndex, endIndex));

				subIndex = endIndex;
				endIndex = c.getMessage().length();
			}
			
			for (int i = lines.size()-1;i>=0;i--) {
				String str = lines.get(i);
				
				Rectangle r = GuiMath.getStringBounds(gBuff, str, 0, 0);
				
				gBuff.setFont(gBuff.getFont().deriveFont(14f));
				gBuff.translate(0, -(r.height + 2));
				GuiUtil.drawString(gBuff, str, CenterMode.LEFT, x + 10, (buff.getHeight()-(r.height + 2))+r.height/2);
			}

			if (totalYTrans > buff.getHeight()) {
				break;
			}
		}

		gBuff.dispose();

		g.drawImage(buff, null, serverX, serverY);
	}

	@Override
	protected void cleanup() {
		gui.getNetworkManger().sendGoodbye();
	}
}
