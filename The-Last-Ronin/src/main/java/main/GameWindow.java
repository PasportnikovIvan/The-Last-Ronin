package main;

import javax.swing.JFrame;
import java.awt.event.WindowEvent;
import java.awt.event.WindowFocusListener;
// JFRAME - Used for making GUI (Graphics user interface)
// got Title, Border. Size can be changed, can add other components as Buttons, Textfields, etc.

//This is one of the Main classes.
//This class is for window.(JFrame)
public class GameWindow {
    private JFrame jframe;

    public GameWindow(GamePanel gamePanel) { //addind parameter of the "painting" to the "frame"

        jframe = new JFrame();

        jframe.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); //exit on close
        jframe.add(gamePanel);
        jframe.setLocationRelativeTo(null);
        jframe.setResizable(false); //to not resize window
        jframe.pack(); //to fit the preferred size
        jframe.setVisible(true); //makes the window open
        //Nothing is foolproof. Makes all boolean FALSE when window is no more in focus
        jframe.addWindowFocusListener(new WindowFocusListener() {
            @Override
            public void windowGainedFocus(WindowEvent e) {

            }

            @Override
            public void windowLostFocus(WindowEvent e) {
                gamePanel.getGame().windowFocusLost();
            }
        });
    }
}
