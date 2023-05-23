package objects;

import main.Game;

import static utilz.Constants.ObjectConstants.*;

//Container class of the objects for the boxes and barrels
public class GameContainer extends GameObject {

    public GameContainer(int x, int y, int objType) {
        super(x, y, objType);
        createHitbox();
    }

    //two different sizes of the hitboxes
    private void createHitbox() {
        if (objType == BOX) {
            initHitbox(25, 20);

            xDrawOffset = (int)(7 * Game.SCALE);
            yDrawOffset = (int)(10 * Game.SCALE);
        } else {
            initHitbox(23, 25);

            xDrawOffset = (int)(8 * Game.SCALE);
            yDrawOffset = (int)(5 * Game.SCALE);
        }

        //moving hitbox from the air to the ground, because objects are drown at the left-top corner
        hitbox.y += yDrawOffset + (int)(Game.SCALE * 2);
        hitbox.x += xDrawOffset / 2;
    }

    public void update() {
        if (doAnimation) {
            updateAnimationTick();
        }
    }
}
