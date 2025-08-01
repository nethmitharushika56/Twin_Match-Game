package controller;

import javax.swing.*;

import java.awt.Insets;
import java.util.Timer;
import java.util.TimerTask;

/**
 * Main game controller that manages game state and coordinates between UI and logic
/**
/**
 * Main game controller that manages game state and coordinates between UI and logic
 */
public class GameController {

    private static final GameLevel BEGINNER = null;

    // Configure game settings based on selected level
    private void configureGameSettings(GameLevel level, GameState gameState) {
        if (level == GameLevel.BEGINNER) {
            gameState.setTotalPairs(4);
            gameState.setTimeLimit(0); // No time limit
        } else if (level == GameLevel.INTERMEDIATE) {
                gameState.setTotalPairs(8);
                gameState.setTimeLimit(180); // 3 minutes
                // You may handle additional levels here if needed
        }

        Object gameWindow = null;
        if (gameWindow == null) {
            gameWindow = new GameWindow(this);
        }

        ((GameWindow) gameWindow).startNewGame(gameState);
        startTimer();
    }

    /**
     * Called when a tile is selected from the UI
     */
    public void onTileSelected(int row, int col) {
        Object gameState = null;
        if (gameState == null || ((GameState) gameState).isGameOver() || ((GameState) gameState).isPaused()) return;

        Tile selectedTile = ((GameState) gameState).getTile(row, col);
        if (selectedTile == null || selectedTile.isMatched() || selectedTile.isFlipped()) return;

        selectedTile.flip();
        SoundManager.playTileFlipSound();
        GameWindow gameWindow = null;
        gameWindow.updateTile(row, col);

        if (((GameState) gameState).getFirstSelection() == null) {
            ((GameState) gameState).setFirstSelection(selectedTile);
        } else {
            ((GameState) gameState).setSecondSelection(selectedTile);
            ((GameState) gameState).incrementAttempts();

            new Timer().schedule(new TimerTask() {
                @Override
                public void run() {
                    SwingUtilities.invokeLater(() -> checkForMatch());
                }
            }, 1000);
        }
    }

    /**
     * Check if the selected tiles match
     */
    private void checkForMatch() {
        GameState gameState = null;
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

            GameWindow gameWindow = null;
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
        GameState gameState = null;
        if (gameState.getTimeLimit() > 0) {
            final int timeRemaining = gameState.getTimeLimit();
            Timer gameTimer = new Timer();
            gameTimer.scheduleAtFixedRate(new TimerTask() {
                @Override
                public void run() {
                    SwingUtilities swingUtilities = new SwingUtilities();
                    SwingUtilities swingUtilities2 = new SwingUtilities();
                    swingUtilities2.invokeLater(() -> {
                        GameState gameState = GameState.getInstance();
                        int timeLeft = gameState.getTimeRemaining();
                        GameWindow gameWindow = GameWindow.getInstance();
                        if (gameWindow != null) {
                            gameWindow.updateTimer(timeLeft);
                        }
                        if (timeLeft <= 0) {
                            endGame(false);
                        }
                }
            }, 1000, 1000);
        }
    }

    /**
     * Ends the game and handles high score dialog
     */
    private void endGame(boolean won) {
        Object gameTimer = null;
        if (gameTimer != null) {
            ((Timer) gameTimer).cancel();
        }

        GameState gameState = null;
        gameState.setGameOver(true);
        gameState.setWon(won);

        SoundManager.playGameOverSound();

        GameWindow gameWindow = null;
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

        gameWindow.showGameOver(won);
    }

    /**
     * Pause the game (including timer)
     */
    public void pauseGame() {
        GameState gameState = null;
        gameState.setPaused(true);
        Object gameTimer = null;
        if (gameTimer != null) ((Timer) gameTimer).cancel();
    }

    /**
     * Resume game and restart the timer
     */
    public void resumeGame() {
        GameState gameState = null;
        gameState.setPaused(false);
        startTimer();
    }

    public GameState getGameState() {
        return getGameState();
    }

    public void showMainMenu() {
        startGame(); // Redirect to StartScreen
    }

    private void startGame() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'startGame'");
    }

	public void startNewGame(GameLevel level) {
		// TODO Auto-generated method stub
		throw new UnsupportedOperationException("Unimplemented method 'startNewGame'");
	}
}
