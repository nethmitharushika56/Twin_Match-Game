package model;

import java.io.*;
import java.util.*;

/**
 * Manages high scores for each game level
 */
public class HighScoreManager {
    private static final String HIGH_SCORE_FILE = "highscores.dat";
    private static Map<GameLevel, List<HighScore>> highScores = new HashMap<>();
    
    static {
        loadHighScores();
    }
    
    public static class HighScore implements Comparable<HighScore> {
        private String playerName;
        private int score;
        private int attempts;
        private long date;
        
        public HighScore(String playerName, int score, int attempts) {
            this.playerName = playerName;
            this.score = score;
            this.attempts = attempts;
            this.date = System.currentTimeMillis();
        }
        
        public String getPlayerName() { return playerName; }
        public int getScore() { return score; }
        public int getAttempts() { return attempts; }
        public long getDate() { return date; }
        
        @Override
        public int compareTo(HighScore other) {
            // Sort by score (highest first), then by attempts (lowest first)
            if (this.score != other.score) {
                return Integer.compare(other.score, this.score);
            }
            return Integer.compare(this.attempts, other.attempts);
        }
        
        @Override
        public String toString() {
            return String.format("%s - Score: %d, Attempts: %d", playerName, score, attempts);
        }
    }
    
    /**
     * Add a new high score
     */
    public static void addHighScore(GameLevel level, String playerName, int score, int attempts) {
        if (!highScores.containsKey(level)) {
            highScores.put(level, new ArrayList<>());
        }
        
        HighScore newScore = new HighScore(playerName, score, attempts);
        List<HighScore> scores = highScores.get(level);
        scores.add(newScore);
        Collections.sort(scores);
        
        // Keep only top 10 scores
        if (scores.size() > 10) {
            scores = scores.subList(0, 10);
            highScores.put(level, scores);
        }
        
        saveHighScores();
    }
    
    /**
     * Get top high scores for a level
     */
    public static List<HighScore> getHighScores(GameLevel level) {
        return highScores.getOrDefault(level, new ArrayList<>());
    }
    
    /**
     * Check if a score is a high score
     */
    public static boolean isHighScore(GameLevel level, int score) {
        List<HighScore> scores = getHighScores(level);
        if (scores.size() < 10) return true;
        return score > scores.get(scores.size() - 1).getScore();
    }
    
    /**
     * Load high scores from file
     */
    private static void loadHighScores() {
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(HIGH_SCORE_FILE))) {
            highScores = (Map<GameLevel, List<HighScore>>) ois.readObject();
        } catch (Exception e) {
            // File doesn't exist or is corrupted, start with empty scores
            highScores = new HashMap<>();
        }
    }
    
    /**
     * Save high scores to file
     */
    private static void saveHighScores() {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(HIGH_SCORE_FILE))) {
            oos.writeObject(highScores);
        } catch (Exception e) {
            System.err.println("Could not save high scores: " + e.getMessage());
        }
    }
} 