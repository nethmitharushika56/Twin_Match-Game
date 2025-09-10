package controller;

import javax.swing.*;
import model.GameLevel;
import model.GameState;
import model.HighScoreManager;
import model.Tile;
import util.SoundManager;
import view.GameWindow;
import view.LevelSelectionScreen;
import view.StartScreen;
import view.BeginnerLevel;
import view.IntermediateLevel;
import view.AdvancedLevel;

import java.util.Timer;
import java.util.TimerTask;

/**
 * Main game controller that manages game state and coordinates between UI and logic
 */
public class GameController {

    private GameState gameState;
    private GameWindow gameWindow;
    private Timer gameTimer;

    /**
     * Configure game settings based on selected level
     */
    private void configureGameSettings(GameLevel level, GameState gameState) {
        switch (level) {
            case BEGINNER -> {
                gameState.setTotalPairs(4);
                gameState.setTimeLimit(0); // No time limit
            }
            case INTERMEDIATE -> {
                gameState.setTotalPairs(8);
                gameState.setTimeLimit(180); // 3 minutes
            }
            case ADVANCED -> {
                gameState.setTotalPairs(12);
                gameState.setTimeLimit(120); // 2 minutes
            }
        }
    }

    /**
     * Check if the selected tiles match
     */
    private void checkForMatch() {
        Tile first = gameState.getFirstSelection();
        Tile second = gameState.getSecondSelection();

        if (first != null && second != null) {
            if (first.getImageId() == second.getImageId()) {
                first.setMatched(true);
                second.setMatched(true);
                gameState.incrementScore(10);
                gameState.incrementMatchesFound();
                SoundManager.playMatchSound();

                if (gameState.getMatchesFound() >= gameState.getTotalPairs()) {
                    endGame(true);
                }
            } else {
                first.flip();
                second.flip();
                SoundManager.playMismatchSound();
            }

            gameWindow.updateAllTiles();
            gameWindow.updateScore();

            gameState.setFirstSelection(null);
            gameState.setSecondSelection(null);
        }
    }

    /**
     * Starts the game timer if applicable
     */
    private void startTimer() {
        if (gameTimer != null) gameTimer.cancel();

        if (gameState.getTimeLimit() > 0) {
            gameTimer = new Timer();
            gameState.reset();

            gameTimer.scheduleAtFixedRate(new TimerTask() {
                @Override
                public void run() {
                    SwingUtilities.invokeLater(() -> {
                        int timeLeft = gameState.decrementTime();
                        if (gameWindow != null) {
                            gameWindow.updateTimer(timeLeft);
                        }
                        if (timeLeft <= 0) {
                            endGame(false);
                        }
                    });
                }
            }, 0, 1000);
        }
    }

    /**
     * Ends the game and handles high score dialog
     */
    private void endGame(boolean won) {
        if (gameTimer != null) {
            gameTimer.cancel();
            gameTimer = null;
        }

        gameState.setGameOver(true);
        gameState.setWon(won);

        SoundManager.playGameOverSound();

        if (won && HighScoreManager.isHighScore(gameState.getLevel(), gameState.getScore())) {
            String playerName = JOptionPane.showInputDialog(
                gameWindow,
                "🎉 Congratulations! You achieved a high score!\nEnter your name:",
                "High Score!",
                JOptionPane.INFORMATION_MESSAGE
            );
            if (playerName != null && !playerName.trim().isEmpty()) {
                HighScoreManager.addHighScore(
                    gameState.getLevel(),
                    playerName.trim(),
                    gameState.getScore(),
                    gameState.getAttempts()
                );
            }
        }

        if (gameWindow != null) {
            gameWindow.showGameOver(won);
        }
    }

    /**
     * Pause the game (including timer)
     */
    public void pauseGame() {
        gameState.setPaused(true);
        if (gameTimer != null) gameTimer.cancel();
    }

    /**
     * Resume game and restart the timer
     */
    public void resumeGame() {
        gameState.setPaused(false);
        startTimer();
    }

    public GameState getGameState() {
        return gameState;
    }

    public void showMainMenu() {
        new StartScreen(this);
    }

    public void startGame() {
        new StartScreen(this);
    }

    public void showLevelSelection() {
        new LevelSelectionScreen(this);
    }

    public void startNewGame(GameLevel level) {
        gameState = new GameState(level);
        configureGameSettings(level, gameState);

        switch (level) {
            case BEGINNER -> new BeginnerLevel(this);
            case INTERMEDIATE -> new IntermediateLevel(this);
            case ADVANCED -> new AdvancedLevel(this);
            default -> {
                gameWindow = new GameWindow(this);
                gameWindow.startNewGame(gameState);
                startTimer();
            }
        }
    }

    public void showGameScreen(GameLevel level) {
        throw new UnsupportedOperationException("Unimplemented method 'showGameScreen'");
    }

    public void onTileSelected(int row, int col) {
        throw new UnsupportedOperationException("Unimplemented method 'onTileSelected'");
    }

    public void showSettings() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'showSettings'");
    }
}
