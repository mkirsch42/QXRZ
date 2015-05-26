package org.amityregion5.qxrz.common.net;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.net.DatagramPacket;
import java.net.DatagramSocket;

public class UDPInputStream
{

	private DatagramSocket sock;
	private DatagramPacket packet;
	private byte[] buf;

	public UDPInputStream(DatagramSocket ds)
	{
		sock = ds;
		buf = new byte[NetworkObject.BUFFER_SIZE];
		packet = new DatagramPacket(buf, buf.length);
	}
	
	public DatagramPacket getPacket()
	{
		return packet;
	}
	
	public NetworkObject recvObject() throws IOException, ClassNotFoundException
	{
		sock.receive(packet);
		ByteArrayInputStream byteStream = new ByteArrayInputStream(buf, 0, packet.getLength());
		ObjectInputStream outStream = new ObjectInputStream(byteStream);
		return (NetworkObject) outStream.readObject();
	}

}