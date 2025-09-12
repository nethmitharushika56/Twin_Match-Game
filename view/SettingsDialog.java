package view;

import javax.swing.*;
import java.awt.*;

/**
 * Settings dialog for game options
 */
public class SettingsDialog extends JDialog {
    private JCheckBox soundCheckBox;
    private JCheckBox muteCheckBox;
    private boolean settingsChanged = false;

    public SettingsDialog(JFrame parent) {
        super(parent, "Game Settings", true);
        initializeDialog();
    }

    private void initializeDialog() {
        setSize(500, 450);
        setLocationRelativeTo(getParent());
        setResizable(false);

        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));
        mainPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        // Title
        JLabel titleLabel = new JLabel("Game Settings");
        titleLabel.setFont(new Font("Arial Black", Font.BOLD, 26));
        titleLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        titleLabel.setForeground(new Color(70, 130, 180));

        // Sound settings
        JPanel soundPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        soundPanel.setOpaque(false);
        soundCheckBox = new JCheckBox("Enable Sound Effects");
        soundCheckBox.setSelected(SoundManager.isSoundEnabled());
        soundCheckBox.setFont(new Font("Arial", Font.PLAIN, 16));

        muteCheckBox = new JCheckBox("Mute All Sounds");
        muteCheckBox.setSelected(!SoundManager.isSoundEnabled());
        muteCheckBox.setFont(new Font("Arial", Font.PLAIN, 16));
        muteCheckBox.addActionListener(e -> soundCheckBox.setSelected(!muteCheckBox.isSelected()));

        soundPanel.add(soundCheckBox);
        soundPanel.add(muteCheckBox);

        // Profile button
        JPanel profilePanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        profilePanel.setOpaque(false);
        JButton profileButton = new JButton("Profile");
        profileButton.setFont(new Font("Arial", Font.PLAIN, 16));
        profileButton.addActionListener(e -> showProfileDialog());
        profilePanel.add(profileButton);

        // Gameplay instructions button
        JPanel instructionsPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        instructionsPanel.setOpaque(false);
        JButton instructionsButton = new JButton("Gameplay Instructions");
        instructionsButton.setFont(new Font("Arial", Font.PLAIN, 16));
        instructionsButton.addActionListener(e -> showInstructionsDialog());
        instructionsPanel.add(instructionsButton);

        // Privacy & Policies button
        JPanel privacyPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        privacyPanel.setOpaque(false);
        JButton privacyButton = new JButton("Privacy & Policies");
        privacyButton.setFont(new Font("Arial", Font.PLAIN, 16));
        privacyButton.addActionListener(e -> showPrivacyDialog());
        privacyPanel.add(privacyButton);

        // Buttons
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 10));
        buttonPanel.setOpaque(false);

        JButton saveButton = new JButton("Save");
        saveButton.setFont(new Font("Arial", Font.BOLD, 14));
        saveButton.setBackground(new Color(100, 149, 237));
        saveButton.setForeground(Color.WHITE);
        saveButton.setFocusPainted(false);
        saveButton.setBorderPainted(false);
        saveButton.addActionListener(e -> saveSettings());

        JButton cancelButton = new JButton("Cancel");
        cancelButton.setFont(new Font("Arial", Font.BOLD, 14));
        cancelButton.setBackground(new Color(220, 20, 60));
        cancelButton.setForeground(Color.WHITE);
        cancelButton.setFocusPainted(false);
        cancelButton.setBorderPainted(false);
        cancelButton.addActionListener(e -> dispose());

        buttonPanel.add(saveButton);
        buttonPanel.add(cancelButton);

        // Add components
        mainPanel.add(titleLabel);
        mainPanel.add(Box.createVerticalStrut(30));
        mainPanel.add(soundPanel);
        mainPanel.add(Box.createVerticalStrut(20));
        mainPanel.add(profilePanel);
        mainPanel.add(Box.createVerticalStrut(10));
        mainPanel.add(instructionsPanel);
        mainPanel.add(Box.createVerticalStrut(10));
        mainPanel.add(privacyPanel);
        mainPanel.add(Box.createVerticalStrut(40));
        mainPanel.add(buttonPanel);

        setContentPane(mainPanel);
    }

    private void saveSettings() {
        SoundManager.setSoundEnabled(soundCheckBox.isSelected() && !muteCheckBox.isSelected());
        settingsChanged = true;
        dispose();
    }

    public boolean isSettingsChanged() {
        return settingsChanged;
    }

    // Dummy dialogs for profile, instructions, privacy
    private void showProfileDialog() {
        JOptionPane.showMessageDialog(this, "User Profile:\nName: Player1\nLevel: 5", "Profile", JOptionPane.INFORMATION_MESSAGE);
    }

    private void showInstructionsDialog() {
        JOptionPane.showMessageDialog(this, "Gameplay Instructions:\n- Move with arrow keys\n- Collect coins\n- Avoid enemies", "Instructions", JOptionPane.INFORMATION_MESSAGE);
    }

    private void showPrivacyDialog() {
        JOptionPane.showMessageDialog(this, "Privacy & Policies:\n- Your data is safe.\n- We do not share information.", "Privacy & Policies", JOptionPane.INFORMATION_MESSAGE);
    }
}
