package org.amityregion5.qxrz.server.world.gameplay;

public class Player {
	private int health;
	private final int speed = 100; //speed will not be upgradable
	private Weapon[] guns = new Weapon[2];
	private int equipped; //index for currently equipped weapon
	
}
