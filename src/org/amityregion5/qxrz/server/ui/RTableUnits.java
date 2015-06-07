package org.amityregion5.qxrz.server.ui;

import java.awt.Button;

public class RTableUnits implements Comparable<RTableUnits>{
	private String hostname;
	private int ip;
	private Button b;
	
	public RTableUnits(String h, int i, Button button) {
		hostname = h;
		ip = i;
		b = button;
	}
	
	public String getHost(){
		return hostname;
	}
	public int getIP() {
		return ip;
	}
	public Button getButton() {
		return b;
	}
	public int compareTo(RTableUnits o) {
		return hostname.compareTo((o.getHost()));
		/*if (o instanceof RTableUnits) {
			RTableUnits r = (RTableUnits) o;
			return hostname.compareTo((r.getHost()));
		}
		else
			return 0;*/
	}
}
