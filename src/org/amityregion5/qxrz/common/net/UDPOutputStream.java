package org.amityregion5.qxrz.common.net;

import java.io.ByteArrayOutputStream;
import java.io.ObjectOutputStream;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetSocketAddress;
import java.net.SocketException;

public class UDPOutputStream
{

	private DatagramSocket sock;
	private InetSocketAddress addr;
	
	public UDPOutputStream(InetSocketAddress a) throws SocketException
	{
		setAddress(a);
		sock = new DatagramSocket(); // anonymous bind to a random local port
	}
	
	public void setAddress(InetSocketAddress a)
	{
		addr = a;
	}
	
	public void sendObject(Object o) throws Exception
	{
		ByteArrayOutputStream byteArrayStream = new ByteArrayOutputStream();
		
		// I *think* this should work. ~ Eli
		new ObjectOutputStream(byteArrayStream).writeObject(o);
		
		byte[] data = byteArrayStream.toByteArray();
		
		// This shouldn't ever happen...
		if(data.length > NetworkObject.BUFFER_SIZE) throw new Exception("Object is too large!");
		
		sock.send(new DatagramPacket(data, data.length, addr.getAddress(), addr.getPort()));
	}
	
}
