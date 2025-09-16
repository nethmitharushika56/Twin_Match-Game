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

        // Dynamic animated background panel
        JPanel backgroundPanel = new JPanel() {
            private final Image bg = new ImageIcon("assets/level_bg.png").getImage();
            private float animationTime = 0;
            private Timer animationTimer;

            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                Graphics2D g2d = (Graphics2D) g;
                g2d.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);
                g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

                int width = getWidth();
                int height = getHeight();
                
                // Draw base background image
                g2d.drawImage(bg, 0, 0, width, height, this);
                
                // Add animated overlay with transparency
                Color overlayColor = new Color(0, 0, 0, 30);
                g2d.setColor(overlayColor);
                g2d.fillRect(0, 0, width, height);
                
                // Enhanced floating stars with glow
                for (int i = 0; i < 25; i++) {
                    float x = (float) (width * (0.05 + 0.9 * (Math.sin(animationTime * 0.3 + i * 0.4) + 1) / 2));
                    float y = (float) (height * (0.05 + 0.9 * (Math.cos(animationTime * 0.4 + i * 0.3) + 1) / 2));
                    float size = (float) (3 + 4 * Math.sin(animationTime * 2 + i));
                    
                    // Outer glow
                    g2d.setColor(new Color(255, 255, 255, 100));
                    g2d.fillOval((int)x - 3, (int)y - 3, (int)size + 6, (int)size + 6);
                    
                    // Bright center
                    g2d.setColor(new Color(255, 255, 255, 220));
                    g2d.fillOval((int)x, (int)y, (int)size, (int)size);
                }
                
                // Enhanced floating orbs with vibrant colors and glow
                Color[] orbColors = {
                    new Color(255, 50, 50, 180),   // Bright Red
                    new Color(50, 255, 50, 180),   // Bright Green
                    new Color(50, 50, 255, 180),   // Bright Blue
                    new Color(255, 255, 50, 180),  // Bright Yellow
                    new Color(255, 50, 255, 180),  // Bright Magenta
                    new Color(50, 255, 255, 180),  // Bright Cyan
                    new Color(255, 150, 50, 180),  // Bright Orange
                    new Color(150, 50, 255, 180)   // Bright Purple
                };
                
                for (int i = 0; i < 15; i++) {
                    Color orbColor = orbColors[i % orbColors.length];
                    float x = (float) (width * (0.1 + 0.8 * (Math.sin(animationTime * 0.2 + i * 0.7) + 1) / 2));
                    float y = (float) (height * (0.1 + 0.8 * (Math.cos(animationTime * 0.25 + i * 0.5) + 1) / 2));
                    float size = (float) (15 + 10 * Math.sin(animationTime * 1.2 + i));
                    
                    // Outer glow
                    g2d.setColor(new Color(orbColor.getRed(), orbColor.getGreen(), orbColor.getBlue(), 80));
                    g2d.fillOval((int)x - 6, (int)y - 6, (int)size + 12, (int)size + 12);
                    
                    // Main orb
                    g2d.setColor(orbColor);
                    g2d.fillOval((int)x, (int)y, (int)size, (int)size);
                }
                
                // Floating diamonds
                g2d.setColor(new Color(255, 215, 0, 200)); // Gold
                for (int i = 0; i < 10; i++) {
                    float x = (float) (width * (0.2 + 0.6 * (Math.sin(animationTime * 0.6 + i * 1.1) + 1) / 2));
                    float y = (float) (height * (0.2 + 0.6 * (Math.cos(animationTime * 0.5 + i * 0.8) + 1) / 2));
                    float size = (float) (8 + 6 * Math.sin(animationTime * 2.5 + i));
                    
                    // Draw diamond shape
                    int centerX = (int)x;
                    int centerY = (int)y;
                    int[] xPoints = {centerX, centerX + (int)size, centerX, centerX - (int)size};
                    int[] yPoints = {centerY - (int)size, centerY, centerY + (int)size, centerY};
                    g2d.fillPolygon(xPoints, yPoints, 4);
                }
                
                // Enhanced animated gradient lines with glow
                g2d.setStroke(new BasicStroke(4));
                for (int i = 0; i < 8; i++) {
                    float alpha = (float) (80 + 50 * Math.sin(animationTime * 0.8 + i));
                    
                    float startX = (float) (width * (0.05 + 0.9 * (Math.sin(animationTime * 0.15 + i) + 1) / 2));
                    float startY = (float) (height * (0.1 + 0.8 * (Math.cos(animationTime * 0.1 + i) + 1) / 2));
                    float endX = (float) (width * (0.15 + 0.7 * (Math.sin(animationTime * 0.2 + i + Math.PI) + 1) / 2));
                    float endY = (float) (height * (0.2 + 0.6 * (Math.cos(animationTime * 0.18 + i + Math.PI) + 1) / 2));
                    
                    // Glow effect
                    g2d.setColor(new Color(255, 255, 255, (int)(alpha * 0.3)));
                    g2d.setStroke(new BasicStroke(8));
                    g2d.drawLine((int)startX, (int)startY, (int)endX, (int)endY);
                    
                    // Main line
                    g2d.setColor(new Color(255, 255, 255, (int)alpha));
                    g2d.setStroke(new BasicStroke(3));
                    g2d.drawLine((int)startX, (int)startY, (int)endX, (int)endY);
                }
            }
            
            private void startAnimation() {
                animationTimer = new Timer(50, e -> {
                    animationTime += 0.03f;
                    repaint();
                });
                animationTimer.start();
            }
            
            @Override
            public void addNotify() {
                super.addNotify();
                startAnimation();
            }
            
            @Override
            public void removeNotify() {
                super.removeNotify();
                if (animationTimer != null) {
                    animationTimer.stop();
                }
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
        JButton backButton = createStyledButton("Back to Home Page", null);
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