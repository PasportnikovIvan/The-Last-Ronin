package utilz;

import main.Game;

//Constant class of the Game constants ond other Constant classes in this class
public class Constants {

    //Constant value for the gravity in the game
    //the lower gravity - the higher jump
    public static final float GRAVITY = 0.04f * Game.SCALE;

    //how fast going to animate (the lower num - the faster animation)
    public static final int ANI_SPEED = 25;

    //Constants class for the Emotions effects
    public static class Emotion {
        public static final int QUESTION = 0;
        public static final int XCROSS = 1;

        public static final int EMOTION_WIDTH = (int)(14 * Game.SCALE);
        public static final int EMOTION_HEIGHT = (int)(12 * Game.SCALE);

        public static int GetSpriteAmount(int type) {
            switch (type) {
            case QUESTION, XCROSS:
                return 5;
            }
            return 0;
        }
    }

    //Constants class for the projectiles
    public static class Projectiles {
        public static final int ARROW_WIDTH_DEFAULT = 15;
        public static final int ARROW_HEIGHT_DEFAULT = 15;

        public static final int ARROW_WIDTH = (int)(Game.SCALE * ARROW_WIDTH_DEFAULT);
        public static final int ARROW_HEIGHT = (int)(Game.SCALE * ARROW_HEIGHT_DEFAULT);
        public static final float SPEED = 0.75f * Game.SCALE;
    }

    //Constants class for the Objects
    public static class ObjectConstants {
        public static final int RED_POTION = 0;
        public static final int BLUE_POTION = 1;
        public static final int BARREL = 2;
        public static final int BOX = 3;
        public static final int SPIKE = 4;
        public static final int ARCHER_LEFT = 5; //different direction
        public static final int ARCHER_RIGHT = 6;
        public static final int BAMBOO_ONE = 7;
        public static final int BAMBOO_TWO = 8;
        public static final int BAMBOO_THREE = 9;

        public static final int RED_POTION_VALUE = 20; //health
        public static final int BLUE_POTION_VALUE = 10; //power bar

        public static final int CONTAINER_WIDTH_DEFAULT = 40;
        public static final int CONTAINER_HEIGHT_DEFAULT = 30;
        public static final int CONTAINER_WIDTH = (int)(Game.SCALE * CONTAINER_WIDTH_DEFAULT);
        public static final int CONTAINER_HEIGHT = (int)(Game.SCALE * CONTAINER_HEIGHT_DEFAULT);

        public static final int POTION_WIDTH_DEFAULT = 12;
        public static final int POTION_HEIGHT_DEFAULT = 16;
        public static final int POTION_WIDTH = (int)(Game.SCALE * POTION_WIDTH_DEFAULT);
        public static final int POTION_HEIGHT = (int)(Game.SCALE * POTION_HEIGHT_DEFAULT);

        public static final int SPIKE_WIDTH_DEFAULT = 32;
        public static final int SPIKE_HEIGHT_DEFAULT = 32;
        public static final int SPIKE_WIDTH = (int)(Game.SCALE * SPIKE_WIDTH_DEFAULT);
        public static final int SPIKE_HEIGHT = (int)(Game.SCALE * SPIKE_HEIGHT_DEFAULT);

        public static final int ARCHER_WIDTH_DEFAULT = 40;
        public static final int ARCHER_HEIGHT_DEFAULT = 35;
        public static final int ARCHER_WIDTH = (int)(Game.SCALE * ARCHER_WIDTH_DEFAULT);
        public static final int ARCHER_HEIGHT = (int)(Game.SCALE * ARCHER_HEIGHT_DEFAULT);

        //how many sprites in action
        public static int GetSpriteAmount(int object_type) {
            switch (object_type) {
            case RED_POTION, BLUE_POTION:
                return 7;
            case BARREL, BOX:
                return 8;
            case ARCHER_LEFT, ARCHER_RIGHT:
                return 7;
            }
            return 1;
        }

        //GET method for drawing bamboos and sakura in the level
        public static int GetBambooOffsetX(int bambooType) {
            switch (bambooType) {
            case BAMBOO_ONE:
                return (Game.TILES_SIZE / 2) - (GetBambooWidth(bambooType) / 2);
            case BAMBOO_TWO:
                return (int)(Game.TILES_SIZE / 2.5f);
            case BAMBOO_THREE:
                return (int)(Game.TILES_SIZE / 1.65f);
            }
            return 0;
        }

