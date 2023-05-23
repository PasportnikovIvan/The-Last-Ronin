package levels;

import entities.Mob;
import entities.Goblin;
import entities.FastMob;
import main.Game;
import objects.BackgroundBamboo;
import objects.Archer;
import objects.GameContainer;
import objects.Grass;
import objects.Potion;
import objects.Spike;
import static utilz.Constants.EnemyConstants.*;
import static utilz.Constants.ObjectConstants.*;

import java.awt.Color;
import java.awt.Point;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

/**
 * !Rules for adding the level!
 * each pixel is a tile
 * min level height of 14 tiles (pixels)
 * min level width of 26 tiles (pixels)
 * name should be 1.png, 2.png, 3.png, etc..., so there must be a number that is higher than previous level
 * player spawn
 * atleast 1 enemy per level
 */
//class to store the data of the level
public class Level {

    private BufferedImage img;
    private int[][] lvlData;

    private ArrayList<Mob> mobs = new ArrayList<>();
    private ArrayList<Goblin> goblins = new ArrayList<>();
    private ArrayList<FastMob> fastMobs = new ArrayList<>();
    private ArrayList<Potion> potions = new ArrayList<>();
    private ArrayList<Spike> spikes = new ArrayList<>();
    private ArrayList<GameContainer> containers = new ArrayList<>();
    private ArrayList<Archer> archers = new ArrayList<>();
    private ArrayList<BackgroundBamboo> bamboos = new ArrayList<>();
    private ArrayList<Grass> grass = new ArrayList<>();

    private int lvlTilesWide;
    private int maxTilesOffset;
    private int maxLvlOffsetX;
    private Point playerSpawn;

    public Level(BufferedImage img) {
        this.img = img;
        lvlData = new int[img.getHeight()][img.getWidth()];
        loadLevel();
        calcLvlOffsets();
    }

    //The option to draw levels
    //There is image file, where each pixel is a position on the level
    //(for example) img file (4x4) = Level, 4x4 tiles
    //Each pixel have 3 colors (Red, Green, Blue)
    //the color value = the spriteID
    //0-255 in value (48 for now, because of outside sprites 12 width x 4 height)
    //Color color = new Color(red = spriteID, green = enemies and player, blue = objects)
    //This method looping through the image colors just once, instead of one per object/enemy/etc..
    private void loadLevel() {
        //using size of the img to loop through each pixel and add the pixel to lvlData
        for (int y = 0; y < img.getHeight(); y++) {
            for (int x = 0; x < img.getWidth(); x++) {
                Color c = new Color(img.getRGB(x, y));
                int red = c.getRed();
                int green = c.getGreen();
                int blue = c.getBlue();

                loadLevelData(red, x, y);
                loadEntities(green, x, y);
                loadObjects(blue, x, y);
            }
        }
    }

    //loading method for the all levels
    private void loadLevelData(int redValue, int x, int y) {
        if (redValue >= 50) {
            lvlData[y][x] = 0;
        } else {
            lvlData[y][x] = redValue;
        }
        switch (redValue) { //adding grass on the tiles where player can run
            case 0, 1, 2, 3, 30, 31, 33, 34, 35, 36, 37, 38, 39 -> grass.add(new Grass((int)(x * Game.TILES_SIZE), (int)(y * Game.TILES_SIZE) - Game.TILES_SIZE, getRndGrassType(x)));
        }
    }

    private int getRndGrassType(int xPos) {
        return xPos % 2;
    }

    //loading method for the all entities in the game
    private void loadEntities(int greenValue, int x, int y) {
        switch (greenValue) {
            case MOB -> mobs.add(new Mob(x * Game.TILES_SIZE, y * Game.TILES_SIZE));
            case GOBLIN -> goblins.add(new Goblin(x * Game.TILES_SIZE, y * Game.TILES_SIZE));
            case FAST_MOB -> fastMobs.add(new FastMob(x * Game.TILES_SIZE, y * Game.TILES_SIZE));
            case 100 -> playerSpawn = new Point(x * Game.TILES_SIZE, y * Game.TILES_SIZE);
        }
    }

    //loading method for the all objects in the game
    private void loadObjects(int blueValue, int x, int y) {
        switch (blueValue) {
            case RED_POTION, BLUE_POTION -> potions.add(new Potion(x * Game.TILES_SIZE, y * Game.TILES_SIZE, blueValue));
            case BOX, BARREL -> containers.add(new GameContainer(x * Game.TILES_SIZE, y * Game.TILES_SIZE, blueValue));
            case SPIKE -> spikes.add(new Spike(x * Game.TILES_SIZE, y * Game.TILES_SIZE, SPIKE));
            case ARCHER_LEFT, ARCHER_RIGHT -> archers.add(new Archer(x * Game.TILES_SIZE, y * Game.TILES_SIZE, blueValue));
            case BAMBOO_ONE, BAMBOO_TWO, BAMBOO_THREE -> bamboos.add(new BackgroundBamboo(x * Game.TILES_SIZE, y * Game.TILES_SIZE, blueValue));
        }
    }

    private void calcLvlOffsets() {
        lvlTilesWide = img.getWidth();
        maxTilesOffset = lvlTilesWide - Game.TILES_IN_WIDTH;
        maxLvlOffsetX = Game.TILES_SIZE * maxTilesOffset;
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

    public Point getPlayerSpawn() {
        return playerSpawn;
    }

    public ArrayList<Mob> getMobs() {
        return mobs;
    }

    public ArrayList<FastMob> getFastMobs() {
        return fastMobs;
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

    public ArrayList<Archer> getArchers() {
        return archers;
    }

    public ArrayList<Goblin> getGoblins() {
        return goblins;
    }

    public ArrayList<BackgroundBamboo> getBamboos() {
        return bamboos;
    }

    public ArrayList<Grass> getGrass() {
        return grass;
    }
}
