package utilz;

import main.Game;
import objects.Projectile;

import java.awt.geom.Rectangle2D;

//class for helping methods
//will have a few static methods, that take in some data and return some value
//this methods can be used more than for just the Player
public class HelpMethods {

    //method is going to check the position
    public static boolean CanMoveHere(float x, float y, float width, float height, int[][] lvlData) {
        if (!IsSolid(x, y, lvlData)) {
            if (!IsSolid(x + width, y + height, lvlData)) {
                if (!IsSolid(x + width, y, lvlData)) {
                    if (!IsSolid(x, y + height, lvlData)) {
                        return true;
                    }
                }
            }
        }
        return false;
    }

    //collision of the projectiles with level
    public static boolean IsProjectileHittingLevel(Projectile p, int[][] lvlData) {
        return IsSolid(p.getHitbox().x + p.getHitbox().width / 2, p.getHitbox().y + p.getHitbox().height / 2, lvlData);
    }

    //Will only check if entity touch top water. Can't reach bottom water if not touched top water
    public static boolean IsEntityInWater(Rectangle2D.Float hitbox, int[][] lvlData) {
        if (GetTileValue(hitbox.x, hitbox.y + hitbox.height, lvlData) != 48) {
            if (GetTileValue(hitbox.x + hitbox.width, hitbox.y + hitbox.height, lvlData) != 48) {
                return false;
            }
        }
        return true;
    }

    private static int GetTileValue(float xPos, float yPos, int[][] lvlData) {
        int xCord = (int)(xPos / Game.TILES_SIZE);
        int yCord = (int)(yPos / Game.TILES_SIZE);
        return lvlData[yCord][xCord];
    }

    //method going to check whether it is a tile or not, but also check the position is inside whe window
    private static boolean IsSolid(float x, float y, int[][] lvlData) {
        int maxWidth = lvlData[0].length * Game.TILES_SIZE;
        if (x < 0 || x >= maxWidth) {
            return true;
        }
        if (y < 0 || y >= Game.GAME_HEIGHT) {
            return true;
        }

        float xIndex = x / Game.TILES_SIZE;
        float yIndex = y / Game.TILES_SIZE;

        return IsTileSolid((int)xIndex, (int)yIndex, lvlData);
    }

    public static boolean IsTileSolid(int xTile, int yTile, int[][] lvlData) {
        int value = lvlData[yTile][xTile];

        switch (value) {
        case 11, 48, 49:
            return false;
        default:
            return true;
        }
    }

    public static float GetEntityXPosNextToWall(Rectangle2D.Float hitbox, float xSpeed) {
        int currentTile = (int)(hitbox.x / Game.TILES_SIZE);
        if (xSpeed > 0) {
            //Right
            int tileXPos = currentTile * Game.TILES_SIZE;
            int xOffset = (int)(Game.TILES_SIZE - hitbox.width);
            return tileXPos + xOffset - 1;
        } else {
            //Left
            return currentTile * Game.TILES_SIZE;
        }
    }

    public static float GetEntityYPosUnderRoofOrAboveFloor(Rectangle2D.Float hitbox, float airSpeed) {
        int currentTile = (int)(hitbox.y / Game.TILES_SIZE);
        if (airSpeed > 0) {
            //Falling - Touching floor
            int tileYPos = currentTile * Game.TILES_SIZE;
            int yOffset = (int)(Game.TILES_SIZE - hitbox.height);
            return tileYPos + yOffset - 1;
        } else {
            //Jumping
            return currentTile * Game.TILES_SIZE;
        }
    }

    public static boolean IsEntityOnFloor(Rectangle2D.Float hitbox, int[][] lvlData) {
        //Check the pixel below bottom-left and bottom-right
        if (!IsSolid(hitbox.x, hitbox.y + hitbox.height + 1, lvlData)) {
            if (!IsSolid(hitbox.x + hitbox.width, hitbox.y + hitbox.height + 1, lvlData)) {
                return false;
            }
        }
        return true;
    }

