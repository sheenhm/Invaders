package entity;

import engine.DrawManager;
import engine.GameState;

public class EnemyShipB extends EnemyShip {
    /** Scale of HP */
    private final double HPPOWER = .4;
    /** Score earned upon removal */
    private final int POINT = 10;
    public EnemyShipB(final int positionX, final int positionY,
                      final DrawManager.SpriteType spriteType, final GameState gameState) {
        super(positionX, positionY, spriteType, gameState);
        super.HP = (int)(super.HP * HPPOWER);
        super.pointValue = POINT;
    }

    public final void update() {
        if (this.animationCooldown.checkFinished()) {
            this.animationCooldown.reset();
            if (spriteType == DrawManager.SpriteType.EnemyShipB1)
                spriteType = DrawManager.SpriteType.EnemyShipB2;
            else
                spriteType = DrawManager.SpriteType.EnemyShipB1;
        }
    }
}
