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
	
	public static final int GAME_UNIT = 1;

	public static DebugDraw debug = new DebugDraw();

	private World w;
	private boolean running = true;
	private Worlds world;
	private GameModes mode;
	private boolean friendlyfire = false;
	
	public Game(ServerNetworkManager n, GameModes gm)
	{
		net = n;
		// Create world and add test objects
		w = WorldManager.getWorld(Worlds.DEBUG);
		w.attachNetworkManager(net);
		w.attachParent(this);
		mode = gm;
		//w.add(new PlayerEntity());
		//debug = DebugDraw.setup(w);
		// TODO finish compound hitbox normals then add some to the world
		/*w.addObstacle(new Obstacle(new CompoundHitbox().add(
				new RectangleHitbox(new Rectangle2D.Double(50,20,5,10))).add(
				new RectangleHitbox(new Rectangle2D.Double(40,30,15,5)))));
		//w.addObstacle(new Obstacle(new RectangleHitbox(new Rectangle2D.Double(40,30,15,5))));
		 */
	}
	
	@Override
	public void run() {
		long lastMs = System.currentTimeMillis();
		while (running)
		{
			// Update world entities with proportional time
			w.update((System.currentTimeMillis() - lastMs)
					/ (1000.0 / DebugConstants.UPDATE_RATE));
			debug.draw();
			
			NetworkDrawablePacket ndp = w.constructDrawablePacket();
			ndp.setCurrentWorld(world);
			for(NetworkNode node : players.keySet())
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
			
			if(players.size()>0 && (int)(Math.random()*DebugConstants.DROPCHANCEPERUPDATE)==1)
			{
				w.drop();
			}
			
			// Set current time for next update
			lastMs = System.currentTimeMillis();
			// Sleep for next update
			try {
				Thread.sleep(1000 / DebugConstants.DEBUG_FPS);
			} catch (Exception e) {
			}
		}
	}
	
	public GameModes getGM()
	{
		return mode;
	}
	
	public void close() {
		running = false;
	}

	public World getWorld() {
		return w;
	}
	
	public Worlds getWorlds() {
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
	}
	
	public void addTeam(Team t)
	{
		teams.add(t);
	}
	
	public boolean addToTeamByName(Player p, String teamName)
	{
		p.leaveTeam();
		for(Team t : teams)
		{
			if(t.getName().equalsIgnoreCase(teamName))
			{
				p.joinTeam(t);
				net.sendObject(new ChatMessage(p.getName() + " joined " + t.getName()).fromServer());
				return true;
			}
		}
		return false;
	}
	
	public boolean friendlyFire(boolean toggle)
	{
		if(friendlyfire==toggle)
		{
			return false;
		}
		friendlyfire=toggle;
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
}
