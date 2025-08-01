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
    
    private static final String[][] SQUID_CANDY_IMAGES = {
        {"/images/squid1.png", "Gi-hun"},
        {"/images/squid2.png", "Sae-byeok"},
        {"/images/squid3.png", "Sang-woo"},
        {"/images/squid4.png", "Il-nam"},
        {"/images/candy.png", "Candy"}
    };
    
    private static final String[][] SQUID_ADVANCED_IMAGES = {
        {"/images/squid1.png", "Gi-hun"},
        {"/images/squid2.png", "Sae-byeok"},
        {"/images/squid3.png", "Sang-woo"},
        {"/images/squid4.png", "Il-nam"},
        {"/images/squid5.png", "Deok-su"},
        {"/images/squid6.png", "Mi-nyeo"},
        {"/images/squid7.png", "Ali"},
        {"/images/squid8.png", "Jun-ho"}
    };
    
    private static final String[][] SQUID_ADVANCED_25_IMAGES = {
        {"/images/squid1.png", "Gi-hun"},
        {"/images/squid2.png", "Sae-byeok"},
        {"/images/squid3.png", "Sang-woo"},
        {"/images/squid4.png", "Il-nam"},
        {"/images/squid5.png", "Deok-su"},
        {"/images/squid6.png", "Mi-nyeo"},
        {"/images/squid7.png", "Ali"},
        {"/images/squid8.png", "Jun-ho"},
        {"/images/squid9.png", "Hwang"},
        {"/images/squid10.png", "Ji-yeong"},
        {"/images/squid11.png", "Byeong-gi"},
        {"/images/squid12.png", "Han Mi-nyeo"},
        {"/images/squid13.png", "VIP"}
    };
    
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
    
    /**
     * Initialize the game tiles with random image pairs
     */
    private void initializeTiles() {
        if (level == GameLevel.BEGINNER) {
            // 4 pairs + 1 unique candy tile, 3x3 grid
            int gridRows = 3;
            int gridCols = 3;
            tiles = new Tile[gridRows][gridCols];
            int totalTiles = 9;
            int[] imageIds = new int[totalTiles];
            String[] imagePaths = new String[totalTiles];
            String[] displayNames = new String[totalTiles];
            // 4 pairs
            for (int i = 0; i < 4; i++) {
                imageIds[i * 2] = i;
                imageIds[i * 2 + 1] = i;
                imagePaths[i * 2] = SQUID_CANDY_IMAGES[i][0];
                imagePaths[i * 2 + 1] = SQUID_CANDY_IMAGES[i][0];
                displayNames[i * 2] = SQUID_CANDY_IMAGES[i][1];
                displayNames[i * 2 + 1] = SQUID_CANDY_IMAGES[i][1];
            }
            // 1 unique candy tile
            imageIds[8] = 4;
            imagePaths[8] = SQUID_CANDY_IMAGES[4][0];
            displayNames[8] = SQUID_CANDY_IMAGES[4][1];
            // Shuffle
            for (int i = totalTiles - 1; i > 0; i--) {
                int j = random.nextInt(i + 1);
                int tmpId = imageIds[i]; imageIds[i] = imageIds[j]; imageIds[j] = tmpId;
                String tmpPath = imagePaths[i]; imagePaths[i] = imagePaths[j]; imagePaths[j] = tmpPath;
                String tmpName = displayNames[i]; displayNames[i] = displayNames[j]; displayNames[j] = tmpName;
            }
            int tileIndex = 0;
            for (int row = 0; row < gridRows; row++) {
                for (int col = 0; col < gridCols; col++) {
                    tiles[row][col] = new Tile(imageIds[tileIndex], imagePaths[tileIndex], displayNames[tileIndex]);
                    tileIndex++;
                }
            }
            return;
        }
        if (level == GameLevel.INTERMEDIATE) {
            // 8 pairs, 4x4 grid
            int gridRows = 4;
            int gridCols = 4;
            tiles = new Tile[gridRows][gridCols];
            int totalTiles = 16;
            int[] imageIds = new int[totalTiles];
            String[] imagePaths = new String[totalTiles];
            String[] displayNames = new String[totalTiles];
            for (int i = 0; i < 8; i++) {
                imageIds[i * 2] = i;
                imageIds[i * 2 + 1] = i;
                imagePaths[i * 2] = SQUID_ADVANCED_IMAGES[i][0];
                imagePaths[i * 2 + 1] = SQUID_ADVANCED_IMAGES[i][0];
                displayNames[i * 2] = SQUID_ADVANCED_IMAGES[i][1];
                displayNames[i * 2 + 1] = SQUID_ADVANCED_IMAGES[i][1];
            }
            // Shuffle
            for (int i = totalTiles - 1; i > 0; i--) {
                int j = random.nextInt(i + 1);
                int tmpId = imageIds[i]; imageIds[i] = imageIds[j]; imageIds[j] = tmpId;
                String tmpPath = imagePaths[i]; imagePaths[i] = imagePaths[j]; imagePaths[j] = tmpPath;
                String tmpName = displayNames[i]; displayNames[i] = displayNames[j]; displayNames[j] = tmpName;
            }
            int tileIndex = 0;
            for (int row = 0; row < gridRows; row++) {
                for (int col = 0; col < gridCols; col++) {
                    tiles[row][col] = new Tile(imageIds[tileIndex], imagePaths[tileIndex], displayNames[tileIndex]);
                    tileIndex++;
                }
            }
            return;
        }
        if (level == GameLevel.ADVANCED) {
            // 12 pairs + 1 unique, 5x5 grid
            int gridRows = 5;
            int gridCols = 5;
            tiles = new Tile[gridRows][gridCols];
            int totalTiles = 25;
            int[] imageIds = new int[totalTiles];
            String[] imagePaths = new String[totalTiles];
            String[] displayNames = new String[totalTiles];
            // 12 pairs
            for (int i = 0; i < 12; i++) {
                imageIds[i * 2] = i;
                imageIds[i * 2 + 1] = i;
                imagePaths[i * 2] = SQUID_ADVANCED_25_IMAGES[i][0];
                imagePaths[i * 2 + 1] = SQUID_ADVANCED_25_IMAGES[i][0];
                displayNames[i * 2] = SQUID_ADVANCED_25_IMAGES[i][1];
                displayNames[i * 2 + 1] = SQUID_ADVANCED_25_IMAGES[i][1];
            }
            // 1 unique
            imageIds[24] = 12;
            imagePaths[24] = SQUID_ADVANCED_25_IMAGES[12][0];
            displayNames[24] = SQUID_ADVANCED_25_IMAGES[12][1];
            // Shuffle
            for (int i = totalTiles - 1; i > 0; i--) {
                int j = random.nextInt(i + 1);
                int tmpId = imageIds[i]; imageIds[i] = imageIds[j]; imageIds[j] = tmpId;
                String tmpPath = imagePaths[i]; imagePaths[i] = imagePaths[j]; imagePaths[j] = tmpPath;
                String tmpName = displayNames[i]; displayNames[i] = displayNames[j]; displayNames[j] = tmpName;
            }
            int tileIndex = 0;
            for (int row = 0; row < gridRows; row++) {
                for (int col = 0; col < gridCols; col++) {
                    tiles[row][col] = new Tile(imageIds[tileIndex], imagePaths[tileIndex], displayNames[tileIndex]);
                    tileIndex++;
                }
            }
            return;
        }
    }
    
    // Getters and setters
    public GameLevel getLevel() {
        return level;
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

    public int getTimeRemaining() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'getTimeRemaining'");
    }

	public static GameState getInstance() {
		// TODO Auto-generated method stub
		throw new UnsupportedOperationException("Unimplemented method 'getInstance'");
	}

    public Tile getTile(Object row, Object col) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'getTile'");
    }

    public void decrementTime() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'decrementTime'");
    }

    public void reset() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'reset'");
    }
} 