package objects;

//the Objects class of the drawn grass in the game level for the beauty of the gameplay
public class Grass {

    private int x, y, type;

    public Grass(int x, int y, int type) {
        this.x = x;
        this.y = y;
        this.type = type;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public int getType() {
        return type;
    }
}
