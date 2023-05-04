package gamestates;

import entities.Player;
import levels.LevelManager;
import main.Game;
import ui.PauseOverlay;

import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;

//class Playing of the Gamestates
public class Playing extends State implements Statemethods{
    private Player player;
    private LevelManager levelManager;
    private PauseOverlay pauseOverlay;
    private boolean paused = false; //the important boolean, that is going to decide whether to show the pause screen or not

    public Playing(Game game) {
        super(game);
        initClasses();
    }

    private void initClasses() {
        levelManager = new LevelManager(game);
        player = new Player(200, 200, (int) (64 * Game.SCALE), (int) (40 * Game.SCALE));
        player.loadLvlData(levelManager.getCurrentLevel().getLevelData());
        pauseOverlay = new PauseOverlay(this);
    }

    @Override
    public void update() {
        //game have to stop when it's paused
        if (!paused) {
            levelManager.update();
            player.update();
        } else {
            pauseOverlay.update();
        }
    }

    @Override
    public void draw(Graphics g) {
        levelManager.draw(g);
        player.render(g);

        if (paused) {
            pauseOverlay.draw(g);
        }
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        //Left click - attack
        if (e.getButton() == MouseEvent.BUTTON1){
            player.setAttacking(true);
        }
    }

    public void mouseDragged(MouseEvent e) {
        if (paused) {
            pauseOverlay.mouseDragged(e);
        }
    }

    @Override
    public void mousePressed(MouseEvent e) {
        if (paused) {
            pauseOverlay.mousePressed(e);
        }
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        if (paused) {
            pauseOverlay.mouseReleased(e);
        }
    }

    @Override
    public void mouseMoved(MouseEvent e) {
        if (paused) {
            pauseOverlay.mouseMoved(e);
        }
    }

    @Override
    public void keyPressed(KeyEvent e) {
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
            break;
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {
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
}
