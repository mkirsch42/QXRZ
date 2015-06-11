package org.amityregion5.qxrz.common.ui;

import java.awt.Color;
import java.awt.geom.Rectangle2D;

public class NetworkDrawablePlayer extends NetworkDrawableEntity {
	private static final long serialVersionUID = 3015573524048481189L;
	private String nametag = "";
	private Color ntColor;
	private boolean ntItalics = false;
	private String gun;
	private int ammo;
	private int totalAmmo;
	private int health, maxHealth;
	
	public NetworkDrawablePlayer(NetworkDrawableObject[] drawables, Rectangle2D box) {
		super(drawables, box);
	}

	/**
	 * @return the nametag
	 */
	public String getNametag() {
		return nametag;
	}

	/**
	 * @param nametag the nametag to set
	 */
	public void setNametag(String nametag) {
		this.nametag = nametag;
	}

	/**
	 * @return the ntColor
	 */
	public Color getNameColor() {
		return ntColor;
	}

	/**
	 * @param ntColor the ntColor to set
	 */
	public void setNameColor(Color ntColor) {
		this.ntColor = ntColor;
	}

	/**
	 * @return the ntItalics
	 */
	public boolean isItalics() {
		return ntItalics;
	}

	/**
	 * @param ntItalics the ntItalics to set
	 */
	public void setItalics(boolean ntItalics) {
		this.ntItalics = ntItalics;
	}

	/**
	 * @return the gun
	 */
	public String getGun() {
		return gun;
	}

	/**
	 * @param gun the gun to set
	 */
	public void setGun(String gun) {
		this.gun = gun;
	}

	/**
	 * @return the ammo
	 */
	public int getAmmo() {
		return ammo;
	}

	/**
	 * @param ammo the ammo to set
	 */
	public void setAmmo(int ammo) {
		this.ammo = ammo;
	}

	/**
	 * @return the totalAmmo
	 */
	public int getTotalAmmo() {
		return totalAmmo;
	}

	/**
	 * @param totalAmmo the totalAmmo to set
	 */
	public void setTotalAmmo(int totalAmmo) {
		this.totalAmmo = totalAmmo;
	}
	public void setHealth(int health) {
		this.health = health;
	}
	public int getHealth() {
		return health;
	}
	
	public void setMaxHealth(int maxHealth) {
		this.maxHealth = maxHealth;
	}
	
	public int getMaxHealth() {
		return maxHealth;
	}
}
