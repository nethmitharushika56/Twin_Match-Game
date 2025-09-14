package view;

import util.SoundManager;

import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.border.EmptyBorder;
import java.awt.*;

/**
 * Modern animated Settings Dialog
 * Clean UI without emojis
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

        // Neon gradient panel
        JPanel mainPanel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                Graphics2D g2d = (Graphics2D) g;
                int width = getWidth();
                int height = getHeight();

                GradientPaint gp = new GradientPaint(
                        0, 0, new Color(25, 25, 112),   // dark blue
                        0, height, new Color(0, 191, 255) // neon cyan
                );
                g2d.setPaint(gp);
                g2d.fillRect(0, 0, width, height);
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

        // Side buttons
        JPanel leftPanel = new JPanel();
        leftPanel.setLayout(new BoxLayout(leftPanel, BoxLayout.Y_AXIS));
        leftPanel.setOpaque(false);

        JButton profileButton = createModernButton("Profile", e -> showProfileDialog());
        JButton instructionsButton = createModernButton("Instructions", e -> showInstructionsDialog());
        JButton privacyButton = createModernButton("Policy", e -> showPrivacyDialog());

        leftPanel.add(profileButton);
        leftPanel.add(Box.createVerticalStrut(12));
        leftPanel.add(instructionsButton);
        leftPanel.add(Box.createVerticalStrut(12));
        leftPanel.add(privacyButton);

        mainPanel.add(leftPanel, BorderLayout.WEST);

        // Center content
        JPanel centerPanel = new JPanel();
        centerPanel.setOpaque(false);
        centerPanel.setLayout(new BoxLayout(centerPanel, BoxLayout.Y_AXIS));
        centerPanel.setBorder(new EmptyBorder(20, 20, 20, 20));

        // Sound row
        JPanel soundRow = new JPanel(new FlowLayout(FlowLayout.LEFT));
        soundRow.setOpaque(false);

        JLabel soundLabel = new JLabel("Sound");
        soundLabel.setFont(new Font("Segoe UI", Font.PLAIN, 18));
        soundLabel.setForeground(Color.WHITE);

        soundToggle = new AnimatedToggle(SoundManager.isSoundEnabled());
        soundRow.add(soundLabel);
        soundRow.add(Box.createHorizontalStrut(15));
        soundRow.add(soundToggle);

        centerPanel.add(soundRow);

        mainPanel.add(centerPanel, BorderLayout.CENTER);

        // Bottom save/cancel
        JPanel bottomPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 15));
        bottomPanel.setOpaque(false);

        JButton saveButton = createFilledButton("Save", new Color(0, 191, 255), e -> saveSettings());
        JButton cancelButton = createFilledButton("Cancel", new Color(255, 69, 58), e -> dispose());

        bottomPanel.add(saveButton);
        bottomPanel.add(cancelButton);

        mainPanel.add(bottomPanel, BorderLayout.SOUTH);

        setContentPane(mainPanel);
    }

    private Object showInstructionsDialog() {
        throw new UnsupportedOperationException("Unimplemented method 'showInstructionsDialog'");
    }

    private Object showProfileDialog() {
        throw new UnsupportedOperationException("Unimplemented method 'showProfileDialog'");
    }

    private Object showPrivacyDialog() {
        throw new UnsupportedOperationException("Unimplemented method 'showPrivacyDialog'");
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

    private JButton createModernButton(String text, java.awt.event.ActionListener action) {
        JButton button = new JButton(text);
        button.setFont(new Font("Segoe UI", Font.BOLD, 14));
        button.setForeground(Color.WHITE);
        button.setFocusPainted(false);
        button.setContentAreaFilled(false);
        button.setBorder(BorderFactory.createLineBorder(Color.WHITE, 2, true));
        button.setAlignmentX(Component.LEFT_ALIGNMENT);

        // Hover effect
        button.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseEntered(java.awt.event.MouseEvent e) {
                button.setForeground(new Color(0, 255, 200));
            }
            @Override
            public void mouseExited(java.awt.event.MouseEvent e) {
                button.setForeground(Color.WHITE);
            }
        });

        button.addActionListener(action);
        return button;
    }

    private JButton createFilledButton(String text, Color bg, java.awt.event.ActionListener action) {
        JButton button = new JButton(text);
        button.setFont(new Font("Segoe UI Black", Font.BOLD, 14));
        button.setForeground(Color.WHITE);
        button.setBackground(bg);
        button.setFocusPainted(false);
        button.setBorder(new RoundedBorder(12, Color.WHITE));
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
            setPreferredSize(new Dimension(60, 30));

            addMouseListener(new java.awt.event.MouseAdapter() {
                @Override
                public void mouseClicked(java.awt.event.MouseEvent e) {
                    toggle();
                }
            });
        }

        public boolean isOn() {
            return on;
        }

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

            // Track
            g2.setColor(new Color(200, 200, 200, 120));
            g2.fillRoundRect(0, 0, width, height, height, height);

            // Fill
            g2.setColor(new Color(0, 191, 255));
            g2.fillRoundRect(0, 0, (int) (width * anim), height, height, height);

            // Knob
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
