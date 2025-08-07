package view;

import controller.GameController;

/**
 * Simple wrapper for LevelSelectionScreen to preserve naming or abstraction.
 */
public class LevelSelectScreen {

    private final LevelSelectionScreen levelSelectionScreen;

    public LevelSelectScreen(GameController gameController) {
        levelSelectionScreen = new LevelSelectionScreen(gameController);
    }

    public void setVisible(boolean visible) {
        levelSelectionScreen.setVisible(visible);
    }
}
