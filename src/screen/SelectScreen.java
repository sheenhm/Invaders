package screen;

import engine.Cooldown;
import engine.Core;
import engine.SoundManager;

import java.awt.event.KeyEvent;

public class SelectScreen extends Screen {

    /** 사용자 선택 간의 밀리초. */
    private static final int SELECTION_TIME = 200;

    /** 사용자 선택 간의 시간. */
    private Cooldown selectionCooldown;
    /** 1P, 2P 모드 확인 */
    public static int gameMode = 1;
    /** 스킬 모드 확인 */
    public static boolean skillModeOn = true;
    /** 스페이스 키가 눌렸을 때 별도의 동작을 수행하는 변수 */
    private boolean canEscape = false;

    /**
     * 생성자, 화면의 속성을 설정합니다.
     *
     * @param width  화면 너비.
     * @param height 화면 높이.
     * @param fps    초당 프레임, 게임이 실행되는 프레임 속도.
     */
    public SelectScreen(int width, int height, int fps) {
        super(width, height, fps);
        this.returnCode = 8;
        this.selectionCooldown = Core.getCooldown(SELECTION_TIME);
        this.selectionCooldown.reset();
    }

    /**
     * 동작을 시작합니다.
     *
     * @return 다음 화면 코드.
     */
    public final int run() {
        super.run();

        return this.returnCode;
    }

    /**
     * 화면의 요소를 업데이트하고 이벤트를 확인합니다.
     */
    protected final void update() {
        super.update();

        draw();
        if (this.selectionCooldown.checkFinished()
                && this.inputDelay.checkFinished()) {
            if (!canEscape) {
                if (inputManager.isKeyDown(KeyEvent.VK_ESCAPE)) {
                    this.returnCode = 1;
                    this.isRunning = false;
                }
                if (inputManager.isKeyDown(KeyEvent.VK_RIGHT)
                        || inputManager.isKeyDown(KeyEvent.VK_D)
                        || inputManager.isKeyDown(KeyEvent.VK_LEFT)
                        || inputManager.isKeyDown(KeyEvent.VK_A)) {
                    gameMode = (gameMode == 1) ? 2 : 1;
                    this.selectionCooldown.reset();
                }
                if (inputManager.isKeyDown(KeyEvent.VK_SPACE)) {
                    SoundManager.playSound("SFX/S_MenuClick", "menu_select", false, false);
                    canEscape = true;
                    this.selectionCooldown.reset();
                }
            } else {
                if (inputManager.isKeyDown(KeyEvent.VK_ESCAPE)) {
                    canEscape = false;
                    this.selectionCooldown.reset();
                }
                if (inputManager.isKeyDown(KeyEvent.VK_RIGHT)
                        || inputManager.isKeyDown(KeyEvent.VK_D)
                        || inputManager.isKeyDown(KeyEvent.VK_LEFT)
                        || inputManager.isKeyDown(KeyEvent.VK_A)) {
                    skillModeOn = !skillModeOn;
                    this.selectionCooldown.reset();
                }
                if (inputManager.isKeyDown(KeyEvent.VK_SPACE)) {
                    this.isRunning = false;
                    SoundManager.playSound("SFX/S_MenuClick", "menu_select", false, false);
                }
            }
        }
    }

    /**
     * 화면과 관련된 요소를 그립니다.
     */
    private void draw() {
        drawManager.initDrawing(this);

        drawManager.drawSelect2PModeAndSkillModeScreen(this, gameMode, skillModeOn, canEscape);

        drawManager.completeDrawing(this);
    }
}
