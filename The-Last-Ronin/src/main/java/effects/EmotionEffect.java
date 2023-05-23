package effects;

import static utilz.Constants.ANI_SPEED;
import static utilz.Constants.Emotion.*;

//Enemy Effects class for giving simple emotions to the enemies when they get to the wall or to the edge while attacking
public class EmotionEffect {

    private int x, y, type;
    private int aniIndex, aniTick;
    private boolean active = true;

    public EmotionEffect(int x, int y, int type) {
        this.x = x;
        this.y = y;
        this.type = type;
    }

    public void update() {
        aniTick++;
        if (aniTick >= ANI_SPEED) {
            aniTick = 0;
            aniIndex++;
            if (aniIndex >= GetSpriteAmount(type)) {
                active = false;
                aniIndex = 0;
            }
        }
    }

    public void deactive() {
        active = false;
    }

    public void reset(int x, int y) {
        this.x = x;
        this.y = y;
        active = true;
    }

    public int getAniIndex() {
        return aniIndex;
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

    public boolean isActive() {
        return active;
    }
}
