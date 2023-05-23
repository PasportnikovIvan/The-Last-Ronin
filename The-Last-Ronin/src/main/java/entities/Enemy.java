package entities;

import main.Game;

import gamestates.Playing;

import java.awt.geom.Rectangle2D;

import static utilz.Constants.EnemyConstants.*;
import static utilz.Constants.*;
import static utilz.HelpMethods.*;
import static utilz.Constants.Directions.*;

//The main class that all Enemies are using.
public abstract class Enemy extends Entity {

    protected int enemyType;
    protected boolean firstUpdate = true;
    protected int walkDir = LEFT; //stating LEFT direction for enemies
    protected int tileY;
    protected float attackDistance = Game.TILES_SIZE; //one tile
    protected boolean active = true;
    protected boolean attackChecked;
    protected int attackBoxOffsetX;

    public Enemy(float x, float y, int width, int height, int enemyType) {
        super(x, y, width, height);
        this.enemyType = enemyType;
        maxHealth = GetMaxHealth(enemyType);
        currentHealth = maxHealth;
        walkSpeed = Game.SCALE * 0.35f;
    }

    protected void updateAttackBox() {
        attackBox.x = hitbox.x - attackBoxOffsetX;
        attackBox.y = hitbox.y;
    }

    protected void updateAttackBoxFlip() {
        if (walkDir == RIGHT) {
            attackBox.x = hitbox.x + hitbox.width;
        } else {
            attackBox.x = hitbox.x - attackBoxOffsetX;
        }
        attackBox.y = hitbox.y;
    }

    protected void initAttackBox(int w, int h, int attackBoxOffsetX) {
        attackBox = new Rectangle2D.Float(x, y, (int)(w * Game.SCALE), (int)(h * Game.SCALE));
        this.attackBoxOffsetX = (int)(Game.SCALE * attackBoxOffsetX);
    }

    protected void firstUpdateCheck(int[][] lvlData) {
        if (!IsEntityOnFloor(hitbox, lvlData)) {
            inAir = true;
        }
        firstUpdate = false; //first update is gone
    }

    protected void inAirChecks(int[][] lvlData, Playing playing) {
        if (state != HIT && state != DEAD) {
            updateInAir(lvlData);
            playing.getObjectManager().checkSpikesTouched(this);
            if (IsEntityInWater(hitbox, lvlData)) {
                hurt(maxHealth);
            }
        }
    }

    protected void updateInAir(int[][] lvlData) {
        if (CanMoveHere(hitbox.x, hitbox.y + airSpeed, hitbox.width, hitbox.height, lvlData)) {
            hitbox.y += airSpeed;
            airSpeed += GRAVITY;
        } else { //when falling is enough - check the ground collision
            inAir = false;
            hitbox.y = GetEntityYPosUnderRoofOrAboveFloor(hitbox, airSpeed);
            tileY = (int)(hitbox.y / Game.TILES_SIZE);
        }
    }

    protected void move(int[][] lvlData) {
        float xSpeed = 0;

        if (walkDir == LEFT) {
            xSpeed = -walkSpeed;
        } else {
            xSpeed = walkSpeed;
        }
        //check if enemy can move left or right
        if (CanMoveHere(hitbox.x + xSpeed, hitbox.y, hitbox.width, hitbox.height, lvlData)) {
            //don't want enemy to fall down
            if (IsFloor(hitbox, xSpeed, lvlData)) {
                hitbox.x += xSpeed;
                return;
            }
        }

        changeWalkDir();
    }

    protected void turnTowardsPlayer(Player player) {
        if (player.hitbox.x > hitbox.x) {
            walkDir = RIGHT;
        } else {
            walkDir = LEFT;
        }
    }

    //making enemies see the player
    protected boolean canSeePlayer(int[][] lvlData, Player player) {
        int playerTileY = (int)(player.getHitbox().y / Game.TILES_SIZE);
        if (playerTileY == tileY) { //checking that player on the same TileY (height) with the enemy
            if (isPlayerInRange(player)) { //the max range the enemy can see
                if (IsSightClear(lvlData, hitbox, player.hitbox, tileY)) { //no obstacles on the way
                    return true;
                }
            }
        }
        return false;
    }

    protected boolean isPlayerInRange(Player player) {
        int absValue = (int) Math.abs(player.hitbox.x - hitbox.x); //the actual distance between
        return absValue <= attackDistance * 5;
    }

    protected boolean isPlayerCloseForAttack(Player player) {
        int absValue = (int) Math.abs(player.hitbox.x - hitbox.x); //the actual distance between
        switch (enemyType) {
        case MOB -> {
            return absValue <= attackDistance;
        }
        case FAST_MOB -> {
            return absValue <= attackDistance * 2;
        }
        }
        return false;
    }

    public void hurt(int amount) {
        currentHealth -= amount;
        if (currentHealth <= 0) {
            newState(DEAD);
        } else {
            newState(HIT);
            if (walkDir == LEFT) {
                pushBackDir = RIGHT;
            } else {
                pushBackDir = LEFT;
            }
            pushBackOffsetDir = UP;
            pushDrawOffset = 0;
        }
    }

    //checks hitting the player
    protected void checkPlayerHit(Rectangle2D.Float attackBox, Player player) {
        if (attackBox.intersects(player.hitbox)) {
            player.changeHealth(-GetEnemyDMG(enemyType));
        } else if (enemyType == FAST_MOB) {
            return;
        }
        attackChecked = true;
    }

    protected void updateAnimationTick() {
        aniTick++;
        if (aniTick >= ANI_SPEED) {
            aniTick = 0;
            aniIndex++;
            if (aniIndex >= GetSpriteAmount(enemyType, state)) {
                if (enemyType == MOB || enemyType == FAST_MOB) {
                    aniIndex = 0;
                    switch (state) {
                    case ATTACK, HIT -> state = IDLE; //from attacking to idle starts the loop again
                    case DEAD -> active = false;
                    }
                } else if (enemyType == GOBLIN) {
                    if (state == ATTACK) {
                        aniIndex = 3;
                    } else {
                        aniIndex = 0;
                        if (state == HIT) {
                            state = IDLE;
                        } else if (state == DEAD) {
                            active = false;
                        }
                    }
                }
            }
        }
    }

    protected void changeWalkDir() {
        if (walkDir == LEFT) {
            walkDir = RIGHT;
        } else {
            walkDir = LEFT;
        }
    }

    public void resetEnemy() {
        hitbox.x = x;
        hitbox.y = y;
        firstUpdate = true;
        currentHealth = maxHealth;
        newState(IDLE);
        active = true;
        airSpeed = 0;
        pushDrawOffset = 0;
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

    public boolean isActive() {
        return active;
    }

    public float getPushDrawOffset() {
        return pushDrawOffset;
    }
}
