package org.amityregion5.qxrz.server.world;

import java.awt.Graphics2D;
import java.util.ArrayList;
import java.util.List;

import org.amityregion5.qxrz.common.ui.NetworkDrawablePacket;
import org.amityregion5.qxrz.server.world.entity.GameEntity;

public class World
{

	private ArrayList<GameEntity> entities;
	private Landscape l;	
	
	public World()
	{
		entities = new ArrayList<GameEntity>();
		l = new Landscape();
	}

	public void add(GameEntity e)
	{
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
			if(entities.get(i).update(tSinceUpdate, this))
			{
				removeEntity(entities.get(i));
				i--;
			}
		}
	}

	public void draw(Graphics2D g2)
	{
		for (GameEntity t : entities)
		{
			t.getHitbox().debugDraw(g2);
		}
		l.draw(g2);
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
		Thread.dumpStack();
		System.out.println("removing entity #"+e.getId());
		entities.remove(e);
	}
}
