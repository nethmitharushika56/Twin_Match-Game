package view;

import javax.swing.*;
import java.awt.*;

/**
 * Level selection screen that allows the player to choose the difficulty level.
 */
public class LevelSelectionScreen extends JFrame {
    private final GameController controller;

    public LevelSelectionScreen(GameController controller) {
        this.controller = controller;
        initializeUI();
    }

    private void initializeUI() {
        setTitle("Select Game Difficulty");
        setSize(600, 500);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setResizable(false);

        JPanel mainPanel = new JPanel();
        mainPanel.setBackground(new Color(10, 30, 60));
        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));
        mainPanel.setBorder(BorderFactory.createEmptyBorder(30, 30, 30, 30));

        JLabel titleLabel = new JLabel("Choose Your Level");
        titleLabel.setFont(new Font("Arial Black", Font.BOLD, 32));
        titleLabel.setForeground(Color.WHITE);
        titleLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        mainPanel.add(titleLabel);
        mainPanel.add(Box.createVerticalStrut(40));

        for (GameLevel level : GameLevel.values()) {
            mainPanel.add(createLevelButton(level));
            mainPanel.add(Box.createVerticalStrut(20));
        }

        JButton backButton = new JButton("Back to Main Menu");
        backButton.setFont(new Font("Arial", Font.PLAIN, 16));
        backButton.setFocusPainted(false);
        backButton.setBackground(new Color(100, 100, 100));
        backButton.setForeground(Color.WHITE);
        backButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        backButton.setPreferredSize(new Dimension(250, 40));
        backButton.addActionListener(e -> {
            SoundManager.playButtonClickSound();
            controller.showMainMenu();
            dispose();
        });

        mainPanel.add(Box.createVerticalStrut(30));
        mainPanel.add(backButton);

        setContentPane(mainPanel);
    }

    /**
     * Creates a styled button for a specific difficulty level
     */
    private JPanel createLevelButton(GameLevel level) {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setMaximumSize(new Dimension(500, 80));
        panel.setBackground(new Color(20, 40, 70));
        panel.setBorder(BorderFactory.createLineBorder(new Color(80, 150, 255), 2));
        
        JButton levelButton = new JButton(level.getDisplayName());
        levelButton.setFont(new Font("Arial", Font.BOLD, 22));
        levelButton.setFocusPainted(false);
        levelButton.setBackground(new Color(0, 120, 215));
        levelButton.setForeground(Color.WHITE);
        levelButton.setPreferredSize(new Dimension(200, 60));

        levelButton.addActionListener(e -> {
            SoundManager.playButtonClickSound();
            controller.startNewGame(level);
            dispose();
        });

        JLabel descriptionLabel = new JLabel("<html><div style='color:white; font-size:12px;'>" + level.getDescription() + "</div></html>");
        descriptionLabel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        panel.add(levelButton, BorderLayout.WEST);
        panel.add(descriptionLabel, BorderLayout.CENTER);

        return panel;
    }
}
