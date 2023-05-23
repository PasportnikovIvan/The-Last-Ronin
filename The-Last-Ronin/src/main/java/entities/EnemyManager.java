package entities;

import gamestates.Playing;
import levels.Level;
import utilz.LoadSave;
import static utilz.Constants.EnemyConstants.*;

import java.awt.Graphics;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;

//The Enemy Manager class.
//This class is going to have similar role as LevelManager have with levels
public class EnemyManager {

    private Playing playing;
    private BufferedImage[][] mobArr, goblinArr, fastMobArr;
    private Level currentLevel;

    public EnemyManager(Playing playing) {
        this.playing = playing;
        loadEnemyImgs();
    }

    public void loadEnemies(Level level) {
        this.currentLevel = level;
    }

    //updates Enemy
    public void update(int[][] lvlData) {
        boolean isAnyActive = false;
        for (Mob m : currentLevel.getMobs()) {
            if (m.isActive()) {
                m.update(lvlData, playing);
                isAnyActive = true;
            }
        }

        for (Goblin gob : currentLevel.getGoblins()) {
            if (gob.isActive()) {
                gob.update(lvlData, playing);
                isAnyActive = true;
            }
        }

        for (FastMob f : currentLevel.getFastMobs()) {
            if (f.isActive()) {
                f.update(lvlData, playing);
                isAnyActive = true;
            }
        }

        if (!isAnyActive) {
            playing.setLevelCompleted(true);
        }
    }

    //draws Enemy
    public void draw(Graphics g, int xLvlOffset) {
        drawMobs(g, xLvlOffset);
        drawGoblins(g, xLvlOffset);
        drawFastMobs(g, xLvlOffset);
    }

    private void drawFastMobs(Graphics g, int xLvlOffset) {
        for (FastMob f : currentLevel.getFastMobs()) {
            if (f.isActive()) {
                g.drawImage(fastMobArr[f.getState()][f.getAniIndex()], (int) f.getHitbox().x - xLvlOffset - FAST_MOB_DRAWOFFSET_X + f.flipX(), (int) f.getHitbox().y - FAST_MOB_DRAWOFFSET_Y + (int) f.getPushDrawOffset(), FAST_MOB_WIDTH * f.flipW(), FAST_MOB_HEIGHT, null);
//				f.drawHitbox(g, xLvlOffset);
//				f.drawAttackBox(g, xLvlOffset);
            }
        }
    }

    private void drawGoblins(Graphics g, int xLvlOffset) {
        for (Goblin gob : currentLevel.getGoblins()) {
            if (gob.isActive()) {
                g.drawImage(goblinArr[gob.getState()][gob.getAniIndex()], (int) gob.getHitbox().x - xLvlOffset - GOBLIN_DRAWOFFSET_X + gob.flipX(), (int) gob.getHitbox().y - GOBLIN_DRAWOFFSET_Y + (int) gob.getPushDrawOffset(), GOBLIN_WIDTH * gob.flipW(), GOBLIN_HEIGHT, null);
//				gob.drawHitbox(g, xLvlOffset);
            }
        }
    }

    private void drawMobs(Graphics g, int xLvlOffset) {
        for (Mob m : currentLevel.getMobs()) {
            if (m.isActive()) {
                g.drawImage(mobArr[m.getState()][m.getAniIndex()], (int) m.getHitbox().x - xLvlOffset - MOB_DRAWOFFSET_X + m.flipX(), (int) m.getHitbox().y - MOB_DRAWOFFSET_Y + (int) m.getPushDrawOffset(), MOB_WIDTH * m.flipW(), MOB_HEIGHT, null);
//				m.drawHitbox(g, xLvlOffset);
//				m.drawAttackBox(g, xLvlOffset);
            }
        }
    }

    public void checkEnemyHit(Rectangle2D.Float attackBox) {
        for (Mob m : currentLevel.getMobs()) {
            if (m.isActive()) {
                if (m.getState() != DEAD && m.getState() != HIT) { //fixed bug with immortal enemies (spam attack to the enemy)
                    if (attackBox.intersects(m.getHitbox())) { //attack box is overlapped
                        m.hurt(20);
                        if (playing.getPlayer().getPowerAttack()) {
                            m.hurt(20); //20 more damage with dash attack
                        }
                        return;
                    }
                }
            }
        }

        for (Goblin gob : currentLevel.getGoblins()) {
            if (gob.isActive()) {
                if (gob.getState() == ATTACK && gob.getAniIndex() >= 3) {
                    return;
                } else {
                    if (gob.getState() != DEAD && gob.getState() != HIT) {
                        if (attackBox.intersects(gob.getHitbox())) {
                            gob.hurt(20);
                            if (playing.getPlayer().getPowerAttack()) {
                                gob.hurt(20); //20 more damage with dash attack
                            }
                            return;
                        }
                    }
                }
            }
        }

        for (FastMob f : currentLevel.getFastMobs()) {
            if (f.isActive()) {
                if (f.getState() != DEAD && f.getState() != HIT) {
                    if (attackBox.intersects(f.getHitbox())) {
                        f.hurt(20);
                        if (playing.getPlayer().getPowerAttack()) {
                            f.hurt(20); //20 more damage with dash attack
                        }
                        return;
                    }
                }
            }
        }
    }

    //subImages from sprites
    private void loadEnemyImgs() {
        mobArr = getImgArr(LoadSave.GetSpriteAtlas(LoadSave.MOB_SPRITE), 6, 5, MOB_WIDTH_DEFAULT, MOB_HEIGHT_DEFAULT);
        goblinArr = getImgArr(LoadSave.GetSpriteAtlas(LoadSave.GOBLIN_ATLAS), 7, 5, GOBLIN_WIDTH_DEFAULT, GOBLIN_HEIGHT_DEFAULT);
        fastMobArr = getImgArr(LoadSave.GetSpriteAtlas(LoadSave.FAST_MOB_ATLAS), 8, 5, FAST_MOB_WIDTH_DEFAULT, FAST_MOB_HEIGHT_DEFAULT);
    }

    private BufferedImage[][] getImgArr(BufferedImage atlas, int xSize, int ySize, int spriteW, int spriteH) {
        BufferedImage[][] tempArr = new BufferedImage[ySize][xSize];
        for (int j = 0; j < tempArr.length; j++) {
            for (int i = 0; i < tempArr[j].length; i++) {
                tempArr[j][i] = atlas.getSubimage(i * spriteW, j * spriteH, spriteW, spriteH);
            }
        }
        return tempArr;
    }

    public void resetAllEnemies() {
        for (Mob m : currentLevel.getMobs()) {
            m.resetEnemy();
        }
        for (Goblin gob : currentLevel.getGoblins()) {
            gob.resetEnemy();
        }
        for (FastMob f : currentLevel.getFastMobs()) {
            f.resetEnemy();
        }
    }
}
