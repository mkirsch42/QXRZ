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
		long lastMs = System.currentTimeMillis();
		World w = new World();
		w.add(new PlayerEntity());
		w.addObstacle(new Obstacle(new RectangleHitbox(new Rectangle(4,2,5,10))));
		
		while(true)
		{
			w.update( (double)( System.currentTimeMillis()-lastMs ) / (1000/UPDATE_RATE) );
			lastMs = System.currentTimeMillis();
			Thread.sleep(1000/UPDATE_RATE);
		}
	}

}
