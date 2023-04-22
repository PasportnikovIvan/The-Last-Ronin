package inputs;

import gamestates.Gamestate;
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
        switch (Gamestate.state) {
        case MENU:
            gamePanel.getGame().getMenu().keyPressed(e);
            break;
        case PLAYING:
            gamePanel.getGame().getPlaying().keyPressed(e);
            break;
        default:
            break;
        }
    }

    //stopping the direction
    @Override
    public void keyReleased(KeyEvent e) {
        switch (Gamestate.state) {
        case MENU:
            gamePanel.getGame().getMenu().keyReleased(e);
            break;
        case PLAYING:
            gamePanel.getGame().getPlaying().keyReleased(e);
            break;
        default:
            break;
        }
    }
}
