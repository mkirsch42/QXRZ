
package org.amityregion5.qxrz.server.ui;


public class RTableUnits implements Comparable{
	private String hostname;
	private int ip;

	
	public RTableUnits(String h, int i) {
		hostname = h;
		ip = i;
	}
	
	public String getHost(){
		return hostname;
	}
	public int getIP() {
		return ip;
	}
	public int compareTo(Object o) {
		if (o instanceof RTableUnits) {
			RTableUnits r = (RTableUnits) o;
			return hostname.compareTo((r.getHost()));
		}
		else
			return 0;
	}
}

