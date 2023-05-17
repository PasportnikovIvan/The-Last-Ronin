package entities;

import gamestates.Playing;
import levels.Level;
import utilz.LoadSave;
import static utilz.Constants.EnemyConstants.*;

import java.awt.Graphics;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

//The Enemy Manager class.
//This class is going to have similar role as LevelManager have with levels
public class EnemyManager {

    private Playing playing;
    private BufferedImage[][] mobArr;
    private ArrayList<Mob> mobs = new ArrayList<>();

    public EnemyManager(Playing playing) {
        this.playing = playing;
        loadEnemyImgs();
    }

    public void loadEnemies(Level level) {
        mobs = level.getMobs();
        System.out.println("size of mobs: " + mobs.size());
    }

    //updates Enemy
    public void update(int[][] lvlData, Player player) {
        boolean isAnyActive = false;
        for (Mob m : mobs) {
            if (m.isActive()) {
                m.update(lvlData, player);
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
    }

    private void drawMobs(Graphics g, int xLvlOffset) {
        for (Mob m : mobs) {
            if (m.isActive()) {
                g.drawImage(mobArr[m.getState()][m.getAniIndex()], (int) m.getHitbox().x - xLvlOffset - MOB_DRAWOFFSET_X + m.flipX(), (int) m.getHitbox().y - MOB_DRAWOFFSET_Y, MOB_WIDTH * m.flipW(), MOB_HEIGHT, null);
//                m.drawHitbox(g, xLvlOffset);
//                m.drawAttackBox(g, xLvlOffset);
            }
        }
    }

    public void checkEnemyHit(Rectangle2D.Float attackBox) {
        for (Mob m : mobs) {
            if (m.isActive()) {
                if (m.getCurrentHealth() > 0) { //fixed bug with immortal enemies (spam attack to the enemy)
                    if (attackBox.intersects(m.getHitbox())) { //attack box is overlapped
                        m.hurt(10);
                        return;
                    }
                }
            }
        }
    }

    //subImages from sprites
    private void loadEnemyImgs() {
        mobArr = new BufferedImage[5][6];
        //getting Enemy sprites from sprites
        BufferedImage temp = LoadSave.GetSpriteAtlas(LoadSave.MOB_SPRITE);
        //making matrix for choosing subImgs
        for (int j = 0; j < mobArr.length; j++) {
            for (int i = 0; i < mobArr[j].length; i++) {
                mobArr[j][i] = temp.getSubimage(i * MOB_WIDTH_DEFAULT, j * MOB_HEIGHT_DEFAULT, MOB_WIDTH_DEFAULT, MOB_HEIGHT_DEFAULT);
            }
        }
    }

    public void resetAllEnemies() {
        for (Mob m : mobs) {
            m.resetEnemy();
        }
    }
}
