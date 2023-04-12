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
    private Game game;

    public GamePanel(Game game) {
        mouseInputs = new MouseInputs(this);
        this.game = game;

        setPanelSize();
        addKeyListener(new KeyboardInputs(this)); //inputs
        addMouseListener(mouseInputs); //inputs
        addMouseMotionListener(mouseInputs); //inputs
    }

    private void setPanelSize() {
        Dimension size = new Dimension(1280, 800); //imgs are 32x32
        //solving the problem of the border
        setPreferredSize(size);
    }

    public void updateGame() {

    }

    //JPanel magic! method. It gets called when the game starts.
    public void paintComponent(Graphics g) {
        super.paintComponent(g); //PREwork before GamePanel
        game.render(g);
    }

    public Game getGame() {
        return game;
    }
}
