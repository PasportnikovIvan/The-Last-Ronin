package entities;

import gamestates.Playing;

import static utilz.Constants.Emotion.*;
import static utilz.Constants.Directions.LEFT;
import static utilz.Constants.EnemyConstants.*;
import static utilz.HelpMethods.CanMoveHere;
import static utilz.HelpMethods.IsFloor;

//The class of the actual enemy Fast Mob
public class FastMob extends Enemy {

    public FastMob(float x, float y) {
        super(x, y, FAST_MOB_WIDTH, FAST_MOB_HEIGHT, FAST_MOB);
        initHitbox(18, 31);
        initAttackBox(20, 20, 20);
    }

    //updates Enemies
    public void update(int[][] lvlData, Playing playing) {
        updateBehavior(lvlData, playing);
        updateAnimationTick();
        updateAttackBoxFlip();
    }

    //this method is going to calculate how the Enemy be able to move around and patrol
    private void updateBehavior(int[][] lvlData, Playing playing) {
        //mob spawn is a little bit in the air
        if (firstUpdate) {
            firstUpdateCheck(lvlData);
        }
        if (inAir) { //falling down
            inAirChecks(lvlData, playing);
        } else { //patrol
            switch (state) {
            case IDLE:
                if (IsFloor(hitbox, lvlData)) {
                    newState(RUNNING);
                } else {
                    inAir = true;
                }
                break;
            case RUNNING:
                if (canSeePlayer(lvlData, playing.getPlayer())) {
                    turnTowardsPlayer(playing.getPlayer());
                    if (isPlayerCloseForAttack(playing.getPlayer())) {
                        newState(ATTACK);
                    }
                }

                move(lvlData);
                break;
            case ATTACK:
                if (aniIndex == 0) {
                    attackChecked = false;
                } else if (aniIndex == 3) {
                    if (!attackChecked) {
                        checkPlayerHit(attackBox, playing.getPlayer());
                    }
                    attackMove(lvlData, playing);
                }
                break;
            case HIT:
                if (aniIndex <= GetSpriteAmount(enemyType, state) - 2) {
                    pushBack(pushBackDir, lvlData, 2f);
                }
                updatePushBackDrawOffset();
                break;
            }
        }
    }

    protected void attackMove(int[][] lvlData, Playing playing) {
        float xSpeed = 0;

        if (walkDir == LEFT) {
            xSpeed = -walkSpeed;
        } else {
            xSpeed = walkSpeed;
        }

        if (CanMoveHere(hitbox.x + xSpeed * 4, hitbox.y, hitbox.width, hitbox.height, lvlData)) {
            if (IsFloor(hitbox, xSpeed * 4, lvlData)) {
                hitbox.x += xSpeed * 4;
                return;
            }
        }
        newState(IDLE);
        playing.addEmotion((int) hitbox.x, (int) hitbox.y, XCROSS);
    }
}
