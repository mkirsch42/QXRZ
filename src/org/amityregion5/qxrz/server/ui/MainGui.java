package org.amityregion5.qxrz.server.ui;

import java.awt.Font;
import java.awt.ScrollPane;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.WindowConstants;

import org.amityregion5.qxrz.server.net.ServerNetworkManager;

public class MainGui
{	private Font f = new Font("Times New Roman", Font.PLAIN, 32);
	public static void main(String [] args) throws Exception{ 
		
		MainGui n = new MainGui();
	}
	private JFrame frame;
	private ServerNetworkManager networkManager;
	
	public MainGui()
	{
		//networkManager = nm;
		
		frame = new JFrame("QXRZ");
		frame.setSize(1080,1000);
		//panel.setLayout(new BoxLayout(panel, BoxLayout.PAGE_AXIS));

		//HashSet<NetworkNode> c = networkManager.getClients();
			/*for(Iterator<NetworkNode> i = c.iterator(); i.hasNext();) {
				NetworkNode n = i.next();*/
		
		RTable table = new RTable();
		for(int i = 0; i < 10; i++)
			table.add("test " + i, i);
		JPanel title = new JPanel();
		JLabel t = new JLabel("Host Name and IP");
		t.setFont(f);
		title.add(t);
		JLabel client = new JLabel("Client List");
		title.setSize(300, 75);
		title.setLocation(400, 0);
		JPanel gamedata = new JPanel();
		JLabel temp = new JLabel("Game Data");
		temp.setFont(f);
		gamedata.add(temp);
		gamedata.setSize(400, 50);
		gamedata.setLocation(0, 600);
		ScrollPane scroll = table.getPanel();
		scroll.setSize(500, 500);
		scroll.setLocation(300,300);
		
		//title.setPreferredSize(new Dimension(100,100));
		//title.setLocation(500,0);
		//SocketAddress addr = networkManager.getSocket();
		
		/*
		 * JLabel ipLabel = new JLabel("Address:" + addr + "", SwingConstants.CENTER);
		ipLabel.setHorizontalAlignment(SwingConstants.CENTER);
		ipLabel.setVerticalAlignment(SwingConstants.CENTER);
		JLabel ClientList = new JLabel("Client List", SwingConstants.LEFT);
		
		panel.add(ipLabel);
		panel.add(ClientList);*/
		frame.add(scroll);
		frame.add(gamedata);
		frame.add(title);
		
		frame.setVisible(true);
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
