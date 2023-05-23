package objects;

import audio.AudioPlayer;
import entities.Enemy;
import entities.Player;
import gamestates.Playing;
import levels.Level;
import main.Game;
import utilz.LoadSave;
import static utilz.Constants.ObjectConstants.*;
import static utilz.HelpMethods.CanArcherSeePlayer;
import static utilz.HelpMethods.IsProjectileHittingLevel;
import static utilz.Constants.Projectiles.*;

import java.awt.Graphics;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

//Object handler class
public class ObjectManager {

    private Playing playing;
    private BufferedImage[][] potionImgs, containerImgs, bambooImgs;
    private BufferedImage[] archerImgs, grassImgs;
    private BufferedImage spikeImg, arrowImg;
    private ArrayList<Potion> potions;
    private ArrayList<GameContainer> containers;
    private ArrayList<Projectile> projectiles = new ArrayList<>(); //not using the Level for the projectiles. Every time archer shoot - arrow adds to array

    private Level currentLevel;

    public ObjectManager(Playing playing) {
        this.playing = playing;
        currentLevel = playing.getLevelManager().getCurrentLevel();
        loadImgs();
    }

    //touched by player
    public void checkSpikesTouched(Player p) {
        for (Spike s : currentLevel.getSpikes()) {
            if (s.getHitbox().intersects(p.getHitbox())) {
                p.kill();
            }
        }
    }

    //touched by enemy
    public void checkSpikesTouched(Enemy e) {
        for (Spike s : currentLevel.getSpikes()) {
            if (s.getHitbox().intersects(e.getHitbox())) {
                e.hurt(200);
            }
        }
    }

    //if player's hitbox is overlapped the object
    public void checkObjectTouched(Rectangle2D.Float hitbox) {
        for (Potion p : potions) {
            if (p.isActive()) {
                if (hitbox.intersects(p.getHitbox())) {
                    p.setActive(false);
                    applyEffectToPlayer(p);
                }
            }
        }
    }

    public void applyEffectToPlayer(Potion p) {
        if (p.getObjType() == RED_POTION) {
            playing.getPlayer().changeHealth(RED_POTION_VALUE);
        } else {
            playing.getPlayer().changePower(BLUE_POTION_VALUE);
        }
    }

    //checking the attack of the player hitting the containers
    public void checkObjectHit(Rectangle2D.Float attackbox) {
        for (GameContainer gc : containers) {
            if (gc.isActive() && !gc.doAnimation) {
                if (gc.getHitbox().intersects(attackbox)) {
                    gc.setAnimation(true);
                    playing.getGame().getAudioPlayer().playEffect(AudioPlayer.BOX_BREAK); //breaking sound
                    int type = 0;
                    if (gc.getObjType() == BARREL) {
                        type = 1;
                    }
                    potions.add(new Potion((int)(gc.getHitbox().x + gc.getHitbox().width / 2), (int)(gc.getHitbox().y - gc.getHitbox().height / 2), type));
                    return;
                }
            }
        }
    }

    public void loadObjects(Level newLevel) {
        currentLevel = newLevel;
        potions = new ArrayList<>(newLevel.getPotions());
        containers = new ArrayList<>(newLevel.getContainers());
        projectiles.clear();
    }

