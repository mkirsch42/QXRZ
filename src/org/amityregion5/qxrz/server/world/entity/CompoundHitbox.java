package org.amityregion5.qxrz.server.world.entity;

import java.util.ArrayList;

public class CompoundHitbox extends Hitbox
{

	private ArrayList<Hitbox> hbs;
	
	public CompoundHitbox()
	{
		hbs = new ArrayList<Hitbox>();
	}
	
	public void add(Hitbox h)
	{
		hbs.add(h);
	}

	@Override
	public boolean intersects(Hitbox h2)
	{
		for(Hitbox h : hbs)
		{
			if(h.intersects(h2))
			{
				return true;
			}
		}
		return false;
	}
	
}
