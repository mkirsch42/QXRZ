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
import org.amityregion5.qxrz.server.util.ColorUtil;
import org.amityregion5.qxrz.server.util.TextParseHelper;
import org.amityregion5.qxrz.server.world.DebugDraw;
import org.amityregion5.qxrz.server.world.gameplay.GameModes;
import org.amityregion5.qxrz.server.world.gameplay.Pickup;
import org.amityregion5.qxrz.server.world.gameplay.Player;
import org.amityregion5.qxrz.server.world.gameplay.Team;

//github.com/mkirsch42/QXRZ.gitimport org.amityregion5.qxrz.common.net.ChatMessage;

public final class Main {
	private static Game g;

	public static void main(String[] args) throws Exception {
		Logger.getGlobal().setLevel(Level.OFF);

		String s = (String) JOptionPane.showInputDialog(null,
				"Enter Server name", "Server Name Query",
				JOptionPane.PLAIN_MESSAGE, null, null, System.getProperty("user.name") + "'s server");

		if (s == null) {
			return;
		}

		ServerNetworkManager netManager = new ServerNetworkManager(s, 8000);
		// TODO maybe all the manager stuff should be created within the GUI
		netManager.attachEventListener(new NetEventListener() {
			@Override
			public void newNode(AbstractNetworkNode c) {
				Player p = new Player(g.getWorld(), c.getName());
				g.addPlayer((NetworkNode) c, p);
				try
				{
					((NetworkNode)c).send(new ChatMessage("Welcome to " + s).fromServer());
				} catch (Exception e)
				{
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				try {
					new MainGui(netManager).show();
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
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
					try {
						new MainGui(netManager).show();
					} catch (Exception e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					//here
				} else if (netObj instanceof ChatMessage) {
					// echo it back out
					String msg = ((ChatMessage)netObj).getMessage();
					if(msg.charAt(0)=='/')
					{
						if(msg.length()>=6 && msg.substring(0,6).equalsIgnoreCase("/leave"))
						{
							if(g.findPlayer(c).getTeam()==null)
								return;
							netManager.sendObject(new ChatMessage(g.findPlayer(c).getName() + " left " + g.findPlayer(c).getTeam().getName()).fromServer());
							g.findPlayer(c).leaveTeam();
						}
						if(msg.length()>=6 && msg.substring(0,6).equalsIgnoreCase("/join "))
						{
							if(g.getGM().hasTeams)
								return;
							String[] args = msg.substring(6).split(" ");
							if(!g.addToTeamByName(g.findPlayer(c), args[0]))
							{
								Team t;
								if(args.length==1)
								{
									t = new Team(args[0]);
								}
								else
								{
									t = new Team(ColorUtil.stringToColor(args[1]), args[0]);
								}
								g.addTeam(t);
								g.findPlayer(c).joinTeam(t);
							}
							netManager.sendObject(new ChatMessage(g.findPlayer(c).getName() + " joined " + g.findPlayer(c).getTeam().getName()).fromServer());
						}
						if(msg.length()>=3 && msg.substring(0,3).equalsIgnoreCase("/ff"))
						{
							String[] args = msg.substring(3).split(" ");
							if(args.length==1)
							{
								g.friendlyFire(!g.friendlyFire());
								netManager.sendObject(new ChatMessage("Friendly fire is " + TextParseHelper.boolToOnOff(g.friendlyFire())).fromServer());
							}
							else
							{
								try{
									if(g.friendlyFire(TextParseHelper.onOffToBool(args[1])))
									{
										netManager.sendObject(new ChatMessage("Friendly fire is " + TextParseHelper.boolToOnOff(g.friendlyFire())).fromServer());
									}
								} catch(IllegalArgumentException e){try{c.send(new ChatMessage("Illegal argument- only use on or off").fromServer());}catch(Exception e2){}}
							}
						}
					}
					else
					{
						netManager.sendObject(new ChatMessage(c.getName() + ": " + msg).setFrom(c.getSocketAddress()));
					}
				}
			}
		});

		netManager.start();

		// new MainGui().show();

		new MainGui(netManager).show();
		g = new Game(netManager, GameModes.ENDLESS); // TODO game needs access to network, too...
		// TODO server panel should show actual IP, not 0.0.0.0
		if (DebugConstants.DEBUG_GUI) {
			Game.debug = DebugDraw.setup(g.getWorld());
		}
		Pickup pe = new Pickup("ps", 10, 500, 0, 3000);
		g.getWorld().add(pe.getEntity());
		g.run();
	}

}
