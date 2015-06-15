package org.amityregion5.qxrz.server.world.gameplay;

import org.amityregion5.qxrz.server.world.World;
import org.amityregion5.qxrz.server.world.entity.PlayerEntity;
import org.amityregion5.qxrz.server.world.vector2d.Vector2D;

public class SpecialMovement {
	private SpecialMovements type;
	//private Landscape la;
	private long timeused;
	private long cooldownTime;
	private long cooldownUse;
	
	private static final int TELEPORT_LEN = 10;
	private static final int TELEPORT_COOLDOWN = 25000;
	private static final int TELEPORT_MULT = 50;
	
	private static final int DASH_LEN = 50;
	private static final int DASH_COOLDOWN = 10000;
	private static final int DASH_MULT = 10;
	
	private static final int ROLL_LEN = 100;
	private static final int ROLL_COOLDOWN = 5000;
	private static final int ROLL_MULT = 5;
	
	public SpecialMovement(SpecialMovements t, World wo)
	{
		type = t;
		//la = wo.getLandscape();
	}
	
	public void dash(PlayerEntity p, long call)
	{
		//maybe some animation
		if (call < cooldownUse)
		{	/*invalid*/ }
		else
		{
			if (!p.getVel().equals(new Vector2D())) {
				p.setVel(p.getVel().multiply(DASH_MULT));
				timeused++;
				if (timeused >= DASH_LEN) {
					timeused = 0;
					cooldownTime = DASH_COOLDOWN;
					cooldownUse = call + DASH_COOLDOWN;
				}
			}
		}
	}
	
	public void roll(PlayerEntity p, long call)
	{
		//maybe some animation
		if (call < cooldownUse)
		{	/*invalid*/ }
		else
		{
			if (!p.getVel().equals(new Vector2D())) {
				p.setVel(p.getVel().multiply(ROLL_MULT));
				timeused++;
				if (timeused >= ROLL_LEN) {
					timeused = 0;
					cooldownTime = ROLL_COOLDOWN;
					cooldownUse = call + ROLL_COOLDOWN;
				}
			}
		}
	}
	public void teleport(PlayerEntity p, long call)
	{
		//maybe some animation
		if (call < cooldownUse)
		{	/*invalid*/ }
		else
		{
			if (!p.getVel().equals(new Vector2D())) {
				p.setVel(p.getVel().multiply(TELEPORT_MULT));
				timeused++;
				if (timeused >= TELEPORT_LEN) {
					timeused = 0;
					cooldownTime = TELEPORT_COOLDOWN;
					cooldownUse = call + TELEPORT_COOLDOWN;
				}
			}
		}
	}
	
	public SpecialMovements getType()
	{
		return type;
	}
	public boolean equals(SpecialMovement o)
	{
		if (o != null && getType() == o.getType())
		{
			return true;
		}
		return false;
	}
	public long getTimeUsed()
	{
		return timeused;
	}

	public double getPercentCooldown() {
		if (System.currentTimeMillis() > cooldownUse) {
			return 1;
		}
		return (cooldownUse - System.currentTimeMillis())/((double)cooldownTime);
	}
}
