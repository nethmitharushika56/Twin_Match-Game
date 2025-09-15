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
        setSize(520, 460);
        setLocationRelativeTo(getParent());
        setResizable(false);

        // Gradient background
        JPanel mainPanel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                Graphics2D g2d = (Graphics2D) g;
                g2d.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);

                GradientPaint gp = new GradientPaint(
                        0, 0, new Color(0, 0, 139),
                        0, getHeight(), new Color(173, 216, 230)
                );
                g2d.setPaint(gp);
                g2d.fillRect(0, 0, getWidth(), getHeight());
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

        // Left panel with vertical items
        JPanel leftPanel = new JPanel();
        leftPanel.setLayout(new BoxLayout(leftPanel, BoxLayout.Y_AXIS));
        leftPanel.setOpaque(false);
        leftPanel.setBorder(new EmptyBorder(10, 10, 10, 10));

        // Sound row
        JPanel soundRow = new JPanel(new FlowLayout(FlowLayout.LEFT));
        soundRow.setOpaque(false);
        JLabel soundLabel = new JLabel("Sound");
        soundLabel.setFont(new Font("Segoe UI", Font.BOLD, 16));
        soundLabel.setForeground(Color.WHITE);
        soundToggle = new AnimatedToggle(SoundManager.isSoundEnabled());
        soundRow.add(soundLabel);
        soundRow.add(Box.createHorizontalStrut(15));
        soundRow.add(soundToggle);

        // Buttons
        JButton profileButton = createRoundButton("Profile", e -> showProfileDialog());
        JButton instructionsButton = createRoundButton("Instructions", e -> showInstructionsDialog());
        JButton privacyButton = createRoundButton("Policy", e -> showPrivacyDialog());

        // Add to left panel
        leftPanel.add(soundRow);
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

        JButton saveButton = createFilledButton("Save", new Color(30, 144, 255), e -> saveSettings()); // light blue
        JButton cancelButton = createFilledButton("Cancel", new Color(220, 20, 60), e -> dispose());   // red

        bottomPanel.add(saveButton);
        bottomPanel.add(cancelButton);

        mainPanel.add(bottomPanel, BorderLayout.SOUTH);

        setContentPane(mainPanel);
    }

    private void showInstructionsDialog() {
        JOptionPane.showMessageDialog(this,
                "📘 Game Instructions\n\n1. Start the game using the Play button.\n2. Use arrow keys / controls to move.\n3. Score points by completing objectives.\n4. You can pause from the menu.\n\nEnjoy playing!",
                "Instructions", JOptionPane.INFORMATION_MESSAGE);
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
        button.setForeground(Color.WHITE);
        button.setBackground(bg);
        button.setOpaque(true);
        button.setFocusPainted(false);
        button.setBorder(new RoundedBorder(12, Color.WHITE));
        button.setPreferredSize(new Dimension(120, 40));

        // Hover darker
        button.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseEntered(java.awt.event.MouseEvent e) {
                button.setBackground(bg.darker());
            }
            @Override
            public void mouseExited(java.awt.event.MouseEvent e) {
                button.setBackground(bg);
            }
        });

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
            setPreferredSize(new Dimension(60, 30));

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
