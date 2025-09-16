package controller;

import javax.swing.JFrame;

import model.GameLevel;
import model.GameState;
import view.AdvancedLevel;
import view.BeginnerLevel;
import view.IntermediateLevel;
import view.LevelSelectionScreen;
import view.SettingsDialog;
import view.StartScreen;

/**
 * Central UI controller: routes between screens and starts/resumes levels.
 */
public class GameController {

    private GameState currentState;  // holds current game progress
    private JFrame currentFrame;     // keeps track of the active game window

    /** Launch the start screen. */
    public void startGame() {
        closeCurrentFrame();
        currentFrame = new StartScreen(this);
    }

    /** Show the main menu (alias of start). */
    public void showMainMenu() {
        startGame();
    }

    /** Open the level selection screen. */
    public void showLevelSelection() {
        closeCurrentFrame();
        currentFrame = new LevelSelectionScreen(this);
    }

    /** Open the settings dialog (no specific parent). */
    public void showSettings() {
        SettingsDialog dialog = new SettingsDialog(currentFrame);
        dialog.setVisible(true);
    }

    /** Open the settings dialog with an explicit parent frame. */
    public void showSettings(JFrame parent) {
        SettingsDialog dialog = new SettingsDialog(parent);
        dialog.setVisible(true);
    }

    /** Start a new game for the selected level by opening the corresponding screen. */
    public void startNewGame(GameLevel level) {
        if (level == null) return;

        closeCurrentFrame();
        switch (level) {
            case BEGINNER:
                currentFrame = new BeginnerLevel(this);
                break;
            case INTERMEDIATE:
                currentFrame = new IntermediateLevel(this);
                break;
            case ADVANCED:
                currentFrame = new AdvancedLevel(this);
                break;
            default:
                startGame();
        }

        // Create a new game state
        currentState = new GameState(level);
    }

    /** Resume game if there is a saved state. */
    public void resumeGame() {
        if (currentState == null) {
            // No saved game → go back to level selection
            showLevelSelection();
            return;
        }

        closeCurrentFrame();
        GameLevel level = currentState.getLevel();

        switch (level) {
            case BEGINNER:
                currentFrame = new BeginnerLevel(this);
                break;
            case INTERMEDIATE:
                currentFrame = new IntermediateLevel(this);
                break;
            case ADVANCED:
                currentFrame = new AdvancedLevel(this);
                break;
            default:
                showMainMenu();
        }
    }

    /** Pause the game (simply save state, keep currentFrame open). */
    public void pauseGame() {
        if (currentState != null) {
            currentState.setPaused(true);
        }
    }

    /** Called when a tile is selected. */
    public void onTileSelected(int row, int col) {
        if (currentState != null) {
            currentState.handleTileSelection(row, col);
        }
    }

    /** Returns the active game state. */
    public GameState getGameState() {
        return currentState;
    }

    /** Utility: Close the current frame before switching screens. */
    private void closeCurrentFrame() {
        if (currentFrame != null) {
            currentFrame.dispose();
            currentFrame = null;
        }
    }
}
