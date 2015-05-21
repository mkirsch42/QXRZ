package org.amityregion5.qxrz.server.net;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.net.DatagramPacket;
import java.net.DatagramSocket;

public class UDPInputStream
{

	private DatagramSocket sock = null;
	private DatagramPacket dp = null;
	private byte[] buf = null;

	public DatagramPacket getDp()
	{
		return dp;
	}

	public void setDp(DatagramPacket dp)
	{
		this.dp = dp;
	}

	public UDPInputStream(DatagramSocket ds)
	{
		this.sock = ds;
		buf = new byte[1024 * 5];
		dp = new DatagramPacket(buf, buf.length);
	}

	public void setSocket(DatagramSocket ds)
	{
		this.sock = ds;
	}
	
	public DatagramSocket getSocket()
	{
		return this.sock;
	}
	
	public NetworkObject recvObject() throws IOException, ClassNotFoundException
	{
		sock.receive(dp);
		ByteArrayInputStream byteStream = new ByteArrayInputStream(buf, 0, dp.getLength());
		ObjectInputStream outStream = new ObjectInputStream(byteStream);
		return (NetworkObject)outStream.readObject();
	}

}