        //GET method for drawing bamboos and sakura in the level
        public static int GetBambooOffsetY(int bambooType) {
            switch (bambooType) {
            case BAMBOO_ONE:
                return -GetBambooHeight(bambooType) + Game.TILES_SIZE * 1;
            case BAMBOO_TWO, BAMBOO_THREE:
                return -GetBambooHeight(bambooType) + (int)(Game.TILES_SIZE / 1.25f);
            }
            return 0;
        }

        //GET method for drawing bamboos and sakura in the level
        public static int GetBambooWidth(int bambooType) {
            switch (bambooType) {
            case BAMBOO_ONE:
                return (int)(39 * Game.SCALE);
            case BAMBOO_TWO:
                return (int)(62 * Game.SCALE);
            case BAMBOO_THREE:
                return -(int)(62 * Game.SCALE);
            }
            return 0;
        }

        //GET method for drawing bamboos and sakura in the level
        public static int GetBambooHeight(int bambooType) {
            switch (bambooType) {
            case BAMBOO_ONE:
                return (int)(int)(92 * Game.SCALE);
            case BAMBOO_TWO, BAMBOO_THREE:
                return (int)(54 * Game.SCALE);
            }
            return 0;
        }
    }

    //Constants class for the Enemies
    public static class EnemyConstants {
        public static final int MOB = 0;
        public static final int GOBLIN = 1;
        public static final int FAST_MOB = 2;

        //for enemy's action constants
        public static final int IDLE = 0;
        public static final int RUNNING = 1;
        public static final int ATTACK = 2;
        public static final int HIT = 3;
        public static final int DEAD = 4;

        //the default size of the sprite
        public static final int MOB_WIDTH_DEFAULT = 64;
        public static final int MOB_HEIGHT_DEFAULT = 40;
        //the actual size of the sprite
        public static final int MOB_WIDTH = (int)(MOB_WIDTH_DEFAULT * Game.SCALE);
        public static final int MOB_HEIGHT = (int)(MOB_HEIGHT_DEFAULT * Game.SCALE);
        public static final int MOB_DRAWOFFSET_X = (int)(21 * Game.SCALE);
        public static final int MOB_DRAWOFFSET_Y = (int)(6 * Game.SCALE);

        //the default size of the sprite
        public static final int GOBLIN_WIDTH_DEFAULT = 64;
        public static final int GOBLIN_HEIGHT_DEFAULT = 40;
        //the actual size of the sprite
        public static final int GOBLIN_WIDTH = (int)(GOBLIN_WIDTH_DEFAULT * Game.SCALE);
        public static final int GOBLIN_HEIGHT = (int)(GOBLIN_HEIGHT_DEFAULT * Game.SCALE);
        public static final int GOBLIN_DRAWOFFSET_X = (int)(19 * Game.SCALE);
        public static final int GOBLIN_DRAWOFFSET_Y = (int)(4 * Game.SCALE);

        //the default size of the sprite
        public static final int FAST_MOB_WIDTH_DEFAULT = 64;
        public static final int FAST_MOB_HEIGHT_DEFAULT = 40;
        //the actual size of the sprite
        public static final int FAST_MOB_WIDTH = (int)(FAST_MOB_WIDTH_DEFAULT * Game.SCALE);
        public static final int FAST_MOB_HEIGHT = (int)(FAST_MOB_HEIGHT_DEFAULT * Game.SCALE);
        public static final int FAST_MOB_DRAWOFFSET_X = (int)(23 * Game.SCALE);
        public static final int FAST_MOB_DRAWOFFSET_Y = (int)(5 * Game.SCALE);

        //how many sprites in action
        public static int GetSpriteAmount(int enemy_type, int enemy_state) {
            switch (enemy_state) {
            case IDLE: {
                if (enemy_type == MOB) {
                    return 5;
                } else if (enemy_type == GOBLIN || enemy_type == FAST_MOB) {
                    return 6;
                }
            }
            case RUNNING:
                return 6;
            case ATTACK:
                if (enemy_type == FAST_MOB) {
                    return 8;
                } else if (enemy_type == MOB) {
                    return 4;
                }
                return 7;
            case HIT:
                return 4;
            case DEAD:
                return 6;
            }

            return 0;
        }

