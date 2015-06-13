package org.amityregion5.qxrz.server;

import java.io.Serializable;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.swing.JOptionPane;

import org.amityregion5.qxrz.common.control.NetworkInputData;
import org.amityregion5.qxrz.common.game.ChangeClassPacket;
import org.amityregion5.qxrz.common.game.ReadyPacket;
import org.amityregion5.qxrz.common.net.AbstractNetworkNode;
import org.amityregion5.qxrz.common.net.ChatMessage;
import org.amityregion5.qxrz.common.net.Goodbye;
import org.amityregion5.qxrz.common.net.NetEventListener;
import org.amityregion5.qxrz.common.net.NetworkNode;
import org.amityregion5.qxrz.common.ui.LobbyInformationPacket;
import org.amityregion5.qxrz.server.net.ServerNetworkManager;
import org.amityregion5.qxrz.server.ui.MainGui;
import org.amityregion5.qxrz.server.util.ColorUtil;
import org.amityregion5.qxrz.server.util.TextParseHelper;
import org.amityregion5.qxrz.server.world.DebugDraw;
import org.amityregion5.qxrz.server.world.gameplay.GameModes;
import org.amityregion5.qxrz.server.world.gameplay.Player;
import org.amityregion5.qxrz.server.world.gameplay.SpecialMovement;
import org.amityregion5.qxrz.server.world.gameplay.SpecialMovements;
import org.amityregion5.qxrz.server.world.gameplay.Team;

//github.com/mkirsch42/QXRZ.gitimport org.amityregion5.qxrz.common.net.ChatMessage;

public final class Main {
	private Game g;
	private ServerNetworkManager netManager;
	private MainGui gui;
	private GameState gs;
	private String s;

	public static void main(String[] args) throws Exception {
		new Main(args);
	}

	public Main(String[] args) throws Exception {
		Logger.getGlobal().setLevel(Level.OFF);

		gs = GameState.LOBBY;

		if (args.length > 0) {
			s = args[0];
		} else {
			s = (String) JOptionPane.showInputDialog(null,
					"Enter Server name", "Server Name Query",
					JOptionPane.PLAIN_MESSAGE, null, null, System.getProperty("user.name") + "'s server");

			if (s == null) {
				return;
			}
		}

		netManager = new ServerNetworkManager(s, 8000);
		gui = new MainGui(netManager, g);
		// TODO maybe all the manager stuff should be created within the GUI

		attachEventListener();

		netManager.setAllowConnections(false);
		netManager.start();

		g = new Game(netManager, GameModes.LASTMAN, this); // TODO game needs access to network, too...
		// TODO server panel should show actual IP, not 0.0.0.0
		if (DebugConstants.DEBUG_GUI) {
			Game.debug = DebugDraw.setup(g.getWorld());
		}

		returnToLobby();
		// new MainGui().show();

		//gui.show();
		//g.run();
	}

	public void returnToLobby() {
		netManager.setAllowConnections(true);
		gs = GameState.LOBBY;
		g.close();
		startLobby();
		for (Player p : g.getPlayers().values()) {
			p.setReady(false);
		}
	}

	private void startLobby() {
		double nsPerUpdate = 1000000000.0 / DebugConstants.DEBUG_FPS;

		double then = System.nanoTime();
		double unprocessed = 0;
		boolean shouldRender = false;
		while (GameState.LOBBY == gs)
		{
			double now = System.nanoTime();
			unprocessed += (now - then) / nsPerUpdate;
			then = now;
			// update
			while (unprocessed >= 1)
			{
				unprocessed--;
				shouldRender = true;
			}

			if (shouldRender)
			{
				doLobbyUpdate();
				shouldRender = false;
			}
			else{try{Thread.sleep(1);} catch (InterruptedException e){}}
		}
	}

