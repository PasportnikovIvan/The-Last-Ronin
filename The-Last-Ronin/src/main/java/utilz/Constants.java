package utilz;

import main.Game;

public class Constants {

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