    private void loadImgs() {
        BufferedImage potionSprite = LoadSave.GetSpriteAtlas(LoadSave.POTION_ATLAS);
        potionImgs = new BufferedImage[2][7];

        for (int j = 0; j < potionImgs.length; j++) {
            for (int i = 0; i < potionImgs[j].length; i++) {
                potionImgs[j][i] = potionSprite.getSubimage(12 * i, 16 * j, 12, 16);
            }
        }

        BufferedImage containerSprite = LoadSave.GetSpriteAtlas(LoadSave.CONTAINER_ATLAS);
        containerImgs = new BufferedImage[2][8];

        for (int j = 0; j < containerImgs.length; j++) {
            for (int i = 0; i < containerImgs[j].length; i++) {
                containerImgs[j][i] = containerSprite.getSubimage(40 * i, 30 * j, 40, 30);
            }
        }

        spikeImg = LoadSave.GetSpriteAtlas(LoadSave.TRAP_ATLAS);

        BufferedImage archerSprite = LoadSave.GetSpriteAtlas(LoadSave.ARCHER_ATLAS);
        archerImgs = new BufferedImage[7];

        for (int i = 0; i < archerImgs.length; i++) {
            archerImgs[i] = archerSprite.getSubimage( 40 * i, 0, 40, 35);
        }

        arrowImg = LoadSave.GetSpriteAtlas(LoadSave.ARROW);

        BufferedImage bambooOneImg = LoadSave.GetSpriteAtlas(LoadSave.BAMBOO_ONE_ATLAS);
        bambooImgs = new BufferedImage[2][4];

        for (int i = 0; i < 4; i++) {
            bambooImgs[0][i] = bambooOneImg.getSubimage(i * 39, 0, 39, 92);
        }

        BufferedImage bambooTwoImg = LoadSave.GetSpriteAtlas(LoadSave.BAMBOO_TWO_ATLAS);

        for (int i = 0; i < 4; i++) {
            bambooImgs[1][i] = bambooTwoImg.getSubimage(i * 62, 0, 62, 54);
        }

        BufferedImage grassTemp = LoadSave.GetSpriteAtlas(LoadSave.GRASS_ATLAS);
        grassImgs = new BufferedImage[2];

        for (int i = 0; i < grassImgs.length; i++) {
            grassImgs[i] = grassTemp.getSubimage(32 * i, 0, 32, 32);
        }
    }

    public void update(int[][] lvlData, Player player) {
        updateBackgroundBamboos();
        for (Potion p : potions) {
            if (p.isActive()) {
                p.update();
            }
        }

        for (GameContainer gc : containers) {
            if (gc.isActive()) {
                gc.update();
            }
        }

        updateArchers(lvlData, player);
        updateProjectiles(lvlData, player);
    }

    private void updateBackgroundBamboos() {
        for (BackgroundBamboo bb : currentLevel.getBamboos())
            bb.update();
    }

    private void updateProjectiles(int[][] lvlData, Player player) {
        for (Projectile p : projectiles) {
            if (p.isActive()) {
                p.updatePos();
                if (p.getHitbox().intersects(player.getHitbox())) {
                    player.changeHealth(-25);
                    p.setActive(false); //collision of the projectiles with player
                } else if (IsProjectileHittingLevel(p, lvlData)) { //collision of the projectiles with level
                    p.setActive(false);
                }
            }
        }
    }

    private boolean isPlayerInRange(Archer a, Player player) {
        int absValue = (int) Math.abs(player.getHitbox().x - a.getHitbox().x);
        return absValue <= Game.TILES_SIZE * 5;
    }

    private boolean isPlayerInfrontOfArcher(Archer a, Player player) {
        if (a.getObjType() == ARCHER_LEFT) {
            if (a.getHitbox().x > player.getHitbox().x) {
                return true;
            }
        } else if (a.getHitbox().x < player.getHitbox().x) {
            return true;
        }
        return false;
    }

    private void updateArchers(int[][] lvlData, Player player) {
        for (Archer a : currentLevel.getArchers()) {
            if (!a.doAnimation) { //if the archer is not animating
                if (a.getTileY() == player.getTileY()) { //tileY is the same
                    if (isPlayerInRange(a, player)) { //is player in range
                        if (isPlayerInfrontOfArcher(a, player)) { //is player infront of archer
                            if (CanArcherSeePlayer(lvlData, player.getHitbox(), a.getHitbox(), a.getTileY())) { //checking line of sight
                                a.setAnimation(true);
                            }
                        }
                    }
                }
            }
            a.update();
            //timing of the shooting
            if (a.getAniIndex() == 4 && a.getAniTick() == 0) {
                shootArcher(a);
                playing.getGame().getAudioPlayer().playEffect(AudioPlayer.ARROW_WHIZZ);
            }
        }
    }

    private void shootArcher(Archer a) {
        int dir = 1;
        if (a.getObjType() == ARCHER_LEFT) {
            dir = -1;
        }
        projectiles.add(new Projectile((int)(a.getHitbox().x), (int)(a.getHitbox().y), dir));
    }

