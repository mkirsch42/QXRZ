package org.amityregion5.qxrz.server.ui;

import java.awt.Button;
import java.awt.Font;
import java.awt.ScrollPane;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

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
	private List<Button> b;
	private RTable table;
	private ScrollPane scroll;
	public MainGui(ServerNetworkManager manager) throws Exception
	{	
		networkManager = manager;
		
		frame = new JFrame("QXRZ");
		frame.setSize(600, 600);
		table = new RTable();
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
		title.setSize(300, 75);
		title.setLocation(400, 0);
		JPanel gamedata = new JPanel();
		JLabel temp = new JLabel("Game Data");
		temp.setFont(f);
		gamedata.add(temp);
		gamedata.setSize(400, 50);
		gamedata.setLocation(0, 600);
		JPanel buttonPanel = new JPanel();
		b = new ArrayList<Button>();
		for (int i = 0; i < c.size(); i++) {
			Button x = new Button("X");
			x.setSize(50, 30);
			x.addActionListener(new ButtonListener());
			b.add(x);
			buttonPanel.add(x);
		}
		InetAddress a = InetAddress.getLocalHost();
		table.add(a.toString(), 0);
		Button x = new Button("X");
		x.setSize(50, 30);
		x.addActionListener(new ButtonListener());
		b.add(x);
		buttonPanel.add(x);
		JPanel panel = table.getPanel();
		panel.add(buttonPanel);
		scroll = new ScrollPane();
		scroll.add(panel);
		scroll.setSize(500, 500);
		scroll.setLocation(300,100);
		

		frame.add(scroll);
		frame.add(gamedata);
		frame.add(title);
		
		frame.setVisible(true);
		frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
	}
	private class ButtonListener implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			Object cause = e.getSource();
			for(int i = 0; i < b.size(); i++)
				if(cause == b.get(i))
				{b.remove(i);
				table.remove(i);
				networkManager.removeClient(i);
				}
				frame.remove(scroll);
				JPanel panel = table.getPanel();
				JPanel buttonPanel = new JPanel();
				for (int i = 0; i < b.size(); i++)
					buttonPanel.add(b.get(i));
				panel.add(buttonPanel);
				scroll = new ScrollPane();
				scroll.add(panel);
				frame.add(scroll);
		}
		
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
