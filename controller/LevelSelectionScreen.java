package controller;

import model.GameLevel;
import util.SoundManager;

import javax.swing.*;
import java.awt.*;

/**
 * Neon-style Level Selection screen with glowing green buttons and background.
 */
public class LevelSelectionScreen extends JFrame {
    private final GameController controller;
    private final Image backgroundImage;

    public LevelSelectionScreen(GameController controller) {
        this.controller = controller;
        this.backgroundImage = new ImageIcon("assets/level_selection_bg.jpg").getImage(); // ✅ Make sure this image exists
        initializeUI();
        setVisible(true);
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
                g.drawImage(backgroundImage, 0, 0, getWidth(), getHeight(), this);
            }
        };
        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));
        mainPanel.setBorder(BorderFactory.createEmptyBorder(40, 60, 40, 60));
        mainPanel.setOpaque(false);

        // Title
        JLabel titleLabel = new JLabel("Choose Your Level");
        titleLabel.setFont(new Font("Arial Black", Font.BOLD, 38));
        titleLabel.setForeground(Color.WHITE);
        titleLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        mainPanel.add(titleLabel);
        mainPanel.add(Box.createVerticalStrut(40));

        // Level buttons
        mainPanel.add(createStyledButton("Beginner", GameLevel.BEGINNER));
        mainPanel.add(Box.createVerticalStrut(20));
        mainPanel.add(createStyledButton("Intermediate", GameLevel.INTERMEDIATE));
        mainPanel.add(Box.createVerticalStrut(20));
        mainPanel.add(createStyledButton("Advanced", GameLevel.ADVANCED));
        mainPanel.add(Box.createVerticalStrut(40));

        // Back button
        JButton backButton = createGlowingGreenButton("Back to Main Menu");
        backButton.addActionListener(e -> {
            SoundManager.playButtonClickSound();
            controller.showMainMenu();
            dispose();
        });
        mainPanel.add(backButton);

        setContentPane(mainPanel);
    }

    private JButton createStyledButton(String label, GameLevel level) {
        JButton button = createGlowingGreenButton(label);
        button.addActionListener(e -> {
            SoundManager.playButtonClickSound();
            controller.showGameScreen(level);
            dispose();
        });
        return button;
    }

    private JButton createGlowingGreenButton(String text) {
        return new JButton(text) {
            @Override
            protected void paintComponent(Graphics g) {
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

                int arc = 40;
                int w = getWidth();
                int h = getHeight();

                // Dark green gradient background
                GradientPaint gradient = new GradientPaint(0, 0, new Color(0x003300),
                                                           w, h, new Color(0x006600));
                g2.setPaint(gradient);
                g2.fillRoundRect(0, 0, w, h, arc, arc);

                // Glowing green border
                g2.setColor(new Color(0x00FF00));
                g2.setStroke(new BasicStroke(3f));
                g2.drawRoundRect(1, 1, w - 3, h - 3, arc, arc);

                super.paintComponent(g);
                g2.dispose();
            }
        };
    }
}
