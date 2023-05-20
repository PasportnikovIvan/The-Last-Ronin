package audio;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.BooleanControl;
import javax.sound.sampled.Clip;
import javax.sound.sampled.FloatControl;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;

import java.io.IOException;
import java.net.URL;
import java.util.Random;

//Audio class for the audio in the game
public class AudioPlayer {

    //constant values for the sound-files
    //songs
    public static int MENU_1 = 0;
    public static int LEVEL_1 = 1;
    public static int LEVEL_2 = 2;
    //effects
    public static int DIE = 0;
    public static int JUMP = 1;
    public static int GAMEOVER = 2;
    public static int LVL_COMPLETED = 3;
    public static int ATTACK_ONE = 4;
    public static int ATTACK_TWO = 5;
    public static int ATTACK_THREE = 6;
    public static int ARROW_WHIZZ = 7;
    public static int ENEMY_ATTACK = 8;
    public static int BOX_BREAK = 9;

    /**
     * Clip can be considered as a music player
     * Going to take audioInputStream and open it in this Clip
     * ability to change it: stop, continue, change volume, etc.
     * limitations for the clip: .wav files only, keep the Bitrate of the .wav file to 1411 kbps or less, and 44100 hz in sample rate
     */
    private Clip[] songs, effects;
    private int currentSongId;
    private float volume = 0.5f;
    private boolean songMute, effectMute;
    private Random random = new Random();

    public AudioPlayer() {
        loadSongs();
        loadEffects();
        playSong(MENU_1);
    }

    private void loadSongs() {
        String[] names = {"menu", "level1", "level2"}; //array for all the songs
        songs = new Clip[names.length];
        for (int i = 0; i < songs.length; i++) {
            songs[i] = getClip(names[i]);
        }
    }

    private void loadEffects() {
        String[] effectNames = {"die", "jump", "gameover", "lvlcompleted", "attack1", "attack2", "attack3", "arrowWhizz", "enemyAttack", "boxBreak"}; //array for all the effects
        effects = new Clip[effectNames.length];
        for (int i = 0; i < effects.length; i++) {
            effects[i] = getClip(effectNames[i]);
        }

        updateEffectsVolume(); //for effects volume in the start of the game
    }

    private Clip getClip(String name) {
        URL url = getClass().getResource("/audio/" + name + ".wav");
        AudioInputStream audio;
        try {
            audio = AudioSystem.getAudioInputStream(url);
            Clip c = AudioSystem.getClip();
            c.open(audio);
            return c;
        } catch (UnsupportedAudioFileException | IOException | LineUnavailableException e) {
            e.printStackTrace();
        }
        return null;
    }

    public void setVolume(float volume) {
        this.volume = volume;
        updateSongVolume();
        updateEffectsVolume();
    }

    public void stopSong() {
        if (songs[currentSongId].isActive()) {
            songs[currentSongId].stop();
        }
    }

    //different songs for different levels
    public void setLevelSong(int lvlIndex) {
        if (lvlIndex % 2 == 0) {
            playSong(LEVEL_1);
        } else {
            playSong(LEVEL_2);
        }
    }

    public void lvlCompleted() {
        stopSong();
        playEffect(LVL_COMPLETED);
    }

    public void playAttackSound() {
        int start = 4;
        start += random.nextInt(3);
        playEffect(start);
    }

    public void playSong(int song) {
        stopSong(); //stops the previous song

        currentSongId = song;
        updateSongVolume();
        songs[currentSongId].setMicrosecondPosition(0); //making play from beginning
        songs[currentSongId].loop(Clip.LOOP_CONTINUOUSLY); //looping the background song
    }

    public void playEffect(int effect) {
        effects[effect].setMicrosecondPosition(0); //making play from beginning
        effects[effect].start();
    }

    public void toggleSongMute() {
        this.songMute = !songMute;
        for (Clip c : songs) {
            BooleanControl booleanControl = (BooleanControl) c.getControl(BooleanControl.Type.MUTE);
            booleanControl.setValue(songMute);
        }
    }

    public void toggleEffectMute() {
        this.effectMute = !effectMute;
        for (Clip c : effects) {
            BooleanControl booleanControl = (BooleanControl) c.getControl(BooleanControl.Type.MUTE);
            booleanControl.setValue(effectMute);
        }
        //to notice if SFX are muted or not
        if (!effectMute) {
            playEffect(ATTACK_ONE);
        }
    }

    //only update the current song that is playing
    private void updateSongVolume() {
        FloatControl gainControl = (FloatControl) songs[currentSongId].getControl(FloatControl.Type.MASTER_GAIN); //gainer
        float range = gainControl.getMaximum() - gainControl.getMinimum();
        float gain = (range * volume) + gainControl.getMinimum(); //the value that sets to gainer
        gainControl.setValue(gain);
    }

    //update all effects at the same time
    private void updateEffectsVolume() {
        for (Clip c : effects) {
            FloatControl gainControl = (FloatControl) c.getControl(FloatControl.Type.MASTER_GAIN);
            float range = gainControl.getMaximum() - gainControl.getMinimum();
            float gain = (range * volume) + gainControl.getMinimum();
            gainControl.setValue(gain);
        }
    }
}
