import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;

import static org.junit.jupiter.api.Assertions.*;

class SelectScreenTest {

    private SelectScreen selectScreen;

    @BeforeEach
    void setUp() {
        selectScreen = new SelectScreen(800, 600, 60);
    }

    @Test
    void testInitialization() {
        assertEquals(8, selectScreen.getReturnCode());
        assertEquals(1, SelectScreen.gameMode);
        assertTrue(SelectScreen.skillModeOn);
        assertFalse(selectScreen.isRunning());
        assertFalse(selectScreen.canEscape);
    }

    @Test
    void testRun() {
        assertEquals(8, selectScreen.run());
    }

    @Test
    void testUpdateEscape() {
        selectScreen.getInputManager().pressKey(KeyEvent.VK_ESCAPE);
        selectScreen.update();
        assertEquals(1, selectScreen.getReturnCode());
        assertFalse(selectScreen.isRunning());
        assertFalse(selectScreen.canEscape);
    }

    @Test
    void testUpdateToggleGameMode() {
        selectScreen.getInputManager().pressKey(KeyEvent.VK_RIGHT);
        selectScreen.update();
        assertEquals(2, SelectScreen.gameMode);

        SelectScreen.gameMode = 1;

        selectScreen.getInputManager().pressKey(KeyEvent.VK_LEFT);
        selectScreen.update();
        assertEquals(2, SelectScreen.gameMode);
    }

    @Test
    void testUpdateSpaceKey() {
        selectScreen.getInputManager().pressKey(KeyEvent.VK_SPACE);
        selectScreen.update();
        assertTrue(selectScreen.canEscape);

        selectScreen.canEscape = false;

        assertTrue(selectScreen.selectionCooldown.checkFinished());
        selectScreen.update();
        assertFalse(selectScreen.selectionCooldown.checkFinished());
    }

    @Test
    void testUpdateToggleSkillMode() {
        selectScreen.canEscape = true;
        selectScreen.getInputManager().pressKey(KeyEvent.VK_RIGHT);
        selectScreen.update();
        assertFalse(SelectScreen.skillModeOn);
    }

}
