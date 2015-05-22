package org.amityregion5.qxrz.server;

import java.awt.geom.Rectangle2D;

import javax.swing.JApplet;

import org.amityregion5.qxrz.server.world.DebugDraw;
import org.amityregion5.qxrz.server.world.Obstacle;
import org.amityregion5.qxrz.server.world.World;
import org.amityregion5.qxrz.server.world.entity.PlayerEntity;
import org.amityregion5.qxrz.server.world.entity.RectangleHitbox;

public class Game
{

	private static final int UPDATE_RATE = 10;
	private static final int DEBUG_FPS = 10;
	
	public static final double GAME_UNIT = 0.01;
	
	public static DebugDraw debug;
	
	public Game() throws InterruptedException
	{
		// Main game loop, to be moved to separate class
		long lastMs = System.currentTimeMillis();
		// Create world and add test objects
		World w = new World();
		w.add(new PlayerEntity());
		debug = DebugDraw.setup(w);
		w.addObstacle(new Obstacle(new RectangleHitbox(new Rectangle2D.Double(50,13,5,10))));

		while (true)
		{
			// Update world entities with proportional time
			w.update((System.currentTimeMillis() - lastMs)
					/ (1000.0 / UPDATE_RATE));
			debug.invalidate();
			debug.repaint();

			// Set current time for next update
			lastMs = System.currentTimeMillis();
			// Sleep for next update
			Thread.sleep(1000 / DEBUG_FPS);
		}
	}

}
