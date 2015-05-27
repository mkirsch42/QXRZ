package org.amityregion5.qxrz.server.world;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Shape;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.ArrayList;

import javax.swing.JApplet;
import javax.swing.JFrame;

import org.amityregion5.qxrz.client.ui.util.GuiUtil;
import org.amityregion5.qxrz.client.ui.util.ImageModification;
import org.amityregion5.qxrz.server.Main;

public class DebugDraw extends JApplet
{
	private static final long serialVersionUID = 1075647760634317935L;
	final static Color bg = Color.white;
    final static Color fg = Color.black;
	private World w;  
	private boolean dummy = true;
	public static ArrayList<Shape> buffer = new ArrayList<Shape>();
	
	private static final double SCALE = 0.15;
	private static final int WIDTH = 1000;
	private static final int HEIGHT = 700;

	public static DebugDraw setup(World W)
	{
		if(W==null)
		{
			return new DebugDraw();
		}
		JFrame f = new JFrame("ShapesDemo2D");
		f.addWindowListener(new WindowAdapter()
		{
			public void windowClosing(WindowEvent e)
			{
				System.exit(0);
			}
		});
		DebugDraw applet = new DebugDraw();
		f.getContentPane().add("Center", applet);
		((DebugDraw)applet).init(W);
		f.pack();
		f.setSize(new Dimension(WIDTH, HEIGHT));
		
		f.setVisible(true);
		return applet;
	}
	
	public void init(World W) {
        //Initialize drawing colors
		w=W;
		dummy=false;
        setBackground(bg);
        setForeground(fg);
    }
	
	public void draw()
	{
		if(dummy)
			return;
		invalidate();
		repaint();
	}
	
	public void paint(Graphics g)
	{
		Graphics2D rg = (Graphics2D) g;
		
		GuiUtil.applyRenderingHints(rg);
		
		BufferedImage buff = ImageModification.createBlankBufferedImage(getWidth(), getHeight());
		
		Graphics2D g2 = buff.createGraphics();
		
		GuiUtil.applyRenderingHints(g2);
		
		rg.setColor(Color.WHITE);
		rg.fillRect(0, 0, getWidth(), getHeight());
		
		g2.scale(SCALE, SCALE);
		g2.translate(5, 5);
		//g2.clearRect(-5,-5,getWidth(),getHeight());
		
		g2.setColor(Color.WHITE);
		g2.fillRect(-5, -5, getWidth(), getHeight());
		
		g2.setStroke(new BasicStroke(25));
		g2.setColor(Color.GREEN);
		boolean green = true;
		while(buffer.size()>0)
		{
			g2.draw(buffer.remove(0));
			if(green)
			{
				g2.setColor(Color.RED);
				green = false;
			}
			else
			{
				g2.setColor(Color.GREEN);
				green = true;
			}
		}
		g2.setStroke(new BasicStroke(10));
		g2.setColor(Color.BLACK);
		if(w!=null)
			w.draw(g2);
		
		g2.dispose();
		
		rg.drawImage(buff, null, 0, 0);
	}
}
