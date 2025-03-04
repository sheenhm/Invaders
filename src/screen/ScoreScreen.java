package screen;

import engine.*;

import java.awt.event.KeyEvent;
import java.io.IOException;
import java.util.Collections;
import java.util.List;

/**
 * Implements the score screen.
 *
 * @author <a href="mailto:RobertoIA1987@gmail.com">Roberto Izquierdo Amo</a>
 */
public class ScoreScreen extends Screen {

    /**
     * Milliseconds between changes in user selection.
     */
    private static final int SELECTION_TIME = 200;
    /**
     * Maximum number of high scores.
     */
    private static final int MAX_HIGH_SCORE_NUM = 7;
    /**
     * Code of first mayus character.
     */
    private static final int FIRST_CHAR = 65;
    /**
     * Code of last mayus character.
     */
    private static final int LAST_CHAR = 90;

    /**
     * Current gameMode.
     */
    private final int gameMode;
    /**
     * Current score.
     */
    private final int score;
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
     * Total ships destroyed by the player.
     */
    private final int shipsDestroyed1;
    private int shipsDestroyed2;
    /**
     * List of past high scores.
     */
    private List<Score> highScores;
    /**
     * Checks if current score is a new high score.
     */
    private boolean isNewRecord;
    /**
     * Player name for record input.
     */
    private char[] name;
    /**
     * Character of players name selected for change.
     */
    private int nameCharSelected;
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
    public ScoreScreen(final int width, final int height, final int fps,
                       final GameState gameState) {
        super(width, height, fps);

        this.gameMode = gameState.getMode();
        this.score = gameState.getScore();
        this.livesRemaining1 = gameState.getLivesRemaining1p();
        this.bulletsShot1 = gameState.getBulletsShot1();
        if (gameState.getMode() == 2) {
            this.livesRemaining2 = gameState.getLivesRemaining2p();
            this.bulletsShot2 = gameState.getBulletsShot2();
            this.shipsDestroyed2 = gameState.getShipsDestroyed2();
        }

        this.shipsDestroyed1 = gameState.getShipsDestroyed();
        this.isNewRecord = false;
        
        // Initialize player name and selection-related variables
        this.name = getPlayerName();
        this.nameCharSelected = 0;
        this.selectionCooldown = Core.getCooldown(SELECTION_TIME);
        this.selectionCooldown.reset();

        AchievementManager.getInstance().checkScore(this.score);

        // Load and update high scores
        loadHighScores();

        // Attempt to reset player items using the FileManager
        resetPlayerItems();
    }

    /**
     * Retrieves the player name from the file manager.
     *
     * @return The player name as a character array.
     */
    private char[] getPlayerName() {
        try {
            return Core.getFileManager().getCurrentPlayer().getName().toCharArray();
        } catch (IOException e) {
            logger.warning("An error occurred while accessing player data: " + e.getMessage());
            return new char[0];
        }
    }

    /**
     * Loads high scores from the file manager and updates the new record status.
     */
    private void loadHighScores() {
        try {
            this.highScores = Core.getFileManager().loadHighScores(this.gameMode);
            updateNewRecordStatus();
        } catch (IOException e) {
            logger.warning("Couldn't load high scores!");
        }
    }

    /**
     * Updates the new record status based on the current score and existing high scores.
     */
    private void updateNewRecordStatus() {
        if (highScores.size() < MAX_HIGH_SCORE_NUM || highScores.get(highScores.size() - 1).getScore() < this.score) {
            this.isNewRecord = true;
        }
    }

    /**
     * Attempts to reset player items using the FileManager.
     * Logs a warning if the reset operation fails.
     */
    private void resetPlayerItems() {
        try {
            Core.getFileManager().resetPlayerItem();
        } catch (IOException e) {
            logger.warning("Couldn't reset item!");
        }
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

        if (gameMode == 1) {
            draw();
        }
        else if(gameMode == 2){
            draw2();
        }

        if (this.inputDelay.checkFinished()) {
            // Handles input during the game record screen, allowing navigation back to the main menu or restarting the game.
            handleExitRecordScreenInput();
            
            if (this.isNewRecord && this.selectionCooldown.checkFinished()) {
                // Handles player name input during the record phase
                handlePlayerNameInput();
            }
        }

    }