	private void doLobbyUpdate() {
		for (NetworkNode n : g.getPlayers().keySet()) {
			Player p  = g.getPlayers().get(n);
			try {
				n.send(new LobbyInformationPacket(p.getSpecMove().getType(), p.isReady()));
			} catch (Exception e) {e.printStackTrace();}
		}
	}


	private void attachEventListener() {
		netManager.attachEventListener(new NetEventListener() {
			@Override
			public void newNode(AbstractNetworkNode c) {
				gui.addClient((NetworkNode) c);
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
					//gui.redraw();
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				// TODO do stuff for new client (drawing, inventory whatever)
				// You should cast c to a NetworkNode before using
			}

			@Override
			public void dataReceived(NetworkNode c, Serializable netObj) {
				if (gs == GameState.LOBBY) {
					if (netObj instanceof ReadyPacket) {
						g.findPlayer(c).setReady(((ReadyPacket)netObj).isReady());
						if (g.allPlayersReady()) {
							gs = GameState.INGAME;
							netManager.setAllowConnections(false);
							new Thread(g, "Server Game Thread").start();
						}
					} else if (netObj instanceof ChangeClassPacket) {
						ChangeClassPacket ccp = (ChangeClassPacket)netObj;
						Player p = g.findPlayer(c);
						int ordinal = p.getSpecMove().getType().ordinal();
						if (ccp.isRight()) {
							ordinal++;
							if (ordinal >= SpecialMovements.values().length) {
								ordinal = 0;
							}
						} else {
							ordinal--;
							if (ordinal < 0) {
								ordinal = SpecialMovements.values().length-1;
							}
						}
						p.setSpecMove(new SpecialMovement(SpecialMovements.values()[ordinal], g.getWorld()));
					}
				}
				if (gs == GameState.INGAME) {
					if (netObj instanceof NetworkInputData) {
						Player from = g.findPlayer(c);
						from.input((NetworkInputData) netObj);
						// System.out.println((NetworkInputData)netObj);
					}
				}
				if (netObj instanceof Goodbye) {
					// also stop drawing player and stuff
					g.removePlayer(c);
					int i = netManager.removeClient(c);
					gui.removeRow(i);
					try {
						//gui.redraw();
					} catch (Exception e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					//here
				} else if (netObj instanceof ChatMessage) {
					// echo it back out
					String msg = ((ChatMessage)netObj).getMessage();
					if(msg.charAt(0)=='/' && gs == GameState.INGAME)
					{
						if(msg.toLowerCase().startsWith("/leave"))
						{
							if(g.findPlayer(c).getTeam()==null)
								return;
							netManager.sendObject(new ChatMessage(g.findPlayer(c).getName() + " left " + g.findPlayer(c).getTeam().getName()).fromServer());
							g.findPlayer(c).leaveTeam();
						}
						if(msg.toLowerCase().startsWith("/join "))
						{
							System.out.println(msg);
							if(!g.getGM().hasTeams)
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
									t = new Team(ColorUtil.stringToColor(args[1].toUpperCase()), args[0]);
								}
								g.addTeam(t);
								g.findPlayer(c).joinTeam(t);
							}
							netManager.sendObject(new ChatMessage(g.findPlayer(c).getName() + " joined " + g.findPlayer(c).getTeam().getName()).fromServer());
						}
						if(msg.toLowerCase().startsWith("/ff"))
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
						if(msg.toLowerCase().startsWith("/hurtme "))
						{
							String[] args = msg.substring(8).split(" ");
							if(args[0].matches("[0-9]*"))
							{
								g.findPlayer(c).hurtme(Integer.parseInt(args[0]));
							}
						}
						if(msg.toLowerCase().startsWith("/equip "))
						{
							String[] args = msg.substring(7).split(" ");
							g.findPlayer(c).equipWep(args[0]);
						}
					}
					else
					{
						netManager.sendObject(new ChatMessage(c.getName() + ": " + msg).setFrom(c.getSocketAddress()));
					}
				}
			}
		});
	}
}
