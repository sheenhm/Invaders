package engine;
import org.junit.jupiter.api.Test;
import screen.LoginScreen;
import java.io.IOException;
import static org.junit.jupiter.api.Assertions.*;

class LoginScreenTest {
    private LoginScreen createLoginScreen() {
        LoginScreen loginScreen = new LoginScreen(448, 520, 60);
        return loginScreen;
    }

    @Test
    void testHandleRightKey() {
        LoginScreen loginScreen = createLoginScreen();
        int initialSelectValue = loginScreen.nameCharSelected;
        loginScreen.handleRightKey();
        assertEquals((initialSelectValue + 1) % 3, loginScreen.nameCharSelected);
    }

    @Test
    void testHandleLeftKey() {
        LoginScreen loginScreen = createLoginScreen();
        int initialSelection = loginScreen.nameCharSelected;
        loginScreen.handleLeftKey();
        assertEquals((initialSelection - 1 + 3) % 3, loginScreen.nameCharSelected);
    }

    @Test
    void testHandleUpKey() {
        LoginScreen loginScreen = createLoginScreen();
        char initialChar = loginScreen.name[loginScreen.nameCharSelected];
        loginScreen.handleUpKey();
        assertEquals((char) ((initialChar == 'Z') ? 'A' : initialChar + 1), loginScreen.name[loginScreen.nameCharSelected]);
    }

    @Test
    void testUpdateNameChar() {
        LoginScreen loginScreen = createLoginScreen();
        char initialChar = loginScreen.name[loginScreen.nameCharSelected];
        int offset = 2; // Replace with your desired offset
        loginScreen.updateNameChar(offset);
        assertEquals((char) ((initialChar + offset - 'A' + 26) % 26 + 'A'), loginScreen.name[loginScreen.nameCharSelected]);
    }

    @Test
    void testProcessSpaceKey() {
        LoginScreen loginScreen = createLoginScreen();
        try {
            loginScreen.processSpaceKey();
            Player loadedPlayer = Core.getFileManager().loadPlayer(loginScreen.name);
            assertNotNull(loadedPlayer);
            assertEquals(String.valueOf(loginScreen.name), loadedPlayer.getName());
        } catch (IOException e) {
            fail("IOException: " + e.getMessage());
        }
    }
}
