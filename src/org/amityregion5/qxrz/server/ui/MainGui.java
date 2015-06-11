package org.amityregion5.qxrz.server.ui;

import java.awt.Button;
import java.awt.Color;
import java.awt.Font;
import java.net.InetAddress;
import java.util.List;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.WindowConstants;
import javax.swing.table.DefaultTableModel;

import org.amityregion5.qxrz.server.Game;
import org.amityregion5.qxrz.server.net.ServerNetworkManager;

public class MainGui
{
	private Font f = new Font("Sans Serif", Font.PLAIN, 32);
	private  JTable table;
	public static void main(String[] args) throws Exception
	{
		System.err
				.println("DO NOT USE THIS MAIN METHOD. USE server.Main. IF YOU DONT WANT THE DEBUG STUFF TO DRAW TURN OFF DEBUG_GUI IN DebugConstants.java.");
		// MainGui n = new MainGui();
	}

	private JFrame frame;
	private ServerNetworkManager manager;
	private List<Button> b;
	private Game g;

	public MainGui(ServerNetworkManager _manager, Game g) throws Exception
	{
		manager = _manager;
		
		frame = new JFrame("QXRZ");
		frame.setSize(600, 600);
		// frame.setResizable(false);

		JPanel mainPanel = new JPanel();
		mainPanel.setLayout(null);
		
		JLabel iplbl = new JLabel("Host Name and IP: " + InetAddress.getLocalHost().toString());
		JLabel namelbl = new JLabel("Server Name: " + manager.getServerName());
		
		namelbl.setLocation(10, 10);
		namelbl.setSize(frame.getWidth(), 15);
		
		iplbl.setLocation(10, 30);
		iplbl.setSize(frame.getWidth(), 15);
		
		//mainPanel.setBackground(new Color(255, 0, 0));
		
		mainPanel.add(iplbl);
		mainPanel.add(namelbl);
		
//		//JPanel listPanel = table.getPanel();
//		listPanel.setLayout(null);
//		
//		listPanel.setLocation(0, 60);
//		listPanel.setSize(frame.getHeight(), frame.getWidth());
//		listPanel.setBackground(new Color(0, 255, 0));
//		
//		mainPanel.add(listPanel);
		
		Object [][] data = new Object[][] {{"Name 1", "10.7.123.543", "Remove 1"}, {"Name 2", "20.4.5", "Remove2"}, {"Name 3", "30.43", "Remove3"}, {"Name 4", "40.85", "Remove4"}, {"Name 5", "50.34", "Remove5"}};
		
		DefaultTableModel dm = new DefaultTableModel(data, new Object [] {"Name", "IP", "(Remove)"});
		table = new JTable(dm);
		
		JScrollPane scroll = new JScrollPane(table);
		scroll.setLocation(10, 60);
		scroll.setSize(frame.getWidth() - 35, frame.getHeight() - 110);
		
		frame.add(scroll);
		
		frame.add(mainPanel);

		frame.setVisible(true);
		frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
	}
//
//	private class ButtonListener implements ActionListener
//	{
//		public void actionPerformed(ActionEvent e)
//		{
//			int i = b.indexOf(e.getSource());
//			b.remove(i);
//			table.remove(i);
//
//			//g.removePlayer(networkManager.removeClient(i));
//
//			scroll.remove(i);
//			/*frame.remove(scroll);
//			JPanel panel = table.getPanel();
//			JPanel buttonPanel = new JPanel();
//			for (int i1 = 0; i1 < b.size(); i1++)
//				buttonPanel.add(b.get(i1));
//			panel.add(buttonPanel);
//			scroll = new ScrollPane();
//			scroll.setSize(300, 300);
//			scroll.setLocation(100, 100);
//			scroll.add(panel);
//			frame.add(scroll);*/
//		}
//
//	}
//
//	public void redraw()
//	{
//		frame.remove(scroll);
//		// table = new RTable();
//		ArrayList<NetworkNode> c = networkManager.getClients();
//
//		for (Iterator<NetworkNode> i = c.iterator(); i.hasNext();)
//		{
//			NetworkNode n = i.next();
//			InetSocketAddress a = n.getAddress();
//			InetAddress p = a.getAddress();
//			table.add(p.toString(), a.getPort());
//		}
//
//		JPanel buttonPanel = new JPanel();
//		b = new ArrayList<Button>();
//		for (int i = 0; i < c.size(); i++)
//		{
//			Button x = new Button("X");
//			x.setSize(50, 30);
//			x.addActionListener(new ButtonListener());
//			b.add(x);
//			buttonPanel.add(x);
//		}
//
//		JPanel panel = table.getPanel();
//		panel.add(buttonPanel);
//		panel.setLocation(SwingConstants.CENTER, SwingConstants.CENTER);
//		scroll = new ScrollPane();
//
//		scroll.setSize(300, 300);
//		scroll.setLocation(100, 100);
//		scroll.add(buttonPanel);
//
//		frame.add(scroll);
//	}
//
//	public void show()
//	{
//		frame.setVisible(true);
//	}
//
//	public void hide()
//	{
//		frame.setVisible(false);
//	}

}
