package org.amityregion5.qxrz.common.ui;

import java.awt.Point;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Iterator;

import org.amityregion5.qxrz.common.audio.AudioMessage;
import org.amityregion5.qxrz.common.world.Worlds;

public class NetworkDrawablePacket implements Serializable, Iterable<NetworkDrawableEntity>
{

	/**
	 * 
	 */
	private static final long serialVersionUID = -9098804499975029270L;

	private ArrayList<NetworkDrawableEntity> drawables;
	private ArrayList<AudioMessage> playables;
	private int clientIndex;
	private Worlds currentWorld;
	
	public NetworkDrawablePacket()
	{
		drawables = new ArrayList<NetworkDrawableEntity>();
		playables = new ArrayList<AudioMessage>();
	}

	public void add(NetworkDrawableEntity ndo)
	{
		drawables.add(ndo);
	}
	
	public void add(AudioMessage a)
	{
		playables.add(a);
	}
	
	public void setClientIndex(int cid)
	{
		clientIndex = cid;
	}
	
	public NetworkDrawablePlayer getClientObject()
	{
		return (drawables.get(clientIndex) instanceof NetworkDrawablePlayer ? (NetworkDrawablePlayer)drawables.get(clientIndex) : null);
	}
	
	public int getClientIndex()
	{
		return clientIndex;
	}
	
	public Point getClientLocation()
	{
		return new Point((int)getClientObject().getBox().getCenterX(), (int)getClientObject().getBox().getCenterY());
	}
	
	public ArrayList<NetworkDrawableEntity> getDrawables()
	{
		return drawables;
	}

	public ArrayList<AudioMessage> getPlayables()
	{
		return playables;
	}
	
	@Override
	public Iterator<NetworkDrawableEntity> iterator()
	{
		return drawables.iterator();
	}
	
	public void setCurrentWorld(Worlds currentWorld) {
		this.currentWorld = currentWorld;
	}
	
	public Worlds getCurrentWorld() {
		return currentWorld;
	}
}
