package inputs;

import gamestates.Gamestate;
import main.GamePanel;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

//Adding inputs and making sure they respond to events on keyboard.
public class KeyboardInputs implements KeyListener {

    private GamePanel gamePanel;
    public KeyboardInputs(GamePanel gamePanel) {
        this.gamePanel = gamePanel;
    }

    @Override
    public void keyTyped(KeyEvent e) {

    }

    //making controllers A_D, Space - starting direction, ESC - opening options
    @Override
    public void keyPressed(KeyEvent e) {
        switch (Gamestate.state) {
        case MENU:
            gamePanel.getGame().getMenu().keyPressed(e);
            break;
        case PLAYING:
            gamePanel.getGame().getPlaying().keyPressed(e);
            break;
        case OPTIONS:
            gamePanel.getGame().getGameOptions().keyPressed(e);
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
        case CREDITS:
            gamePanel.getGame().getCredits().keyReleased(e);
            break;
        default:
            break;
        }
    }
}
