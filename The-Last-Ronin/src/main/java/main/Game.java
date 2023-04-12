package main;

import entities.Player;

import java.awt.*;

//This is one of the Main classes.
//This one is "game" class. This is where added everything together.
public class Game implements Runnable {

    private GameWindow gameWindow;
    private GamePanel gamePanel;
    private Thread gameThread; //Creating Thread
    private final int FPS_SET = 120; //set FPS Max
    private final int UPS_SET = 200; //set UPS/tick

    private Player player;

    public Game() {
        initClasses(); //init all entities without long list

        gamePanel = new GamePanel(this);
        gameWindow = new GameWindow(gamePanel);
        gamePanel.requestFocus(); //requests that gets the input focus

        startGameLoop();
    }

    private void initClasses() {
        player = new Player(200, 200);
    }

    //Creating Thread for game loop.
    private void startGameLoop() {
        gameThread = new Thread(this);
        gameThread.start(); //Runnable is passed into a Thread
    }

    //stable gameUpdate for the loop
    public void update() {
        player.update();
    }

    public void render(Graphics g) {
        player.render(g);
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

    //Nothing is foolproof. Makes all boolean FALSE when window is no more in focus
    public void windowFocusLost() {
        player.resetDirBooleans();
    }

    public Player getPlayer() {
        return player;
    }
}
