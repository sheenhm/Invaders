package screen;

import engine.Cooldown;
import engine.Core;
import engine.Player;

import java.awt.event.KeyEvent;
import java.io.IOException;

/**
 * Implements the title screen.
 * 
 * @author <a href="mailto:RobertoIA1987@gmail.com">Roberto Izquierdo Amo</a>
 * 
 */

public class LoginScreen extends Screen {

	/** Milliseconds between changes in user selection. */
	private static final int SELECTION_TIME = 200;
	private final char[] name;
	/** Character of players name selected for change. */
	private int nameCharSelected;
	/** Time between changes in user selection. */
	private final Cooldown selectionCooldown;
	/** Code of first mayus character. */
	private static final int FIRST_CHAR = 65;
	/** Code of last mayus character. */
	private static final int LAST_CHAR = 90;


	/**
	 * Constructor, establishes the properties of the screen.
	 *
	 * @param width
	 *            Screen width.
	 * @param height
	 *            Screen height.
	 * @param fps
	 *            Frames per second, frame rate at which the game is run.
	 */
	public LoginScreen(final int width, final int height, final int fps) {
		super(width, height, fps);

		// Defaults to play.
		this.returnCode = 2;
		this.selectionCooldown = Core.getCooldown(SELECTION_TIME);
		this.selectionCooldown.reset();
		this.name = "AAA".toCharArray();
		this.nameCharSelected = 0;
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

		if (isProcessInput()) {
			processInput();
		}
	}

	/**
	 * Checks whether it's allowed to process user input.
	 *
	 * @return True if input processing is allowed, false otherwise.
	 */
	private boolean isProcessInput() {
		return selectionCooldown.checkFinished() && inputDelay.checkFinished();
	}

	/**
	 * Retrieves the key that is currently pressed.
	 *
	 * @return The keycode of the pressed key, or null if no key is pressed.
	 */
	private Integer getPressedKey() {
		return inputManager.getKeyCode();
	}

	/**
	 * Processes user input.
	 */
	private void processInput() {
		Integer pressedKey = getPressedKey();

		if (pressedKey != null) {
			handleKeyPress(pressedKey);
		}
	}

	/**
	 * Handles key presses based on the given keycode.
	 *
	 * @param key The keycode of the pressed key.
	 */
	private void handleKeyPress(int key) {
		switch (key) {
			case KeyEvent.VK_RIGHT:
				handleRightKey();
				break;
			case KeyEvent.VK_LEFT:
				handleLeftKey();
				break;
			case KeyEvent.VK_UP:
				handleUpKey();
				break;
			case KeyEvent.VK_DOWN:
				handleDownKey();
				break;
			case KeyEvent.VK_SPACE:
				handleSpaceKey();
				break;
			default:
				break;
		}
	}

	/** Handles right arrow key press */
	private void handleRightKey() {
		nameCharSelected = (nameCharSelected == 2) ? 0 : nameCharSelected + 1;
		resetSelectionCooldown();
	}

	/** Handles left arrow key press */
	private void handleLeftKey() {
		nameCharSelected = (nameCharSelected == 0) ? 2 : nameCharSelected - 1;
		resetSelectionCooldown();
	}

	/** Handles up arrow key press */
	private void handleUpKey() {
		updateNameChar(1);
		resetSelectionCooldown();
	}

	/** Handles down arrow key press */
	private void handleDownKey() {
		updateNameChar(-1);
		resetSelectionCooldown();
	}

	/**
	 * Updates the selected character in the username based on the given offset.
	 */
	private void updateNameChar(int offset) {
		name[nameCharSelected] = (char) ((name[nameCharSelected] == LAST_CHAR) ? FIRST_CHAR : name[nameCharSelected] + offset);
	}

	/** Handles the spacebar key press*/
	private void handleSpaceKey() {
		try {
			processSpaceKey();
		} catch (IOException e) {
			handlePlayerFileException(e);
		}

		returnCode = 1;
		isRunning = false;
	}

	/**
	 * Saves a new player or loads an existing player based on the current username.
	 */
	private void processSpaceKey() throws IOException {
		Player loadedPlayer = Core.getFileManager().loadPlayer(name);
		if (loadedPlayer == null) {
			Core.getFileManager().saveNewPlayer(name);
			logger.info("New player saved successfully");
		} else {
			Core.getFileManager().updateLoginTimeOfCurrentPlayer();
			logger.info("Player loaded successfully");
		}
	}

	private void resetSelectionCooldown() {
		selectionCooldown.reset();
	}

	private void handlePlayerFileException(IOException e) {
		logger.warning("Couldn't load or save player! Error: " + e.getMessage());
	}



	/**
	 * Draws the elements associated with the screen.
	 */
	private void draw() {
		drawManager.initDrawing(this);
		drawManager.drawUsernameInput(this, this.name, this.nameCharSelected);

		drawManager.completeDrawing(this);
	}
}
