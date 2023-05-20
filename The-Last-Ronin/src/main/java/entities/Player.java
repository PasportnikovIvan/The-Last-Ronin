package entities;

import audio.AudioPlayer;
import gamestates.Playing;
import main.Game;
import utilz.LoadSave;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;

import static utilz.Constants.*;
import static utilz.Constants.PlayerConstants.*;
import static utilz.HelpMethods.*;

//This is Player class.
public class Player extends Entity {

    private BufferedImage[][] animations;
    private boolean moving = false, attacking = false;
    private boolean left, right, jump; //Moving With Booleans
    private int[][] lvlData;
    private float xDrawOffset = 22 * Game.SCALE;
    private float yDrawOffset = 6 * Game.SCALE;

    //Jumping / Gravity
    private float jumpSpeed = -2.25f * Game.SCALE;
    private float fallSpeedAfterCollision = 0.5f * Game.SCALE; //in case hitting the roof

    //StatusBarUI
    private BufferedImage statusBarImg;

    private int statusBarWidth = (int)(192 * Game.SCALE);
    private int statusBarHeight = (int)(58 * Game.SCALE);
    private int statusBarX = (int)(10 * Game.SCALE);
    private int statusBarY = (int)(10 * Game.SCALE);

    private int healthBarWidth = (int)(150 * Game.SCALE);
    private int healthBarHeight = (int)(4 * Game.SCALE);
    private int healthBarXStart = (int)(34 * Game.SCALE);
    private int healthBarYStart = (int)(14 * Game.SCALE);

    private int healthWidth = healthBarWidth;

    private int powerBarWidth = (int)(104 * Game.SCALE);
    private int powerBarHeight = (int)(2 * Game.SCALE);
    private int powerBarXStart = (int)(44 * Game.SCALE);
    private int powerBarYStart = (int)(34 * Game.SCALE);
    private int powerWidth = powerBarWidth;
    private int powerMaxValue = 200;
    private int powerValue = powerMaxValue;

    //variables for flipping the sprite
    private int flipX = 0;
    private int flipW = 1;

    private boolean attackChecked;
    private Playing playing;

    private int tileY = 0;

    //dash attack
    private boolean powerAttackActive;
    private int powerAttackTick;
    private int powerGrowSpeed = 15;
    private int powerGrowTick;

    public Player(float x, float y, int width, int height, Playing playing) {
        super(x, y, width, height);
        this.playing = playing;
        this.state = IDLE;
        this.maxHealth = 100;
        this.currentHealth = maxHealth;
        this.walkSpeed = 1.0f * Game.SCALE;
        loadAnimations();
        initHitbox(20, 29);
        initAttackBox();
    }

    public void setSpawn(Point spawn) {
        this.x = spawn.x;
        this.y = spawn.y;
        hitbox.x = x;
        hitbox.y = y;
    }

    private void initAttackBox() {
        attackBox = new Rectangle2D.Float(x, y, (int)(25 * Game.SCALE), (int)(20 * Game.SCALE));
    }

    //updates Player
    public void update() {
        updateHealthBar();
        updatePowerBar();

        if (currentHealth <= 0) { //Game over
            if (state != DEAD) { //Player started dying
                state = DEAD;
                aniTick = 0;
                aniIndex = 0;
                playing.setPlayerDying(true);
                playing.getGame().getAudioPlayer().playEffect(AudioPlayer.DIE);
            } else if (aniIndex == GetSpriteAmount(DEAD) - 1 && aniTick >= ANI_SPEED - 1) { //player is completely dead and the game is over
                playing.setGameOver(true);
                playing.getGame().getAudioPlayer().stopSong();
                playing.getGame().getAudioPlayer().playEffect(AudioPlayer.GAMEOVER);
            } else { //player is still dying
                updateAnimationTick();
            }
            return;
        }

        updateAttackBox();

        updatePos();
        if (moving) {
            checkPotionTouched();
            checkSpikesTouched();
            tileY = (int)(hitbox.y / Game.TILES_SIZE);
            if (powerAttackActive) { //still moving in the dash attack even if stayed still
                powerAttackTick++;
                if (powerAttackTick >= 35) { //length of the dash attack
                    powerAttackTick = 0;
                    powerAttackActive = false;
                }
            }
        }
        if (attacking || powerAttackActive) {
            checkAttack();
        }
        updateAnimationTick();
        setAnimation();
    }

