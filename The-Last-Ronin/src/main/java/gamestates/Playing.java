package gamestates;

import entities.EnemyManager;
import entities.Player;
import levels.LevelManager;
import main.Game;
import objects.ObjectManager;
import ui.GameCompletedOverlay;
import ui.GameOverOverlay;
import ui.LevelCompletedOverlay;
import ui.PauseOverlay;
import utilz.LoadSave;
import effects.EmotionEffect;
import effects.Sakura;
import static utilz.Constants.Environment.*;
import static utilz.Constants.Emotion.*;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.util.Random;
import java.util.ArrayList;

//class Playing of the Gamestates
public class Playing extends State implements Statemethods {
    private Player player;
    private LevelManager levelManager;
    private EnemyManager enemyManager;
    private ObjectManager objectManager;
    private PauseOverlay pauseOverlay;
    private GameOverOverlay gameOverOverlay;
    private GameCompletedOverlay gameCompletedOverlay;
    private LevelCompletedOverlay levelCompletedOverlay;
    private Sakura sakura;

    private boolean paused = false; //the important boolean, that is going to decide whether to show the pause screen or not

    private int xLvlOffset; //Offset that will be adding or removing from, to redraw everything to the left or to the right
    private int leftBorder = (int)(0.25 * Game.GAME_WIDTH); //the line, which if the Player is beyond then starts calculating if anything to move
    private int rightBorder = (int)(0.75 * Game.GAME_WIDTH);
    //calculating the max value the Offset can have (stop moving level close to border)
    private int maxLvlOffsetX;

    private BufferedImage backgroundImg, bigCloud, smallCloud;
    private BufferedImage[] questionImgs, xcrossImgs;
    private ArrayList<EmotionEffect> emotionEffects = new ArrayList<>();
    private int[] smallCloudsPos;
    private Random rnd = new Random();

    private boolean gameOver; //false from start
    private boolean lvlCompleted;
    private boolean gameCompleted;
    private boolean playerDying;
    private boolean drawSakura;

    public Playing(Game game) {
        super(game);
        initClasses();

        backgroundImg = LoadSave.GetSpriteAtlas(LoadSave.PLAYING_BG_IMG);

        bigCloud = LoadSave.GetSpriteAtlas(LoadSave.BIG_CLOUDS);
        smallCloud = LoadSave.GetSpriteAtlas(LoadSave.SMALL_CLOUDS);
        smallCloudsPos = new int[8];
        //making random Y position of the clouds
        for (int i = 0; i < smallCloudsPos.length; i++) {
            smallCloudsPos[i] = (int)(90 * Game.SCALE) + rnd.nextInt((int)(100 * Game.SCALE));
        }

        loadEmotion();
        calcLvlOffset();
        loadStartLevel(); //load first enemies
        setDrawSakuraBoolean();
    }

    private void loadEmotion() {
        loadEmotionImgs();

        //Load emotions array with premade objects, that gets activated when needed.
        //it's a way of avoiding ConcurrentModificationException error - adding to a list that is being looped through.
        for (int i = 0; i < 10; i++) {
            emotionEffects.add(new EmotionEffect(0, 0, XCROSS));
        }
        for (int i = 0; i < 10; i++) {
            emotionEffects.add(new EmotionEffect(0, 0, QUESTION));
        }

        for (EmotionEffect ee : emotionEffects) {
            ee.deactive();
        }
    }

    private void loadEmotionImgs() {
        BufferedImage qtemp = LoadSave.GetSpriteAtlas(LoadSave.QUESTION_ATLAS);
        questionImgs = new BufferedImage[5];
        for (int i = 0; i < questionImgs.length; i++) {
            questionImgs[i] = qtemp.getSubimage(i * 14, 0, 14, 12);
        }

        BufferedImage xtemp = LoadSave.GetSpriteAtlas(LoadSave.XCROSS_ATLAS);
        xcrossImgs = new BufferedImage[5];
        for (int i = 0; i < xcrossImgs.length; i++) {
            xcrossImgs[i] = xtemp.getSubimage(i * 14, 0, 14, 12);
        }
    }

    public void loadNextLevel() {
        levelManager.setLvlIndex(levelManager.getLvlIndex() + 1);
        levelManager.loadNextLevel();
        player.setSpawn(levelManager.getCurrentLevel().getPlayerSpawn());
        resetAll();
    }

    //method is called to load enemies and objects from the start of the level, otherwise game won't have any of them
    private void loadStartLevel() {
        enemyManager.loadEnemies(levelManager.getCurrentLevel());
        objectManager.loadObjects(levelManager.getCurrentLevel());
    }

    private void calcLvlOffset() {
        maxLvlOffsetX = levelManager.getCurrentLevel().getLvlOffset();
    }

