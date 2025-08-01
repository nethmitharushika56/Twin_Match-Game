package view;

import javax.swing.*;
import java.awt.*;
import controller.GameController;

public class StartScreen extends JFrame {
    private final GameController controller;
    private final Image backgroundImage;

    public StartScreen(GameController controller) {
        this.controller = controller;
        this.backgroundImage = new ImageIcon("assets/twin_match_bg.jpg").getImage(); // Ensure this path is correct
        initializeStartScreen();
    }

    private void initializeStartScreen() {
        setTitle("Twin Match Quest - Start");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setSize(900, 700);
        setLocationRelativeTo(null);
        setResizable(false);

        // Background panel
        JPanel mainPanel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                g.drawImage(backgroundImage, 0, 0, getWidth(), getHeight(), this);
            }
        };
        mainPanel.setLayout(new BorderLayout());

        // Custom glowing start button
        JButton startButton = new JButton("START GAME") {
            @Override
            protected void paintComponent(Graphics g) {
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

                int arc = 40;

                // Glowing blue gradient fill
                GradientPaint gradient = new GradientPaint(
                        0, 0, new Color(0x003366),
                        getWidth(), getHeight(), new Color(0x3399FF)
                );
                g2.setPaint(gradient);
                g2.fillRoundRect(0, 0, getWidth(), getHeight(), arc, arc);

                // White glowing border
                g2.setColor(Color.WHITE);
                g2.setStroke(new BasicStroke(3f));
                g2.drawRoundRect(1, 1, getWidth() - 3, getHeight() - 3, arc, arc);

                super.paintComponent(g);
                g2.dispose();
            }
        };

        startButton.setFont(new Font("Arial Black", Font.BOLD, 28));
        startButton.setForeground(Color.WHITE);
        startButton.setContentAreaFilled(false);
        startButton.setFocusPainted(false);
        startButton.setBorderPainted(false);
        startButton.setOpaque(false);
        startButton.setPreferredSize(new Dimension(320, 80));
        startButton.setHorizontalAlignment(SwingConstants.CENTER);

        // Button action
        startButton.addActionListener(e -> {
            controller.showLevelSelection();
            dispose();
        });

        // Add button to panel
        JPanel buttonPanel = new JPanel();
        buttonPanel.setOpaque(false);
        buttonPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 0, 30));
        buttonPanel.add(startButton);

        mainPanel.add(buttonPanel, BorderLayout.SOUTH);

        setContentPane(mainPanel);
        setVisible(true);
    }
}
