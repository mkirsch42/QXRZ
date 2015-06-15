package org.amityregion5.qxrz.common.world;

import java.awt.Rectangle;

import org.amityregion5.qxrz.server.Game;
import org.amityregion5.qxrz.server.world.Obstacle;
import org.amityregion5.qxrz.server.world.World;
import org.amityregion5.qxrz.server.world.entity.RectangleHitbox;
import org.amityregion5.qxrz.server.world.gameplay.Pickup;
import org.amityregion5.qxrz.server.world.gameplay.WeaponTypes;

public class WorldManager {

	private static World defaultWorld;
	private static World danWorld;

	static {
		{
			defaultWorld = new World();

			defaultWorld.addObstacle(new Obstacle(new RectangleHitbox(new Rectangle(1000,500,400,400)), "building/1"));
			defaultWorld.addObstacle(new Obstacle(new RectangleHitbox(new Rectangle(2000,1500,500,1000)), "building/1"));
			defaultWorld.addObstacle(new Obstacle(new RectangleHitbox(new Rectangle(1500,2900+2*Game.GAME_UNIT,2000,500)), "building/1"));
			
			int i = 500;
			for(WeaponTypes w : WeaponTypes.values())
			{
				defaultWorld.add(new Pickup(w.text, w.cmaxammo, i, 0, 3000).getEntity());
				i+=500;
			}
			
			//defaultWorld.add(new Pickup("fl", 100, 500, 0, 3000).getEntity());
			//defaultWorld.add(new Pickup("ro", 200, 500, 0, 3000).getEntity());
			defaultWorld.add(new Pickup(17, 0, 1000, 5000).getEntity());
			
			//defaultWorld.setBackgroundAsset("weapons/flamethrower");

			createBorder(defaultWorld, 2500, 1500, 1500);
		}
		{
			danWorld = new World();

			
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
