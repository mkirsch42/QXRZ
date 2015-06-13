package org.amityregion5.qxrz.common.world;

import java.awt.Rectangle;

import org.amityregion5.qxrz.server.Game;
import org.amityregion5.qxrz.server.world.Obstacle;
import org.amityregion5.qxrz.server.world.World;
import org.amityregion5.qxrz.server.world.entity.RectangleHitbox;
import org.amityregion5.qxrz.server.world.gameplay.Pickup;

public class WorldManager {

	private static World defaultWorld;
	private static World danWorld;

	static {
		{
			defaultWorld = new World();

			defaultWorld.addObstacle(new Obstacle(new RectangleHitbox(new Rectangle(1000,500,400,400)), "--AABB--"));
			defaultWorld.addObstacle(new Obstacle(new RectangleHitbox(new Rectangle(2000,1500,500,1000)), "--AABB--"));
			defaultWorld.addObstacle(new Obstacle(new RectangleHitbox(new Rectangle(1500,2900+2*Game.GAME_UNIT,2000,500)), "--AABB--"));
			
			defaultWorld.add(new Pickup("ro", 10, 500, 0, 3000).getEntity());
			defaultWorld.add(new Pickup(17, 0, 1000, 5000).getEntity());

			createBorder(defaultWorld, 2500, 1500, 1500);
		}
		{
			danWorld = new World();

			danWorld.addObstacle(new Obstacle(new RectangleHitbox(new Rectangle(1000,500,400,400)), "--AABB--"));
			danWorld.addObstacle(new Obstacle(new RectangleHitbox(new Rectangle(2000,1500,500,1000)), "--AABB--"));
			danWorld.addObstacle(new Obstacle(new RectangleHitbox(new Rectangle(1500,2900+2*Game.GAME_UNIT,2000,500)), "--AABB--"));

			createBorder(danWorld, 2500, 1500, 1500);
		}
	}

	private static void createBorder(World w, int radius, int xCenter, int yCenter) {
		w.addObstacle(new Obstacle(new RectangleHitbox(new Rectangle(xCenter - radius - 100, yCenter - radius - 100, radius*2+200, 100)),"--AABB--"));
		w.addObstacle(new Obstacle(new RectangleHitbox(new Rectangle(xCenter - radius - 100, yCenter + radius, radius*2 + 200, 100)),"--AABB--"));

		w.addObstacle(new Obstacle(new RectangleHitbox(new Rectangle(xCenter - radius - 100, yCenter - radius - 100, 100, radius*2 + 200)),"--AABB--"));
		w.addObstacle(new Obstacle(new RectangleHitbox(new Rectangle(xCenter + radius, yCenter - radius - 100, 100, radius*2 + 200)),"--AABB--"));
		w.bounds(new Rectangle(xCenter-radius, yCenter-radius, radius*2, radius*2));
	}

	public static World getWorld(Worlds world) {
		if (world == Worlds.DAN_WORLD) {
			return danWorld;
		}
		return defaultWorld;
	}
}