    /**
     * Handles input during the game record screen, allowing navigation back to the main menu or restarting the game.
     */
    private void handleExitRecordScreenInput() {
        if (inputManager.isKeyDown(KeyEvent.VK_ESCAPE)) {
            // Return to the main menu.
            this.returnCode = 1;
            this.isRunning = false;
            saveScore(gameMode);
        } else if (inputManager.isKeyDown(KeyEvent.VK_SPACE)) {
            // Play again.
            this.returnCode = 7;
            this.isRunning = false;
            saveScore(gameMode);
        }
    }


    /**
     * Handles player name input during the record phase, allowing navigation and modification of the player's name.
     */
    private void handlePlayerNameInput() {
        if (inputManager.isKeyDown(KeyEvent.VK_RIGHT)) {
            if (this.nameCharSelected == 2) {
                this.nameCharSelected = 0;
            } else {
                this.nameCharSelected += 1;
            }
            this.selectionCooldown.reset();
        }
        if (inputManager.isKeyDown(KeyEvent.VK_LEFT)) {
            if (this.nameCharSelected == 0) {
                this.nameCharSelected = 2;
            } else {
                this.nameCharSelected -= 1;
            }
            this.selectionCooldown.reset();
        }
        if (inputManager.isKeyDown(KeyEvent.VK_UP)) {
            if (this.name[this.nameCharSelected] == LAST_CHAR) {
                this.name[this.nameCharSelected] = (char) FIRST_CHAR;
            } else {
                this.name[this.nameCharSelected] = (char) (this.name[this.nameCharSelected] + 1);
            }
            this.selectionCooldown.reset();
        }
        if (inputManager.isKeyDown(KeyEvent.VK_DOWN)) {
            if (this.name[this.nameCharSelected] == FIRST_CHAR) {
                this.name[this.nameCharSelected] = (char) LAST_CHAR;
            } else {
                this.name[this.nameCharSelected] = (char) (this.name[this.nameCharSelected] - 1);
            }
            this.selectionCooldown.reset();
        }
    }

    /**
     * Saves the score as a high score.
     *
     * @param gameMode Current game mode.
     */
    private void saveScore(final int gameMode) {
        highScores.add(new Score(new String(this.name), score));
        try {
            Core.getFileManager().updateCurrencyOfCurrentPlayer(score / 10);
        } catch (IOException e) {
            throw new RuntimeException();
        }
        Collections.sort(highScores);
        if (highScores.size() > MAX_HIGH_SCORE_NUM)
            highScores.remove(highScores.size() - 1);

        try {
            Core.getFileManager().saveHighScores(highScores, gameMode);
        } catch (IOException e) {
            logger.warning("Couldn't load high scores!");
        }
    }

    /**
     * Draws the elements associated with the screen.
     */
    private void draw() {
        drawManager.initDrawing(this);

        drawManager.drawGameOver(this, this.inputDelay.checkFinished(),
                this.isNewRecord);
        drawManager.drawResults(this, this.score, this.livesRemaining1,
                this.shipsDestroyed1, (float) this.shipsDestroyed1
                        / this.bulletsShot1, this.isNewRecord);

        if (this.isNewRecord)
            drawManager.drawNameInput(this, this.name, this.nameCharSelected);

        drawManager.completeDrawing(this);
    }

    /**
     * Draws the elements associated with the screen for 2P mode.
     */
    private void draw2() {
        drawManager.initDrawing(this);

        drawManager.drawGameOver(this, this.inputDelay.checkFinished(),
                this.isNewRecord);
        drawManager.drawResults(this, this.score, this.livesRemaining1, this.livesRemaining2,
                this.shipsDestroyed1 + this.shipsDestroyed2, (float) this.shipsDestroyed1
                        / this.bulletsShot1, (float) this.shipsDestroyed2 / this.bulletsShot2, this.isNewRecord);

        if (this.isNewRecord)
            drawManager.drawNameInput(this, this.name, this.nameCharSelected);

        drawManager.completeDrawing(this);
    }
}
