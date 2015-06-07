package org.amityregion5.qxrz.common.world;

import java.awt.Rectangle;

import org.amityregion5.qxrz.server.Game;
import org.amityregion5.qxrz.server.world.Obstacle;
import org.amityregion5.qxrz.server.world.World;
import org.amityregion5.qxrz.server.world.entity.RectangleHitbox;

public class WorldManager {
	
	private static World defaultWorld;
	
	static {
		defaultWorld = new World();
		
		defaultWorld.addObstacle(new Obstacle(new RectangleHitbox(new Rectangle(1000,500,400,400))));
		defaultWorld.addObstacle(new Obstacle(new RectangleHitbox(new Rectangle(2000,1500,500,1000))));
		defaultWorld.addObstacle(new Obstacle(new RectangleHitbox(new Rectangle(1500,2900+2*Game.GAME_UNIT,2000,500))));
		defaultWorld.addObstacle(new Obstacle(new RectangleHitbox(new Rectangle(-1000, -1000, 5000, 100))));
		defaultWorld.addObstacle(new Obstacle(new RectangleHitbox(new Rectangle(-1000, -1000, 100, 5000))));
		defaultWorld.addObstacle(new Obstacle(new RectangleHitbox(new Rectangle(3900, -1000, 100, 5000))));
		defaultWorld.addObstacle(new Obstacle(new RectangleHitbox(new Rectangle(-1000, 3900, 5000, 100))));
	}
	
	public static World getWorld(Worlds world) {
		return defaultWorld;
	}
}
