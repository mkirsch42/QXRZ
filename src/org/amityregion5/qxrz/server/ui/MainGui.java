package org.amityregion5.qxrz.server.ui;

import java.net.InetAddress;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.WindowConstants;

public class MainGui
{

	private JFrame frame;
	
	public MainGui(int port)
	{
		frame = new JFrame("QXRZ");
		frame.setSize(600,600);
		
		JPanel panel = new JPanel();
		panel.setLayout(new BoxLayout(panel, BoxLayout.PAGE_AXIS));
		panel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
		
		JLabel ipLabel = new JLabel("<html>" + InetAddress.getLocalHost().toString() + "</br>" + port + "</html>");
		
		
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
