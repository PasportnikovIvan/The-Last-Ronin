package inputs;

import main.Game;
import main.GamePanel;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import static utilz.Constants.Directions.*;

//Adding inputs and making sure they respond to events on keyboard.
public class KeyboardInputs implements KeyListener {

    private GamePanel gamePanel;
    public KeyboardInputs(GamePanel gamePanel) {
        this.gamePanel = gamePanel;
    }

    @Override
    public void keyTyped(KeyEvent e) {

    }

    //making controllers WASD - starting direction
    @Override
    public void keyPressed(KeyEvent e) {
        switch (e.getKeyCode()) {
        case KeyEvent.VK_W:
            gamePanel.getGame().getPlayer().setUp(true);
            break;
        case KeyEvent.VK_A:
            gamePanel.getGame().getPlayer().setLeft(true);
            break;
        case KeyEvent.VK_S:
            gamePanel.getGame().getPlayer().setDown(true);
            break;
        case KeyEvent.VK_D:
            gamePanel.getGame().getPlayer().setRight(true);
            break;
        }
    }

    //stopping the direction
    @Override
    public void keyReleased(KeyEvent e) {
        switch (e.getKeyCode()) {
        case KeyEvent.VK_W:
            gamePanel.getGame().getPlayer().setUp(false);
            break;
        case KeyEvent.VK_A:
            gamePanel.getGame().getPlayer().setLeft(false);
            break;
        case KeyEvent.VK_S:
            gamePanel.getGame().getPlayer().setDown(false);
            break;
        case KeyEvent.VK_D:
            gamePanel.getGame().getPlayer().setRight(false);
            break;
        }
    }
}
