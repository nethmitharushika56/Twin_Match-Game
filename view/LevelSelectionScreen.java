package view;

import controller.GameController;
import model.GameLevel;
import util.SoundManager;
import view.BeginnerLevel;
import javax.swing.*;
import java.awt.*;

/**
 * Clean Level Selection Screen in the view package
 */
public class LevelSelectionScreen extends JFrame {

    private final GameController controller;

    public LevelSelectionScreen(GameController controller) {
        this.controller = controller;
        initializeUI();
        setVisible(true);
    }

    private void initializeUI() {
        setTitle("Select Difficulty - Twin Match Quest");
        setSize(700, 600);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setResizable(false);

        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));
        mainPanel.setBorder(BorderFactory.createEmptyBorder(50, 60, 50, 60));
        mainPanel.setBackground(new Color(0x001F4D));

        // Title
        JLabel titleLabel = new JLabel("Choose Your Level", SwingConstants.CENTER);
        titleLabel.setFont(new Font("Arial Black", Font.BOLD, 42));
        titleLabel.setForeground(Color.WHITE);
        titleLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        mainPanel.add(titleLabel);
        mainPanel.add(Box.createVerticalStrut(50));

        // Level Buttons
        for (GameLevel level : GameLevel.values()) {
            JButton levelButton = createStyledButton(level.getDisplayName(), level);
            mainPanel.add(levelButton);

            JLabel descLabel = new JLabel("<html><div style='color:white; font-size:14px;'>" +
                    level.getDescription() + "</div></html>", SwingConstants.CENTER);
            descLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
            mainPanel.add(descLabel);

            mainPanel.add(Box.createVerticalStrut(20));
        }

        // Back button
        JButton backButton = createStyledButton("Back to Main Menu", null);
        backButton.addActionListener(e -> {
            SoundManager.playButtonClickSound();
            controller.showMainMenu();
            dispose();
        });
        mainPanel.add(Box.createVerticalStrut(30));
        mainPanel.add(backButton);

        setContentPane(mainPanel);
    }

    private JButton createStyledButton(String text, GameLevel level) {
        JButton button = new JButton(text) {
            @Override
            protected void paintComponent(Graphics g) {
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

                int arc = 50;
                int w = getWidth();
                int h = getHeight();

                // Gradient fill
                GradientPaint gradient = new GradientPaint(0, 0, new Color(0x001F4D),
                        0, h, new Color(0x003366));
                g2.setPaint(gradient);
                g2.fillRoundRect(0, 0, w, h, arc, arc);

                // Blue border
                g2.setColor(new Color(0x00BFFF));
                g2.setStroke(new BasicStroke(3f));
                g2.drawRoundRect(1, 1, w - 3, h - 3, arc, arc);

                g2.dispose();
                super.paintComponent(g);
            }
        };

        button.setFont(new Font("Arial Black", Font.BOLD, 24));
        button.setForeground(Color.WHITE);
        button.setFocusPainted(false);
        button.setContentAreaFilled(false);
        button.setBorderPainted(false);
        button.setOpaque(false);
        button.setMaximumSize(new Dimension(300, 70));
        button.setAlignmentX(Component.CENTER_ALIGNMENT);

        if (level != null) {
            button.addActionListener(e -> {
                SoundManager.playButtonClickSound();
                System.out.println("DEBUG: " + level + " clicked");
                controller.startNewGame(level);
                dispose();
            });
        }

        return button;
    }
}
