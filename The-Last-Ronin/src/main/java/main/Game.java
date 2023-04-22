package main;

import gamestates.Gamestate;
import gamestates.Menu;
import gamestates.Playing;

import java.awt.Graphics;

//This is one of the Main classes.
//This one is "game" class. This is where added everything together.
public class Game implements Runnable {

    private GameWindow gameWindow;
    private GamePanel gamePanel;
    private Thread gameThread; //Creating Thread
    private final int FPS_SET = 120; //set FPS Max
    private final int UPS_SET = 200; //set UPS/tick

    private Playing playing;
    private Menu menu;

    //Size calc
    public final static int TILES_DEFAULT_SIZE = 32;
    public final static float SCALE = 2f; //how much should scale everything (player, enemies, etc.)
    public final static int TILES_IN_WIDTH = 26;
    public final static int TILES_IN_HEIGHT = 14;
    public final static int TILES_SIZE = (int)(TILES_DEFAULT_SIZE * SCALE);
    public final static int GAME_WIDTH = TILES_SIZE * TILES_IN_WIDTH;
    public final static int GAME_HEIGHT = TILES_SIZE * TILES_IN_HEIGHT;

    public Game() {
        initClasses(); //init all entities without long list

        gamePanel = new GamePanel(this);
        gameWindow = new GameWindow(gamePanel);
        gamePanel.requestFocus(); //requests that gets the input focus

        startGameLoop();
    }

    private void initClasses() {
        menu = new Menu(this);
        playing = new Playing(this);
    }

    //Creating Thread for game loop.
    private void startGameLoop() {
        gameThread = new Thread(this);
        gameThread.start(); //Runnable is passed into a Thread
    }

    //stable gameUpdate for the loop
    public void update() {
        switch (Gamestate.state) {
        case MENU:
            menu.update();
            break;
        case PLAYING:
            playing.update();
            break;
        default:
            break;
        }
    }

    public void render(Graphics g) {
        switch (Gamestate.state) {
        case MENU:
            menu.draw(g);
            break;
        case PLAYING:
            playing.draw(g);
            break;
        default:
            break;
        }
    }

    @Override
    public void run() { //method of Runnable

        double timePerFrame = 1000000000.0 / FPS_SET; //duration of the frame (need to use nanosecs)
        double timePerUpdate = 1000000000.0 / UPS_SET; //the time between update (need to use nanosecs)

        long previousTime = System.nanoTime();

        int frames = 0;
        int updates = 0;
        long lastCheck = System.currentTimeMillis();

        double deltaU = 0;
        double deltaF = 0; //for frame

        //stable game loop
        while(true) {
            long currentTime = System.nanoTime();

            deltaU += (currentTime - previousTime) / timePerUpdate;
            deltaF += (currentTime - previousTime) / timePerFrame;
            previousTime = currentTime;

            if (deltaU >= 1) {
                update();
                updates++;
                deltaU--; //removing 100%
            }

            if (deltaF >= 1) {
                gamePanel.repaint();
                frames++;
                deltaF--;
            }

            //FPS check
            if (System.currentTimeMillis() - lastCheck >= 1000) {
                lastCheck = System.currentTimeMillis();
                System.out.println("FPS: " + frames + " | UPS: " + updates);
                frames = 0;
                updates = 0;
            }
        }
    }

    public void windowFocusLost() {
        if (Gamestate.state == Gamestate.PLAYING) {
            playing.getPlayer().resetDirBooleans();
        }
    }

    public Menu getMenu() {
        return menu;
    }

    public Playing getPlaying() {
        return playing;
    }
}
