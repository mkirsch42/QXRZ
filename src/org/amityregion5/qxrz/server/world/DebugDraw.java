package org.amityregion5.qxrz.server.world;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Shape;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.ArrayList;

import javax.swing.JApplet;
import javax.swing.JFrame;

public class DebugDraw extends JApplet
{
	private static final long serialVersionUID = 1075647760634317935L;
	final static Color bg = Color.white;
    final static Color fg = Color.black;
	private World w;  
	public static ArrayList<Shape> buffer = new ArrayList<Shape>();
	
	public static JApplet setup(World W)
	{
		JFrame f = new JFrame("ShapesDemo2D");
		f.addWindowListener(new WindowAdapter()
		{
			public void windowClosing(WindowEvent e)
			{
				System.exit(0);
			}
		});
		JApplet applet = new DebugDraw();
		f.getContentPane().add("Center", applet);
		((DebugDraw)applet).init(W);
		f.pack();
		f.setSize(new Dimension(500, 500));
		f.setVisible(true);
		return applet;
	}
	
	public void init(World W) {
        //Initialize drawing colors
		w=W;
        setBackground(bg);
        setForeground(fg);
    }
	
	public void paint(Graphics g)
	{
		Graphics2D g2 = (Graphics2D) g;
		g2.scale(2,2);
		g2.translate(5, 5);
		g2.clearRect(-5,-5,getWidth(),getHeight());
		g2.setColor(Color.GREEN);
		while(buffer.size()>0)
		{
			g2.draw(buffer.remove(0));
		}
		g2.setColor(Color.BLACK);
		if(w!=null)
			w.draw(g2);
	}
}
