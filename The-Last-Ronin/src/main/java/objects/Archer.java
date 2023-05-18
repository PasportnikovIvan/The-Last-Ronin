package objects;

import main.Game;

//Archer class in objects of the archer in the box like static enemy
public class Archer extends GameObject {

    private int tileY; //checking the player on same tile for attack

    public Archer(int x, int y, int objType) {
        super(x, y, objType);
        tileY = y / Game.TILES_SIZE;
        initHitbox(40, 35);
        //moving to the center and placing on the ground
        hitbox.x -= (int)(4 * Game.SCALE);
        hitbox.y -= (int)(3 * Game.SCALE);
    }

    public void update() {
        if (doAnimation) { //doing animation if only archer sees the player
            updateAnimationTick();
        }
    }

    public int getTileY() {
        return tileY;
    }
}
