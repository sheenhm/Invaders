package test.engine;

import engine.SoundManager;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class SoundManagerTest {
    private static final String SOUND_FILE_PATH_SHORT = "SFX/S_Gameover";
    private static final String TEST_CLIP_NAME = "S_gameover";
    @Test
    void playSound() throws InterruptedException {
        SoundManager.playSound(SOUND_FILE_PATH_SHORT, TEST_CLIP_NAME, false, false);
        Thread.sleep(200);
        assertTrue(SoundManager.isPlaying(TEST_CLIP_NAME));

    }

    @Test
    void testPlaySound() throws InterruptedException {
        SoundManager.playSound(SOUND_FILE_PATH_SHORT, TEST_CLIP_NAME, false, false, 0.5f);
        Thread.sleep(100);
        assertTrue(SoundManager.isPlaying(TEST_CLIP_NAME));
    }

    @Test
    void stopSound() throws InterruptedException {
        SoundManager.stopSound(TEST_CLIP_NAME);
        Thread.sleep(100);
        assertFalse(SoundManager.isPlaying(TEST_CLIP_NAME));
    }

    @Test
    void testStopSound() throws InterruptedException {
        SoundManager.stopSound(TEST_CLIP_NAME, 2f);
        Thread.sleep(100);
        assertFalse(SoundManager.isPlaying(TEST_CLIP_NAME));
    }

    @Test
    void setMasterVolume() {
        SoundManager.setMasterVolume(200);
        assertTrue(SoundManager.getVolume() == 200);
    }

    @Test
    void isPlaying() throws InterruptedException {
        SoundManager.playSound(SOUND_FILE_PATH_SHORT, TEST_CLIP_NAME, false, false, 0.5f);
        Thread.sleep(100);
        assertTrue(SoundManager.isPlaying(TEST_CLIP_NAME));
    }

    @Test
    void playBGM() throws InterruptedException {
        SoundManager.playBGM(1);
        Thread.sleep(100);
        assertTrue(SoundManager.isPlaying("level1"));
    }

    @Test
    void resetBGM() throws InterruptedException {
        SoundManager.resetBGM();
        Thread.sleep(100);
        assertFalse(SoundManager.isPlaying("level1"));
        assertFalse(SoundManager.isPlaying("level2"));
        assertFalse(SoundManager.isPlaying("level3"));
        assertFalse(SoundManager.isPlaying("level4"));
        assertFalse(SoundManager.isPlaying("level5"));
        assertFalse(SoundManager.isPlaying("level6"));
        assertFalse(SoundManager.isPlaying("level7"));
    }

    @Test
    void getValue() throws InterruptedException {
        int volume = 200;

        for (int i = 0; i < 3; i++) {
            float minimum = -80;
            float maximum = 6;
            float one = ((Math.abs(minimum)+Math.abs(maximum))/100);
            float resTest = (float)(minimum + one*(50*Math.log10(volume)));
            Thread.sleep(100);
            if(resTest<minimum) assertTrue(SoundManager.getValue(volume) == minimum);
            else if(resTest>maximum) assertTrue(SoundManager.getValue(volume) == maximum);
            else  assertTrue(SoundManager.getValue(volume) == resTest);
            volume = volume - 50;
        }

    }
}