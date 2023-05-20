package gamestates;

import audio.AudioPlayer;
import main.Game;
import ui.MenuButton;

import java.awt.event.MouseEvent;

//State class will be the super class for all the game states
public class State {

    protected Game game;

    public State(Game game) {
        this.game = game;
    }

    //check if pressing inside the button
    public boolean isIn(MouseEvent e, MenuButton mb) {
        return mb.getBounds().contains(e.getX(), e.getY());
    }

    public Game getGame() {
        return game;
    }

    public void setGamestate(Gamestate state) {
        switch (state) {
        case MENU -> game.getAudioPlayer().playSong(AudioPlayer.MENU_1);
        case PLAYING -> game.getAudioPlayer().setLevelSong(game.getPlaying().getLevelManager().getLvlIndex());
        }

        Gamestate.state = state;
    }
}
