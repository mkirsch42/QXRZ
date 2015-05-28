package org.amityregion5.qxrz.common.net;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.util.logging.Logger;

public class UDPInputStream
{

	private DatagramSocket sock;
	private DatagramPacket packet;
	private byte[] buf;
	private Logger l = Logger.getGlobal();
	public UDPInputStream(DatagramSocket ds) throws Exception
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
		l.info("Listening on " + sock.getLocalPort());
		sock.receive(packet);
		l.info("Data received");
		ByteArrayInputStream byteStream = new ByteArrayInputStream(buf, 0, packet.getLength());
		ObjectInputStream outStream = new ObjectInputStream(byteStream);
		return (NetworkObject) outStream.readObject();
	}

	public DatagramSocket getSock()
	{
		return sock;
	}


}