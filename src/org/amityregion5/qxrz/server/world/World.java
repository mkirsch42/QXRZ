package org.amityregion5.qxrz.server.world;

import java.awt.Graphics2D;
import java.util.ArrayList;
import java.util.List;

import org.amityregion5.qxrz.common.net.ChatMessage;
import org.amityregion5.qxrz.common.ui.NetworkDrawablePacket;
import org.amityregion5.qxrz.server.Game;
import org.amityregion5.qxrz.server.net.ServerNetworkManager;
import org.amityregion5.qxrz.server.world.entity.GameEntity;
import org.amityregion5.qxrz.server.world.entity.Hitbox;
import org.amityregion5.qxrz.server.world.entity.PlayerEntity;
import org.amityregion5.qxrz.server.world.entity.ShapeHitbox;
import org.amityregion5.qxrz.server.world.gameplay.Player;

public class World
{

	private ArrayList<GameEntity> entities;
	private Landscape l;	
	private ServerNetworkManager net;
	private Game g;
	
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
	public Player winner() //checks all player entities to determine a winner if one player is left alive
	{
		ArrayList<PlayerEntity> pl = new ArrayList<PlayerEntity>();
		for (GameEntity e : entities)
		{
			if (e instanceof PlayerEntity)
				pl.add((PlayerEntity) e);
		}
		int co = 0;
		Player w = null;
		for (PlayerEntity p : pl)
		{
			if (!(p.getGameModel().dead()))
			{
				co++;
				w = p.getGameModel();
			}
		}
		if (co==1)
		{
			return w;
		}
		else {return null;}
	}
}
