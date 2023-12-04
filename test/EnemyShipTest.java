import engine.DrawManager;
import engine.GameState;
import entity.EnemyShip;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class EnemyShipTest {

    @Test
    @DisplayName("EnemyShip Construction Test")
    void testEnemyShipConstruction() {
        GameState gameState = new GameState(1, 0, 3, 0, 0);
        double hpPower = 1.5;
        double bulletSpeedPower = 2.0;
        double bulletCooldown = 1.0;
        int point = 50;

        EnemyShip enemyShip = new EnemyShip(10, 20,
                DrawManager.SpriteType.EnemyShipA1, gameState, hpPower, bulletSpeedPower, bulletCooldown, point);

        assertEquals(10, enemyShip.getPositionX());
        assertEquals(20, enemyShip.getPositionY());
        assertEquals(DrawManager.SpriteType.EnemyShipA1, enemyShip.getSpriteType());
        assertFalse(enemyShip.isDestroyed());
        assertEquals(point, enemyShip.getPointValue());
    }

    @Test
    @DisplayName("EnemyShip Movement Test")
    void testEnemyShipMovement() {
        GameState gameState = new GameState(1, 0, 3, 0, 0);
        EnemyShip enemyShip = new EnemyShip(0, 0,
                DrawManager.SpriteType.EnemyShipA1, gameState, 1.0, 1.0, 1.0, 50);

        enemyShip.move(5, 10);

        assertEquals(5, enemyShip.getPositionX());
        assertEquals(10, enemyShip.getPositionY());
    }
}