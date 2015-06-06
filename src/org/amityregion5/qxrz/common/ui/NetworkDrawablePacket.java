package org.amityregion5.qxrz.common.ui;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Iterator;

import org.amityregion5.qxrz.common.world.Worlds;

public class NetworkDrawablePacket implements Serializable, Iterable<NetworkDrawableObject>
{

	/**
	 * 
	 */
	private static final long serialVersionUID = -9098804499975029270L;

	private ArrayList<NetworkDrawableObject> drawables;
	private int clientIndex;
	private Worlds currentWorld;
	
	public NetworkDrawablePacket()
	{
		drawables = new ArrayList<NetworkDrawableObject>();
	}

	public void add(NetworkDrawableObject ndo)
	{
		drawables.add(ndo);
	}
	
	public void setClientIndex(int cid)
	{
		clientIndex = cid;
	}
	
	public NetworkDrawableObject getClientObject()
	{
		return drawables.get(clientIndex);
	}
	
	public int getClientIndex()
	{
		return clientIndex;
	}
	
	public ArrayList<NetworkDrawableObject> getDrawables()
	{
		return drawables;
	}

	@Override
	public Iterator<NetworkDrawableObject> iterator()
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
