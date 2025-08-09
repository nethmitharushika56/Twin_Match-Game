package controller;

import model.GameLevel;
import util.SoundManager;

import javax.swing.*;
import java.awt.*;

/**
 * Neon-style Level Selection screen with glowing white text and gradient backgrounds.
 */
public class LevelSelectionScreen extends JFrame {
    private final GameController controller;
    private final Image backgroundImage;

    public LevelSelectionScreen(GameController controller) {
        this.controller = controller;
        this.backgroundImage = new ImageIcon("assets/level_bg.png").getImage(); // ✅ Ensure image exists
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

        // Title Label with dark green gradient background and glowing white text// Title Label with dark green gradient background, white text & glowing border
JLabel titleLabel = new JLabel("Choose Your Level", SwingConstants.CENTER) {
    @Override
    protected void paintComponent(Graphics g) {
        Graphics2D g2 = (Graphics2D) g.create();
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        int w = getWidth();
        int h = getHeight();

        // Dark green gradient background
        GradientPaint gradient = new GradientPaint(0, 0, new Color(0x003300),
                                                   w, h, new Color(0x006600));
        g2.setPaint(gradient);
        g2.fillRoundRect(0, 0, w, h, 50, 50);

        // Glowing border effect
        for (int i = 4; i >= 1; i--) {
            g2.setColor(new Color(0x00FF99, true)); // Neon green glow
            g2.setStroke(new BasicStroke(i * 2f));
            g2.drawRoundRect(i, i, w - (i * 2), h - (i * 2), 50, 50);
        }

        super.paintComponent(g);
        g2.dispose();
    }
};
titleLabel.setFont(new Font("Arial Black", Font.BOLD, 50)); // Bigger font
titleLabel.setForeground(Color.WHITE); // Normal white text
titleLabel.setOpaque(false);
titleLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
titleLabel.setPreferredSize(new Dimension(600, 100)); // Bigger box

        mainPanel.add(titleLabel);
        mainPanel.add(Box.createVerticalStrut(40));

        // Level Buttons
        mainPanel.add(createStyledButton("Beginner", GameLevel.BEGINNER));
        mainPanel.add(Box.createVerticalStrut(20));
        mainPanel.add(createStyledButton("Intermediate", GameLevel.INTERMEDIATE));
        mainPanel.add(Box.createVerticalStrut(20));
        mainPanel.add(createStyledButton("Advanced", GameLevel.ADVANCED));
        mainPanel.add(Box.createVerticalStrut(40));

        // Back Button
        JButton backButton = createCustomButton("Back to Main Menu");
        backButton.addActionListener(e -> {
            SoundManager.playButtonClickSound();
            controller.showMainMenu();
            dispose();
        });
        backButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        mainPanel.add(backButton);

        setContentPane(mainPanel);
    }

    private JButton createStyledButton(String label, GameLevel level) {
        JButton button = createCustomButton(label);
        button.addActionListener(e -> {
            SoundManager.playButtonClickSound();
            controller.showGameScreen(level);
            dispose();
        });
        button.setAlignmentX(Component.CENTER_ALIGNMENT);
        return button;
    }

    private JButton createCustomButton(String text) {
        JButton button = new JButton(text) {
            @Override
            protected void paintComponent(Graphics g) {
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

                int arc = 50; // More rounded corners
                int w = getWidth();
                int h = getHeight();

                // Dark blue gradient
                GradientPaint gradient = new GradientPaint(0, 0, new Color(0x001F4D),
                                                           w, h, new Color(0x003366));
                g2.setPaint(gradient);
                g2.fillRoundRect(0, 0, w, h, arc, arc);

                // Glowing blue border
                g2.setColor(new Color(0x00BFFF));
                g2.setStroke(new BasicStroke(3f));
                g2.drawRoundRect(1, 1, w - 3, h - 3, arc, arc);

                super.paintComponent(g);
                g2.dispose();
            }
        };

        // White, bigger text
        button.setFont(new Font("Arial Black", Font.BOLD, 24));
        button.setForeground(Color.WHITE);
        button.setFocusPainted(false);
        button.setContentAreaFilled(false);
        button.setBorderPainted(false);
        button.setOpaque(false);
        button.setPreferredSize(new Dimension(280, 70));

        return button;
    }
}
