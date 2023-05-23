package levels;

import main.Game;
import utilz.LoadSave;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

/**
 * !Rules for adding the level!
 * min level height of 14 tiles (pixels)
 * min level width of 26 tiles (pixels)
 * name should be 1.png, 2.png, 3.png, etc..., so there must be a number that is higher than previous level
 * player spawn
 * atleast 1 enemy per level
 */
//level handler class
public class LevelManager {

    private Game game;
    private BufferedImage[] levelSprite;
    private BufferedImage[] waterSprite;
    private ArrayList<Level> levels;
    private int lvlIndex = 0, aniTick, aniIndex;

    public LevelManager(Game game) {
        this.game = game;
        importOutsideSprites();
        createWater();
        levels = new ArrayList<>();
        buildAllLevels();
    }

    private void createWater() {
        //(same as clouds)
        waterSprite = new BufferedImage[5];
        BufferedImage img = LoadSave.GetSpriteAtlas(LoadSave.WATER_TOP);
        for (int i = 0; i < 4; i++) {
            waterSprite[i] = img.getSubimage(i * 32, 0, 32, 32);
        }
        waterSprite[4] = LoadSave.GetSpriteAtlas(LoadSave.WATER_BOTTOM);
    }

    public void loadNextLevel() {
        Level newLevel = levels.get(lvlIndex);
        game.getPlaying().getEnemyManager().loadEnemies(newLevel);
        game.getPlaying().getPlayer().loadLvlData(newLevel.getLevelData());
        game.getPlaying().setMaxLvlOffset(newLevel.getLvlOffset());
        game.getPlaying().getObjectManager().loadObjects(newLevel);
    }

    private void buildAllLevels() {
        BufferedImage[] allLevels = LoadSave.GetAllLevels();
//        BufferedImage[] newallLevels = MakeNewLevelArray(allLevels); //the control of each level
        for (BufferedImage img : allLevels) { //newallLevels
            levels.add(new Level(img));
        }
    }

    private BufferedImage[] MakeNewLevelArray(BufferedImage[] allLevels) {
        int numOfTheLevelNeeded = 1;
        int amountOfDeletedLevels = numOfTheLevelNeeded - 1;
        BufferedImage[] newallLevels = new BufferedImage[allLevels.length - amountOfDeletedLevels];
        for (int i = amountOfDeletedLevels; i < allLevels.length; i++) {
            newallLevels[i-amountOfDeletedLevels] = allLevels[i];
        }
        return newallLevels;
    }

    private void importOutsideSprites() {
        BufferedImage img = LoadSave.GetSpriteAtlas(LoadSave.LEVEL_ATLAS);
        levelSprite = new BufferedImage[48];
        for (int j = 0; j < 4; j++) {
            for (int i = 0; i < 12; i++) {
                int index = j * 12 + i;
                levelSprite[index] = img.getSubimage(i * 32, j * 32, 32, 32);
            }
        }
    }

    public void draw(Graphics g, int lvlOffset) {
        for (int j = 0; j < Game.TILES_IN_HEIGHT; j++) {
            for (int i = 0; i < levels.get(lvlIndex).getLevelData()[0].length; i++) {
                int index = levels.get(lvlIndex).getSpriteIndex(i, j);
                int x = Game.TILES_SIZE * i - lvlOffset;
                int y = Game.TILES_SIZE * j;
                if (index == 48) {
                    g.drawImage(waterSprite[aniIndex], x, y, Game.TILES_SIZE, Game.TILES_SIZE, null);
                } else if (index == 49) {
                    g.drawImage(waterSprite[4], x, y, Game.TILES_SIZE, Game.TILES_SIZE, null);
                } else {
                    g.drawImage(levelSprite[index], x, y, Game.TILES_SIZE, Game.TILES_SIZE, null);
                }
            }
        }
    }

    public void update() {
        updateWaterAnimation();
    }

    private void updateWaterAnimation() {
        aniTick++;
        if (aniTick >= 40) {
            aniTick = 0;
            aniIndex++;

            if (aniIndex >= 4) {
                aniIndex = 0;
            }
        }
    }

    public Level getCurrentLevel() {
        return levels.get(lvlIndex);
    }

    public int getAmountOfLevels() {
        return levels.size();
    }

    public int getLvlIndex() {
        return lvlIndex;
    }

    public void setLvlIndex(int lvlIndex) {
        this.lvlIndex = lvlIndex;
    }
}
