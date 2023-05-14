package utilz;

import main.Game;

public class Constants {

    //Constant value for the gravity in the game
    //the lower gravity - the higher jump
    public static final float GRAVITY = 0.04f * Game.SCALE;

    //how fast going to animate (the lower num - the faster animation)
    public static final int ANI_SPEED = 25;

    //Constants class for the Enemies
    public static class EnemyConstants {
        public static final int MOB = 0;

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

        //how many sprites in action
        public static int GetSpriteAmount(int enemy_type, int enemy_state) {
            switch (enemy_type) {
            case MOB:
                switch (enemy_state) {
                case IDLE:
                    return 5;
                case RUNNING:
                case DEAD:
                    return 6;
                case ATTACK:
                case HIT:
                    return 4;
                }
            }

            return 0;
        }

        //returning max health for each type of the enemy
        public static int GetMaxHealth(int enemy_type) {
            switch (enemy_type) {
            case MOB:
                return 15;
            default:
                return 1;
            }
        }

        //the type of the Enemy making damage to the Player
        public static int GetEnemyDMG(int enemy_type) {
            switch (enemy_type) {
            case MOB:
                return 15;
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
        public static final int GROUND = 4;
        public static final int HIT = 5;
        public static final int ATTACK_1 = 6;
        public static final int ATTACK_JUMP_1 = 7;
        public static final int ATTACK_JUMP_2 = 8;

        //how many sprites in action
        public static int GetSpriteAmount(int player_action) {

            switch (player_action) {
            case RUNNING:
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
            case GROUND:
                return 2;
            case FALLING:
            default:
                return 1;
            }
        }
    }
}
