package view;

import controller.GameController;
import model.GameLevel;
import util.SoundManager;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

/**
 * Green-themed Level Selection screen with custom styled buttons and glowing borders.
 */
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
            backgroundImage = ImageIO.read(new File("assets/level_bg.jpg")); // adjust if needed
        } catch (IOException e) {
            e.printStackTrace();
        }
        
    }

    class BackgroundPanel extends JPanel {
        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            if (backgroundImage != null) {
                g.drawImage(backgroundImage, 0, 0, getWidth(), getHeight(), this);
            }
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
    
        JLabel titleLabel = new JLabel("Choose Your Level");
        titleLabel.setFont(new Font("Arial Black", Font.BOLD, 36));
        titleLabel.setForeground(Color.WHITE);
        titleLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        backgroundPanel.add(titleLabel);
        backgroundPanel.add(Box.createVerticalStrut(40));
    
        for (GameLevel level : GameLevel.values()) {
            backgroundPanel.add(createStyledPanel(level));
            backgroundPanel.add(Box.createVerticalStrut(20));
        }
    
        RoundedButton backButton = new RoundedButton("Back to Main Menu");
        backButton.setPreferredSize(new Dimension(250, 50));
        backButton.setFont(new Font("Arial", Font.BOLD, 18));
        backButton.addActionListener(e -> {
            SoundManager.playButtonClickSound();
            controller.showMainMenu();
            dispose();
        });
    
        backgroundPanel.add(Box.createVerticalStrut(30));
        backgroundPanel.add(backButton);
    
        setContentPane(backgroundPanel);
    }
    
    private JPanel createStyledPanel(GameLevel level) {
        JPanel panel = new JPanel(new BorderLayout()) {
            @Override
            protected void paintComponent(Graphics g) {
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                GradientPaint gp = new GradientPaint(0, 0, new Color(0, 60, 0, 180), 0, getHeight(), new Color(0, 100, 0, 180));
                g2.setPaint(gp);
                g2.fillRoundRect(0, 0, getWidth(), getHeight(), 30, 30);
                g2.setColor(new Color(0, 255, 100));
                g2.setStroke(new BasicStroke(3));
                g2.drawRoundRect(1, 1, getWidth() - 3, getHeight() - 3, 30, 30);
                g2.dispose();
                super.paintComponent(g);
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
            controller.startNewGame(level); // Make sure GameLevel matches model package
            dispose();
        });

        JLabel descLabel = new JLabel("<html><div style='color:white; font-size:13px;'>" + level.getDescription() + "</div></html>");
        descLabel.setBorder(BorderFactory.createEmptyBorder(5, 15, 5, 10));

        panel.add(levelButton, BorderLayout.WEST);
        panel.add(descLabel, BorderLayout.CENTER);
        return panel;
    }

    // Custom styled button class
    static class RoundedButton extends JButton {
        public RoundedButton(String text) {
            super(text);
            setFocusPainted(false);
            setContentAreaFilled(false);
            setForeground(Color.WHITE);
            setBorderPainted(false);
        }

        @Override
        protected void paintComponent(Graphics g) {
            Graphics2D g2 = (Graphics2D) g.create();
            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            GradientPaint gp = new GradientPaint(0, 0, new Color(0, 120, 0), 0, getHeight(), new Color(0, 60, 0));
            g2.setPaint(gp);
            g2.fillRoundRect(0, 0, getWidth(), getHeight(), 40, 40);
            g2.setColor(new Color(0, 255, 100));
            g2.setStroke(new BasicStroke(3));
            g2.drawRoundRect(1, 1, getWidth() - 3, getHeight() - 3, 40, 40);
            g2.dispose();
            super.paintComponent(g);
        }
    }
}
