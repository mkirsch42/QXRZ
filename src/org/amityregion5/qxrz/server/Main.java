package org.amityregion5.qxrz.server;

import java.awt.Rectangle;

import javax.swing.JApplet;

import org.amityregion5.qxrz.server.ui.MainGui;
import org.amityregion5.qxrz.server.world.DebugDraw;
import org.amityregion5.qxrz.server.world.Obstacle;
import org.amityregion5.qxrz.server.world.World;
import org.amityregion5.qxrz.server.world.entity.PlayerEntity;
import org.amityregion5.qxrz.server.world.entity.RectangleHitbox;

public final class Main
{

	private static final int UPDATE_RATE = 240;
	
	public static void main(String[] args) throws InterruptedException
	{
		new MainGui().show();
		
		// Main game loop, to be moved to separate class
		long lastMs = System.currentTimeMillis();
		// Create world and add test objects
		World w = new World();
		w.add(new PlayerEntity());
		//w.addObstacle(new Obstacle(new RectangleHitbox(new Rectangle(200,90,5,10))));
		
		/* TODO instantiate server stuff and register event listeners with anonymous class
		 * 
		 * snm.addServerEventListener(new ServerEventListener() {
		 * 	methods go here
		 * });
		 */
		
		JApplet debug = DebugDraw.setup(w);
		
		while(true)
		{
			// Update world entities with proportional time
			w.update( ( System.currentTimeMillis()-lastMs ) / (1000.0/UPDATE_RATE) );
			debug.invalidate();
			debug.repaint();
			
			// Set current time for next update
			lastMs = System.currentTimeMillis();
			// Sleep for next update
			Thread.sleep(1000/UPDATE_RATE);
		}
	}

}
