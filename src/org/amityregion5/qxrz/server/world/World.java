package org.amityregion5.qxrz.server.world;

import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import org.amityregion5.qxrz.common.audio.AudioMessage;
import org.amityregion5.qxrz.common.net.ChatMessage;
import org.amityregion5.qxrz.common.ui.NetworkDrawablePacket;
import org.amityregion5.qxrz.server.Game;
import org.amityregion5.qxrz.server.net.ServerNetworkManager;
import org.amityregion5.qxrz.server.world.entity.GameEntity;
import org.amityregion5.qxrz.server.world.entity.Hitbox;
import org.amityregion5.qxrz.server.world.entity.PickupEntity;
import org.amityregion5.qxrz.server.world.entity.PlayerEntity;
import org.amityregion5.qxrz.server.world.entity.ShapeHitbox;
import org.amityregion5.qxrz.server.world.gameplay.Pickup;
import org.amityregion5.qxrz.server.world.gameplay.WeaponTypes;

public class World
{

	private ArrayList<GameEntity> entities;
	private ArrayList<AudioMessage> sounds;
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
		sounds = new ArrayList<AudioMessage>();
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
		//System.out.println("Adding entity #" + e.getId());
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
		while(sounds.size()>0)
		{
			System.out.println("sending sound: " + sounds.get(0).getAsset());
			ndp.add(sounds.remove(0));
		}
		return ndp;
	}
	public void removeEntity(GameEntity e)
	{
		//Thread.dumpStack();
		//System.out.println("removing entity #"+e.getId());
		entities.remove(e);
	}
	public void say(String msg)
	{
		net.sendObject(new ChatMessage(msg).fromServer());
	}

	public ArrayList<PlayerEntity> checkPlayerCollisions(Hitbox shapeHitbox, int id)
	{
		ArrayList<PlayerEntity> collisions = new ArrayList<PlayerEntity>();
		for(GameEntity ge : new ArrayList<GameEntity>(entities))
		{
			if(!(ge instanceof PlayerEntity))
			{
				continue;
			}
			PlayerEntity e = (PlayerEntity)ge;
			if(shapeHitbox.intersects(e.getHitbox()) && id!=e.getGameModel().getId())
			{
				collisions.add(e);
			}
		}
		if(collisions.size()==0)
			// Hack fix to keep support for older code
			return null;
		return collisions;
	}

	public Rectangle getBounds()
	{
		return bounds;
	}
	
	private int numPickups()
	{
		int count = 0;
		for(GameEntity e : entities)
		{
			if(e instanceof PickupEntity)
				count++;
		}
		return count;
	}

	public void addSound(AudioMessage am)
	{
		sounds.add(am);
	}

	public void drop()
	{
		if(numPickups()>=15)
			return;
		Pickup p = null;
		Random r = new Random();
		do
		{
			int i = r.nextInt(WeaponTypes.values().length);
			WeaponTypes w = WeaponTypes.values()[i];
			if(w.equals(WeaponTypes.KNIFE))
			{
				p = new Pickup(r.nextInt(25), r.nextInt((int)bounds.getWidth())+(int)bounds.getMinX(),
						r.nextInt((int)bounds.getHeight())+(int)bounds.getMinY(), -1);
			}
			else
			{
				int maxammo = w.clips * w.cmaxammo;
				p = new Pickup(w.text, r.nextInt(maxammo/2)+maxammo/2, r.nextInt((int)bounds.getWidth())+(int)bounds.getMinX(),
						r.nextInt((int)bounds.getHeight())+(int)bounds.getMinY(), -1);
			}
			p.setOnePickup();
		} while (checkEntityCollisions(p.getEntity().getHitbox(), p.getEntity().getId())!=null || l.checkCollisions(p.getEntity().getHitbox())!=null);
		//System.out.println("new pickup at " + p.getEntity().getPos());
		add(p.getEntity());
	}
}
