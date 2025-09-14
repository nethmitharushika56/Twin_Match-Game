package view;

import util.SoundManager;

import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.border.EmptyBorder;
import java.awt.*;

/**
 * Settings dialog for game options with toggle-style sound control
 */
public class SettingsDialog extends JDialog {
    private JToggleButton soundToggle;
    private boolean settingsChanged = false;

    public SettingsDialog(JFrame parent) {
        super(parent, "Game Settings", true);
        initializeDialog();
    }

    private void initializeDialog() {
        setSize(500, 450);
        setLocationRelativeTo(getParent());
        setResizable(false);

        // Gradient background main panel (green -> blue)
        JPanel mainPanel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                Graphics2D g2d = (Graphics2D) g;
                int width = getWidth();
                int height = getHeight();
                GradientPaint gp = new GradientPaint(
                        0, 0, new Color(0, 200, 0),
                        0, height, new Color(0, 100, 255)
                );
                g2d.setPaint(gp);
                g2d.fillRect(0, 0, width, height);
            }
        };
        mainPanel.setLayout(new BorderLayout());
        mainPanel.setBorder(new EmptyBorder(12, 12, 12, 12));

        // Header (title centered)
        JLabel titleLabel = new JLabel("Game Settings", SwingConstants.CENTER);
        titleLabel.setFont(new Font("Arial Black", Font.BOLD, 28));
        titleLabel.setForeground(Color.WHITE);
        JPanel header = new JPanel(new BorderLayout());
        header.setOpaque(false);
        header.setBorder(new EmptyBorder(10, 0, 10, 0));
        header.add(titleLabel, BorderLayout.CENTER);
        mainPanel.add(header, BorderLayout.NORTH);

        // Left vertical buttons (stacked, top-left)
        JPanel leftButtonPanel = new JPanel();
        leftButtonPanel.setLayout(new BoxLayout(leftButtonPanel, BoxLayout.Y_AXIS));
        leftButtonPanel.setOpaque(false);
        leftButtonPanel.setBorder(new EmptyBorder(10, 10, 10, 10));

        JButton profileButton = new JButton("Profile");
        styleTransparentButton(profileButton);
        profileButton.setAlignmentX(Component.LEFT_ALIGNMENT);
        profileButton.addActionListener(e -> showProfileDialog());

        JButton instructionsButton = new JButton("Instructions");
        styleTransparentButton(instructionsButton);
        instructionsButton.setAlignmentX(Component.LEFT_ALIGNMENT);
        instructionsButton.addActionListener(e -> showInstructionsDialog());

        JButton privacyButton = new JButton("Policy");
        styleTransparentButton(privacyButton);
        privacyButton.setAlignmentX(Component.LEFT_ALIGNMENT);
        privacyButton.addActionListener(e -> showPrivacyDialog());

        leftButtonPanel.add(profileButton);
        leftButtonPanel.add(Box.createVerticalStrut(10));
        leftButtonPanel.add(instructionsButton);
        leftButtonPanel.add(Box.createVerticalStrut(10));
        leftButtonPanel.add(privacyButton);

        mainPanel.add(leftButtonPanel, BorderLayout.WEST);

        // Center area for settings (sound toggle, etc.)
        JPanel centerPanel = new JPanel();
        centerPanel.setOpaque(false);
        centerPanel.setLayout(new BoxLayout(centerPanel, BoxLayout.Y_AXIS));
        centerPanel.setBorder(new EmptyBorder(20, 20, 20, 20));

        // Sound row (label + toggle)
        JPanel soundRow = new JPanel(new FlowLayout(FlowLayout.LEFT));
        soundRow.setOpaque(false);

        JLabel soundLabel = new JLabel("Sound");
        soundLabel.setFont(new Font("Arial", Font.PLAIN, 16));
        soundLabel.setForeground(Color.WHITE);

        soundToggle = new JToggleButton();
        soundToggle.setSelected(SoundManager.isSoundEnabled());
        soundToggle.setPreferredSize(new Dimension(64, 32));
        styleSmallToggle(soundToggle);
        soundToggle.addActionListener(e -> {
            updateToggleStyle(soundToggle);
            SoundManager.setSoundEnabled(soundToggle.isSelected());
        });

        soundRow.add(soundLabel);
        soundRow.add(Box.createHorizontalStrut(12));
        soundRow.add(soundToggle);

        centerPanel.add(soundRow);

        mainPanel.add(centerPanel, BorderLayout.CENTER);

        // Bottom Save / Cancel buttons
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 12));
        buttonPanel.setOpaque(false);

        JButton saveButton = new JButton("Save");
        styleFilledButton(saveButton, new Color(173, 216, 230)); // light blue
        saveButton.addActionListener(e -> saveSettings());

        JButton cancelButton = new JButton("Cancel");
        styleFilledButton(cancelButton, new Color(220, 20, 60)); // red
        cancelButton.addActionListener(e -> dispose());

        buttonPanel.add(saveButton);
        buttonPanel.add(cancelButton);

        mainPanel.add(buttonPanel, BorderLayout.SOUTH);

        setContentPane(mainPanel);
    }

    /** Save settings to SoundManager */
    private void saveSettings() {
        SoundManager.setSoundEnabled(soundToggle.isSelected());
        settingsChanged = true;
        dispose();
    }

    public boolean isSettingsChanged() {
        return settingsChanged;
    }

    /** Dummy dialogs for other sections */
    private void showProfileDialog() {
        JOptionPane.showMessageDialog(this,
                "User Profile:\nName: Player1\nLevel: 5",
                "Profile", JOptionPane.INFORMATION_MESSAGE);
    }

    private void showInstructionsDialog() {
        JOptionPane.showMessageDialog(this,
                "Gameplay Instructions:\n- Move with arrow keys\n- Collect coins\n- Avoid enemies",
                "Instructions", JOptionPane.INFORMATION_MESSAGE);
    }

    private void showPrivacyDialog() {
        JOptionPane.showMessageDialog(this,
                "Privacy & Policies:\n- Your data is safe.\n- We do not share information.",
                "Privacy & Policies", JOptionPane.INFORMATION_MESSAGE);
    }

    /* ---------------- Styling helpers ---------------- */

    /** Transparent, rounded-left buttons (for Profile/Instructions/Policy) */
    private void styleTransparentButton(JButton button) {
        button.setFont(new Font("Arial", Font.BOLD, 14));
        button.setContentAreaFilled(false);
        button.setFocusPainted(false);
        button.setForeground(Color.WHITE);
        button.setBorder(BorderFactory.createLineBorder(Color.WHITE, 2, true)); // rounded border
        button.setOpaque(false);
        button.setPreferredSize(new Dimension(120, 36));
    }

    /** Filled button with rounded border (for Save/Cancel) */
    private void styleFilledButton(JButton button, Color bgColor) {
        button.setFont(new Font("Arial Black", Font.BOLD, 14));
        button.setBackground(bgColor);
        button.setForeground(Color.WHITE);
        button.setFocusPainted(false);
        button.setOpaque(true);
        button.setBorder(new RoundedBorder(10)); // rounded border
        button.setPreferredSize(new Dimension(110, 38));
    }

    /** Small toggle style (simple rounded border + filled background) */
    private void styleSmallToggle(JToggleButton toggle) {
        toggle.setFocusPainted(false);
        toggle.setBorder(new RoundedBorder(12));
        toggle.setOpaque(true);
        updateToggleStyle(toggle);
    }

    /** Update toggle colours (on/off) */
    private void updateToggleStyle(JToggleButton toggle) {
        if (toggle.isSelected()) {
            toggle.setBackground(new Color(100, 149, 237)); // blue when on
        } else {
            toggle.setBackground(Color.BLUE); // gray when off (muted)
        }
    }

    /** Rounded border helper */
    private static class RoundedBorder implements Border {
        private final int radius;
        private final Color color;

        RoundedBorder(int radius) {
            this(radius, Color.WHITE);
        }

        RoundedBorder(int radius, Color color) {
            this.radius = radius;
            this.color = color;
        }

        @Override
        public Insets getBorderInsets(Component c) {
            return new Insets(radius + 2, radius + 6, radius + 2, radius + 6);
        }

        @Override
        public boolean isBorderOpaque() {
            return false;
        }

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
