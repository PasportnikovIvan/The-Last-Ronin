package effects;

import main.Game;
import utilz.LoadSave;

import java.awt.Graphics;
import java.awt.geom.Point2D;
import java.awt.image.BufferedImage;
import java.util.Random;

//Effects class for the sakura particles in the game
public class Sakura {

    private Point2D.Float[] flovers;
    private Random rand;
    private float sakuraSpeed = 0.3f;
    private BufferedImage sakuraParticle;

    //Adding particles this way can cost a lot in computer power. Disable it if the game lags.
    public Sakura() {
        rand = new Random();
        flovers = new Point2D.Float[300];
        sakuraParticle = LoadSave.GetSpriteAtlas(LoadSave.SAKURA_PARTICLE);
        initDrops();
    }

    private void initDrops() {
        for (int i = 0; i < flovers.length; i++) {
            flovers[i] = getRndPos();
        }
    }

    private Point2D.Float getRndPos() {
        return new Point2D.Float((int)getNewX(0), rand.nextInt(Game.GAME_HEIGHT));
    }

    public void update(int xLvlOffset) {
        for (Point2D.Float p : flovers) {
            p.y += sakuraSpeed;
            if (p.y >= Game.GAME_HEIGHT) {
                p.y = -20;
                p.x = getNewX(xLvlOffset);
            }
        }
    }

    private float getNewX(int xLvlOffset) {
        float value = (-Game.GAME_WIDTH) + rand.nextInt((int)(Game.GAME_WIDTH * 3f)) + xLvlOffset;
        return value;
    }

    public void draw(Graphics g, int xLvlOffset) {
        for (Point2D.Float p : flovers) {
            g.drawImage(sakuraParticle, (int)p.getX() - xLvlOffset, (int)p.getY(), 6, 6, null);
        }
    }
}