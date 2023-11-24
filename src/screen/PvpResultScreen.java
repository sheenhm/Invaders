package screen;

import engine.*;

import java.awt.event.KeyEvent;
public class PvpResultScreen extends Screen {

    /**
     * Milliseconds between changes in user selection.
     */
    private static final int SELECTION_TIME = 200;

    /**
     * Code of first mayus character.
     */
    private static final int FIRST_CHAR = 65;
    /**
     * Code of last mayus character.
     */
    private static final int LAST_CHAR = 90;

    /**
     * 1p's lives left.
     */
    private final int livesRemaining1;
    /**
     * 2p's lives left.
     */
    private int livesRemaining2;
    /**
     * Total bullets shot by the players.
     */
    private final int bulletsShot1;
    private int bulletsShot2;
    /**
     * Time between changes in user selection.
     */
    private final Cooldown selectionCooldown;

    /**
     * Constructor, establishes the properties of the screen.
     *
     * @param width     Screen width.
     * @param height    Screen height.
     * @param fps       Frames per second, frame rate at which the game is run.
     * @param gameState Current game state.
     */
    public PvpResultScreen(final int width, final int height, final int fps,
                       final GameState gameState) {
        super(width, height, fps);

        this.livesRemaining1 = gameState.getLivesRemaining1p();
        this.bulletsShot1 = gameState.getBulletsShot1();
        this.livesRemaining2 = gameState.getLivesRemaining2p();
        this.bulletsShot2 = gameState.getBulletsShot2();
        this.selectionCooldown = Core.getCooldown(SELECTION_TIME);
        this.selectionCooldown.reset();
    }

    /**
     * Starts the action.
     *
     * @return Next screen code.
     */
    public final int run() {
        super.run();

        return this.returnCode;
    }

    /**
     * Updates the elements on screen and checks for events.
     */
    protected final void update() {
        super.update();

        draw();

        if (this.inputDelay.checkFinished()) {
            if (inputManager.isKeyDown(KeyEvent.VK_ESCAPE)) {
                // Return to main menu.
                this.returnCode = 1;
                this.isRunning = false;

            } else if (inputManager.isKeyDown(KeyEvent.VK_SPACE)) {
                // Play again.
                this.returnCode = 7;
                this.isRunning = false;
            }
        }

    }
    private void draw() {
        drawManager.initDrawing(this);
        String winner = "1p";
        if (this.livesRemaining2 > 0) winner = "2p";
        drawManager.drawGameOver(this, this.inputDelay.checkFinished(), winner);
        drawManager.drawResults(this, this.livesRemaining1, this.livesRemaining2, (3 - (float) this.livesRemaining2)
                / this.bulletsShot1, (3 - (float) this.livesRemaining1) / this.bulletsShot2);

        drawManager.completeDrawing(this);
    }
}
