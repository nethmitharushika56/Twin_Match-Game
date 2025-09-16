package view;

import util.SoundManager;

import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.border.EmptyBorder;
import java.awt.*;

/**
 * Modern Settings Dialog with gradient background and styled buttons
 */
public class SettingsDialog extends JDialog {
    private AnimatedToggle soundToggle;
    private boolean settingsChanged = false;

    public SettingsDialog(JFrame parent) {
        super(parent, "Game Settings", true);
        initializeDialog();
    }

    private void initializeDialog() {
        setSize(560, 460);
        setLocationRelativeTo(getParent());
        setResizable(false);

        // Dynamic animated background
        JPanel mainPanel = new JPanel() {
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
                
                // Animated gradient colors
                float hue1 = (float) (0.6 + 0.1 * Math.sin(animationTime * 0.5));
                float hue2 = (float) (0.7 + 0.1 * Math.sin(animationTime * 0.3 + Math.PI/3));
                
                Color color1 = Color.getHSBColor(hue1, 0.8f, 0.6f);
                Color color2 = Color.getHSBColor(hue2, 0.6f, 0.9f);
                
                // Animated gradient direction
                float gradientAngle = (float) (Math.sin(animationTime * 0.2) * 0.5 + 0.5);
                GradientPaint gp = new GradientPaint(
                        0, 0, color1,
                        width * gradientAngle, height * (1 - gradientAngle), color2
                );
                
                g2d.setPaint(gp);
                g2d.fillRect(0, 0, width, height);
                
                // Enhanced floating particles with glow
                for (int i = 0; i < 20; i++) {
                    float x = (float) (width * (0.1 + 0.8 * (Math.sin(animationTime + i) + 1) / 2));
                    float y = (float) (height * (0.1 + 0.8 * (Math.cos(animationTime * 0.7 + i * 0.5) + 1) / 2));
                    float size = (float) (4 + 3 * Math.sin(animationTime * 2 + i));
                    
                    // Outer glow
                    g2d.setColor(new Color(255, 255, 255, 80));
                    g2d.fillOval((int)x - 2, (int)y - 2, (int)size + 4, (int)size + 4);
                    
                    // Bright center
                    g2d.setColor(new Color(255, 255, 255, 200));
                    g2d.fillOval((int)x, (int)y, (int)size, (int)size);
                }
                
                // Enhanced floating bubbles with rainbow colors
                Color[] bubbleColors = {
                    new Color(255, 100, 100, 150), // Bright Red
                    new Color(100, 255, 100, 150), // Bright Green
                    new Color(100, 100, 255, 150), // Bright Blue
                    new Color(255, 255, 100, 150), // Bright Yellow
                    new Color(255, 100, 255, 150), // Bright Magenta
                    new Color(100, 255, 255, 150), // Bright Cyan
                    new Color(255, 150, 100, 150)  // Bright Orange
                };
                
                for (int i = 0; i < 12; i++) {
                    Color bubbleColor = bubbleColors[i % bubbleColors.length];
                    float x = (float) (width * (0.05 + 0.9 * (Math.sin(animationTime * 0.3 + i * 0.8) + 1) / 2));
                    float y = (float) (height * (0.05 + 0.9 * (Math.cos(animationTime * 0.4 + i * 0.6) + 1) / 2));
                    float size = (float) (12 + 8 * Math.sin(animationTime * 1.5 + i));
                    
                    // Outer glow
                    g2d.setColor(new Color(bubbleColor.getRed(), bubbleColor.getGreen(), bubbleColor.getBlue(), 60));
                    g2d.fillOval((int)x - 4, (int)y - 4, (int)size + 8, (int)size + 8);
                    
                    // Main bubble
                    g2d.setColor(bubbleColor);
                    g2d.fillOval((int)x, (int)y, (int)size, (int)size);
                }
                
                // Floating sparkles
                g2d.setColor(new Color(255, 215, 0, 180)); // Gold
                for (int i = 0; i < 8; i++) {
                    float x = (float) (width * (0.15 + 0.7 * (Math.sin(animationTime * 0.8 + i * 1.2) + 1) / 2));
                    float y = (float) (height * (0.15 + 0.7 * (Math.cos(animationTime * 0.6 + i * 0.9) + 1) / 2));
                    float size = (float) (6 + 4 * Math.sin(animationTime * 3 + i));
                    
                    // Draw star shape
                    int centerX = (int)x;
                    int centerY = (int)y;
                    int[] xPoints = {centerX, centerX + (int)size, centerX + (int)(size/2), centerX, centerX - (int)(size/2)};
                    int[] yPoints = {centerY - (int)size, centerY, centerY + (int)(size/2), centerY + (int)(size/2), centerY + (int)(size/2)};
                    g2d.fillPolygon(xPoints, yPoints, 5);
                }
            }
            
            private void startAnimation() {
                animationTimer = new Timer(50, e -> {
                    animationTime += 0.05f;
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
        mainPanel.setLayout(new BorderLayout());
        mainPanel.setBorder(new EmptyBorder(16, 16, 16, 16));

        // Header
        JLabel titleLabel = new JLabel("Game Settings", SwingConstants.CENTER);
        titleLabel.setFont(new Font("Segoe UI Black", Font.BOLD, 28));
        titleLabel.setForeground(Color.WHITE);
        titleLabel.setBorder(new EmptyBorder(10, 0, 20, 0));
        mainPanel.add(titleLabel, BorderLayout.NORTH);

        // Left panel with vertical layout for all buttons
        JPanel leftPanel = new JPanel();
        leftPanel.setLayout(new BoxLayout(leftPanel, BoxLayout.Y_AXIS));
        leftPanel.setOpaque(false);
        leftPanel.setBorder(new EmptyBorder(10, 10, 10, 10));

        // Sound toggle
        JLabel soundLabel = new JLabel("Sound");
        soundLabel.setFont(new Font("Segoe UI", Font.BOLD, 16));
        soundLabel.setForeground(Color.WHITE);
        soundToggle = new AnimatedToggle(SoundManager.isSoundEnabled());

        // Buttons
        JButton profileButton = createRoundButton("Profile", e -> showProfileDialog());
        JButton instructionsButton = createRoundButton("Instructions to Play", e -> showInstructionsDialog());
        JButton privacyButton = createRoundButton("Privacy and Policy", e -> showPrivacyDialog());

        // Add all items to left panel in one vertical column
        leftPanel.add(soundLabel);
        leftPanel.add(soundToggle);
        leftPanel.add(Box.createVerticalStrut(20));
        leftPanel.add(profileButton);
        leftPanel.add(Box.createVerticalStrut(15));
        leftPanel.add(instructionsButton);
        leftPanel.add(Box.createVerticalStrut(15));
        leftPanel.add(privacyButton);

        mainPanel.add(leftPanel, BorderLayout.WEST);

        // Bottom Save/Cancel row
        JPanel bottomPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 30, 15));
        bottomPanel.setOpaque(false);

        JButton saveButton = createFilledButton("Save", new Color(135, 206, 250), e -> saveSettings()); // light blue
        JButton cancelButton = createFilledButton("Cancel", new Color(255, 0, 0), e -> dispose());      // red

        bottomPanel.add(saveButton);
        bottomPanel.add(cancelButton);

        mainPanel.add(bottomPanel, BorderLayout.SOUTH);

        setContentPane(mainPanel);
    }

    private void showInstructionsDialog() {
        JOptionPane.showMessageDialog(this,
                "🎮 Twin Match Game Instructions\n\n" +
                "HOW TO PLAY:\n" +
                "1. Click on a tile to reveal an image\n" +
                "2. Click on a second tile to reveal another image\n" +
                "3. If the images match, the tiles disappear and you earn points\n" +
                "4. If they don't match, the tiles flip back\n" +
                "5. Complete the board by matching all pairs\n\n" +
                "GAME LEVELS:\n" +
                "• Beginner: 8 pairs (16 tiles) - No time limit\n" +
                "• Intermediate: 8 pairs (16 tiles) - 1 minute limit\n" +
                "• Advanced: 11 pairs + 3 bonus tiles (25 tiles) - 1 minute limit\n" +
                "  * Bonus tiles add 15 minutes when clicked!\n\n" +
                "CONTROLS:\n" +
                "• Mouse Click: Select tiles\n" +
                "• Pause Button: Pause/Resume game\n" +
                "• Main Menu Button: Return to level selection\n\n" +
                "Good luck and have fun! 🎯",
                "Game Instructions", JOptionPane.INFORMATION_MESSAGE);
    }

    private void showProfileDialog() {
        JOptionPane.showMessageDialog(this,
                "👤 Profile Settings\n\n• Username: Player1\n• Level: Beginner\n• High Score: 0\n\nProfile customization will be available soon.",
                "Profile", JOptionPane.INFORMATION_MESSAGE);
    }

    private void showPrivacyDialog() {
        JOptionPane.showMessageDialog(this,
                "🔒 Privacy Policy\n\nThis game respects your privacy.\n• No personal data is collected.\n• Sound and game settings are stored locally.\n• No internet connection is required.\n\nBy playing, you agree to fair use.",
                "Policy", JOptionPane.INFORMATION_MESSAGE);
    }

    private void saveSettings() {
        SoundManager.setSoundEnabled(soundToggle.isOn());
        settingsChanged = true;
        dispose();
    }

    public boolean isSettingsChanged() {
        return settingsChanged;
    }

    /* ------------------ Helper Methods ------------------ */

    private JButton createRoundButton(String text, java.awt.event.ActionListener action) {
        JButton button = new JButton(text);
        button.setFont(new Font("Segoe UI", Font.BOLD, 14));
        button.setForeground(Color.WHITE);
        button.setFocusPainted(false);
        button.setContentAreaFilled(false);
        button.setBorder(new RoundedBorder(15, Color.WHITE));
        button.setAlignmentX(Component.LEFT_ALIGNMENT);
        button.setPreferredSize(new Dimension(140, 40));

        button.addActionListener(action);
        return button;
    }

    private JButton createFilledButton(String text, Color bg, java.awt.event.ActionListener action) {
        JButton button = new JButton(text);
        button.setFont(new Font("Segoe UI Black", Font.BOLD, 14));
        button.setForeground(Color.BLACK);
        button.setBackground(bg);
        button.setOpaque(true);
        button.setFocusPainted(false);
        button.setBorder(new RoundedBorder(12, bg.darker()));
        button.setPreferredSize(new Dimension(120, 40));

        button.addActionListener(action);
        return button;
    }

    /* ------------------ Animated Toggle ------------------ */
    private static class AnimatedToggle extends JComponent {
        private boolean on;
        private float anim = 0f;
        private Timer timer;

        AnimatedToggle(boolean initial) {
            this.on = initial;
            this.anim = initial ? 1f : 0f;
            setPreferredSize(new Dimension(20, 10));

            addMouseListener(new java.awt.event.MouseAdapter() {
                @Override
                public void mouseClicked(java.awt.event.MouseEvent e) {
                    toggle();
                }
            });
        }

        public boolean isOn() { return on; }

        private void toggle() {
            on = !on;
            if (timer != null && timer.isRunning()) timer.stop();

            timer = new Timer(15, e -> {
                if (on && anim < 1f) anim += 0.1f;
                else if (!on && anim > 0f) anim -= 0.1f;
                anim = Math.max(0f, Math.min(1f, anim));
                repaint();
                if (anim == 0f || anim == 1f) ((Timer) e.getSource()).stop();
            });
            timer.start();
        }

        @Override
        protected void paintComponent(Graphics g) {
            Graphics2D g2 = (Graphics2D) g.create();
            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

            int width = getWidth();
            int height = getHeight();

            g2.setColor(new Color(200, 200, 200, 120));
            g2.fillRoundRect(0, 0, width, height, height, height);

            g2.setColor(new Color(0, 191, 255));
            g2.fillRoundRect(0, 0, (int) (width * anim), height, height, height);

            int knobSize = height - 4;
            int x = (int) ((width - knobSize - 4) * anim) + 2;
            g2.setColor(Color.WHITE);
            g2.fillOval(x, 2, knobSize, knobSize);

            g2.dispose();
        }
    }

    /** Rounded border */
    private static class RoundedBorder implements Border {
        private final int radius;
        private final Color color;

        RoundedBorder(int radius, Color color) {
            this.radius = radius;
            this.color = color;
        }

        @Override
        public Insets getBorderInsets(Component c) {
            return new Insets(6, 6, 6, 6);
        }
        @Override
        public boolean isBorderOpaque() { return false; }
        @Override
        public void paintBorder(Component c, Graphics g, int x, int y, int width, int height) {
            Graphics2D g2 = (Graphics2D) g.create();
            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            g2.setColor(color);
            g2.setStroke(new BasicStroke(2));
            g2.drawRoundRect(x, y, width - 1, height - 1, radius, radius);
            g2.dispose();
        }
    }
}
