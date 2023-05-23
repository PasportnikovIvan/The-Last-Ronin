package utilz;

import java.net.URISyntaxException;
import java.net.URL;

import javax.imageio.ImageIO;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;

//class for loading and saving data
public class LoadSave {

    //only have the declaration up here, whatever class doing
    public static final String PLAYER_ATLAS = "player_sprites.png";
    public static final String LEVEL_ATLAS = "outside_sprites.png";
//    public static final String LEVEL_ONE_DATA = "level_one_data.png";
//    public static final String LEVEL_ONE_DATA = "level_one_data_long.png";
    public static final String MENU_BUTTONS = "button_atlas.png";
    public static final String MENU_BACKGROUND = "menu_background.png";
    public static final String PAUSE_BACKGROUND = "pause_menu.png";
    public static final String SOUND_BUTTONS = "sound_button.png";
    public static final String URM_BUTTONS = "urm_buttons.png"; //unpause,replay,menu
    public static final String VOLUME_BUTTONS = "volume_buttons.png";
    public static final String MENU_BACKGROUND_IMG = "background_menu.png";
    public static final String PLAYING_BG_IMG = "playing_bg_img.png";
    public static final String BIG_CLOUDS = "big_clouds.png";
    public static final String SMALL_CLOUDS = "small_clouds.png";
    public static final String MOB_SPRITE = "mob_sprite.png";
    public static final String STATUS_BAR = "health_power_bar.png";
    public static final String COMPLETED_IMG = "completed_sprite.png";
    public static final String POTION_ATLAS = "potions_sprites.png";
    public static final String CONTAINER_ATLAS = "objects_sprites.png";
    public static final String TRAP_ATLAS = "trap_atlas.png";
    public static final String ARCHER_ATLAS = "archer_atlas.png";
    public static final String ARROW = "arrow.png";
    public static final String DEATH_SCREEN = "death_screen.png";
    public static final String OPTIONS_MENU = "options_menu.png";
    public static final String GOBLIN_ATLAS = "goblin_atlas.png";
    public static final String QUESTION_ATLAS = "question_atlas.png";
    public static final String XCROSS_ATLAS = "xcross_atlas.png";
    public static final String FAST_MOB_ATLAS = "fast_mob_atlas.png";
    public static final String CREDITS = "credits_list.png";
    public static final String GRASS_ATLAS = "grass_atlas.png";
    public static final String BAMBOO_ONE_ATLAS = "bamboo_one_atlas.png";
    public static final String BAMBOO_TWO_ATLAS = "bamboo_two_atlas.png";
    public static final String GAME_COMPLETED = "game_completed.png";
    public static final String SAKURA_PARTICLE = "sakura_particle.png";
    public static final String WATER_TOP = "water_atlas_animation.png";
    public static final String WATER_BOTTOM = "water.png";

    //getting all atlases
    public static BufferedImage GetSpriteAtlas(String fileName) {
        BufferedImage img = null;
        InputStream is = LoadSave.class.getResourceAsStream("/" + fileName);

        try {
            img = ImageIO.read(is);

        } catch (IOException e) {
            e.printStackTrace();
        } finally { //closing input stream to free up resources and avoid problems
            try {
                is.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return img;
    }

    //this method is going to get all the imgs and return them sorted
    public static BufferedImage[] GetAllLevels() {
        URL url = LoadSave.class.getResource("/lvls");
        File file = null;

        try {
            file = new File(url.toURI());
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }

        File[] files = file.listFiles();
        File[] filesSorted = new File[files.length];

        //sorting lvls imgs
        for (int i = 0; i < filesSorted.length; i++) {
            for (int j = 0; j < files.length; j++) {
                if (files[j].getName().equals((i + 1) + ".png")) {
                    filesSorted[i] = files[j];
                }
            }
        }

        //getting lvls imgs
        BufferedImage[] imgs = new BufferedImage[filesSorted.length];

        for (int i = 0; i < imgs.length; i++)
            try {
                imgs[i] = ImageIO.read(filesSorted[i]);
            } catch (IOException e) {
                e.printStackTrace();
            }

        return imgs;
    }
}
