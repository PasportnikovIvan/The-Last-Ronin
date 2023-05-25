package levels;

import levels.Level;
import entities.Mob;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

import org.junit.jupiter.api.Assertions;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static utilz.LoadSave.GetAllLevels;

public class LevelTest {

    private BufferedImage img;
    private Level level1, level2, level3, level4, level5;

    @BeforeEach
    public void setup() {
        img = mock(BufferedImage.class);
        level5 = new Level(GetAllLevels()[4]); //the 5th level with the index 4
        level1 = new Level(GetAllLevels()[0]);
        level2 = new Level(GetAllLevels()[1]);
        level3 = new Level(GetAllLevels()[2]);
        level4 = new Level(GetAllLevels()[3]);
    }

    @Test
    public void testGetLevelData() {
        // Set up the test
        int[][] levelData1 = {{11,11,11,11,11,11,11,12,13,13,13,13,13,13,13,13,13,13,13,13,13,13,13,13,13,13,13,13,13,13,13,13,13,13,13,13,13,13,13,13,13,13,13,13,13,13,13,13,13,13},{11,11,11,11,11,11,11,12,13,13,13,13,13,13,13,13,13,13,13,13,13,13,13,13,13,13,13,13,13,13,13,13,13,13,13,13,13,13,13,13,13,13,13,13,13,13,13,13,13,13},{11,11,11,11,11,11,11,12,13,13,13,13,13,13,13,13,13,13,13,13,13,13,13,13,4,25,25,25,25,25,25,25,25,25,25,25,25,25,25,25,25,25,25,25,25,25,25,25,25,5},{11,11,11,11,11,11,11,12,13,13,13,13,13,13,13,13,13,13,13,13,13,13,13,13,14,11,11,11,11,11,11,11,11,11,11,11,11,11,11,11,11,11,11,11,11,11,11,11,11,12},{11,11,11,11,11,11,11,12,13,13,13,13,13,13,13,13,13,13,13,13,13,13,13,13,14,11,11,11,11,11,11,11,11,11,11,11,11,11,11,11,11,11,11,11,11,11,11,11,11,12},{11,11,11,11,11,11,11,24,25,25,25,25,25,25,25,25,25,25,25,25,25,25,25,25,26,11,11,11,11,11,11,11,11,11,11,11,11,11,11,11,11,11,11,11,11,11,11,11,11,12},{11,11,11,11,11,11,11,11,11,11,11,11,11,11,11,11,11,11,11,11,11,11,11,11,11,11,11,11,11,11,11,11,11,11,11,11,11,11,11,11,11,11,11,11,11,11,11,11,11,12},{11,11,11,11,11,11,11,11,11,11,11,11,11,11,11,11,11,11,11,11,11,11,11,11,11,11,11,11,11,11,11,11,11,11,11,11,11,11,11,11,11,11,11,11,11,11,11,11,11,12},{11,11,11,11,11,11,11,11,11,11,11,11,11,11,11,11,11,11,11,11,11,11,11,11,11,11,36,37,38,11,11,36,38,11,11,36,37,37,37,37,37,38,11,11,11,11,11,11,11,12},{11,11,11,11,11,11,11,11,11,11,11,11,11,11,11,11,11,11,11,11,11,11,11,11,11,11,11,11,11,11,11,11,11,11,11,11,11,11,11,11,11,11,11,11,11,11,11,11,11,12},{11,11,11,11,11,11,11,0,1,1,1,1,1,1,1,2,11,11,11,0,1,1,1,1,2,11,11,11,11,11,11,11,11,11,11,11,11,11,11,11,11,11,11,0,1,1,1,1,1,17},{48,48,48,48,48,48,48,12,13,13,13,13,13,13,13,14,11,11,11,12,13,13,13,13,14,11,11,11,11,11,11,11,11,11,11,11,11,11,11,11,11,11,11,12,13,13,13,13,13,13},{49,49,49,49,49,49,49,12,13,13,13,13,13,13,13,16,1,1,1,17,13,13,13,13,16,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,17,13,13,13,13,13,13},{49,49,49,49,49,49,49,12,13,13,13,13,13,13,13,13,13,13,13,13,13,13,13,13,13,13,13,13,13,13,13,13,13,13,13,13,13,13,13,13,13,13,13,13,13,13,13,13,13,13}};
        int[][] levelData2 = {{13,13,13,13,13,13,13,13,13,13,13,13,13,13,13,13,13,13,13,13,13,13,13,13,13,13,13,13,13,13,13,13,13,13,13,13,13,13,13,13,13,13,13,13,13,13,13,13,13,13,13,13,13,13,13,13,13,13,13,13,13,13,13,13,13,13,13,13,13,13,13,13,13,13,13},{13,13,13,13,13,13,13,13,13,13,13,13,13,13,13,13,13,13,13,13,13,13,13,13,13,13,13,13,13,13,13,13,13,13,13,13,13,13,13,13,13,13,13,13,13,13,13,13,13,13,13,13,13,13,13,13,13,13,13,13,13,13,13,13,13,13,13,13,13,13,13,13,13,13,13},{4,25,25,25,25,25,25,25,25,25,25,25,25,25,25,25,25,25,25,25,25,25,25,25,25,25,25,25,25,25,25,25,25,25,25,25,25,25,25,25,25,25,25,25,25,25,25,25,25,25,25,25,25,25,25,25,25,25,25,25,25,25,25,25,25,25,25,25,25,25,25,25,25,25,5},{14,11,11,11,11,11,11,11,11,11,11,11,11,11,11,11,11,11,11,11,11,11,11,11,11,11,11,11,11,11,11,11,11,11,11,11,11,11,11,11,11,11,11,11,11,11,11,11,11,11,11,11,11,11,11,11,11,11,11,11,11,11,11,11,11,11,11,11,11,11,11,11,11,11,12},{14,11,11,11,11,11,11,11,11,11,11,11,11,11,11,11,11,11,11,11,11,11,11,11,11,11,11,11,11,11,11,11,11,11,11,11,11,11,11,11,11,11,11,11,11,11,11,11,11,11,11,11,11,11,11,11,11,11,11,11,11,11,11,11,11,11,11,11,11,11,11,11,11,11,12},{14,11,11,11,11,11,11,11,11,11,11,11,11,11,11,11,11,11,11,11,11,11,11,11,11,11,11,11,11,11,11,11,11,11,11,11,11,11,11,11,11,11,11,11,11,11,11,11,11,11,11,11,11,11,11,11,11,11,11,11,11,11,11,11,11,11,11,11,11,11,11,11,11,11,12},{14,11,11,11,11,11,11,11,11,11,11,11,11,11,11,11,11,11,11,11,11,11,11,11,11,11,11,11,11,11,11,11,11,11,11,11,11,11,11,11,11,11,11,11,11,11,11,11,11,11,11,11,11,11,11,11,11,11,11,11,11,11,11,11,11,11,11,11,11,11,11,11,11,11,12},{14,11,11,11,11,11,11,11,11,11,11,11,11,11,11,11,11,11,11,11,11,11,11,11,11,11,11,11,11,11,11,11,11,11,11,11,11,11,11,11,11,11,11,11,11,11,11,11,11,11,11,11,11,11,11,11,11,11,11,11,11,11,11,11,11,11,11,11,11,11,11,11,11,11,12},{14,11,11,11,11,11,11,11,11,11,11,11,11,11,11,11,11,11,11,11,11,11,39,11,11,39,11,11,0,1,1,1,1,1,1,1,1,1,2,11,11,11,11,11,11,11,11,11,11,11,11,0,1,1,1,1,1,2,11,11,11,11,11,11,11,11,11,11,11,11,11,11,11,11,12},{14,11,11,11,11,11,11,11,11,39,11,11,39,11,11,36,37,37,37,38,11,11,11,11,11,11,11,11,12,13,13,13,13,13,13,13,13,13,14,11,11,11,11,11,11,11,11,11,11,11,11,12,13,13,13,13,13,14,11,11,11,11,11,11,11,11,11,11,11,11,11,11,11,11,12},{16,1,1,1,1,1,2,11,11,11,11,11,11,11,11,11,11,11,11,11,11,11,11,11,11,11,11,11,12,13,13,13,13,13,13,13,13,13,16,1,1,1,1,1,1,1,1,1,1,1,1,17,13,13,13,13,13,16,1,1,1,1,1,2,11,11,0,1,1,1,1,1,1,1,17},{13,13,13,13,13,13,14,48,48,48,48,48,48,48,48,48,48,48,48,48,48,48,48,48,48,48,48,48,12,13,13,13,13,13,13,13,13,13,13,13,13,13,13,13,13,13,13,13,13,13,13,13,13,13,13,13,13,13,13,13,13,13,13,16,1,1,17,13,13,13,13,13,13,13,13},{13,13,13,13,13,13,14,49,49,49,49,49,49,49,49,49,49,49,49,49,49,49,49,49,49,49,49,49,12,13,13,13,13,13,13,13,13,13,13,13,13,13,13,13,13,13,13,13,13,13,13,13,13,13,13,13,13,13,13,13,13,13,13,13,13,13,13,13,13,13,13,13,13,13,13},{13,13,13,13,13,13,14,49,49,49,49,49,49,49,49,49,49,49,49,49,49,49,49,49,49,49,49,49,12,13,13,13,13,13,13,13,13,13,13,13,13,13,13,13,13,13,13,13,13,13,13,13,13,13,13,13,13,13,13,13,13,13,13,13,13,13,13,13,13,13,13,13,13,13,13}};
        int[][] levelData3 = {{13,13,13,13,13,13,4,25,25,25,25,25,25,25,25,25,25,25,25,25,25,25,25,25,25,25,25,25,25,25,25,25,25,25,25,25,25,25,25,25,25,25,25,25,25,25,25,25,25,25,25,25,25,25,25,25,25,25,25,25,25,25,25,25,25,25,25,25,25,25,25,25,25,25,25,25,25,25,25,5},{13,13,13,13,13,13,14,11,11,11,11,11,11,11,11,11,11,11,11,11,11,11,11,11,11,11,11,11,11,11,11,11,11,11,11,11,11,11,11,11,11,11,11,11,11,11,11,11,11,11,11,11,11,11,11,11,11,11,11,11,11,11,11,11,11,11,11,11,11,11,11,11,11,11,11,11,11,11,11,12},{4,25,25,25,25,25,26,11,11,11,11,11,11,11,11,11,11,11,11,11,11,11,11,11,11,11,11,11,11,11,11,11,11,11,11,11,11,11,11,11,11,11,11,11,11,11,11,11,11,11,11,11,11,11,11,11,11,11,11,11,11,11,11,11,11,11,11,11,11,11,11,11,11,11,11,11,11,11,11,12},{14,11,11,11,11,11,11,11,11,11,11,11,11,11,11,11,11,11,11,11,11,11,11,11,11,11,11,11,11,11,11,11,11,11,11,11,11,11,11,11,11,11,11,11,11,11,11,11,11,11,11,11,11,11,11,11,11,11,11,11,11,11,11,11,11,11,11,11,11,11,11,11,11,11,11,11,11,11,11,12},{14,11,11,11,11,11,11,11,11,11,11,39,11,11,11,11,11,39,11,36,37,37,37,37,37,37,37,38,11,11,11,11,11,11,11,11,11,11,11,11,11,11,11,11,11,11,11,11,11,11,11,11,11,11,11,11,11,11,11,11,11,11,11,11,11,11,11,11,11,11,11,11,11,11,11,11,11,11,11,12},{14,11,11,11,11,11,11,11,11,11,11,11,11,11,11,39,11,11,11,11,11,11,11,11,11,11,11,11,11,11,11,11,11,11,11,11,11,11,11,11,11,11,11,11,11,11,11,11,11,11,11,11,11,11,11,11,11,11,11,11,11,11,36,37,37,38,11,11,11,36,37,38,11,11,11,11,11,11,11,12},{14,11,11,11,11,11,11,11,11,11,11,11,11,39,11,11,11,11,11,11,11,11,11,11,11,11,11,11,11,11,11,11,11,11,11,11,11,11,11,11,11,11,11,11,11,11,11,11,11,11,11,11,11,11,11,11,36,37,37,38,11,11,11,11,11,11,11,11,11,11,11,11,11,11,11,11,11,11,11,12},{14,11,11,11,11,11,11,11,11,11,11,11,11,11,11,11,11,11,11,11,11,11,11,11,11,11,11,11,11,11,11,11,11,11,11,11,11,11,11,11,11,11,11,11,11,11,11,11,11,11,36,37,37,38,11,11,11,11,11,11,11,11,11,11,11,11,36,37,38,11,11,11,11,36,38,11,11,11,11,12},{14,11,11,11,11,11,11,0,1,1,1,2,11,11,11,11,11,11,11,11,11,11,11,11,11,11,11,11,11,0,1,1,2,11,11,11,11,11,11,3,11,11,11,3,11,11,11,3,11,11,11,11,11,11,36,38,11,11,11,11,11,11,11,11,11,11,11,11,11,11,11,11,11,11,11,11,11,11,11,12},{14,11,11,11,11,11,11,12,13,13,13,14,11,11,11,11,11,11,11,11,11,11,11,11,11,11,11,11,11,12,13,13,14,11,11,11,11,11,11,15,11,11,11,15,11,11,11,15,11,11,11,11,11,11,11,11,11,11,11,11,11,11,11,11,11,11,11,11,11,11,11,11,11,11,11,36,38,11,11,12},{16,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,2,11,11,15,11,11,11,15,11,11,11,15,11,11,11,11,11,11,11,11,11,11,11,11,11,11,11,11,11,11,11,11,11,11,11,11,11,11,11,11,11,11,36,12},{13,13,13,13,13,13,13,13,13,13,13,13,13,13,13,13,13,13,13,13,13,13,13,13,13,13,13,13,13,13,13,13,13,13,13,13,14,11,11,27,11,11,11,27,11,11,11,27,11,11,11,11,11,36,37,37,37,37,37,38,11,11,11,11,11,36,38,11,11,36,38,11,11,11,11,11,11,11,11,12},{13,13,13,13,13,13,13,13,13,13,13,13,13,13,13,13,13,13,13,13,13,13,13,13,13,13,13,13,13,13,13,13,13,13,13,13,14,48,48,48,48,48,48,48,48,48,48,48,48,48,48,48,48,48,48,48,48,48,48,48,48,48,48,48,48,48,48,48,48,48,48,48,48,0,1,1,1,1,1,17},{13,13,13,13,13,13,13,13,13,13,13,13,13,13,13,13,13,13,13,13,13,13,13,13,13,13,13,13,13,13,13,13,13,13,13,13,14,49,49,49,49,49,49,49,49,49,49,49,49,49,49,49,49,49,49,49,49,49,49,49,49,49,49,49,49,49,49,49,49,49,49,49,49,12,13,13,13,13,13,13}};
        int[][] levelData4 = {{13,13,13,13,13,13,4,25,25,25,25,25,25,25,25,25,25,25,25,25,25,25,25,25,25,25,25,25,25,25,25,25,25,25,25,25,25,25,25,25,25,25,25,25,25,25,25,25,25,25,25,25,25,25,25,25,25,25,25,25,25,25,25,25,25,25,25,25,5},{13,13,13,13,13,13,14,11,11,11,11,11,11,11,11,11,11,11,11,11,11,11,11,11,11,11,11,11,11,11,11,11,11,11,11,11,11,11,11,11,11,11,11,11,11,11,11,11,11,11,11,11,11,11,11,11,11,11,11,11,11,11,11,11,11,11,11,11,12},{4,25,25,25,25,25,26,11,11,11,11,11,11,11,11,11,11,11,11,11,11,11,11,11,11,11,11,11,11,11,11,11,11,11,11,11,11,11,11,11,11,11,11,11,11,11,11,11,11,11,11,11,11,11,11,11,11,11,11,11,11,11,11,11,11,11,11,11,12},{14,11,11,11,11,11,11,11,11,11,11,11,11,11,11,11,11,11,11,11,11,11,11,11,11,11,11,11,11,11,11,11,11,11,11,11,11,11,11,11,11,11,11,11,11,11,11,11,11,11,11,11,11,11,11,11,11,11,11,11,11,11,11,11,11,11,11,11,12},{14,11,11,11,11,11,11,11,11,11,11,11,11,11,11,11,11,11,11,11,11,11,11,11,11,11,11,11,11,11,11,11,11,36,37,37,37,37,37,37,37,37,37,37,37,37,37,37,38,11,11,11,11,11,11,11,11,11,11,11,11,11,11,11,11,11,11,11,12},{14,11,11,11,11,11,11,11,11,11,11,11,11,11,11,11,11,11,11,11,11,11,11,11,11,11,11,11,11,11,11,11,11,11,11,11,11,11,11,11,11,11,11,11,11,11,11,11,11,39,11,39,11,11,39,11,39,11,11,39,11,11,39,11,11,39,11,11,12},{14,11,11,11,11,11,11,11,11,11,11,11,11,11,11,11,11,11,11,11,11,11,11,11,11,11,0,1,1,2,11,39,11,11,11,11,11,11,11,11,11,11,11,11,11,11,11,11,11,11,11,11,11,11,11,11,11,11,11,11,11,11,11,11,11,11,11,11,12},{14,11,11,11,11,11,11,11,11,11,11,11,11,11,11,11,11,11,11,11,0,1,1,2,11,11,24,25,25,26,11,11,11,11,11,11,11,11,11,11,11,11,11,11,11,11,11,11,11,11,11,11,11,11,11,11,11,11,11,11,11,11,11,39,11,11,11,11,12},{14,11,11,11,11,11,11,0,1,1,1,2,11,11,0,1,1,2,11,11,24,25,25,26,11,11,11,11,11,11,11,11,11,39,11,11,11,11,11,11,11,11,11,11,11,11,11,11,11,11,11,11,11,11,11,11,11,11,11,11,11,11,11,11,11,39,11,11,12},{14,11,11,11,11,11,11,12,13,13,13,14,11,11,24,25,25,26,11,11,11,11,11,11,11,11,11,11,11,11,11,11,11,11,11,11,11,11,11,11,11,11,11,11,11,11,11,11,11,11,11,11,11,11,11,11,11,11,11,11,11,11,11,11,11,11,11,11,12},{16,1,1,1,1,1,1,17,13,13,13,14,11,11,11,11,11,11,11,11,11,11,11,11,11,11,11,11,11,11,11,39,11,11,11,11,11,11,0,2,11,0,2,11,0,2,11,0,2,11,11,39,11,11,11,39,11,11,11,11,11,39,11,39,11,39,11,11,12},{13,13,13,13,13,13,13,13,13,13,13,14,11,11,11,11,11,11,11,11,11,11,11,11,11,11,11,11,11,11,11,11,11,36,37,37,37,37,37,37,37,37,37,37,37,37,37,43,26,11,11,11,11,39,11,11,11,36,38,11,11,11,11,11,11,11,11,11,12},{13,13,13,13,13,13,13,13,13,13,13,14,48,48,48,48,48,48,48,48,48,48,48,48,48,48,48,48,48,48,48,48,48,48,48,48,48,48,48,48,48,48,48,48,48,48,48,48,48,48,48,48,48,48,48,48,48,48,48,48,48,48,48,48,48,48,48,48,12},{13,13,13,13,13,13,13,13,13,13,13,14,49,49,49,49,49,49,49,49,49,49,49,49,49,49,49,49,49,49,49,49,49,49,49,49,49,49,49,49,49,49,49,49,49,49,49,49,49,49,49,49,49,49,49,49,49,49,49,49,49,49,49,49,49,49,49,49,12}};
        int[][] levelData5 = {{4,25,25,25,25,25,25,25,25,25,25,25,25,25,25,25,25,25,25,25,25,25,25,25,25,25,25,25,25,25,25,25,25,25,25,25,25,25,25,25,25,25,25,25,25,25,25,25,25,25,25,25,25,25,25,25,25,25,25,25,25,25,25,25,25,25,25,25,25,25,25,25,25,25,25,25,25,25,25,25,25,25,25,25,25,25,25,25,25,25,25,25,25,25,25,25,25,25,25,5},{14,11,11,11,11,11,11,11,11,11,11,11,11,11,11,11,11,11,11,11,11,11,11,11,11,11,11,11,11,11,11,11,11,11,11,11,11,11,11,11,11,11,11,11,11,11,11,11,11,11,11,11,11,11,11,11,11,11,11,11,11,11,11,11,11,11,11,11,11,11,11,11,11,11,11,11,11,11,11,11,11,11,11,11,11,11,11,11,11,11,11,11,11,11,11,11,11,11,11,12},{14,11,11,11,11,11,11,11,11,11,11,11,11,11,11,11,11,11,11,11,11,11,11,11,11,11,11,0,1,1,1,1,1,1,1,1,1,1,1,1,1,2,11,11,11,11,11,11,11,11,11,11,11,11,11,11,11,11,11,11,11,11,11,11,11,11,11,11,11,11,11,11,11,11,11,11,11,11,11,11,11,11,11,11,11,11,11,11,11,11,11,11,11,11,11,11,11,11,11,12},{14,11,11,11,11,36,37,37,37,37,37,37,37,37,37,37,38,11,11,11,11,11,11,11,36,37,37,18,13,13,13,13,13,13,13,13,13,13,13,13,13,7,37,37,37,37,37,37,37,37,37,37,37,37,37,37,37,37,37,37,37,37,37,37,38,11,11,11,11,11,11,11,11,11,11,11,11,11,11,11,11,11,11,11,11,11,11,11,11,11,11,11,11,11,11,11,11,11,11,12},{14,11,11,11,11,11,11,11,11,11,11,11,11,11,11,11,11,11,11,11,11,36,38,11,11,11,11,12,13,13,13,13,13,13,13,13,13,13,13,13,13,14,11,11,11,11,11,11,11,11,11,11,11,11,11,11,11,11,11,11,11,11,11,11,11,11,11,11,11,11,0,1,1,1,1,1,1,2,11,11,0,1,2,11,11,0,1,2,11,11,0,2,11,39,11,11,11,11,11,12},{14,11,39,11,11,11,11,11,11,11,11,11,11,11,11,11,11,11,36,38,11,11,11,11,11,11,11,12,13,13,4,25,25,25,5,13,4,25,25,25,5,14,11,11,11,11,11,11,11,11,11,11,11,11,11,11,11,11,11,11,11,11,11,11,11,11,11,11,11,11,12,13,13,13,13,13,13,16,1,1,17,13,16,1,1,17,13,16,1,1,17,14,11,11,11,11,39,11,11,12},{14,11,11,11,11,11,11,11,11,11,11,11,11,11,11,11,11,11,11,11,11,11,11,11,11,11,11,24,25,25,26,11,11,11,24,25,26,11,11,11,24,26,11,39,11,11,39,11,11,11,39,11,11,39,11,11,11,11,11,11,11,11,11,11,0,1,1,1,1,1,17,4,25,25,25,25,25,25,25,25,25,25,25,25,25,25,25,25,25,25,5,14,11,11,11,11,11,11,11,12},{14,11,11,11,0,1,1,2,11,11,0,1,2,11,11,11,11,11,11,11,11,11,11,11,11,11,11,11,11,11,11,11,11,11,11,11,11,11,11,11,11,11,11,11,11,11,11,11,11,11,11,11,11,11,11,11,11,11,11,11,11,36,38,11,24,25,25,25,25,25,25,26,11,11,11,11,11,11,11,11,11,11,11,11,11,11,11,11,11,11,12,14,11,39,11,11,11,11,11,12},{14,11,11,11,12,13,13,14,11,11,12,13,14,11,11,11,11,11,11,11,11,11,11,11,11,36,37,37,37,37,37,38,11,36,37,37,37,38,11,36,37,37,38,11,36,38,11,11,39,11,11,36,38,11,11,39,11,11,11,11,11,11,11,11,11,11,11,11,11,11,11,11,11,11,11,11,11,11,11,11,11,11,11,11,11,11,11,11,11,11,12,14,11,11,11,11,11,11,11,12},{14,11,11,11,12,13,13,16,1,1,17,13,14,11,11,39,11,11,11,39,11,11,39,11,11,11,11,11,11,11,11,11,39,11,11,11,11,11,39,11,11,11,11,11,11,11,11,11,11,11,11,11,11,11,11,11,11,11,39,11,11,11,11,11,11,11,11,11,11,11,11,11,11,11,11,11,11,11,11,11,11,11,11,11,11,11,11,11,11,11,12,14,11,36,37,37,37,38,11,12},{14,11,11,11,24,25,25,25,25,25,25,25,26,11,11,11,11,11,11,11,11,11,11,11,11,11,11,11,11,11,11,11,11,11,11,11,11,11,11,11,11,11,11,11,11,11,11,11,11,11,11,11,11,11,11,11,11,11,11,11,11,11,11,36,37,37,37,37,37,37,37,38,11,11,11,11,11,11,11,11,11,11,11,11,11,11,11,11,11,11,12,14,11,11,11,11,11,11,11,12},{14,11,11,11,11,11,11,11,11,11,11,11,11,11,11,11,11,11,11,11,11,11,11,11,11,11,11,11,11,11,11,11,11,11,11,11,11,11,11,11,11,11,11,11,11,11,11,11,11,11,11,11,11,11,11,11,11,11,11,11,36,38,11,11,11,11,11,11,11,11,11,11,11,11,11,39,11,11,11,39,11,11,39,11,36,37,37,37,37,37,43,26,11,11,11,11,11,11,11,12},{14,48,48,48,48,48,48,48,48,48,48,48,48,48,48,48,48,48,48,48,48,48,48,48,48,48,48,48,48,48,48,48,48,48,48,48,48,48,48,48,48,48,48,48,48,48,48,48,48,48,48,48,48,48,48,48,48,48,48,48,48,48,48,48,48,48,48,48,48,48,48,48,48,48,48,48,48,48,48,48,48,48,48,48,48,48,48,48,48,48,48,48,48,48,48,48,48,48,48,12},{14,49,49,49,49,49,49,49,49,49,49,49,49,49,49,49,49,49,49,49,49,49,49,49,49,49,49,49,49,49,49,49,49,49,49,49,49,49,49,49,49,49,49,49,49,49,49,49,49,49,49,49,49,49,49,49,49,49,49,49,49,49,49,49,49,49,49,49,49,49,49,49,49,49,49,49,49,49,49,49,49,49,49,49,49,49,49,49,49,49,49,49,49,49,49,49,49,49,49,12}};

        // Call the method to test
        int[][] data1 = level1.getLevelData();
        int[][] data2 = level2.getLevelData();
        int[][] data3 = level3.getLevelData();
        int[][] data4 = level4.getLevelData();
        int[][] data5 = level5.getLevelData();

        // Verify the expected result
        Assertions.assertArrayEquals(levelData1, data1);
        Assertions.assertArrayEquals(levelData2, data2);
        Assertions.assertArrayEquals(levelData3, data3);
        Assertions.assertArrayEquals(levelData4, data4);
        Assertions.assertArrayEquals(levelData5, data5);
    }

    @Test
    public void testGetLvlOffset() {

        // Call the method to test
        int lvlOffset1 = level1.getLvlOffset();
        int lvlOffset2 = level2.getLvlOffset();
        int lvlOffset3 = level3.getLvlOffset();
        int lvlOffset4 = level4.getLvlOffset();
        int lvlOffset5 = level5.getLvlOffset();

        // Verify the expected result
        Assertions.assertEquals(1536, lvlOffset1);
        Assertions.assertEquals(3136, lvlOffset2);
        Assertions.assertEquals(3456, lvlOffset3);
        Assertions.assertEquals(2752, lvlOffset4);
        Assertions.assertEquals(4736, lvlOffset5);
    }
}

