package entities;

import audio.AudioPlayer;
import main.Game;

import static utilz.Constants.Directions.*;

import static utilz.Constants.EnemyConstants.*;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.geom.Rectangle2D;

//The class of the actual enemy Mob
public class Mob extends Enemy {

    private int attackBoxOffsetX;

    public Mob(float x, float y) {
        super(x, y, MOB_WIDTH, MOB_HEIGHT, MOB);
        initHitbox(22,29);
        initAttackBox();
    }

    private void initAttackBox() {
        attackBox = new Rectangle2D.Float(x, y, (int)(22 * Game.SCALE), (int)(20 * Game.SCALE));
        attackBoxOffsetX = (int)(Game.SCALE * 22);
    }

    //updates Enemies
    public void update(int[][] lvlData, Player player) {
        updateBehavior(lvlData, player);
        updateAnimationTick();
        updateAttackBox();
    }

    private void updateAttackBox() {
        if (walkDir == LEFT) {
            attackBox.x = hitbox.x - attackBoxOffsetX;
        } else {
            attackBox.x = hitbox.x + attackBoxOffsetX;
        }
        attackBox.y = hitbox.y;
    }

    //this method is going to calculate how the Enemy be able to move around and patrol
    private void updateBehavior(int[][] lvlData, Player player) {
        //mob spawn is a little bit in the air
        if (firstUpdate) {
            firstUpdateCheck(lvlData);
        }
        if (inAir) { //falling down
            updateInAir(lvlData);
        } else { //patrol
            switch (state) {
            case IDLE:
                newState(RUNNING);
                break;
            case RUNNING:
                if (canSeePlayer(lvlData, player)) {
                    turnTowardsPlayer(player);
                    if (isPlayerCloseForAttack(player)) {
                        newState(ATTACK);
                    }
                }

                move(lvlData);
                break;
            case ATTACK:
                if (aniIndex == 0){
                    attackChecked = false;
                }
                if (aniIndex == 2 && !attackChecked) {
                    checkPlayerHit(attackBox, player);
                }
                break;
            case HIT:
                break;
            }
        }
    }

    public int flipX() {
        if (walkDir == RIGHT) {
            return 0;
        } else {
            return width;
        }
    }

    public int flipW() {
        if (walkDir == RIGHT) {
            return 1;
        } else {
            return -1;
        }
    }
}
