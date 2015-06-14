package org.amityregion5.qxrz.server.world.gameplay;

import org.amityregion5.qxrz.server.world.World;
import org.amityregion5.qxrz.server.world.entity.PlayerEntity;
import org.amityregion5.qxrz.server.world.vector2d.Vector2D;

public final class SpecialWeapons
{

	public static void shotgun(World w, Player p, Vector2D v)
	{
		Team team = p.getTeam();
		PlayerEntity entity = p.getEntity();
		Bullet b = new Bullet(entity.getPos(), new Vector2D(v.angle()).multiply(v.length()), p.getEquipped(), entity);
		if(team != null)
			b.setFriendlyFireTeam(team);
		b.setFriendlyFirePlayer(p);
		b.setSource(entity);
		w.add(b.getEntity());

		b = new Bullet(entity.getPos(), new Vector2D(v.angle()+0.1).multiply(v.length()), p.getEquipped(), entity);
		if(team != null)
			b.setFriendlyFireTeam(team);
		b.setFriendlyFirePlayer(p);
		b.setSource(entity);
		w.add(b.getEntity());
		
		b = new Bullet(entity.getPos(), new Vector2D(v.angle()-0.1).multiply(v.length()), p.getEquipped(), entity);
		if(team != null)
			b.setFriendlyFireTeam(team);
		b.setFriendlyFirePlayer(p);
		b.setSource(entity);
		w.add(b.getEntity());
		
		b = new Bullet(entity.getPos(), new Vector2D(v.angle()+0.15).multiply(v.length()), p.getEquipped(), entity);
		if(team != null)
			b.setFriendlyFireTeam(team);
		b.setFriendlyFirePlayer(p);
		b.setSource(entity);
		w.add(b.getEntity());
		
		b = new Bullet(entity.getPos(), new Vector2D(v.angle()-0.15).multiply(v.length()), p.getEquipped(), entity);
		if(team != null)
			b.setFriendlyFireTeam(team);
		b.setFriendlyFirePlayer(p);
		b.setSource(entity);
		w.add(b.getEntity());
	}
	
	public static void firegun(World w, Player p, Vector2D v)
	{
		Team team = p.getTeam();
		PlayerEntity entity = p.getEntity();
		Bullet b = new Bullet(entity.getPos(), new Vector2D(v.angle()).multiply(v.length()), p.getEquipped(), entity);
		if(team != null)
			b.setFriendlyFireTeam(team);
		b.setFriendlyFirePlayer(p);
		b.setSource(entity);
		w.add(b.getEntity());
	}
}
