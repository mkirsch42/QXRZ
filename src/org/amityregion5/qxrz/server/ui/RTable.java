package org.amityregion5.qxrz.server.ui;

import java.awt.Color;
import java.awt.Font;
import java.util.ArrayList;
import java.util.List;

import javax.swing.BoxLayout;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class RTable
{
	private List<RTableUnits> clients;
	private JPanel panel;
	Font f = new Font("Helvetica", Font.PLAIN, 16);
	private Color c1 = new Color(100, 100, 100);
	private Color c2 = new Color(200, 200, 200);

	public RTable()
	{
		clients = new ArrayList<RTableUnits>();
	}

	public void add(String h, int i)
	{
		clients.add(new RTableUnits(h, i));
	}

	public JPanel getPanel()
	{
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

		for (int i = 0; i < clients.size(); i++)
		{
			RTableUnits r = clients.get(i);
			JLabel hos = new JLabel(r.getHost());
			JLabel ipnum = new JLabel(r.getIP() + "");
			if (i % 2 == 0)
			{
				hos.setBackground(c1);
				ipnum.setBackground(c2);
			}
			else
			{
				hos.setBackground(c2);
				ipnum.setBackground(c1);
			}
			hos.setSize(100, 30);
			hos.setFont(f);
			ipnum.setSize(100, 30);
			ipnum.setFont(f);
			host.add(hos);
			ip.add(ipnum);
		}
		panel.add(host);
		panel.add(ip);
		return panel;
	}

	public void remove(int index)
	{
		clients.remove(index);
	}

	public static void main(String[] args)
	{
		int x = 40;
		RTable r = new RTable();
		for (int i = 0; i < x; i++)
		{
			r.add("test" + i, i);
		}
	}
}
