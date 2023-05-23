package entities;

import gamestates.Playing;

import static utilz.Constants.EnemyConstants.*;
import static utilz.HelpMethods.IsFloor;
import static utilz.Constants.Emotion.*;

//The class of the actual enemy Mob
public class Mob extends Enemy {

    public Mob(float x, float y) {
        super(x, y, MOB_WIDTH, MOB_HEIGHT, MOB);
        initHitbox(22,29);
        initAttackBox(22, 20, 22);
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

                if (inAir) {
                    playing.addEmotion((int)hitbox.x, (int)hitbox.y, XCROSS);
                }
                break;
            case ATTACK:
                if (aniIndex == 0) {
                    attackChecked = false;
                }
                if (aniIndex == 2 && !attackChecked) {
                    checkPlayerHit(attackBox, playing.getPlayer());
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
}
