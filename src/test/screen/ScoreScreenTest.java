import engine.GameState;
import org.junit.jupiter.api.DisplayName;
import screen.ScoreScreen;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class ScoreScreenTest {

    private GameState gameState1P;
    private GameState gameState2P;
    private ScoreScreen scoreScreen1p;

    private ScoreScreen scoreScreen2p;

    @BeforeEach
    public void setUp() {
        gameState1P = new GameState(1, 1000, 3, 200, 50);
        scoreScreen1p = new ScoreScreen(800, 600, 60, gameState1P);

        gameState2P = new GameState(2, 800, 3, 2, 100, 150, 50, 50);
        scoreScreen2p = new ScoreScreen(800, 600, 60, gameState2P);
    }

    @Test
    @DisplayName("1p ScoreScreen initialize Test")
    public void testInitialization1p() {
        assertNotNull(scoreScreen1p);
        assertEquals(1, gameState1P.getMode());
        assertEquals(1000, gameState1P.getScore());
        assertEquals(3, gameState1P.getLivesRemaining1p());
        assertEquals(200, gameState1P.getBulletsShot1());
    }

    @Test
    @DisplayName("2p ScoreScreen initialize Test")
    public void testInitialization2p() {
        assertNotNull(scoreScreen2p);
        assertEquals(2, gameState2P.getMode());
        assertEquals(800, gameState2P.getScore());
        assertEquals(3, gameState2P.getLivesRemaining1p());
        assertEquals(2, gameState2P.getLivesRemaining2p());
        assertEquals(100, gameState2P.getBulletsShot1());
        assertEquals(150, gameState2P.getBulletsShot2());
    }
}