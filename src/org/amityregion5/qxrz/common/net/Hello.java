package org.amityregion5.qxrz.common.net;

import java.io.Serializable;

public class Hello implements Serializable
{

	private static final long serialVersionUID = 15L;
	private String name;
	
	public Hello(String name) {
		this.name = name;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	public String getName() {
		return name;
	}
}
