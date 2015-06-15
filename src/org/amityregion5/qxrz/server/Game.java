package org.amityregion5.qxrz.server;

import java.util.ArrayList;
import java.util.HashMap;

import org.amityregion5.qxrz.common.net.ChatMessage;
import org.amityregion5.qxrz.common.net.NetworkNode;
import org.amityregion5.qxrz.common.ui.NetworkDrawablePacket;
import org.amityregion5.qxrz.common.world.WorldManager;
import org.amityregion5.qxrz.common.world.Worlds;
import org.amityregion5.qxrz.server.net.ServerNetworkManager;
import org.amityregion5.qxrz.server.world.DebugDraw;
import org.amityregion5.qxrz.server.world.World;
import org.amityregion5.qxrz.server.world.gameplay.GameModes;
import org.amityregion5.qxrz.server.world.gameplay.Player;
import org.amityregion5.qxrz.server.world.gameplay.Team;

public class Game implements Runnable
{

	ServerNetworkManager net;

	private HashMap<NetworkNode, Player> players = new HashMap<NetworkNode, Player>();
	private ArrayList<Team> teams = new ArrayList<Team>();
	private boolean playerWon = false;

	public static final int GAME_UNIT = 1;

	public static DebugDraw debug = new DebugDraw();

	private World w;
	private boolean running = true;
	private Worlds world;
	private GameModes mode;
	private boolean friendlyfire = false;
	private Main m;
	private int renderCounter = 0;
	private static final int renderSendPacket = 2;

	public Game(ServerNetworkManager n, GameModes gm, Main main, Worlds sw)
	{
		net = n;
		m = main;
		// Create world and add test objects
		world = sw;
		w = WorldManager.getWorld(world);
		w.attachNetworkManager(net);
		w.attachParent(this);
		mode = gm;
		// w.add(new PlayerEntity());
		// debug = DebugDraw.setup(w);
		// TODO finish compound hitbox normals then add some to the world
		/*
		 * w.addObstacle(new Obstacle(new CompoundHitbox().add( new
		 * RectangleHitbox(new Rectangle2D.Double(50,20,5,10))).add( new
		 * RectangleHitbox(new Rectangle2D.Double(40,30,15,5)))));
		 * //w.addObstacle(new Obstacle(new RectangleHitbox(new
		 * Rectangle2D.Double(40,30,15,5))));
		 */
	}

	@Override
	public void run()
	{
		running = true;
		long lastMs = System.currentTimeMillis();
		double nsPerUpdate = 1000000000.0 / DebugConstants.DEBUG_FPS;

		// last update

		double then = System.nanoTime();
		double unprocessed = 0;
		boolean shouldRender = false;
		while (running)
		{
			double now = System.nanoTime();
			unprocessed += (now - then) / nsPerUpdate;
			then = now;
			// update
			while (unprocessed >= 1)
			{
				update(lastMs);
				lastMs = System.currentTimeMillis();
				unprocessed--;
				shouldRender = true;
			}

			if (shouldRender)
			{
				render();
				shouldRender = false;
			}
			else
			{
				try
				{
					Thread.sleep(1);
				} catch (InterruptedException e)
				{
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
	}

	public Player winner() // checks all player entities to determine a winner
	// if one player is left alive
	{
		if (players.size() < 2 || playerWon)
		{
			return null;
		}
		switch (mode)
		{
		case ENDLESS:
			return null;
		case LASTMAN:
			Player winner = null;
			for (NetworkNode n : players.keySet())
			{
				Player p = players.get(n);
				if (!p.isDead())
				{
					if (winner != null)
					{
						return null;
					}
					winner = p;
				}
			}
			return winner;
		}
		return null;
	}

	public void playerLeftTeam(Team t)
	{
		if (t.empty())
		{
			teams.remove(t);
		}
	}

	public GameModes getGM()
	{
		return mode;
	}

	public void close()
	{
		running = false;
	}

	public World getWorld()
	{
		return w;
	}

	public Worlds getWorlds()
	{
		return world;
	}

	public Player findPlayer(NetworkNode n)
	{
		return players.get(n);
	}

	public void addPlayer(NetworkNode n, Player p)
	{
		w.add(p.getEntity());
		players.put(n, p);
		p.randomSpawn();
	}

	public void addTeam(Team t)
	{
		teams.add(t);
	}

	public boolean addToTeamByName(Player p, String teamName)
	{
		p.leaveTeam();
		for (Team t : teams)
		{
			if (t.getName().equalsIgnoreCase(teamName))
			{
				p.joinTeam(t);
				net.sendObject(new ChatMessage(p.getName() + " joined "
						+ t.getName()).fromServer());
				return true;
			}
		}
		return false;
	}

	public boolean friendlyFire(boolean toggle)
	{
		if (friendlyfire == toggle)
		{
			return false;
		}
		friendlyfire = toggle;
		return true;
	}

	public boolean friendlyFire()
	{
		return friendlyfire;
	}

	public void removePlayer(NetworkNode n)
	{
		w.removeEntity(players.get(n).getEntity());
		players.remove(n);
	}

	public ArrayList<Team> getTeams()
	{
		return teams;
	}

	private void update(long lastMs)
	{
		w.update((System.currentTimeMillis() - lastMs)
				/ (1000.0 / DebugConstants.UPDATE_RATE));
		Player winner = winner();
		if (winner != null)
		{
			net.sendObject(new ChatMessage(winner.getName() + " won")
			.fromServer());
			m.returnToLobby();

			for (NetworkNode n : players.keySet())
			{
				players.get(n).respawn(true);
				players.get(n).randomSpawn();
			}
		}

		if (players.size() > 0
				&& (int) (Math.random() * DebugConstants.DROPCHANCEPERUPDATE) == 1)
		{
			w.drop();
		} 
		if (players.isEmpty()){
			m.returnToLobby();
		}

	}

	private void render()
	{
		debug.draw();

		renderCounter++;
		if (renderCounter >= renderSendPacket) {
			renderCounter = 0;
			NetworkDrawablePacket ndp = w.constructDrawablePacket();
			ndp.setCurrentWorld(world);
			for (NetworkNode node : players.keySet())
			{
				int index = w.getEntities().indexOf(players.get(node).getEntity());
				ndp.setClientIndex(index);
				try
				{
					node.send(ndp);
				} catch (Exception e)
				{
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
	}

	public boolean allPlayersReady() {
		return players.values().stream().parallel().allMatch((p)->p.isReady());
	}

	public HashMap<NetworkNode, Player> getPlayers() {
		return players;
	}
}
