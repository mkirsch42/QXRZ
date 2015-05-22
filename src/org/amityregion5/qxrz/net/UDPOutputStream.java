package org.amityregion5.qxrz.net;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.DatagramPacket;
import java.net.DatagramSocket;

public class UDPOutputStream
{

	private DatagramSocket sock;
	
	public void setSocket(DatagramSocket ds)
	{
		sock = ds;
	}
	
	public DatagramSocket getSocket()
	{
		return sock;
	}
	
	public void sendObject(Object o) throws IOException
	{
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		ObjectOutputStream outStream = new ObjectOutputStream(baos);
		outStream.writeObject(o);
		byte[] data = baos.toByteArray();
		sock.send(new DatagramPacket(data, data.length));
	}
	
}
