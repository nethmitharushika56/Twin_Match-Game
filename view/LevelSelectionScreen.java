package view;

import controller.GameController;
import model.GameLevel;
import util.SoundManager;

import javax.swing.*;
import java.awt.*;

/**
 * Level Selection Screen styled like screenshot
 */
public class LevelSelectionScreen extends JFrame {

    private final GameController controller;

    public LevelSelectionScreen(GameController controller) {
        this.controller = controller;
        initializeUI();
        setVisible(true);
    }

    private void initializeUI() {
        setTitle("Select Difficulty - Twin Match");
        setSize(700, 600);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setResizable(false);

        // Background image panel
        JPanel backgroundPanel = new JPanel() {
            private final Image bg = new ImageIcon("assets/level_bg.png").getImage(); // put your bg image path

            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                g.drawImage(bg, 0, 0, getWidth(), getHeight(), this);
            }
        };
        backgroundPanel.setLayout(new BoxLayout(backgroundPanel, BoxLayout.Y_AXIS));
        backgroundPanel.setBorder(BorderFactory.createEmptyBorder(40, 40, 40, 40));

        
        // Title
JLabel titleLabel = new JLabel("Choose Your Level", SwingConstants.CENTER) {
    @Override
    protected void paintComponent(Graphics g) {
        Graphics2D g2 = (Graphics2D) g.create();
        g2.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);

        String text = getText();
        Font font = getFont();
        g2.setFont(font);
        FontMetrics fm = g2.getFontMetrics(font);
        int textWidth = fm.stringWidth(text);
        int textHeight = fm.getHeight();
        int x = (getWidth() - textWidth) / 2;
        int y = (getHeight() - textHeight) / 2 + fm.getAscent();

        // 🔵 Semi-transparent background
        int padding = 20;
        g2.setColor(new Color(0, 0, 0, 120)); // black with transparency
        g2.fillRoundRect(x - padding / 2, y - fm.getAscent() - padding / 2,
                textWidth + padding, textHeight + padding, 25, 25);

        // White outline
        g2.setColor(Color.WHITE);
        for (int dx = -1; dx <= 1; dx++) {
            for (int dy = -1; dy <= 1; dy++) {
                if (dx != 0 || dy != 0) {
                    g2.drawString(text, x + dx, y + dy);
                }
            }
        }

        // Main text
        g2.setColor(getForeground());
        g2.drawString(text, x, y);

        g2.dispose();
    }
};

        titleLabel.setFont(new Font("Arial Black", Font.BOLD, 40));
        titleLabel.setForeground(Color.BLUE.darker());
        titleLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        backgroundPanel.add(titleLabel);
        backgroundPanel.add(Box.createVerticalStrut(50));

        // Level buttons (Beginner, Intermediate, Advanced)
        for (GameLevel level : GameLevel.values()) {
            JButton levelButton = createStyledButton(level.getDisplayName(), level);
            backgroundPanel.add(levelButton);
            backgroundPanel.add(Box.createVerticalStrut(30));
        }

        // ... inside initializeUI() after adding level buttons

// Settings button
JButton settingsButton = createStyledButton("Settings", null);
settingsButton.setMaximumSize(new Dimension(400, 60));
settingsButton.addActionListener(e -> {
    SoundManager.playButtonClickSound();
    controller.showSettings(); // make sure you have this method in your GameController
});
backgroundPanel.add(Box.createVerticalStrut(30)); // spacing before Settings
backgroundPanel.add(settingsButton);


        // Back button
        JButton backButton = createStyledButton("Back to Main Menu", null);
        backButton.setMaximumSize(new Dimension(400, 60));
        backButton.addActionListener(e -> {
            SoundManager.playButtonClickSound();
            controller.showMainMenu();
            dispose();
        });
        backgroundPanel.add(Box.createVerticalStrut(40));
        backgroundPanel.add(backButton);

        setContentPane(backgroundPanel);
    }

    private JButton createStyledButton(String text, GameLevel level) {
        JButton button = new JButton(text) {
            @Override
            protected void paintComponent(Graphics g) {
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

                int arc = 40;
                int w = getWidth();
                int h = getHeight();

                // Gradient fill
                GradientPaint gradient = new GradientPaint(0, 0, new Color(0x000033),
                        0, h, new Color(0x001F4D));
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

        button.setFont(new Font("Arial Black", Font.BOLD, 26));
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
                controller.startNewGame(level);
                dispose();
            });
        }

        return button;
    }
}   