package org.amityregion5.qxrz.server.world;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import org.amityregion5.qxrz.common.net.ChatMessage;
import org.amityregion5.qxrz.common.ui.NetworkDrawablePacket;
import org.amityregion5.qxrz.server.Game;
import org.amityregion5.qxrz.server.net.ServerNetworkManager;
import org.amityregion5.qxrz.server.world.entity.GameEntity;
import org.amityregion5.qxrz.server.world.entity.Hitbox;
import org.amityregion5.qxrz.server.world.entity.PlayerEntity;
import org.amityregion5.qxrz.server.world.entity.ShapeHitbox;
import org.amityregion5.qxrz.server.world.gameplay.GameModes;
import org.amityregion5.qxrz.server.world.gameplay.Pickup;
import org.amityregion5.qxrz.server.world.gameplay.Player;
import org.amityregion5.qxrz.server.world.gameplay.Team;
import org.amityregion5.qxrz.server.world.gameplay.WeaponTypes;

public class World
{

	private ArrayList<GameEntity> entities;
	private Landscape l;	
	private ServerNetworkManager net;
	private Game g;
	private Rectangle bounds;
	
	public World()
	{
		this(null);
	}
	
	public World(ServerNetworkManager n)
	{
		entities = new ArrayList<GameEntity>();
		l = new Landscape();
		net  = n;
	}
	
	public void bounds(Rectangle r)
	{
		bounds = r;
	}
	
	public Game getGame()
	{
		return g;
	}
	
	public void attachNetworkManager(ServerNetworkManager n)
	{
		net = n;
	}
	
	public void attachParent(Game game)
	{
		g=game;
	}
	
	public void add(GameEntity e)
	{
		System.out.println("Adding entity #" + e.getId());
		entities.add(e);
	}
	
	public void addObstacle(Obstacle o)
	{
		l.add(o);
	}
	
	public void update(double tSinceUpdate)
	{
		for (int i=0;i<entities.size();i++)
		{
			GameEntity e = entities.get(i);
			if(e.update(tSinceUpdate, this))
			{
				removeEntity(e);
				i--;
			}
		}
	}

	public void draw(Graphics2D g2)
	{
		for (GameEntity t : new ArrayList<GameEntity>(entities))
		{
			t.getHitbox().debugDraw(g2);
		}
		l.draw(g2);
	}
	
	public GameEntity checkEntityCollisions(Hitbox h, int id)
	{
		for(GameEntity e : new ArrayList<GameEntity>(entities))
		{
			if(h.intersects(e.getHitbox()) && id!=e.getId())
			{
				return e;
			}
		}
		return null;
	}
	
	public Landscape getLandscape() {
		return l;
	}
	
	public List<GameEntity> getEntities() {
		return entities;
	}
	
	public NetworkDrawablePacket constructDrawablePacket()
	{
		NetworkDrawablePacket ndp = new NetworkDrawablePacket();
		for(GameEntity e : new ArrayList<GameEntity>(entities))
		{
			ndp.add(e.getNDE());
		}
		return ndp;
	}
	public void removeEntity(GameEntity e)
	{
		//Thread.dumpStack();
		System.out.println("removing entity #"+e.getId());
		entities.remove(e);
	}
	public void say(String msg)
	{
		net.sendObject(new ChatMessage(msg).fromServer());
	}

	public PlayerEntity checkPlayerCollisions(ShapeHitbox shapeHitbox, int id)
	{
		for(GameEntity ge : new ArrayList<GameEntity>(entities))
		{
			if(!(ge instanceof PlayerEntity))
			{
				continue;
			}
			PlayerEntity e = (PlayerEntity)ge;
			if(shapeHitbox.intersects(e.getHitbox()) && id!=e.getId())
			{
				return e;
			}
		}
		return null;
	}
	public void win(GameModes g) 
	{
		ArrayList<Player> pl = new ArrayList<Player>(); //arraylist of all players
		for (GameEntity e : entities)
		{
			if (e instanceof PlayerEntity)
				pl.add(((PlayerEntity) e).getGameModel());
		}
		if (g.teams)
			winPlayer(pl, g.id);
		else 
			winTeam(pl, g.id);
	}
	public Player winPlayer(ArrayList<Player> pls, int gid)
	{
		switch (gid)
		{
		/*last man standing*/
		case 1:		int co = 0;
					Player w = null;
					for (Player p : pls)
					{
						if (!(p.dead()))
						{
							co++;
							w = p;
						}
					}
					if (co==1)
						return w;
					else {return null;}
		/*endless	i figure it's implemented when the game is ended manually*/
		case 2:		Player win = pls.get(0);
					for (Player p: pls)
					{
						if (p.getScore() > win.getScore())
							win = p; 
					}
					return win;
		/*will we have capture the flag?*/
		}
		return null;
	}
	public Team winTeam(ArrayList<Player> pls int gid)
	{
		Team t1 = g.getTeams().get(0);
		Team t2 = g.getTeams().get(0);
		for (Player p:pls)
		{
			
		}
		switch (gid)
		{
		//last man standing
		case 1:		int co = 0;
					Player w = null;
					for (Player p : pls)
					{
						if (!(p.dead()))
						{
							co++;
							w = p;
						}
					}
					if (co==1)
						return w;
					else {return null;}
		//endless
		case 2:		Player win = pls.get(0);
					for (Player p: pls)
					{
						if (p.getScore() > win.getScore())
							win = p; 
					}
					return win;
		}
		return null;
	}

	public void drop()
	{
		Pickup p = null;
		Random r = new Random();
		do
		{
			WeaponTypes w = WeaponTypes.values()[r.nextInt(WeaponTypes.values().length)];
			int maxammo = w.clips * w.cmaxammo;
			p = new Pickup(w.text, r.nextInt(maxammo/2)+maxammo/2, r.nextInt((int)bounds.getWidth())+(int)bounds.getMinX(),
					r.nextInt((int)bounds.getHeight())+(int)bounds.getMinY(), -1);
			p.setOnePickup();
		} while (checkEntityCollisions(p.getEntity().getHitbox(), p.getEntity().getId())!=null || l.checkCollisions(p.getEntity().getHitbox())!=null);
		System.out.println("new pickup at " + p.getEntity().getPos());
		add(p.getEntity());
	}
}
