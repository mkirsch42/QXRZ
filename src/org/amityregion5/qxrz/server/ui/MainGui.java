package org.amityregion5.qxrz.server.ui;

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

	private JFrame frame;
	private ServerNetworkManager networkManager;
	
	public MainGui(ServerNetworkManager networkManager)
	{
		this.networkManager = networkManager;
		
		frame = new JFrame("QXRZ");
		frame.setSize(600,600);
		
		JPanel panel = new JPanel();
		panel.setLayout(new BoxLayout(panel, BoxLayout.PAGE_AXIS));
		panel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
		
		JLabel ipLabel = new JLabel("Address:" + networkManager.getAddress() + "", SwingConstants.CENTER);
		ipLabel.setHorizontalAlignment(SwingConstants.CENTER);
		ipLabel.setVerticalAlignment(SwingConstants.CENTER);
		panel.add(ipLabel);
		
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
