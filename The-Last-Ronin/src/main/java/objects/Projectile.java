package objects;

import main.Game;

import static utilz.Constants.Projectiles.*;

import java.awt.geom.Rectangle2D;

//Projectile class in objects of the arrow
public class Projectile {

    private Rectangle2D.Float hitbox;
    private int dir; //speed * direction for the direction of the arrow
    private boolean active = true;

    public Projectile(int x, int y, int dir) {
        int xOffset = (int)(-3 * Game.SCALE);
        int yOffset = (int)(8 * Game.SCALE);

        //fixing the spawn X position of the arrow
        if (dir == 1) {
            xOffset = (int)(29 * Game.SCALE);
        }

        hitbox = new Rectangle2D.Float(x + xOffset, y + yOffset, ARROW_WIDTH, ARROW_HEIGHT);
        this.dir = dir;
    }

    public int getDir() {
        return dir;
    }

    public void updatePos() {
        hitbox.x += dir * SPEED;
    }

    public void setPos(int x, int y) {
        hitbox.x = x;
        hitbox.y = y;
    }

    public Rectangle2D.Float getHitbox() {
        return hitbox;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    public boolean isActive() {
        return active;
    }
}