    private void initClasses() {
        levelManager = new LevelManager(game);
        enemyManager = new EnemyManager(this);
        objectManager = new ObjectManager(this);

        player = new Player(200, 200, (int) (64 * Game.SCALE), (int) (40 * Game.SCALE), this);
        player.loadLvlData(levelManager.getCurrentLevel().getLevelData());
        player.setSpawn(levelManager.getCurrentLevel().getPlayerSpawn());

        pauseOverlay = new PauseOverlay(this);
        gameOverOverlay = new GameOverOverlay(this);
        levelCompletedOverlay = new LevelCompletedOverlay(this);
        gameCompletedOverlay = new GameCompletedOverlay(this);

        sakura = new Sakura();
    }

    @Override
    public void update() {
        //game have to stop when it's paused
        if (paused) {
            pauseOverlay.update();
        } else if (lvlCompleted) {
            levelCompletedOverlay.update();
        } else if (gameCompleted) {
            gameCompletedOverlay.update();
        } else if (gameOver) {
            gameOverOverlay.update();
        } else if (playerDying) {
            player.update();
        } else { //not gameOver
            updateEmotion();
            if (drawSakura) {
                sakura.update(xLvlOffset);
            }
            levelManager.update();
            objectManager.update(levelManager.getCurrentLevel().getLevelData(), player);
            player.update();
            enemyManager.update(levelManager.getCurrentLevel().getLevelData());
            checkCloseToBorder();
        }
    }

    private void updateEmotion() {
        for (EmotionEffect ee : emotionEffects) {
            if (ee.isActive()) {
                ee.update();
            }
        }
    }

    private void drawEmotion(Graphics g, int xLvlOffset) {
        for (EmotionEffect ee : emotionEffects) {
            if (ee.isActive()) {
                if (ee.getType() == QUESTION) {
                    g.drawImage(questionImgs[ee.getAniIndex()], ee.getX() - xLvlOffset, ee.getY(), EMOTION_WIDTH, EMOTION_HEIGHT, null);
                } else {
                    g.drawImage(xcrossImgs[ee.getAniIndex()], ee.getX() - xLvlOffset, ee.getY(), EMOTION_WIDTH, EMOTION_HEIGHT, null);
                }
            }
        }
    }

    //Not adding a new one emotion, using the old one
    public void addEmotion(int x, int y, int type) {
        emotionEffects.add(new EmotionEffect(x, y - (int)(Game.SCALE * 15), type));
        for (EmotionEffect ee : emotionEffects) {
            if (!ee.isActive()) {
                if (ee.getType() == type) {
                    ee.reset(x, -(int)(Game.SCALE * 15));
                    return;
                }
            }
        }
    }

    private void checkCloseToBorder() {
        int playerX = (int)(player.getHitbox().x);
        int diff = playerX - xLvlOffset; //checking the player's position, knowing difference moving lvl

        if (diff > rightBorder) {
            xLvlOffset += diff - rightBorder;
        } else if (diff < leftBorder) {
            xLvlOffset += diff - leftBorder;
        }

        //making sure that xLvlOffset isn't getting too high or too low
        xLvlOffset = Math.max(Math.min(xLvlOffset, maxLvlOffsetX), 0);
    }

    @Override
    public void draw(Graphics g) {
        g.drawImage(backgroundImg, 0, 0, Game.GAME_WIDTH, Game.GAME_HEIGHT, null);

        drawClouds(g);
        if (drawSakura) {
            sakura.draw(g, xLvlOffset);
        }

        levelManager.draw(g, xLvlOffset);
        objectManager.draw(g, xLvlOffset);
        player.render(g, xLvlOffset);
        enemyManager.draw(g, xLvlOffset);
        objectManager.drawGrass(g, xLvlOffset);
        objectManager.drawBackgroundBamboo(g, xLvlOffset);
        drawEmotion(g, xLvlOffset);

        if (paused) {
            g.setColor(new Color(0, 0, 0, 150));
            g.fillRect(0, 0, Game.GAME_WIDTH, Game.GAME_HEIGHT);
            pauseOverlay.draw(g);
        } else if (gameOver) {
            gameOverOverlay.draw(g);
        } else if (lvlCompleted) {
            levelCompletedOverlay.draw(g);
        } else if (gameCompleted) {
            gameCompletedOverlay.draw(g);
        }
    }

    private void drawClouds(Graphics g) {
        for (int i = 0; i < 4; i++) {
            g.drawImage(bigCloud, i * BIG_CLOUD_WIDTH - (int)(xLvlOffset * 0.3), (int)(219 * Game.SCALE), BIG_CLOUD_WIDTH, BIG_CLOUD_HEIGHT, null);
        }

        for (int i = 0; i < smallCloudsPos.length; i++) {
            g.drawImage(smallCloud, SMALL_CLOUD_WIDTH * 4 * i - (int)(xLvlOffset * 0.7), smallCloudsPos[i], SMALL_CLOUD_WIDTH, SMALL_CLOUD_HEIGHT, null);
        }
    }

