package org.amityregion5.qxrz.server.world.gameplay;

public enum WeaponTypes {
    SHOTGUN(	"sg", 	8, 		3, 	16, 3,	2,	6, 		5000),
    FIREGUN(	"fl", 	200, 	1, 	0, 	6,	0,	35,		5000),
    REVOLVER(	"ps", 	6, 		3, 	6, 	5,	30,	10,		5000),
    RIFLE(		"as", 	12,		3,	24, 9,	2,	7, 		5000),
    ARROWGUN(	"bo", 	1, 		6, 	5, 	1,	2,	100, 	5000),
    ROCKETGUN(	"ro", 	4, 		2, 	4, 	2,	3,	80, 	5000);

    public final String text;
    public final int cmaxammo;
    public final int clips;
    public final int reserve;
    public final int rof;
    public final int retime;
    public final int dmg;
    public final int speed;
    
    private WeaponTypes(final String text, final int cmaxammo, final int clips, 
    		final int reserve, final int rof, final int retime, 
    		final int dmg, final int speed) 
    {
        this.text = text;
        this.cmaxammo = cmaxammo;
        this.clips = clips;
        this.reserve = reserve;
        this.rof = rof;
        this.retime = retime;
        this.dmg = dmg;
        this.speed = speed;
    }
}