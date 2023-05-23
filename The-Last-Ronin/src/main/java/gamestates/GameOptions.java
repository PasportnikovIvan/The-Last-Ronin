package gamestates;

import main.Game;
import ui.AudioOptions;
import ui.PauseButton;
import ui.UrmButton;
import utilz.LoadSave;
import static utilz.Constants.UI.URMButtons.*;

import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;

//class Game Options of the Gamestates
//this class making able to access the sound controls
public class GameOptions extends State implements Statemethods {

    private AudioOptions audioOptions; //the sound controls
    private BufferedImage backgroundImg, optionsBackgroundImg;
    private int bgOptX, bgOptY, bgOptW, bgOptH;
    private UrmButton menuB;

    public GameOptions(Game game) {
        super(game);
        loadImgs();
        loadButton();
        audioOptions = game.getAudioOptions();
    }

    private void loadButton() {
        int menuX = (int)(387 * Game.SCALE);
        int menuY = (int)(325 * Game.SCALE);

        menuB = new UrmButton(menuX, menuY, URM_SIZE, URM_SIZE, 2);
    }

    private void loadImgs() {
        backgroundImg = LoadSave.GetSpriteAtlas(LoadSave.MENU_BACKGROUND_IMG);
        optionsBackgroundImg = LoadSave.GetSpriteAtlas(LoadSave.OPTIONS_MENU);

        bgOptW = (int)(optionsBackgroundImg.getWidth() * Game.SCALE);
        bgOptH = (int)(optionsBackgroundImg.getHeight() * Game.SCALE);
        bgOptX = Game.GAME_WIDTH / 2 - bgOptW / 2;
        bgOptY = (int)(15 * Game.SCALE);
    }

    @Override
    public void update() {
        menuB.update();
        audioOptions.update();
    }

    @Override
    public void draw(Graphics g) {
        g.drawImage(backgroundImg, 0, 0, Game.GAME_WIDTH, Game.GAME_HEIGHT, null);
        g.drawImage(optionsBackgroundImg, bgOptX, bgOptY, bgOptW, bgOptH, null);

        menuB.draw(g);
        audioOptions.draw(g);
    }

    //for the volume slider
    public void mouseDragged(MouseEvent e) {
        audioOptions.mouseDragged(e);
    }

    @Override
    public void mouseClicked(MouseEvent e) {

    }

    @Override
    public void mousePressed(MouseEvent e) {
        if (isIn(e, menuB)) {
            menuB.setMousePressed(true);
        } else {
            audioOptions.mousePressed(e);
        }
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        if (isIn(e, menuB)) {
            if (menuB.isMousePressed()) {
                Gamestate.state = Gamestate.MENU;
            }
        } else {
            audioOptions.mouseReleased(e);
        }
        menuB.resetBools();
    }

    @Override
    public void mouseMoved(MouseEvent e) {
        menuB.setMouseOver(false);

        if (isIn(e, menuB)) {
            menuB.setMouseOver(true);
        } else {
            audioOptions.mouseMoved(e);
        }
    }

    @Override
    public void keyPressed(KeyEvent e) {
        //another variant to go to menu
        if (e.getKeyCode() == KeyEvent.VK_ESCAPE) {
            Gamestate.state = Gamestate.MENU;
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {

    }

    private boolean isIn(MouseEvent e, PauseButton b) {
        return b.getBounds().contains(e.getX(), e.getY());
    }
}
