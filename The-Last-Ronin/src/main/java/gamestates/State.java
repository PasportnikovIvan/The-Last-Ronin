package gamestates;

import main.Game;

//State class will be the super class for all the game states
public class State {

    protected Game game;

    public State(Game game) {
        this.game = game;
    }

    public Game getGame() {
        return game;
    }
}
