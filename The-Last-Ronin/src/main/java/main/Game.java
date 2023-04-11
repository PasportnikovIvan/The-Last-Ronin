package main;

//This is one of the Main classes.
//This one is "game" class. This is where added everything together.
public class Game implements Runnable {

    private GameWindow gameWindow;
    private GamePanel gamePanel;
    private Thread gameThread; //Creating Thread
    private final int FPS_SET = 120; //set FPS Max

    public Game() {
        gamePanel = new GamePanel();
        gameWindow = new GameWindow(gamePanel);
        gamePanel.requestFocus(); //requests that gets the input focus
        startGameLoop();
    }

    //Creating Thread for game loop.
    private void startGameLoop() {
        gameThread = new Thread(this);
        gameThread.start(); //Runnable is passed into a Thread
    }

    @Override
    public void run() { //method of Runnable

        double timePerFrame = 1000000000.0 / FPS_SET; //duration of the frame (need to use nanosecs)
        long lastFrame = System.nanoTime();
        long now = System.nanoTime();

        int frames = 0;
        long lastCheck = System.currentTimeMillis();

        //time per frame
        while(true) {
            now = System.nanoTime();
            if (now - lastFrame >= timePerFrame) {
                gamePanel.repaint();
                lastFrame = now;
                frames++;
            }

            //FPS check
            if (System.currentTimeMillis() - lastCheck >= 1000) {
                lastCheck = System.currentTimeMillis();
                System.out.println("FPS: " + frames);
                frames = 0;
            }
        }
    }
}
