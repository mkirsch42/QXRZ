package org.amityregion5.qxrz.server.net;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.net.DatagramPacket;
import java.net.DatagramSocket;

public class UDPInputStream
{

	private DatagramSocket ds = null;
	private DatagramPacket dp = null;
	private byte[] buf = null;

	public UDPInputStream(DatagramSocket ds)
	{
		this.ds = ds;
		buf = new byte[1024 * 5];
		dp = new DatagramPacket(buf, buf.length);
	}

	public Object recvObject() throws IOException, ClassNotFoundException
	{
		ds.receive(dp);
		ByteArrayInputStream bis = new ByteArrayInputStream(buf, 0,
				dp.getLength());
		ObjectInputStream ois = new ObjectInputStream(bis);
		return ois.readObject();
	}

}