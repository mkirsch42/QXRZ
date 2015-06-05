package org.amityregion5.qxrz.common.control;

import java.io.Serializable;
import java.util.BitSet;

public class NetworkInputData implements Serializable
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1377930683051446611L;

	
	private BitSet inputsDown;
	private double mouseX;
	private double mouseY;
	
	public NetworkInputData()
	{
		//WASD, <>, [M1], R, [Space]
		inputsDown = new BitSet(9);
	}
	
	public void set(NetworkInputMasks inputMask, boolean value) {
		inputsDown.set(inputMask.getMaskIndex(), value);
	}
	
	public boolean get(NetworkInputMasks inputMask) {
		return inputsDown.get(inputMask.getMaskIndex());
	}
	
	public double getMouseX() {
		return mouseX;
	}
	
	public double getMouseY() {
		return mouseY;
	}
	
	public void setMouseX(double x) {
		this.mouseX = x;
	}
	public void setMouseY(double mouseY) {
		this.mouseY = mouseY;
	}
	public int getNumDown()
	{
		return inputsDown.cardinality();
	}
	public String toString()
	{
		String str = "";
		for(NetworkInputMasks nim : NetworkInputMasks.values())
		{
			if(get(nim))
			{
				str += nim.toString() + " ";
			}
			str += mouseX + " , " + mouseY;
		}
		return str;
	}
}
