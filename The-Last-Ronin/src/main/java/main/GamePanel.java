package main;

import inputs.KeyboardInputs;
import inputs.MouseInputs;

import javax.imageio.ImageIO;
import javax.swing.JPanel;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;

import static utilz.Constants.PlayerConstants.*;
import static utilz.Constants.Directions.*;

//This is one of the Main classes.
//This class is for game screen.(JPanel)
public class GamePanel extends JPanel {

    private MouseInputs mouseInputs;
    private float xDelta = 100, yDelta = 100;
    private BufferedImage img; //importing img
    private BufferedImage[][] animations;
    private int aniTick, aniIndex, aniSpeed = 15; //how fast going to animate (the lower num - the faster animation)
    private int playerAction = IDLE;
    private int playerDir = -1; //not moving = no direction
    private boolean moving = false;

    public GamePanel() {
        mouseInputs = new MouseInputs(this);

        importImg();
        loadAnimations();
        
        setPanelSize();
        addKeyListener(new KeyboardInputs(this)); //inputs
        addMouseListener(mouseInputs); //inputs
        addMouseMotionListener(mouseInputs); //inputs
    }

    //subImages from sprites
    private void loadAnimations() {
        animations = new BufferedImage[9][6];

        //making matrix for choosing subImgs
        for (int j = 0; j < animations.length; j++) {
            for (int i = 0; i < animations[j].length; i++) {
                animations[j][i] = img.getSubimage(i * 64, j*40, 64, 40);
            }
        }
    }

    private void importImg() {
        InputStream is = getClass().getResourceAsStream("/player_sprites.png");

        try {
            img = ImageIO.read(is);
        } catch (IOException e) {
            e.printStackTrace();
        } finally { //closing input stream to free up resources and avoid problems
            try {
                is.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private void setPanelSize() {
        Dimension size = new Dimension(1280, 800); //imgs are 32x32
        //solving the problem of the border
        setMinimumSize(size);
        setPreferredSize(size);
        setMaximumSize(size);
    }

    public void setDirection(int direction) {
        this.playerDir = direction;
        moving = true;
    }

    public void setMoving(boolean moving) {
        this.moving = moving;
    }

    private void updateAnimationTick() {
        aniTick++;
        if (aniTick >= aniSpeed) {
            aniTick = 0;
            aniIndex++;
            if (aniIndex >= GetSpriteAmount(playerAction)) {
                aniIndex = 0;
            }
        }
    }

    private void setAnimation() {
        if (moving) {
            playerAction = RUNNING;
        } else {
            playerAction = IDLE;
        }
    }

    //moving on the game field
    private void updatePos() {
        if (moving) {
            switch (playerDir) {
            case LEFT:
                xDelta -= 5;
                break;
            case UP:
                yDelta -= 5;
                break;
            case RIGHT:
                xDelta += 5;
                break;
            case DOWN:
                yDelta += 5;
                break;
            }
        }
    }

    //JPanel magic! method. It gets called when the game starts.
    public void paintComponent(Graphics g) {
        super.paintComponent(g); //PREwork before GamePanel
        updateAnimationTick();

        setAnimation();
        updatePos();

        g.drawImage(animations[playerAction][aniIndex], (int) xDelta, (int) yDelta, 256, 160, null);
    }
}
