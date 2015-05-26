package org.amityregion5.qxrz.server.world.entity;

public class Weapon {
	
	private int clipsize;
	private int curclipcount;
	private int clips;
	private int type;
	
	public Weapon()
	{
		
	}
	
	public void pickClip()
	{
		clips++;
	}
	public void fire()
	{
		curclipcount--;
	}
	
}
