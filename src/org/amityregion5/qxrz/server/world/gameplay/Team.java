package org.amityregion5.qxrz.server.world.gameplay;

import java.awt.Color;
import java.util.ArrayList;

public class Team
{

	Color teamColor;
	ArrayList<Player> members;
	private int id;
	private static int lastId = 0;
	private String name;
	
	public Team()
	{
		this(Color.BLACK);
	}
	
	public Team(Color c)
	{
		teamColor = c;
	}
	
	public Team(String n)
	{
		this(Color.BLACK, n);
	}
	
	public Team(Color c, String n)
	{
		id = lastId++;
		teamColor = c;
		name = n;
	}
	
	public boolean isOnTeam(Player p)
	{
		return members.contains(p);
	}
	
	public boolean isOnTeam(int id)
	{
		return isOnTeam(new Player(id));
	}
	
	public int getId()
	{
		return id;
	}
	
	public void join(Player p)
	{
		members.add(p);
	}
	
	public void leave(Player p)
	{
		members.remove(p);
	}
	
	public boolean equals(Object o)
	{
		if(!(o instanceof Team))
		{
			return true;
		}
		return ((Team)o).getId()==id;
	}
	
}
