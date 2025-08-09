package view;

import controller.GameController;
import model.GameLevel;
import util.SoundManager;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Objects;

public class LevelSelectionScreen extends JFrame {
    private final GameController controller;
    private BufferedImage backgroundImage;

    public LevelSelectionScreen(GameController controller) {
        this.controller = controller;
        loadBackgroundImage();
        initializeUI();
    }

    private void loadBackgroundImage() {
        try {
            backgroundImage = ImageIO.read(Objects.requireNonNull(getClass().getResource("/assets/level_bg.png")));
        } catch (IOException | NullPointerException e) {
            System.err.println("Could not load background image.");
            e.printStackTrace();
        }
    }

    private void initializeUI() {
        setTitle("Select Difficulty - Twin Match");
        setSize(700, 550);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setResizable(false);

        BackgroundPanel backgroundPanel = new BackgroundPanel();
        backgroundPanel.setLayout(new BoxLayout(backgroundPanel, BoxLayout.Y_AXIS));
        backgroundPanel.setBorder(BorderFactory.createEmptyBorder(40, 40, 40, 40));
        backgroundPanel.setOpaque(false);

        // 🔷 Title Label with dark green gradient & glowing white text
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
                g2.fillRoundRect(0, 0, w, h, 40, 40);

                // Glow effect for white text
                g2.setFont(getFont());
                String text = getText();
                FontMetrics fm = g2.getFontMetrics();
                int x = (w - fm.stringWidth(text)) / 2;
                int y = ((h - fm.getHeight()) / 2) + fm.getAscent();

                g2.setColor(new Color(255, 255, 255, 100));
                for (int i = 1; i <= 5; i++) {
                    g2.drawString(text, x - i, y - i);
                    g2.drawString(text, x + i, y + i);
                    g2.drawString(text, x - i, y + i);
                    g2.drawString(text, x + i, y - i);
                }

                g2.setColor(Color.WHITE);
                g2.drawString(text, x, y);

                g2.dispose();
            }
        };
        titleLabel.setFont(new Font("Arial Black", Font.BOLD, 42));
        titleLabel.setPreferredSize(new Dimension(500, 80));
        titleLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        titleLabel.setOpaque(false);

        backgroundPanel.add(titleLabel);
        backgroundPanel.add(Box.createVerticalStrut(40));

        // 🔹 Level Panels
        for (GameLevel level : GameLevel.values()) {
            backgroundPanel.add(createStyledPanel(level));
            backgroundPanel.add(Box.createVerticalStrut(20));
        }

        // 🔹 Back Button
        RoundedButton backButton = new RoundedButton("Back to Main Menu");
        backButton.setPreferredSize(new Dimension(280, 70));
        backButton.setFont(new Font("Arial Black", Font.BOLD, 22));
        backButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        backButton.addActionListener(e -> {
            SoundManager.playButtonClickSound();
            controller.showMainMenu();
            dispose();
        });

        backgroundPanel.add(Box.createVerticalStrut(30));
        backgroundPanel.add(backButton);

        makeTransparent(backgroundPanel);
        setContentPane(backgroundPanel);
    }

    private JPanel createStyledPanel(GameLevel level) {
        JPanel panel = new JPanel(new BorderLayout()) {
            @Override
            protected void paintComponent(Graphics g) {
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                GradientPaint gp = new GradientPaint(0, 0, new Color(0x001F4D),
                                                     0, getHeight(), new Color(0x003366));
                g2.setPaint(gp);
                g2.fillRoundRect(0, 0, getWidth(), getHeight(), 30, 30);
                g2.setColor(new Color(0x00BFFF)); // Blue glow
                g2.setStroke(new BasicStroke(3));
                g2.drawRoundRect(1, 1, getWidth() - 3, getHeight() - 3, 30, 30);
                g2.dispose();
            }
        };

        panel.setOpaque(false);
        panel.setMaximumSize(new Dimension(600, 80));
        panel.setBorder(BorderFactory.createEmptyBorder(10, 15, 10, 15));

        RoundedButton levelButton = new RoundedButton(level.getDisplayName());
        levelButton.setFont(new Font("Arial Black", Font.BOLD, 20));
        levelButton.setPreferredSize(new Dimension(200, 50));
        levelButton.addActionListener(e -> {
            SoundManager.playButtonClickSound();
            controller.startNewGame(level);
            dispose();
        });

        JLabel descLabel = new JLabel("<html><div style='color:white; font-size:13px;'>" + level.getDescription() + "</div></html>");
        descLabel.setBorder(BorderFactory.createEmptyBorder(5, 15, 5, 10));
        descLabel.setOpaque(false);

        panel.add(levelButton, BorderLayout.WEST);
        panel.add(descLabel, BorderLayout.CENTER);
        return panel;
    }

    // ✅ Background panel
    class BackgroundPanel extends JPanel {
        @Override
        protected void paintComponent(Graphics g) {
            if (backgroundImage != null) {
                g.drawImage(backgroundImage, 0, 0, getWidth(), getHeight(), this);
            }
            super.paintComponent(g);
        }
    }

    // ✅ Transparent recursion
    private void makeTransparent(Component comp) {
        if (comp instanceof JComponent) {
            ((JComponent) comp).setOpaque(false);
        }
        if (comp instanceof Container) {
            for (Component child : ((Container) comp).getComponents()) {
                makeTransparent(child);
            }
        }
    }

    // ✅ Custom button with dark blue gradient
    static class RoundedButton extends JButton {
        public RoundedButton(String text) {
            super(text);
            setFocusPainted(false);
            setContentAreaFilled(false);
            setForeground(Color.WHITE);
            setBorderPainted(false);
            setOpaque(false);
        }

        @Override
        protected void paintComponent(Graphics g) {
            Graphics2D g2 = (Graphics2D) g.create();
            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

            int arc = 50;
            GradientPaint gp = new GradientPaint(0, 0, new Color(0x001F4D),
                                                 0, getHeight(), new Color(0x003366));
            g2.setPaint(gp);
            g2.fillRoundRect(0, 0, getWidth(), getHeight(), arc, arc);

            g2.setColor(new Color(0x00BFFF));
            g2.setStroke(new BasicStroke(3));
            g2.drawRoundRect(1, 1, getWidth() - 3, getHeight() - 3, arc, arc);

            g2.dispose();
            super.paintComponent(g);
        }
    }
}
