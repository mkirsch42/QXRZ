package org.amityregion5.qxrz.server;

import java.awt.geom.Rectangle2D;

import javax.swing.JApplet;

import org.amityregion5.qxrz.server.world.DebugDraw;
import org.amityregion5.qxrz.server.world.Obstacle;
import org.amityregion5.qxrz.server.world.World;
import org.amityregion5.qxrz.server.world.entity.CompoundHitbox;
import org.amityregion5.qxrz.server.world.entity.PlayerEntity;
import org.amityregion5.qxrz.server.world.entity.RectangleHitbox;

public class Game
{
	
	public static final double GAME_UNIT = 0.01;
	
	public static DebugDraw debug;
	
	public Game() throws InterruptedException
	{
		// Create world and add test objects
		World w = new World();
		w.add(new PlayerEntity());
		debug = DebugDraw.setup(w);
		w.addObstacle(new Obstacle(new RectangleHitbox(new Rectangle2D.Double(10,5,4,4))));
		w.addObstacle(new Obstacle(new RectangleHitbox(new Rectangle2D.Double(20,15,5,10))));
		w.addObstacle(new Obstacle(new RectangleHitbox(new Rectangle2D.Double(15,29+2*GAME_UNIT,20,5))));
		// TODO finish compound hitbox normals then add some to the world
		/*w.addObstacle(new Obstacle(new CompoundHitbox().add(
				new RectangleHitbox(new Rectangle2D.Double(50,20,5,10))).add(
				new RectangleHitbox(new Rectangle2D.Double(40,30,15,5)))));
		//w.addObstacle(new Obstacle(new RectangleHitbox(new Rectangle2D.Double(40,30,15,5))));
		*/
		long lastMs = System.currentTimeMillis();
		
		while (true)
		{
			// Update world entities with proportional time
			w.update((System.currentTimeMillis() - lastMs)
					/ (1000.0 / DebugConstants.UPDATE_RATE));
			debug.draw();

			// Set current time for next update
			lastMs = System.currentTimeMillis();
			// Sleep for next update
			Thread.sleep(1000 / DebugConstants.DEBUG_FPS);
		}
	}

}
