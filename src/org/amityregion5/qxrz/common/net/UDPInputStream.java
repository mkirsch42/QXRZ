package org.amityregion5.qxrz.common.net;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.net.DatagramPacket;
import java.net.DatagramSocket;

public class UDPInputStream
{

	private DatagramSocket sock = null;
	private DatagramPacket packet = null;
	private byte[] buf = null;

	public UDPInputStream(DatagramSocket ds)
	{
		sock = ds;
		buf = new byte[1024 * 5]; // TODO decide on a good size here, and make a final int.
		packet = new DatagramPacket(buf, buf.length);
	}
	
	public DatagramPacket getPacket()
	{
		return packet;
	}

	/* I don't think we need any of these functions... */
	
//	public void setDp(DatagramPacket dp)
//	{
//		this.dp = dp;
//	}

//	public void setSocket(DatagramSocket ds)
//	{
//		sock = ds;
//	}
	
//	public DatagramSocket getSocket()
//	{
//		return sock;
//	}
	
	public NetworkObject recvObject() throws IOException, ClassNotFoundException
	{
		sock.receive(packet);
		ByteArrayInputStream byteStream = new ByteArrayInputStream(buf, 0, packet.getLength());
		ObjectInputStream outStream = new ObjectInputStream(byteStream);
		return (NetworkObject) outStream.readObject();
	}

}