    /**
     * Checking the bottomleft of the enemy here +/- the xSpeed. Never check
     * bottom right in case the enemy is going to the right. It would be more
     * correct checking the bottomleft for left direction and bottomright for the
     * right direction. But it won't have big effect in the game. The enemy will
     * change direction sooner when there is an edge on the right side of the
     * enemy, when it's going right.
     */
    public static boolean IsFloor(Rectangle2D.Float hitbox, float xSpeed, int[][] lvlData) {
        if (xSpeed > 0) { //if going right
            return IsSolid(hitbox.x + hitbox.width + xSpeed, hitbox.y + hitbox.height + 1, lvlData);
        } else {
            return IsSolid(hitbox.x + xSpeed, hitbox.y + hitbox.height + 1, lvlData);
        }
    }

    public static boolean IsFloor(Rectangle2D.Float hitbox, int[][] lvlData) {
        if (!IsSolid(hitbox.x + hitbox.width, hitbox.y + hitbox.height + 1, lvlData)) {
            if (!IsSolid(hitbox.x, hitbox.y + hitbox.height + 1, lvlData)) {
                return false;
            }
        }
        return true;
    }

    public static boolean CanArcherSeePlayer(int[][] lvlData, Rectangle2D.Float firstHitbox, Rectangle2D.Float secondHitbox, int yTile) {
        int firstXTile = (int)(firstHitbox.x / Game.TILES_SIZE);
        int secondXTile = (int)(secondHitbox.x / Game.TILES_SIZE);

        if (firstXTile > secondXTile) {
            return IsAllTilesClear(secondXTile, firstXTile, yTile, lvlData);
        } else {
            return IsAllTilesClear(firstXTile, secondXTile, yTile, lvlData);
        }
    }

    public static boolean IsAllTilesClear(int xStart, int xEnd, int y, int[][] lvlData) {
        for (int i = 0; i < xEnd - xStart; i++) {
            if (IsTileSolid(xStart + i, y, lvlData)) {
                return false;
            }
        }
        return true;
    }

    public static boolean IsAllTilesWalkable(int xStart, int xEnd, int y, int[][] lvlData) {
        if (IsAllTilesClear(xStart, xEnd, y, lvlData)) {
            for (int i = 0; i < xEnd - xStart; i++) {
                if (!IsTileSolid(xStart + i, y + 1, lvlData)) { //for the tiles under
                    return false;
                }
            }
        }
        return true;
    }

    //checking if there is a line of sight between two points
    //this method checks both player x and player x + width.
    public static boolean IsSightClear(int[][] lvlData, Rectangle2D.Float enemyBox, Rectangle2D.Float playerBox, int yTile) {
        int firstXTile = (int) (enemyBox.x / Game.TILES_SIZE);

        int secondXTile;
        if (IsSolid(playerBox.x, playerBox.y + playerBox.height + 1, lvlData)) {
            secondXTile = (int)(playerBox.x / Game.TILES_SIZE);
        } else {
            secondXTile = (int)((playerBox.x + playerBox.width) / Game.TILES_SIZE);
        }

        if (firstXTile > secondXTile) {
            return IsAllTilesWalkable(secondXTile, firstXTile, yTile, lvlData);
        } else {
            return IsAllTilesWalkable(firstXTile, secondXTile, yTile, lvlData);
        }
    }

    //checking if there is a line of sight between two points
    public static boolean IsSightClear_OLD_METHOD(int[][] lvlData, Rectangle2D.Float firstHitbox, Rectangle2D.Float secondHitbox, int yTile) {
        int firstXTile = (int)(firstHitbox.x / Game.TILES_SIZE);
        int secondXTile = (int)(secondHitbox.x / Game.TILES_SIZE);

        //check to know bigger tile for the FOR loop
        if (firstXTile > secondXTile) {
            return IsAllTilesWalkable(secondXTile, firstXTile, yTile, lvlData);
        } else {
            return IsAllTilesWalkable(firstXTile, secondXTile, yTile, lvlData);
        }
    }
}
