package model;

import java.util.Random;

/**
 * Manages the current state of the game including tiles, score, and game progress
 */
public class GameState {
    private GameLevel level;
    private Tile[][] tiles;
    private int totalPairs;
    private int timeLimit;
    private int score;
    private int attempts;
    private int matchesFound;
    private boolean gameOver;
    private boolean won;
    private boolean paused;
    private Tile firstSelection;
    private Tile secondSelection;
    private Random random;
    
    
    public GameState() {
        this.random = new Random();
        this.score = 0;
        this.attempts = 0;
        this.matchesFound = 0;
        this.gameOver = false;
        this.won = false;
        this.paused = false;
    }
    
    public GameState(GameLevel level2) {
        //TODO Auto-generated constructor stub
    }

    /**
     * Reset the game state for a new game
     */
    public void resetGame() {
        this.score = 0;
        this.attempts = 0;
        this.matchesFound = 0;
        this.gameOver = false;
        this.won = false;
        this.paused = false;
        this.firstSelection = null;
        this.secondSelection = null;
        
        initializeTiles();
    }
    
    
    private void initializeTiles() {
		// TODO Auto-generated method stub
		throw new UnsupportedOperationException("Unimplemented method 'initializeTiles'");
	}

	public void setLevel(GameLevel intermediate) {
        this.level = intermediate;
    }
    
    public Tile[][] getTiles() {
        return tiles;
    }

    public Tile getTile(int row, int col) {
        if (row >= 0 && row < tiles.length && col >= 0 && col < tiles[0].length) {
            return tiles[row][col];
        }
        return null;
    }
    
    public int getTotalPairs() {
        return totalPairs;
    }
    
    public void setTotalPairs(int totalPairs) {
        this.totalPairs = totalPairs;
    }
    
    public int getTimeLimit() {
        return timeLimit;
    }
    
    public void setTimeLimit(int timeLimit) {
        this.timeLimit = timeLimit;
    }
    
    public int getScore() {
        return score;
    }
    
    public void incrementScore(int points) {
        this.score += points;
    }
    
    public int getAttempts() {
        return attempts;
    }
    
    public void incrementAttempts() {
        this.attempts++;
    }
    
    public int getMatchesFound() {
        return matchesFound;
    }
    
    public void incrementMatchesFound() {
        this.matchesFound++;
    }
    
    public boolean isGameOver() {
        return gameOver;
    }
    
    public void setGameOver(boolean gameOver) {
        this.gameOver = gameOver;
    }
    
    public boolean isWon() {
        return won;
    }
    
    public void setWon(boolean won) {
        this.won = won;
    }
    
    public boolean isPaused() {
        return paused;
    }
    
    public void setPaused(boolean paused) {
        this.paused = paused;
    }
    
    public Tile getFirstSelection() {
        return firstSelection;
    }
    
    public void setFirstSelection(Tile firstSelection) {
        this.firstSelection = firstSelection;
    }
    
    public Tile getSecondSelection() {
        return secondSelection;
    }
    
    public void setSecondSelection(Tile secondSelection) {
        this.secondSelection = secondSelection;
    }
    
    public int getGridRows() {
        return tiles != null ? tiles.length : 0;
    }
    
    public int getGridCols() {
        return tiles != null && tiles.length > 0 ? tiles[0].length : 0;
    }

    public GameLevel getLevel() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'getLevel'");
    }

    public void handleTileSelection(int row, int col) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'handleTileSelection'");
    }

    public Tile getTile(Object row, Object col) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'getTile'");
    }
}
