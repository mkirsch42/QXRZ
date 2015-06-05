package org.amityregion5.qxrz.server.ui;

import java.awt.Font;
import java.awt.ScrollPane;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.WindowConstants;

import org.amityregion5.qxrz.common.net.NetworkNode;
import org.amityregion5.qxrz.server.net.ServerNetworkManager;

public class MainGui
{	private Font f = new Font("Sans Serif", Font.PLAIN, 32);
	public static void main(String [] args) throws Exception{ 
		System.err.println("DO NOT USE THIS MAIN METHOD. USE server.Main. IF YOU DONT WANT THE DEBUG STUFF TO DRAW TURN OFF DEBUG_GUI IN DebugConstants.java.");
		//MainGui n = new MainGui();
	}
	private JFrame frame;
	private ServerNetworkManager networkManager;
	
	public MainGui(ServerNetworkManager manager) throws Exception
	{	
		networkManager = manager;
		
		frame = new JFrame("QXRZ");
		frame.setSize(1080,1000);
		RTable table = new RTable();
		ArrayList<NetworkNode> c = networkManager.getClients();
			
		for(Iterator<NetworkNode> i = c.iterator(); i.hasNext();) {
				NetworkNode n = i.next();
				InetSocketAddress a = n.getAddress();
				InetAddress p = a.getAddress();
				table.add(p.toString(), a.getPort());
		}
		
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
