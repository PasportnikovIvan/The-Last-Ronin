package ui;

import utilz.LoadSave;
import static utilz.Constants.UI.PauseButtons.*;

import java.awt.Graphics;
import java.awt.image.BufferedImage;

//The ui class of the Sound Buttons of the Pause menu
public class SoundButton extends PauseButton{

    private BufferedImage[][] soundImgs; //2d array (buttonArray 3x2)
    private boolean mouseOver, mousePressed;
    private boolean muted; //to determine if sound unMuted - uses the first row of the sprites, else the second row
    private int rowIndex, colIndex;

    public SoundButton(int x, int y, int width, int height) {
        super(x, y, width, height);

        loadSoundImgs();
    }

    private void loadSoundImgs() {
        //need to temporary store the ButtonAtlas
        BufferedImage temp = LoadSave.GetSpriteAtlas(LoadSave.SOUND_BUTTONS);
        soundImgs = new BufferedImage[2][3];
        for (int j = 0; j < soundImgs.length; j++) {
            for (int i = 0; i < soundImgs[j].length; i++) {
                soundImgs[j][i] = temp.getSubimage(i * SOUND_SIZE_DEFAULT, j * SOUND_SIZE_DEFAULT, SOUND_SIZE_DEFAULT, SOUND_SIZE_DEFAULT);
            }
        }
    }

    public void update() {
        if (muted) {
            rowIndex = 1;
        } else {
            rowIndex = 0;
        }

        colIndex = 0;
        if (mouseOver) {
            colIndex = 1;
        }
        if (mousePressed) {
            colIndex = 2;
        }
    }

    //have to call reset booleans when release the button
    public void resetBools() {
        mouseOver = false;
        mousePressed = false;
    }

    public void draw(Graphics g) {
        g.drawImage(soundImgs[rowIndex][colIndex], x, y, width, height, null);
    }

    public boolean isMouseOver() {
        return mouseOver;
    }

    public void setMouseOver(boolean mouseOver) {
        this.mouseOver = mouseOver;
    }

    public boolean isMousePressed() {
        return mousePressed;
    }

    public void setMousePressed(boolean mousePressed) {
        this.mousePressed = mousePressed;
    }

    public boolean isMuted() {
        return muted;
    }

    public void setMuted(boolean muted) {
        this.muted = muted;
    }
}
