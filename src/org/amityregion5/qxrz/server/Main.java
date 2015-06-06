package org.amityregion5.qxrz.server;

import java.io.Serializable;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.swing.JOptionPane;

import org.amityregion5.qxrz.common.control.NetworkInputData;
import org.amityregion5.qxrz.common.net.AbstractNetworkNode;
import org.amityregion5.qxrz.common.net.ChatMessage;
import org.amityregion5.qxrz.common.net.Goodbye;
import org.amityregion5.qxrz.common.net.NetEventListener;
import org.amityregion5.qxrz.common.net.NetworkNode;
import org.amityregion5.qxrz.server.net.ServerNetworkManager;
import org.amityregion5.qxrz.server.ui.MainGui;
import org.amityregion5.qxrz.server.world.DebugDraw;
import org.amityregion5.qxrz.server.world.gameplay.Player;

//github.com/mkirsch42/QXRZ.gitimport org.amityregion5.qxrz.common.net.ChatMessage;

public final class Main {
	private static Game g;

	public static void main(String[] args) throws Exception {
		Logger.getGlobal().setLevel(Level.OFF);

		String s = (String) JOptionPane.showInputDialog(null,
				"Enter Server name", "Server Name Query",
				JOptionPane.PLAIN_MESSAGE, null, null, "QXRZ Server");

		if (s == null) {
			return;
		}

		ServerNetworkManager netManager = new ServerNetworkManager(s, 8000);
		// TODO maybe all the manager stuff should be created within the GUI
		netManager.attachEventListener(new NetEventListener() {
			@Override
			public void newNode(AbstractNetworkNode c) {
				Player p = new Player(g.getWorld());
				g.addPlayer((NetworkNode) c, p);
				// TODO do stuff for new client (drawing, inventory whatever)
				// You should cast c to a NetworkNode before using
			}

			@Override
			public void dataReceived(NetworkNode c, Serializable netObj) {
				if (netObj instanceof NetworkInputData) {
					Player from = g.findPlayer(c);
					from.input((NetworkInputData) netObj);
					// System.out.println((NetworkInputData)netObj);
				} else if (netObj instanceof Goodbye) {
					// also stop drawing player and stuff
					g.removePlayer(c);
					netManager.removeClient(c);
				} else if (netObj instanceof ChatMessage) {
					// echo it back out
					netManager.sendObject(new ChatMessage(c.getName() + ": " + ((ChatMessage)netObj).getMessage()).setFrom(c.getSocketAddress()));
				}
			}
		});

		netManager.start();

		// new MainGui().show();

		new MainGui(netManager).show();
		g = new Game(netManager); // TODO game needs access to network, too...
		// TODO server panel should show actual IP, not 0.0.0.0
		if (DebugConstants.DEBUG_GUI) {
			Game.debug = DebugDraw.setup(g.getWorld());
		}
		g.run();
	}

}
