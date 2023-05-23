package entities;

import gamestates.Playing;

import static utilz.Constants.EnemyConstants.*;
import static utilz.Constants.Emotion.*;
import static utilz.HelpMethods.CanMoveHere;
import static utilz.HelpMethods.IsFloor;
import static utilz.Constants.Directions.*;

//The class of the actual enemy Goblin
public class Goblin extends Enemy {

    private boolean preRoll = true;
    private int tickSinceLastDmgToPlayer;
    private int tickAfterRollInIdle;
    private int rollDurationTick, rollDuration = 300;

    public Goblin(float x, float y) {
        super(x, y, GOBLIN_WIDTH, GOBLIN_HEIGHT, GOBLIN);
        initHitbox(24, 31);
    }

    //updates Enemies
    public void update(int[][] lvlData, Playing playing) {
        updateBehavior(lvlData, playing);
        updateAnimationTick();
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
                preRoll = true;
                if (tickAfterRollInIdle >= 120) {
                    if (IsFloor(hitbox, lvlData)) {
                        newState(RUNNING);
                    } else {
                        inAir = true;
                    }
                    tickAfterRollInIdle = 0;
                    tickSinceLastDmgToPlayer = 60;
                } else {
                    tickAfterRollInIdle++;
                }
                break;
            case RUNNING:
                if (canSeePlayer(lvlData, playing.getPlayer())) {
                    newState(ATTACK);
                    setWalkDir(playing.getPlayer());
                }

                move(lvlData, playing);
                break;
            case ATTACK:
                if (preRoll) {
                    if (aniIndex >= 3)
                        preRoll = false;
                } else {
                    move(lvlData, playing);
                    checkDmgToPlayer(playing.getPlayer());
                    checkRollOver(playing);
                }
                break;
            case HIT:
                if (aniIndex <= GetSpriteAmount(enemyType, state) - 2) {
                    pushBack(pushBackDir, lvlData, 2f);
                }
                updatePushBackDrawOffset();
                tickAfterRollInIdle = 120;
                break;
            }
        }
    }

    private void checkDmgToPlayer(Player player) {
        if (hitbox.intersects(player.getHitbox())) {
            if (tickSinceLastDmgToPlayer >= 60) {
                tickSinceLastDmgToPlayer = 0;
                player.changeHealth(-GetEnemyDMG(enemyType), this);
            } else {
                tickSinceLastDmgToPlayer++;
            }
        }
    }

    private void setWalkDir(Player player) {
        if (player.getHitbox().x > hitbox.x) {
            walkDir = RIGHT;
        } else {
            walkDir = LEFT;
        }
    }

    protected void move(int[][] lvlData, Playing playing) {
        float xSpeed = 0;

        if (walkDir == LEFT) {
            xSpeed = -walkSpeed;
        } else {
            xSpeed = walkSpeed;
        }
        if (state == ATTACK) {
            xSpeed *= 2;
        }
        //check if enemy can move left or right
        if (CanMoveHere(hitbox.x + xSpeed, hitbox.y, hitbox.width, hitbox.height, lvlData)) {
            //don't want enemy to fall down
            if (IsFloor(hitbox, xSpeed, lvlData)) {
                hitbox.x += xSpeed;
                return;
            }
        }

        if (state == ATTACK) {
            rollOver(playing);
            rollDurationTick = 0;
        }

        changeWalkDir();

    }

    private void checkRollOver(Playing playing) {
        rollDurationTick++;
        if (rollDurationTick >= rollDuration) {
            rollOver(playing);
            rollDurationTick = 0;
        }
    }

    private void rollOver(Playing playing) {
        newState(IDLE);
        playing.addEmotion((int) hitbox.x, (int) hitbox.y, QUESTION);
    }
}
