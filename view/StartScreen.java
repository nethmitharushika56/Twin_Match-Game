package view;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Start screen with underwater theme and START button
 */
public class StartScreen extends JFrame {
    private GameController controller;
    
    public StartScreen(GameController controller) {
        this.controller = controller;
        initializeStartScreen();
    }
    
    private void initializeStartScreen() {
        setTitle("Twin Match Quest - Start");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(900, 700);
        setLocationRelativeTo(null);
        setResizable(false);
        
        // Create main panel with underwater background
        JPanel mainPanel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                Graphics2D g2d = (Graphics2D) g;
                g2d.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);
                
                // Create underwater gradient background
                GradientPaint gradient = new GradientPaint(
                    0, 0, new Color(0, 150, 136), // Light teal at top
                    0, getHeight(), new Color(0, 50, 100) // Dark blue at bottom
                );
                g2d.setPaint(gradient);
                g2d.fillRect(0, 0, getWidth(), getHeight());
                
                // Add underwater bubbles
                g2d.setColor(new Color(0, 255, 200, 100));
                for (int i = 0; i < 30; i++) {
                    int x = (int) (Math.random() * getWidth());
                    int y = (int) (Math.random() * getHeight());
                    int size = (int) (Math.random() * 15) + 5;
                    g2d.fillOval(x, y, size, size);
                }
                
                // Add some coral-like decorations on the sides
                g2d.setColor(new Color(255, 100, 100, 150));
                // Left coral
                int[] xLeft = {50, 80, 60, 40, 70, 50, 30, 60, 40};
                int[] yLeft = {getHeight()-200, getHeight()-180, getHeight()-160, getHeight()-140, getHeight()-120, getHeight()-100, getHeight()-80, getHeight()-60, getHeight()-40};
                g2d.fillPolygon(xLeft, yLeft, xLeft.length);
                
                // Right coral
                g2d.setColor(new Color(100, 255, 100, 150));
                int[] xRight = {getWidth()-50, getWidth()-80, getWidth()-60, getWidth()-40, getWidth()-70, getWidth()-50, getWidth()-30, getWidth()-60, getWidth()-40};
                int[] yRight = {getHeight()-150, getHeight()-130, getHeight()-110, getHeight()-90, getHeight()-70, getHeight()-50, getHeight()-30, getHeight()-10, getHeight()+10};
                g2d.fillPolygon(xRight, yRight, xRight.length);
            }
        };
        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));
        
        // Game title with 3D effect
        JLabel titleLabel = new JLabel("TWIN MATCH QUEST");
        titleLabel.setFont(new Font("Arial Black", Font.BOLD, 56));
        titleLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        titleLabel.setForeground(Color.WHITE);
        titleLabel.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(new Color(255, 0, 0), 4),
            BorderFactory.createEmptyBorder(30, 50, 30, 50)
        ));
        
        // Subtitle
        JLabel subtitleLabel = new JLabel("Squid Game Memory Challenge");
        subtitleLabel.setFont(new Font("Arial", Font.PLAIN, 24));
        subtitleLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        subtitleLabel.setForeground(new Color(200, 255, 200));
        
        // START button
        JButton startButton = createStartButton();
        
        // Add components with spacing
        mainPanel.add(Box.createVerticalStrut(120));
        mainPanel.add(titleLabel);
        mainPanel.add(Box.createVerticalStrut(30));
        mainPanel.add(subtitleLabel);
        mainPanel.add(Box.createVerticalStrut(100));
        mainPanel.add(startButton);
        mainPanel.add(Box.createVerticalStrut(50));
        
        setContentPane(mainPanel);
    }
    
    private JButton createStartButton() {
        JButton button = new JButton("START") {
            @Override
            protected void paintComponent(Graphics g) {
                Graphics2D g2d = (Graphics2D) g;
                g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                
                // Create gradient for button
                GradientPaint buttonGradient = new GradientPaint(
                    0, 0, new Color(255, 140, 0), // Orange at top
                    0, getHeight(), new Color(255, 69, 0) // Red-orange at bottom
                );
                g2d.setPaint(buttonGradient);
                g2d.fillRoundRect(0, 0, getWidth(), getHeight(), 25, 25);
                
                // Add shadow effect
                g2d.setColor(new Color(0, 0, 0, 60));
                g2d.fillRoundRect(3, 3, getWidth(), getHeight(), 25, 25);
                
                // Draw text
                g2d.setColor(Color.WHITE);
                g2d.setFont(getFont());
                FontMetrics fm = g2d.getFontMetrics();
                int textX = (getWidth() - fm.stringWidth(getText())) / 2;
                int textY = (getHeight() + fm.getAscent()) / 2;
                g2d.drawString(getText(), textX, textY);
            }
        };
        
        button.setAlignmentX(Component.CENTER_ALIGNMENT);
        button.setPreferredSize(new Dimension(300, 70));
        button.setFont(new Font("Arial Black", Font.BOLD, 28));
        button.setForeground(Color.WHITE);
        button.setFocusPainted(false);
        button.setBorderPainted(false);
        button.setContentAreaFilled(false);
        button.setOpaque(false);
        
        button.addActionListener(e -> {
            SoundManager.playButtonClickSound();
            // Open the level selection window
            controller.showLevelSelect();
            dispose(); // Close this start screen