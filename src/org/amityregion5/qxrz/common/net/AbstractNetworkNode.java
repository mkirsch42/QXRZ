package org.amityregion5.qxrz.common.net;

import java.io.Serializable;
import java.net.InetSocketAddress;

/* extended by
 *   - NetworkNode
 *   - ServerInfo
 */
public abstract class AbstractNetworkNode implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 7661039239230519837L;
	protected InetSocketAddress addr;

	public InetSocketAddress getAddress() {
		return addr;
	}

	public void setAddress(InetSocketAddress a) {
		addr = a;
	}
	
	@Override
	public boolean equals(Object obj) {
		if (!(obj instanceof AbstractNetworkNode)) {
			return false;
		}
		
		AbstractNetworkNode ann = (AbstractNetworkNode) obj;
		return addr.equals(ann.getAddress());
	}
}
