package view;

import javax.swing.*;
import java.awt.*;

/**
 * Settings dialog for game options
 */
public class SettingsDialog extends JDialog {
    private JCheckBox soundCheckBox;
    private JCheckBox animationCheckBox;
    private boolean settingsChanged = false;
    
    public SettingsDialog(JFrame parent) {
        super(parent, "Game Settings", true);
        initializeDialog();
    }
    
    private void initializeDialog() {
        setSize(400, 300);
        setLocationRelativeTo(getParent());
        setResizable(false);
        
        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));
        mainPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        
        // Title
        JLabel titleLabel = new JLabel("Game Settings");
        titleLabel.setFont(new Font("Arial Black", Font.BOLD, 24));
        titleLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        titleLabel.setForeground(new Color(70, 130, 180));
        
        // Sound settings
        JPanel soundPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        soundPanel.setOpaque(false);
        soundCheckBox = new JCheckBox("Enable Sound Effects");
        soundCheckBox.setSelected(SoundManager.isSoundEnabled());
        soundCheckBox.setFont(new Font("Arial", Font.PLAIN, 16));
        soundPanel.add(soundCheckBox);
        
        // Animation settings
        JPanel animationPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        animationPanel.setOpaque(false);
        animationCheckBox = new JCheckBox("Enable Animations");
        animationCheckBox.setSelected(true);
        animationCheckBox.setFont(new Font("Arial", Font.PLAIN, 16));
        animationPanel.add(animationCheckBox);
        
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
        mainPanel.add(animationPanel);
        mainPanel.add(Box.createVerticalStrut(40));
        mainPanel.add(buttonPanel);
        
        setContentPane(mainPanel);
    }
    
    private void saveSettings() {
        SoundManager.setSoundEnabled(soundCheckBox.isSelected());
        settingsChanged = true;
        dispose();
    }
    
    public boolean isSettingsChanged() {
        return settingsChanged;
    }
} 