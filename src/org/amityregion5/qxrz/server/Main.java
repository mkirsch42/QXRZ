package org.amityregion5.qxrz.server;

import java.awt.Rectangle;

import org.amityregion5.qxrz.server.ui.MainGui;
import org.amityregion5.qxrz.server.world.Obstacle;
import org.amityregion5.qxrz.server.world.World;
import org.amityregion5.qxrz.server.world.entity.PlayerEntity;
import org.amityregion5.qxrz.server.world.entity.RectangleHitbox;

public final class Main
{

	private static final int UPDATE_RATE = 1;
	
	public static void main(String[] args) throws InterruptedException
	{
		new MainGui().show();
		
		// Main game loop, to be moved to separate class
		long lastMs = System.currentTimeMillis();
		// Create world and add test objects
		World w = new World();
		w.add(new PlayerEntity());
		w.addObstacle(new Obstacle(new RectangleHitbox(new Rectangle(4,2,5,10))));
		
		/* TODO instantiate server stuff and register event listeners with anonymous class
		 * 
		 * snm.addServerEventListener(new ServerEventListener() {
		 * 	methods go here
		 * });
		 */
		
		while(true)
		{
			// Update world entities with proportional time
			w.update( (double)( System.currentTimeMillis()-lastMs ) / (1000/UPDATE_RATE) );
			// Set current time for next update
			lastMs = System.currentTimeMillis();
			// Sleep for next update
			Thread.sleep(1000/UPDATE_RATE);
		}
	}

}
