package org.amityregion5.qxrz.common.ui;

import java.awt.Color;
import java.awt.Graphics2D;
import org.amityregion5.qxrz.client.ui.screen.WindowData;
import org.amityregion5.qxrz.server.world.entity.Hitbox;
import org.amityregion5.qxrz.server.world.entity.Hitboxed;

/**
 * 
 * In case you don't know AABB stands for Axis Aligned Bounding Box
 * 
 */
public class AABBDrawer<T extends Hitboxed> implements IObjectDrawer<T> {
	@Override
	public void draw(Graphics2D g, T object, Viewport vp, WindowData d) {
		g.setColor(Color.BLACK);
		Hitbox h = object.getHitbox();
		double xFact = d.getWidth()/vp.width;
		double xOff = vp.getXOff() * xFact;
		double yFact = d.getHeight()/vp.height;
		double yOff = vp.getYOff() * xFact;
		g.drawRect((int)Math.round(h.getAABB().getX() * xFact - xOff), (int)Math.round(h.getAABB().getY() * yFact - yOff),
				(int)Math.round(h.getAABB().getWidth() * xFact), (int)Math.round(h.getAABB().getHeight() * yFact));
	}
}
