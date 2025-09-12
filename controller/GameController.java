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
 * Central UI controller: routes between screens and starts levels.
 */
public class GameController {

    /** Launch the start screen. */
    public void startGame() {
        new StartScreen(this);
    }

    /** Show the main menu (alias of start). */
    public void showMainMenu() {
        startGame();
    }

    /** Open the level selection screen. */
    public void showLevelSelection() {
        new LevelSelectionScreen(this);
    }

    /** Open the settings dialog (no specific parent). */
    public void showSettings() {
        SettingsDialog dialog = new SettingsDialog((JFrame) null);
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

        switch (level) {
            case BEGINNER:
                new BeginnerLevel(this);
                break;
            case INTERMEDIATE:
                new IntermediateLevel(this);
                break;
            case ADVANCED:
                new AdvancedLevel(this);
                break;
            default:
                // Fallback to start screen if unknown
                startGame();
        }
    }

    public void onTileSelected(int finalRow, int finalCol) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'onTileSelected'");
    }

    public GameState getGameState() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'getGameState'");
    }

    public void resumeGame() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'resumeGame'");
    }

    public void pauseGame() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'pauseGame'");
    }
}


