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
import javax.swing.SwingConstants;
import javax.swing.WindowConstants;

import org.amityregion5.qxrz.common.net.NetworkNode;
import org.amityregion5.qxrz.server.Game;
import org.amityregion5.qxrz.server.net.ServerNetworkManager;

public class MainGui
{
	private Font f = new Font("Sans Serif", Font.PLAIN, 32);

	public static void main(String[] args) throws Exception
	{
		System.err
				.println("DO NOT USE THIS MAIN METHOD. USE server.Main. IF YOU DONT WANT THE DEBUG STUFF TO DRAW TURN OFF DEBUG_GUI IN DebugConstants.java.");
		// MainGui n = new MainGui();
	}

	private JFrame frame;
	private ServerNetworkManager networkManager;
	private List<Button> b;
	private RTable table;
	private ScrollPane scroll;
	private Game g;

	public MainGui(ServerNetworkManager manager, Game g) throws Exception
	{
		networkManager = manager;

		frame = new JFrame("QXRZ");
		frame.setSize(600, 600);
		// frame.setResizable(false);

		JPanel title = new JPanel();
		JLabel t = new JLabel("Host Name and IP: ");
		JLabel server = new JLabel(InetAddress.getLocalHost().toString());
		title.add(t);
		title.add(server);

		table = new RTable();
		scroll = new ScrollPane();
		redraw();
		// frame.add(gamedata);
		frame.add(title);

		frame.setVisible(true);
		frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
	}

	private class ButtonListener implements ActionListener
	{
		public void actionPerformed(ActionEvent e)
		{
			int i = b.indexOf(e.getSource());
			b.remove(i);
			table.remove(i);

			//g.removePlayer(networkManager.removeClient(i));

			scroll.remove(i);
			/*frame.remove(scroll);
			JPanel panel = table.getPanel();
			JPanel buttonPanel = new JPanel();
			for (int i1 = 0; i1 < b.size(); i1++)
				buttonPanel.add(b.get(i1));
			panel.add(buttonPanel);
			scroll = new ScrollPane();
			scroll.setSize(300, 300);
			scroll.setLocation(100, 100);
			scroll.add(panel);
			frame.add(scroll);*/
		}

	}

	public void redraw()
	{
		frame.remove(scroll);
		// table = new RTable();
		ArrayList<NetworkNode> c = networkManager.getClients();

		for (Iterator<NetworkNode> i = c.iterator(); i.hasNext();)
		{
			NetworkNode n = i.next();
			InetSocketAddress a = n.getAddress();
			InetAddress p = a.getAddress();
			table.add(p.toString(), a.getPort());
		}

		JPanel buttonPanel = new JPanel();
		b = new ArrayList<Button>();
		for (int i = 0; i < c.size(); i++)
		{
			Button x = new Button("X");
			x.setSize(50, 30);
			x.addActionListener(new ButtonListener());
			b.add(x);
			buttonPanel.add(x);
		}

		JPanel panel = table.getPanel();
		panel.add(buttonPanel);
		panel.setLocation(SwingConstants.CENTER, SwingConstants.CENTER);
		scroll = new ScrollPane();

		scroll.setSize(300, 300);
		scroll.setLocation(100, 100);
		scroll.add(buttonPanel);

		frame.add(scroll);
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
