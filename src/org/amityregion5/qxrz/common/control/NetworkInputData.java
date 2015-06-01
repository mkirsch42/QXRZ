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
	private int mouseX;
	private int mouseY;
	
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
	
	public int getMouseX() {
		return mouseX;
	}
	
	public int getMouseY() {
		return mouseY;
	}
	
	public void setMouseX(int mouseX) {
		this.mouseX = mouseX;
	}
	public void setMouseY(int mouseY) {
		this.mouseY = mouseY;
	}
}
