package levels;

import entities.Mob;
import main.Game;
import objects.GameContainer;
import objects.Potion;
import objects.Spike;
import utilz.HelpMethods;
import static utilz.HelpMethods.GetLevelData;
import static utilz.HelpMethods.GetMobs;
import static utilz.HelpMethods.GetPlayerSpawn;

import java.awt.Point;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

/**
 * !Rules for adding the level!
 * min level height of 14 tiles (pixels)
 * min level width of 26 tiles (pixels)
 * name should be 1.png, 2.png, 3.png, etc...
 * player spawn
 * atleast 1 enemy per level
 */
//class to store the data of the level
public class Level {

    private BufferedImage img;
    private int[][] lvlData;
    private ArrayList<Mob> mobs;
    private ArrayList<Potion> potions;
    private ArrayList<Spike> spikes;
    private ArrayList<GameContainer> containers;
    private int lvlTilesWide;
    private int maxTilesOffset;
    private int maxLvlOffsetX;
    private Point playerSpawn;

    public Level(BufferedImage img) {
        this.img = img;
        createLevelData();
        createEnemies();
        createPotions();
        createContainers();
        createSpikes();
        calcLvlOffsets();
        calcPlayerSpawn();
    }

    private void createSpikes() {
        spikes = HelpMethods.GetSpikes(img);
    }

    private void createContainers() {
        containers = HelpMethods.GetContainers(img);
    }

    private void createPotions() {
        potions = HelpMethods.GetPotions(img);
    }

    private void calcPlayerSpawn() {
        playerSpawn = GetPlayerSpawn(img);
    }

    private void calcLvlOffsets() {
        lvlTilesWide = img.getWidth();
        maxTilesOffset = lvlTilesWide - Game.TILES_IN_WIDTH;
        maxLvlOffsetX = Game.TILES_SIZE * maxTilesOffset;
    }

    private void createEnemies() {
        mobs = GetMobs(img);
    }

    private void createLevelData() {
        lvlData = GetLevelData(img);
    }

    public int getSpriteIndex(int x, int y) {
        return lvlData[y][x];
    }

    public int[][] getLevelData() {
        return lvlData;
    }

    public int getLvlOffset() {
        return maxLvlOffsetX;
    }

    public ArrayList<Mob> getMobs() {
        return mobs;
    }

    public Point getPlayerSpawn() {
        return playerSpawn;
    }

    public ArrayList<Potion> getPotions() {
        return potions;
    }

    public ArrayList<GameContainer> getContainers() {
        return containers;
    }

    public ArrayList<Spike> getSpikes() {
        return spikes;
    }
}
