package org.amityregion5.qxrz.common.net;

import java.io.Serializable;
import java.net.InetSocketAddress;

/* extended by
 *   - NetworkNode
 *   - ServerInfo
 */
public class AbstractNetworkNode implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 7661039239230519837L;
	protected InetSocketAddress addr;
	protected String name;
	
	public AbstractNetworkNode(String n)
	{
		name = n;
	}
	
	public InetSocketAddress getAddress() {
		return addr;
	}

	public void setAddress(InetSocketAddress a) {
		addr = a;
	}

	
	public String getName()
	{
		return name;
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