        //returning max health for each type of the enemy
        public static int GetMaxHealth(int enemy_type) {
            switch (enemy_type) {
            case MOB:
                return 50;
            case GOBLIN, FAST_MOB:
                return 25;
            default:
                return 1;
            }
        }

        //the type of the Enemy making damage to the Player
        public static int GetEnemyDMG(int enemy_type) {
            switch (enemy_type) {
            case MOB:
                return 15;
            case GOBLIN:
                return 20;
            case FAST_MOB:
                return 25;
            default:
                return 0;
            }
        }
    }

    //class for scaling background
    public static class Environment {
        public static final int BIG_CLOUD_WIDTH_DEFAULT = 448;
        public static final int BIG_CLOUD_HEIGHT_DEFAULT = 101;
        public static final int SMALL_CLOUD_WIDTH_DEFAULT = 74;
        public static final int SMALL_CLOUD_HEIGHT_DEFAULT = 24;

        public static final int BIG_CLOUD_WIDTH = (int)(BIG_CLOUD_WIDTH_DEFAULT * Game.SCALE);
        public static final int BIG_CLOUD_HEIGHT = (int)(BIG_CLOUD_HEIGHT_DEFAULT * Game.SCALE);
        public static final int SMALL_CLOUD_WIDTH = (int)(SMALL_CLOUD_WIDTH_DEFAULT * Game.SCALE);
        public static final int SMALL_CLOUD_HEIGHT = (int)(SMALL_CLOUD_HEIGHT_DEFAULT * Game.SCALE);
    }

    //class in Const class for UI, such as buttons, etc.
    public static class UI {

        //Buttons for the menu
        public static class Buttons {
            public static final int B_WIDTH_DEFAULT = 140;
            public static final int B_HEIGHT_DEFAULT = 56;
            public static final int B_WIDTH = (int)(B_WIDTH_DEFAULT * Game.SCALE);
            public static final int B_HEIGHT = (int)(B_HEIGHT_DEFAULT * Game.SCALE);
        }

        //Buttons for the Pause menu
        public static class PauseButtons {
            public static final int SOUND_SIZE_DEFAULT = 42;
            public static final int SOUND_SIZE = (int)(SOUND_SIZE_DEFAULT * Game.SCALE);
        }

        //UnPause, Replay, Menu Buttons for the Pause menu
        public static class URMButtons {
            public static final int URM_SIZE_DEFAULT = 56;
            public static final int URM_SIZE = (int)(URM_SIZE_DEFAULT * Game.SCALE);
        }

        //The Volume SlideBar of the Pause menu
        public static class VolumeButtons {
            public static final int VOLUME_WIDTH_DEFAULT = 28;
            public static final int VOLUME_HEIGHT_DEFAULT = 44;
            public static final int SLIDER_WIDTH_DEFAULT = 215;

            public static final int VOLUME_WIDTH = (int)(VOLUME_WIDTH_DEFAULT * Game.SCALE);
            public static final int VOLUME_HEIGHT = (int)(VOLUME_HEIGHT_DEFAULT * Game.SCALE);
            public static final int SLIDER_WIDTH = (int)(SLIDER_WIDTH_DEFAULT * Game.SCALE);
        }
    }

    //class in Const class for player's direction constants
    public static class Directions {
        public static final int LEFT = 0;
        public static final int UP = 1;
        public static final int RIGHT = 2;
        public static final int DOWN = 3;
    }

    //class in Const class for player's action constants
    public static class PlayerConstants {
        public static final int IDLE = 0;
        public static final int RUNNING = 1;
        public static final int JUMP = 2;
        public static final int FALLING = 3;
        public static final int ATTACK_1 = 4;
        public static final int HIT = 5;
        public static final int DEAD = 6;

        //for the future game updates
        public static final int ATTACK_JUMP_1 = 7;
        public static final int ATTACK_JUMP_2 = 8;

        //how many sprites in action
        public static int GetSpriteAmount(int player_action) {
            switch (player_action) {
            case RUNNING:
            case DEAD:
                return 6;
            case IDLE:
                return 5;
            case ATTACK_1:
            case ATTACK_JUMP_1:
            case ATTACK_JUMP_2:
                return 4;
            case HIT:
            case JUMP:
                return 3;
            case FALLING:
            default:
                return 1;
            }
        }
    }
}
