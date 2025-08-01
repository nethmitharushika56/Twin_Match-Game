package main;

import javax.swing.SwingUtilities;
import javax.swing.UIManager;

import controller.GameController;

/**
 * Main entry point for Twin Match Quest game
 * A memory-based picture matching game with multiple difficulty levels
 */
public class Main {
    
    public static void main(String[] args) {
        // Set system look and feel for better UI
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception e) {
            System.err.println("Could not set system look and feel: " + e.getMessage());
        }
        
        // Start the game on the Event Dispatch Thread
        SwingUtilities.invokeLater(() -> {
            GameController controller = new GameController();
            ((GameController) controller).startGame();
        });
    }
} 