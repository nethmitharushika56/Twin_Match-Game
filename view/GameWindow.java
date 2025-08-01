package view;

import javax.swing.*;

import controller.GameController;
import model.GameState;
import model.Tile;
import util.SoundManager;

import java.awt.*;

/**
 * Main game window that handles all UI components and user interactions
 */
public class GameWindow extends JFrame {
    private GameController controller;
    private JPanel mainPanel;
    private JPanel gamePanel;
    private JPanel infoPanel;
    private JLabel scoreLabel;
    private JLabel timerLabel;
    private JLabel attemptsLabel;
    private JButton[][] tileButtons;
    private JButton pauseButton;
    private JButton menuButton;
    private CardLayout cardLayout;
    
    public GameWindow(GameController controller) {
        this.controller = controller;
        initializeWindow();
        createMainMenu();
        createGamePanel();
    }
    
    /**
     * Initialize the main window
     */
    private void initializeWindow() {
        setTitle("Twin Match Quest");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(800, 600);
        setLocationRelativeTo(null);
        setResizable(false);
        
        // Use card layout for switching between menu and game
        cardLayout = new CardLayout();
        mainPanel = new JPanel(cardLayout);
        setContentPane(mainPanel);
    }
    
    /**
     * Create the main menu panel
     */
    private void createMainMenu() {
        JPanel menuPanel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                Graphics2D g2d = (Graphics2D) g;
                g2d.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);
                
                // Create underwater gradient background
                GradientPaint gradient = new GradientPaint(
                    0, 0, new Color(0, 150, 136), // Light teal at top
                    0, getHeight(), new Color(0, 50, 100) // Dark blue at bottom
                );
                g2d.setPaint(gradient);
                g2d.fillRect(0, 0, getWidth(), getHeight());
                
