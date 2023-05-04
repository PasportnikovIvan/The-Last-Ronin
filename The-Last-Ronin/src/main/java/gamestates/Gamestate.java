package gamestates;

//class for Storing data, that is wished to track off
//(like Constants class)
//That class will have constants that correspond to the Gamestates (Menu, Playing, LvlSelect and Settings)
public enum Gamestate {

    PLAYING, MENU, OPTIONS, QUIT;

    public static Gamestate state = MENU;
}
