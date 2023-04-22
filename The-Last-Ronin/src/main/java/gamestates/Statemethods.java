package gamestates;

import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;

//This Interface is the collection of the Methods that each class creates if they implement this Interface
//It's a great way of making sure that throughout the project whatever class that implements this Interface - must have all this methods in it
public interface Statemethods {

    public void update();

    public void draw(Graphics g);

    public void mouseClicked(MouseEvent e);

    public void mousePressed(MouseEvent e);

    public void mouseReleased(MouseEvent e);

    public void mouseMoved(MouseEvent e);

    public void keyPressed(KeyEvent e);

    public void keyReleased(KeyEvent e);
}