    private void checkSpikesTouched() {
        playing.checkSpikesTouched(this);
    }

    private void checkPotionTouched() {
        playing.checkPotionTouched(hitbox);
    }

    private void checkAttack() {
        if (attackChecked || aniIndex != 1) {
            return;
        }
        attackChecked = true;
        if (powerAttackActive) { //every update checking the dash attack
            attackChecked = false;
        }
        playing.checkEnemyHit(attackBox);
        playing.checkObjectHit(attackBox);
        playing.getGame().getAudioPlayer().playAttackSound();
    }

    private void updateAttackBox() {
        if (right || (powerAttackActive && flipW == 1)) {
            attackBox.x = hitbox.x + hitbox.width + (int)(Game.SCALE * 2);
        } else if (left || (powerAttackActive && flipW == -1)) {
            attackBox.x = hitbox.x - hitbox.width - (int)(Game.SCALE * 2);
        }

        attackBox.y = hitbox.y + (Game.SCALE * 10);
    }

    private void updateHealthBar() {
        healthWidth = (int)((currentHealth / (float)maxHealth) * healthBarWidth);
    }

    private void updatePowerBar() {
        powerWidth = (int)((powerValue / (float)powerMaxValue) * powerBarWidth);

        powerGrowTick++;
        if (powerGrowTick >= powerGrowSpeed) {
            powerGrowTick = 0;
            changePower(1);
        }
    }

    //renders Player
    public void render(Graphics g, int lvlOffset) {
        g.drawImage(animations[state][aniIndex], (int)(hitbox.x - xDrawOffset) - lvlOffset + flipX, (int)(hitbox.y - yDrawOffset), width * flipW, height, null);
//        drawHitbox(g, lvlOffset);
//        drawAttackBox(g, lvlOffset);
        drawUI(g);
    }

    private void drawUI(Graphics g) {
        //Background UI
        g.drawImage(statusBarImg, statusBarX, statusBarY, statusBarWidth, statusBarHeight, null);

        //Health bar
        g.setColor(Color.red);
        g.fillRect(healthBarXStart + statusBarX, healthBarYStart + statusBarY, healthWidth, healthBarHeight);

        //Power bar
        g.setColor(Color.yellow);
        g.fillRect(powerBarXStart + statusBarX, powerBarYStart + statusBarY, powerWidth, powerBarHeight);
    }

    private void updateAnimationTick() {
        aniTick++;
        if (aniTick >= ANI_SPEED) {
            aniTick = 0;
            aniIndex++;
            if (aniIndex >= GetSpriteAmount(state)) {
                aniIndex = 0;
                attacking = false;
                attackChecked = false;
            }
        }
    }

    private void setAnimation() {
        int startAni = state;

        if (moving) {
            state = RUNNING;
        } else {
            state = IDLE;
        }

        if (inAir) {
            if (airSpeed < 0) {
                state = JUMP;
            } else {
                state = FALLING;
            }
        }

        //keeping the same animation index throughout the entire attack
        if (powerAttackActive) {
            state = ATTACK_JUMP_2;
            aniIndex = 1;
            aniTick = 0;
            return;
        }

        //solving running and attacking = attacking animation
        if (attacking) {
            state = ATTACK_1;
            if (startAni != ATTACK_1) { //creating faster returning attack animation
                aniIndex = 1;
                aniTick = 0;
                return;
            }
        }

        if (startAni != state) {
            resetAniTick();
        }
    }

    private void resetAniTick() {
        aniTick = 0;
        aniIndex = 0;
    }