    public void resetAll() {
        gameOver = false;
        paused = false;
        lvlCompleted = false;
        playerDying = false;
        drawSakura = false;
        setDrawSakuraBoolean();
        player.resetAll();
        enemyManager.resetAllEnemies();
        objectManager.resetAllObjects();
        emotionEffects.clear();
    }

    public void setGameCompleted() {
        gameCompleted = true;
    }

    public void resetGameCompleted() {
        gameCompleted = false;
    }

    //this method makes it amount of sakura particles 30% of the time you load a level.
    private void setDrawSakuraBoolean() {
        if (rnd.nextFloat() >= 0.7f)
            drawSakura = true;
    }

    public void setGameOver(boolean gameOver) {
        this.gameOver = gameOver;
    }

    public void checkObjectHit(Rectangle2D.Float attackBox) {
        objectManager.checkObjectHit(attackBox);
    }

    public void checkEnemyHit(Rectangle2D.Float attackBox) {
        enemyManager.checkEnemyHit(attackBox);
    }

    public void checkPotionTouched(Rectangle2D.Float hitbox) {
        objectManager.checkObjectTouched(hitbox);
    }

    public void checkSpikesTouched(Player p) {
        objectManager.checkSpikesTouched(p);
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        if (!gameOver) {
            //Left click - attack
            if (e.getButton() == MouseEvent.BUTTON1) {
                player.setAttacking(true);
            } else if (e.getButton() == MouseEvent.BUTTON3) { //Right click - dash attack
                player.powerAttack();
            }
        }
    }

    public void mouseDragged(MouseEvent e) {
        if (!gameOver && !gameCompleted && !lvlCompleted) {
            if (paused) {
                pauseOverlay.mouseDragged(e);
            }
        }
    }

    @Override
    public void mousePressed(MouseEvent e) {
        if (gameOver) {
            gameOverOverlay.mousePressed(e);
        } else if (paused) {
            pauseOverlay.mousePressed(e);
        } else if (lvlCompleted) {
            levelCompletedOverlay.mousePressed(e);
        } else if (gameCompleted) {
            gameCompletedOverlay.mousePressed(e);
        }
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        if (gameOver) {
            gameOverOverlay.mouseReleased(e);
        } else if (paused) {
            pauseOverlay.mouseReleased(e);
        } else if (lvlCompleted) {
            levelCompletedOverlay.mouseReleased(e);
        } else if (gameCompleted) {
            gameCompletedOverlay.mouseReleased(e);
        }
    }

    @Override
    public void mouseMoved(MouseEvent e) {
        if (gameOver) {
            gameOverOverlay.mouseMoved(e);
        } else if (paused) {
            pauseOverlay.mouseMoved(e);
        } else if (lvlCompleted) {
            levelCompletedOverlay.mouseMoved(e);
        } else if (gameCompleted) {
            gameCompletedOverlay.mouseMoved(e);
        }
    }

    @Override
    public void keyPressed(KeyEvent e) {
        if (!gameOver && !gameCompleted && !lvlCompleted) {
            switch (e.getKeyCode()) {
            case KeyEvent.VK_A:
                player.setLeft(true);
                break;
            case KeyEvent.VK_D:
                player.setRight(true);
                break;
            case KeyEvent.VK_SPACE:
                player.setJump(true);
                break;
            case KeyEvent.VK_ESCAPE:
                //flips the Gamestate
                paused = !paused;
            }
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {
        if (!gameOver && !gameCompleted && !lvlCompleted) {
            switch (e.getKeyCode()) {
            case KeyEvent.VK_A:
                player.setLeft(false);
                break;
            case KeyEvent.VK_D:
                player.setRight(false);
                break;
            case KeyEvent.VK_SPACE:
                player.setJump(false);
                break;
            }
        }
    }

    public void setLevelCompleted(boolean levelCompleted) {
        game.getAudioPlayer().lvlCompleted();
        //if no more levels
        if (levelManager.getLvlIndex() + 1 >= levelManager.getAmountOfLevels()) {
            setGameCompleted();
            levelManager.setLvlIndex(0);
            levelManager.loadNextLevel();
            resetAll();
            return;
        }
        this.lvlCompleted = levelCompleted;
    }

    public void setMaxLvlOffset(int lvlOffset) {
        this.maxLvlOffsetX = lvlOffset;
    }

    public void unpauseGame() {
        paused = false;
    }

    //Nothing is foolproof. Makes all boolean FALSE when window is no more in focus
    public void windowFocusLost() {
        player.resetDirBooleans();
    }

    public Player getPlayer() {
        return player;
    }

    public EnemyManager getEnemyManager() {
        return enemyManager;
    }

    public ObjectManager getObjectManager() {
        return objectManager;
    }

    public LevelManager getLevelManager() {
        return levelManager;
    }

    public void setPlayerDying(boolean playerDying) {
        this.playerDying = playerDying;
    }
}
