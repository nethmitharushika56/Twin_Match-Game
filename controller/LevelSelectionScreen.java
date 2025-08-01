package controller;

import controller.GameController;
import model.GameLevel;
import util.SoundManager;

import javax.swing.*;
import java.awt.*;

/**
 * Neon-style Level Selection screen.
 */
public class LevelSelectionScreen extends JFrame {
    private final GameController controller;

    public LevelSelectionScreen(GameController controller) {
        this.controller = controller;
        initializeUI();
    }

    private void initializeUI() {
        setTitle("Select Difficulty - Twin Match Quest");
        setSize(700, 600);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setResizable(false);

        // Background panel
        JPanel mainPanel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                setBackground(new Color(10, 10, 30)); // Deep dark blue
            }
        };
        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));
        mainPanel.setBorder(BorderFactory.createEmptyBorder(40, 60, 40, 60));

        JLabel titleLabel = new JLabel("Choose Your Level");
        titleLabel.setFont(new Font("Orbitron", Font.BOLD, 36));
        titleLabel.setForeground(Color.CYAN);
        titleLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        mainPanel.add(titleLabel);
        mainPanel.add(Box.createVerticalStrut(40));

        // Add level buttons
        mainPanel.add(createNeonButton("Beginner", GameLevel.BEGINNER, new Color(0x00FF99)));
        mainPanel.add(Box.createVerticalStrut(20));
        mainPanel.add(createNeonButton("Intermediate", GameLevel.INTERMEDIATE, new Color(0x3399FF)));
        mainPanel.add(Box.createVerticalStrut(20));
        mainPanel.add(createNeonButton("Advanced", GameLevel.ADVANCED, new Color(0xFF3366)));
        mainPanel.add(Box.createVerticalStrut(40));

        // Back button
        JButton backButton = new JButton("Back to Main Menu");
        backButton.setFont(new Font("Arial", Font.PLAIN, 18));
        backButton.setBackground(Color.DARK_GRAY);
        backButton.setForeground(Color.WHITE);
        backButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        backButton.setFocusPainted(false);
        backButton.setPreferredSize(new Dimension(240, 50));
        backButton.addActionListener(e -> {
            SoundManager.playButtonClickSound();
            controller.showMainMenu();
            dispose();
        });

        mainPanel.add(backButton);

        setContentPane(mainPanel);
    }

    private JButton createNeonButton(String label, GameLevel level, Color glowColor) {
        JButton button = new JButton(label);
        button.setFont(new Font("Arial", Font.BOLD, 22));
        button.setForeground(Color.WHITE);
        button.setFocusPainted(false);
        button.setBackground(glowColor.darker().darker());
        button.setAlignmentX(Component.CENTER_ALIGNMENT);
        button.setPreferredSize(new Dimension(300, 60));
        button.setMaximumSize(new Dimension(300, 60));

        // Add glowing border
        button.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(glowColor, 3, true),
                BorderFactory.createEmptyBorder(10, 20, 10, 20)
        ));

        button.addActionListener(e -> {
            SoundManager.playButtonClickSound();
            controller.showGameScreen(level);
            dispose();
        });

        return button;
    }
}