    //moving on the game field
    //solving counter-direction
    private void updatePos() {
        moving = false;

        if (jump) {
            jump();
        }

        //if staying still or holding left and right at the same time
        if (!inAir) {
            if (!powerAttackActive) { //moving in the dash attack
                if ((!left && !right) || (left && right)) {
                    return;
                }
            }
        }

        //tmp storage of the x Speed
        float xSpeed = 0;

        if (left) {
            xSpeed -= walkSpeed;
            flipX = width;
            flipW = -1;
        }
        if (right) {
            xSpeed += walkSpeed;
            flipX = 0;
            flipW = 1;
        }

        if (powerAttackActive) {
            if (!left && !right) {
                //x depending on direction
                if (flipW == -1) { //facing left
                    xSpeed = -walkSpeed;
                } else { //facing right
                    xSpeed = walkSpeed;
                }
            }

            xSpeed *= 3;
        }

        if (!inAir) {
            if (!IsEntityOnFloor(hitbox, lvlData)) {
                inAir = true;
            }
        }

        //not updating Y pos in dash attack
        if (inAir && !powerAttackActive) {
            if (CanMoveHere(hitbox.x, hitbox.y + airSpeed, hitbox.width, hitbox.height, lvlData)) {
                hitbox.y += airSpeed;
                airSpeed += GRAVITY;
                updateXPos(xSpeed);
            } else {
                hitbox.y = GetEntityYPosUnderRoofOrAboveFloor(hitbox, airSpeed);
                if (airSpeed > 0) {
                    resetInAir();
                } else {
                    airSpeed = fallSpeedAfterCollision;
                }
                updateXPos(xSpeed);
            }
        } else {
            updateXPos(xSpeed);
        }

        moving = true;
    }

    private void jump() {
        if (inAir) {
            return;
        }
        playing.getGame().getAudioPlayer().playEffect(AudioPlayer.JUMP);
        inAir = true;
        airSpeed = jumpSpeed;
    }

    private void resetInAir() {
        inAir = false;
        airSpeed = 0;
    }

    private void updateXPos(float xSpeed) {
        if (CanMoveHere(hitbox.x + xSpeed, hitbox.y, hitbox.width, hitbox.height, lvlData)) {
            hitbox.x += xSpeed;
        } else { //hitting the wall
            hitbox.x = GetEntityXPosNextToWall(hitbox, xSpeed);
            if (powerAttackActive) {
                powerAttackActive = false;
                powerAttackTick = 0;
            }
        }
    }

    public void changeHealth(int value) {
        currentHealth += value;
//        playing.getGame().getAudioPlayer().playEffect(AudioPlayer.ENEMY_ATTACK); //enemy sound of attack

        //making sure not going under 0 or higher the maximum
        if (currentHealth <= 0) {
            currentHealth = 0;
        } else if (currentHealth >= maxHealth) {
            currentHealth = maxHealth;
        }
    }

    //the Player is killed
    public void kill() {
        currentHealth = 0;
    }

    public void changePower(int value) {
        powerValue += value;
        if (powerValue >= powerMaxValue) {
            powerValue = powerMaxValue;
        } else if (powerValue <= 0) {
            powerValue = 0;
        }
    }

    //subImages from sprites
    private void loadAnimations() {
        //getting Player sprites from sprites
        BufferedImage img = LoadSave.GetSpriteAtlas(LoadSave.PLAYER_ATLAS);

        animations = new BufferedImage[9][6];

        //making matrix for choosing subImgs
        for (int j = 0; j < animations.length; j++) {
            for (int i = 0; i < animations[j].length; i++) {
                animations[j][i] = img.getSubimage(i * 64, j * 40, 64, 40);
            }
        }

        statusBarImg = LoadSave.GetSpriteAtlas(LoadSave.STATUS_BAR);
    }

    public void loadLvlData(int[][] lvlData) {
        this.lvlData = lvlData;
        if (!IsEntityOnFloor(hitbox, lvlData)) {
            inAir = true;
        }
    }

    //Bug with unfocused window is fixed!!!
    public void resetDirBooleans() {
        left = false;
        right = false;
    }

    public void setAttacking(boolean attacking) {
        this.attacking = attacking;
    }

    public boolean isLeft() {
        return left;
    }

    public void setLeft(boolean left) {
        this.left = left;
    }

    public boolean isRight() {
        return right;
    }

    public void setRight(boolean right) {
        this.right = right;
    }

    public void setJump(boolean jump) {
        this.jump = jump;
    }

    public void resetAll() {
        resetDirBooleans();
        inAir = false;
        attacking = false;
        moving = false;
        state = IDLE;
        currentHealth = maxHealth;

        //reset the position
        hitbox.x = x;
        hitbox.y = y;

        if (!IsEntityOnFloor(hitbox, lvlData)) {
            inAir = true;
        }
    }

    public int getTileY() {
        return tileY;
    }

    public void powerAttack() {
        if (powerAttackActive) { //already attacking
            return;
        }
        if (powerValue >= 60) {
            powerAttackActive = true;
            changePower(-60);
        }
    }
}