    public void draw(Graphics g, int xLvlOffset) {
        drawPotions(g, xLvlOffset);
        drawContainers(g, xLvlOffset);
        drawTraps(g, xLvlOffset);
        drawArchers(g, xLvlOffset);
        drawProjectiles(g, xLvlOffset);
    }

    public void drawGrass(Graphics g, int xLvlOffset) {
        for (Grass grass : currentLevel.getGrass()) {
            g.drawImage(grassImgs[grass.getType()], grass.getX() - xLvlOffset, grass.getY() + 1, (int)(32 * Game.SCALE), (int)(32 * Game.SCALE), null);
        }
    }

    public void drawBackgroundBamboo(Graphics g, int xLvlOffset) {
        for (BackgroundBamboo bb : currentLevel.getBamboos()) {
            int type = bb.getType();
            if (type == 9) {
                type = 8;
            }
            g.drawImage(bambooImgs[type - 7][bb.getAniIndex()], bb.getX() - xLvlOffset + GetBambooOffsetX(bb.getType()), (int)(bb.getY() + GetBambooOffsetY(bb.getType())), GetBambooWidth(bb.getType()), GetBambooHeight(bb.getType()), null);
        }
    }

    private void drawProjectiles(Graphics g, int xLvlOffset) {
        for (Projectile p : projectiles) {
            if (p.isActive()) {
                if (p.getDir() == -1) {
                    g.drawImage(arrowImg, (int)(p.getHitbox().x - xLvlOffset), (int)(p.getHitbox().y), ARROW_WIDTH, ARROW_HEIGHT, null);
                } else {
                    g.drawImage(arrowImg, (int)(p.getHitbox().x - xLvlOffset), (int)(p.getHitbox().y), -ARROW_WIDTH, ARROW_HEIGHT, null);
                }
            }
        }
    }

    private void drawArchers(Graphics g, int xLvlOffset) {
        for (Archer a : currentLevel.getArchers()) {

            int x = (int)(a.getHitbox().x - xLvlOffset);
            int width = ARCHER_WIDTH;
            //flipping archers
            if (a.getObjType() == ARCHER_RIGHT) {
                x += width;
                width *= -1;
            }
            g.drawImage(archerImgs[a.getAniIndex()], x, (int)(a.getHitbox().y), width, ARCHER_HEIGHT, null);
        }
    }

    private void drawTraps(Graphics g, int xLvlOffset) {
        for (Spike s : currentLevel.getSpikes()) {
            g.drawImage(spikeImg, (int)(s.getHitbox().x - xLvlOffset), (int)(s.getHitbox().y - s.getyDrawOffset()), SPIKE_WIDTH, SPIKE_HEIGHT, null);
        }
    }

    private void drawContainers(Graphics g, int xLvlOffset) {
        for (GameContainer gc : containers) {
            if (gc.isActive()) {
                int type = 0; //BOX
                if (gc.getObjType() == BARREL) {
                    type = 1; //BARREL
                }
                g.drawImage(containerImgs[type][gc.getAniIndex()], (int)(gc.getHitbox().x - gc.getxDrawOffset() - xLvlOffset), (int)(gc.getHitbox().y - gc.getyDrawOffset()), CONTAINER_WIDTH, CONTAINER_HEIGHT, null);
            }
        }
    }

    private void drawPotions(Graphics g, int xLvlOffset) {
        for (Potion p : potions) {
            if (p.isActive()) {
                int type = 0; //BLUE POTION
                if (p.getObjType() == RED_POTION) {
                    type = 1; //RED POTION
                }
                g.drawImage(potionImgs[type][p.getAniIndex()], (int)(p.getHitbox().x - p.getxDrawOffset() - xLvlOffset), (int)(p.getHitbox().y - p.getyDrawOffset()), POTION_WIDTH, POTION_HEIGHT, null);
            }
        }
    }

    public void resetAllObjects() {
        //making sure that array list don't get bigger every reset
        loadObjects(playing.getLevelManager().getCurrentLevel());

        for (Potion p : potions) {
            p.reset();
        }
        for (GameContainer gc : containers) {
            gc.reset();
        }
        for (Archer a : currentLevel.getArchers()) {
            a.reset();
        }
    }
}