                // Add some underwater bubbles
                g2d.setColor(new Color(0, 255, 200, 100));
                for (int i = 0; i < 20; i++) {
                    int x = (int) (Math.random() * getWidth());
                    int y = (int) (Math.random() * getHeight());
                    int size = (int) (Math.random() * 10) + 5;
                    g2d.fillOval(x, y, size, size);
                }
            }
        };
        menuPanel.setLayout(new BoxLayout(menuPanel, BoxLayout.Y_AXIS));
        
        // Title with 3D effect
        JLabel titleLabel = new JLabel("TWIN MATCH QUEST");
        titleLabel.setFont(new Font("Arial Black", Font.BOLD, 48));
        titleLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        titleLabel.setForeground(Color.WHITE);
        titleLabel.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(new Color(255, 0, 0), 3),
            BorderFactory.createEmptyBorder(20, 40, 20, 40)
        ));
        
        // Subtitle
        JLabel subtitleLabel = new JLabel("Memory Matching Game");
        subtitleLabel.setFont(new Font("Arial", Font.PLAIN, 20));
        subtitleLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        subtitleLabel.setForeground(new Color(200, 255, 200));
        
        // Level buttons with underwater styling
        JButton beginnerButton = createUnderwaterButton("BEGINNER", (GameLevel) GameLevel.BEGINNER);
        JButton intermediateButton = createUnderwaterButton("INTERMEDIATE", (GameLevel) GameLevel.INTERMEDIATE);
        JButton advancedButton = createUnderwaterButton("ADVANCED", (GameLevel) GameLevel.ADVANCED);
        
        // Instructions panel
        JPanel instructionsPanel = new JPanel();
        instructionsPanel.setLayout(new BoxLayout(instructionsPanel, BoxLayout.Y_AXIS));
        instructionsPanel.setOpaque(false);
        instructionsPanel.setBorder(BorderFactory.createEmptyBorder(20, 40, 20, 40));
        
        JTextArea instructionsArea = new JTextArea();
        instructionsArea.setText("How to Play:\n" +
                "• Click on tiles to reveal Squid Game characters\n" +
                "• Find matching pairs to clear the board\n" +
                "• Complete the game with as few attempts as possible\n" +
                "• Beginner: 9 tiles, no time limit\n" +
                "• Intermediate: 16 tiles, 3 minutes\n" +
                "• Advanced: 25 tiles, 3 minutes");
        instructionsArea.setEditable(false);
        instructionsArea.setBackground(new Color(0, 100, 80, 150));
        instructionsArea.setForeground(Color.WHITE);
        instructionsArea.setFont(new Font("Arial", Font.PLAIN, 14));
        instructionsArea.setAlignmentX(Component.CENTER_ALIGNMENT);
        instructionsArea.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        
        // Add components with spacing
        menuPanel.add(Box.createVerticalStrut(80));
        menuPanel.add(titleLabel);
        menuPanel.add(Box.createVerticalStrut(20));
        menuPanel.add(subtitleLabel);
        menuPanel.add(Box.createVerticalStrut(60));
        menuPanel.add(beginnerButton);
        menuPanel.add(Box.createVerticalStrut(20));
        menuPanel.add(intermediateButton);
        menuPanel.add(Box.createVerticalStrut(20));
        menuPanel.add(advancedButton);
        menuPanel.add(Box.createVerticalStrut(40));
        menuPanel.add(instructionsArea);
        menuPanel.add(Box.createVerticalStrut(30));
        
        mainPanel.add(menuPanel, "MENU");
    }
    

    /**
    /**
     * Create an underwater-styled button
     */
    private JButton createUnderwaterButton(String text, GameLevel advanced) {
        JButton button = new JButton(text) {
            @Override
            protected void paintComponent(Graphics g) {
                Graphics2D g2d = (Graphics2D) g;
                g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                
                // Create gradient for button
                GradientPaint buttonGradient = new GradientPaint(
                    0, 0, new Color(255, 140, 0), // Orange at top
                    0, getHeight(), new Color(255, 69, 0) // Red-orange at bottom
                );
                g2d.setPaint(buttonGradient);
                g2d.fillRoundRect(0, 0, getWidth(), getHeight(), 20, 20);
                
                // Add shadow effect
                g2d.setColor(new Color(0, 0, 0, 50));
                g2d.fillRoundRect(2, 2, getWidth(), getHeight(), 20, 20);
                
                // Draw text
                g2d.setColor(Color.WHITE);
                g2d.setFont(getFont());
                FontMetrics fm = g2d.getFontMetrics();
                int textX = (getWidth() - fm.stringWidth(getText())) / 2;
                int textY = (getHeight() + fm.getAscent()) / 2;
                g2d.drawString(getText(), textX, textY);
            }
        };
        
        button.setAlignmentX(Component.CENTER_ALIGNMENT);
        button.setPreferredSize(new Dimension(250, 50));
        button.setFont(new Font("Arial Black", Font.BOLD, 18));
        button.setForeground(Color.WHITE);
        button.setFocusPainted(false);
        button.setBorderPainted(false);
        button.setContentAreaFilled(false);
        button.setOpaque(false);
        
        button.addActionListener(e -> {
            SoundManager.playButtonClickSound();
            GameLevel intermediate = null;
            controller.startNewGame((model.GameLevel) intermediate);
            cardLayout.show(mainPanel, "GAME");
        });
        
        return button;
    }
    
    /**
     * Create the game panel
     */
    private void createGamePanel() {
        gamePanel = new JPanel(new BorderLayout());
        gamePanel.setBackground(new Color(240, 248, 255));
        
        // Info panel at top
        createInfoPanel();
        
        // Game grid panel (will be created when game starts)
        JPanel gridPanel = new JPanel();
        gridPanel.setBackground(new Color(240, 248, 255));
        gamePanel.add(gridPanel, BorderLayout.CENTER);
        
        // Control panel at bottom
        createControlPanel();
        
        mainPanel.add(gamePanel, "GAME");
    }
    
    /**
     * Create the info panel showing score, timer, and attempts
     */
    private void createInfoPanel() {
        infoPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 10));
        infoPanel.setBackground(new Color(230, 240, 250));
        infoPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        
        scoreLabel = new JLabel("Score: 0");
        scoreLabel.setFont(new Font("Arial", Font.BOLD, 16));
        scoreLabel.setForeground(new Color(70, 130, 180));
        
        timerLabel = new JLabel("Time: --");
        timerLabel.setFont(new Font("Arial", Font.BOLD, 16));
        timerLabel.setForeground(new Color(70, 130, 180));
        
        attemptsLabel = new JLabel("Attempts: 0");
        attemptsLabel.setFont(new Font("Arial", Font.BOLD, 16));
        attemptsLabel.setForeground(new Color(70, 130, 180));
        
        infoPanel.add(scoreLabel);
        infoPanel.add(timerLabel);
        infoPanel.add(attemptsLabel);
        
        gamePanel.add(infoPanel, BorderLayout.NORTH);
    }
    
    /**
     * Create the control panel with pause and menu buttons
     */
    private void createControlPanel() {
        JPanel controlPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 10));
        controlPanel.setBackground(new Color(230, 240, 250));
        controlPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        
        pauseButton = new JButton("Pause");
        pauseButton.setFont(new Font("Arial", Font.BOLD, 14));
        pauseButton.setBackground(new Color(255, 165, 0));
        pauseButton.setForeground(Color.WHITE);
        pauseButton.setFocusPainted(false);
        pauseButton.setBorderPainted(false);
        pauseButton.addActionListener(e -> {
            SoundManager.playButtonClickSound();
            togglePause();
        });
        
        menuButton = new JButton("Main Menu");
        menuButton.setFont(new Font("Arial", Font.BOLD, 14));
        menuButton.setBackground(new Color(220, 20, 60));
        menuButton.setForeground(Color.WHITE);
        menuButton.setFocusPainted(false);
        menuButton.setBorderPainted(false);
        menuButton.addActionListener(e -> {
            SoundManager.playButtonClickSound();
            showMainMenu();
        });
        
        JButton settingsButton = new JButton("Settings");
        settingsButton.setFont(new Font("Arial", Font.BOLD, 14));
        settingsButton.setBackground(new Color(100, 149, 237));
        settingsButton.setForeground(Color.WHITE);
        settingsButton.setFocusPainted(false);
        settingsButton.setBorderPainted(false);
        settingsButton.addActionListener(e -> {
            SoundManager.playButtonClickSound();
            showSettings();
        });
        
        JButton highScoresButton = new JButton("High Scores");
        highScoresButton.setFont(new Font("Arial", Font.BOLD, 14));
        highScoresButton.setBackground(new Color(255, 215, 0));
        highScoresButton.setForeground(Color.BLACK);
        highScoresButton.setFocusPainted(false);
        highScoresButton.setBorderPainted(false);
        highScoresButton.addActionListener(e -> {
            SoundManager.playButtonClickSound();
            showHighScores();
        });
        
        controlPanel.add(pauseButton);
        controlPanel.add(menuButton);
        controlPanel.add(settingsButton);
        controlPanel.add(highScoresButton);
        
        gamePanel.add(controlPanel, BorderLayout.SOUTH);
    }
    
    /**
     * Start a new game with the given game state
     */
    public void startNewGame(GameState gameState) {
        createGameGrid(gameState);
        updateScore();
        updateTimer(gameState.getTimeLimit());
        updateAttempts();
    }
    
    /**
     * Create the game grid with tiles
     */
    private void createGameGrid(GameState gameState) {
        // Remove existing grid
        gamePanel.remove(gamePanel.getComponent(1)); // Remove center component
        
        // Create new grid
        JPanel gridPanel = new JPanel(new GridLayout(gameState.getGridRows(), gameState.getGridCols(), 5, 5));
        gridPanel.setBackground(new Color(240, 248, 255));
        gridPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        
        tileButtons = new JButton[gameState.getGridRows()][gameState.getGridCols()];
        
        for (int row = 0; row < gameState.getGridRows(); row++) {
            for (int col = 0; col < gameState.getGridCols(); col++) {
                Tile tile = gameState.getTile(row, col);
                JButton button = new JButton();
                button.setPreferredSize(new Dimension(60, 60));
                button.setFont(new Font("Arial", Font.BOLD, 18));
                button.setBackground(new Color(100, 149, 237));
                button.setForeground(Color.WHITE);
                button.setFocusPainted(false);
                button.setBorderPainted(false);
                
                final int finalRow = row;
                final int finalCol = col;
                button.addActionListener(e -> {
                    SoundManager.playTileClickSound();
                    controller.onTileSelected(finalRow, finalCol);
                });
                
                tileButtons[row][col] = button;
                
                if (tile != null) {
                    gridPanel.add(button);
                    updateTileDisplay(row, col, tile);
                }
            }
        }
        
        gamePanel.add(gridPanel, BorderLayout.CENTER);
        gamePanel.revalidate();
        gamePanel.repaint();
    }
    
    /**
     * Update the display of a specific tile
     */
    public void updateTile(int row, int col) {
        GameState gameState = controller.getGameState();
        Tile tile = gameState.getTile(row, col);
        if (tile != null) {
            updateTileDisplay(row, col, tile);
        }
    }
    
    /**
     * Update all tiles display
     */
    public void updateAllTiles() {
        GameState gameState = controller.getGameState();
        for (int row = 0; row < gameState.getGridRows(); row++) {
            for (int col = 0; col < gameState.getGridCols(); col++) {
                Tile tile = gameState.getTile(row, col);
                if (tile != null) {
                    updateTileDisplay(row, col, tile);
                }
            }
        }
    }
    
    /**
     * Update the display of a tile based on its state
     */
    private void updateTileDisplay(int row, int col, Tile tile) {
        JButton button = tileButtons[row][col];
        GameState gameState = controller.getGameState();
        boolean isBeginner = gameState.getLevel() == GameLevel.BEGINNER;
        boolean isAdvanced = gameState.getLevel() == GameLevel.ADVANCED;
        boolean isIntermediate = gameState.getLevel() == GameLevel.INTERMEDIATE;
        if (button != null) {
            if (tile.isMatched()) {
                button.setText("✓");
                button.setIcon(null);
                button.setBackground(new Color(34, 139, 34)); // Green for matched
                button.setEnabled(false);
            } else if (tile.isFlipped()) {
                if (tile.getImageIcon() != null) {
                    button.setText("");
                    button.setIcon(tile.getImageIcon());
                } else {
                    button.setText(tile.getDisplayName());
                    button.setIcon(null);
                }
                button.setBackground((isBeginner || isIntermediate || isAdvanced) ? Color.RED : new Color(255, 215, 0)); // Red for Beginner/Intermediate/Advanced, Gold otherwise
            } else {
                button.setText("?");
                button.setIcon(null);
                button.setBackground((isBeginner || isIntermediate || isAdvanced) ? Color.RED : new Color(100, 149, 237)); // Red for Beginner/Intermediate/Advanced, Blue otherwise
            }
        }
    }
    
    /**
     * Update the score display
     */
    public void updateScore() {
        GameState gameState = controller.getGameState();
        scoreLabel.setText("Score: " + gameState.getScore());
    }
    
    /**
     * Update the timer display
     */
    public void updateTimer(int seconds) {
        if (seconds > 0) {
            int minutes = seconds / 60;
            int remainingSeconds = seconds % 60;
            timerLabel.setText(String.format("Time: %02d:%02d", minutes, remainingSeconds));
        } else {
            timerLabel.setText("Time: --");
        }
    }
    
    /**
     * Update the attempts display
     */
    public void updateAttempts() {
        GameState gameState = controller.getGameState();
        attemptsLabel.setText("Attempts: " + gameState.getAttempts());
    }
    
    /**
     * Toggle pause/resume
     */
    private void togglePause() {
        GameState gameState = controller.getGameState();
        if (gameState.isPaused()) {
            controller.resumeGame();
            pauseButton.setText("Pause");
        } else {
            controller.pauseGame();
            pauseButton.setText("Resume");
        }
    }
    
    /**
     * Show the main menu
     */
    public void showMainMenu() {
        cardLayout.show(mainPanel, "MENU");
    }
    
    /**
     * Show settings dialog
     */
    public void showSettings() {
        SettingsDialog settingsDialog = new SettingsDialog(this);
        settingsDialog.setVisible(true);
    }
    
    /**
     * Show high scores dialog
     */
    public void showHighScores() {
        GameLevel currentLevel = (GameLevel) controller.getGameState().getLevel();
        if (currentLevel != null) {
            HighScoreDialog highScoreDialog = new HighScoreDialog(this, currentLevel);
            highScoreDialog.setVisible(true);
        }
    }
    
    /**
     * Show game over dialog
     */
    public void showGameOver(boolean won) {
        String message = won ? 
            "Congratulations! You've completed the level!" :
            "Time's up! Better luck next time!";
        
        int choice = JOptionPane.showConfirmDialog(
            this,
            message + "\nScore: " + controller.getGameState().getScore() + 
            "\nAttempts: " + controller.getGameState().getAttempts() +
            "\n\nWould you like to play again?",
            "Game Over",
            JOptionPane.YES_NO_OPTION
        );
        
        if (choice == JOptionPane.YES_OPTION) {
            showMainMenu();
        } else {
            System.exit(0);
        }
    }

    public static GameWindow getInstance() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'getInstance'");
    }

    public void updateTile(Object row, Object col) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'updateTile'");
    }
} 