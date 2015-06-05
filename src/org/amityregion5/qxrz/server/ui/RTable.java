package org.amityregion5.qxrz.server.ui;

import java.awt.Button;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.ScrollPane;
import java.util.ArrayList;
import java.util.List;

import javax.swing.BoxLayout;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTable;
import javax.swing.WindowConstants;

public class RTable {
	private List<RTableUnits> servers;
	private JPanel panel;
	private ScrollPane scroll;
	Font f = new Font("Helvetica", Font.PLAIN, 28);
	public RTable() {
		servers = new ArrayList<RTableUnits>();
	}
	
	public void add(String h, int i) {
	servers.add(new RTableUnits(h, i, new Button("X")));
	}
	
	public ScrollPane getPanel() {
		JPanel button = new JPanel();
		button.setLayout(new BoxLayout(button, BoxLayout.PAGE_AXIS));
		button.setSize(100, 100);
		JPanel host = new JPanel();
		host.setLayout(new BoxLayout(host, BoxLayout.PAGE_AXIS));
		host.setSize(100, 100);
		JPanel ip = new JPanel();
		ip.setLayout(new BoxLayout(ip, BoxLayout.PAGE_AXIS));
		ip.setSize(100, 100);
		panel = new JPanel();
		panel.setSize(600, 600);
		scroll = new ScrollPane();
		
		for (int i = 0; i < servers.size(); i++) {
		RTableUnits r = servers.get(i);
		JLabel hos = new JLabel(r.getHost());
		JLabel ipnum = new JLabel(r.getIP() + "");
		Button x = new Button("X");
		hos.setSize(100, 30);
		hos.setFont(f);
		ipnum.setSize(100,30);
		ipnum.setFont(f);
		x.setSize(50, 30);
		host.add(hos);
		ip.add(ipnum);
		button.add(x);
		}
		panel.add(host);
		panel.add(ip);
		panel.add(button);
		scroll.add(panel);
		return scroll;
	}
	public void remove(Object o) {
		
	}
	public static void main(String [] args) {
		int x = 40;
		RTable r = new RTable();
		for(int i = 0; i < x; i++)
		{r.add("test" + i, i);
		}
	}
}

