package org.amityregion5.qxrz.server.ui;

import java.net.SocketAddress;


import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.WindowConstants;

import org.amityregion5.qxrz.server.net.ServerNetworkManager;

public class MainGui
{
	public static void main(String [] args) {
		MainGui n = new MainGui(null);
		n.s
	}
	private JFrame frame;
	private ServerNetworkManager networkManager;
	
	public MainGui(ServerNetworkManager nm)
	{
		networkManager = nm;
		
		frame = new JFrame("QXRZ");
		frame.setSize(600,600);
		JPanel Title = new JPanel();
		JPanel panel = new JPanel();
		panel.setLayout(new BoxLayout(panel, BoxLayout.PAGE_AXIS));
		panel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
		
		SocketAddress addr = networkManager.getSocket();
		
		JLabel ipLabel = new JLabel("Address:" + addr + "", SwingConstants.CENTER);
		ipLabel.setHorizontalAlignment(SwingConstants.CENTER);
		ipLabel.setVerticalAlignment(SwingConstants.CENTER);
		JLabel ClientList = new JLabel("Client List", SwingConstants.LEFT);
		
		panel.add(ipLabel);
		panel.add(ClientList);

		frame.add(panel);
		
		frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
	}
	
	public void show()
	{
		frame.setVisible(true);
	}
	
	public void hide()
	{
		frame.setVisible(false);
	}
	
	
	
}
