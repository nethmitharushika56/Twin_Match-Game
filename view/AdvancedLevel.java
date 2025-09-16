package view;

import controller.GameController;
import util.SoundManager;
import util.AnimationManager;

import javax.swing.*;
import javax.swing.border.EmptyBorder;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.Collections;

public class AdvancedLevel extends JFrame {
    private final int GRID_SIZE = 5; // 5x5 grid = 25 tiles
    private final JButton[] tiles = new JButton[GRID_SIZE * GRID_SIZE];
    private final String BACK_IMAGE = "assets/tiles_back.png";
    private String[] tileImages;
    private JButton firstSelected = null;
    private JButton secondSelected = null;
    private javax.swing.Timer flipBackTimer;
    private int matchedPairs = 0;

    private final GameController controller;
    private JLabel timerLabel;
    private javax.swing.Timer gameTimer;
    private int remainingSeconds = 60; // 1 minute countdown

    // Controls whether player clicks are processed (disabled during initial countdown)
    private boolean allowClicks = false;

    public AdvancedLevel(GameController controller) {
        this.controller = controller;

        setTitle("Advanced Level - Twin Match");
        setSize(900, 950);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // ---------- Background Panel ----------
        JPanel backgroundPanel = new JPanel() {
            private Image bg = new ImageIcon("assets/advanced_bg.png").getImage();

            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                g.drawImage(bg, 0, 0, getWidth(), getHeight(), this);
            }
        };
        backgroundPanel.setLayout(new BorderLayout());
        setContentPane(backgroundPanel);

        // ---------- Top Control Panel ----------
        JPanel controlPanel = new JPanel();
        controlPanel.setOpaque(false);

        JButton pauseBtn = createCustomButton("Pause");
        JButton backBtn = createCustomButton("Back");

        pauseBtn.setPreferredSize(new Dimension(220, 60));
        backBtn.setPreferredSize(new Dimension(220, 60));

        pauseBtn.addActionListener(e -> {
            SoundManager.playButtonClickSound();
            pauseTimer();
            JOptionPane.showMessageDialog(this, "Game Paused");
            startTimer(); // resume after dialog
        });

        backBtn.addActionListener(e -> {
            SoundManager.playButtonClickSound();
            if (gameTimer != null) gameTimer.stop();
            dispose();
            new LevelSelectionScreen(controller);
        });

        // Timer label (reflect actual remainingSeconds)
        timerLabel = new JLabel(formatTime(remainingSeconds));
        timerLabel.setFont(new Font("Arial Black", Font.BOLD, 24));
        timerLabel.setForeground(Color.WHITE);

        controlPanel.add(pauseBtn);
        controlPanel.add(backBtn);
        controlPanel.add(Box.createHorizontalStrut(20));
        controlPanel.add(timerLabel);

        backgroundPanel.add(controlPanel, BorderLayout.NORTH);

        // ---------- Game Grid ----------
        JPanel gridPanel = new JPanel(new GridLayout(GRID_SIZE, GRID_SIZE, 5, 5));
        gridPanel.setOpaque(false);
        backgroundPanel.add(gridPanel, BorderLayout.CENTER);

        tileImages = prepareRandomImages();

        for (int i = 0; i < tiles.length; i++) {
            tiles[i] = new JButton() {
                @Override
                protected void paintComponent(Graphics g) {
                    Graphics2D g2 = (Graphics2D) g.create();
                    g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

                    int w = getWidth();
                    int h = getHeight();
                    int arc = 20;

                    GradientPaint gradient = new GradientPaint(0, 0, new Color(173, 216, 230),
                                                               w, h, Color.WHITE);
                    g2.setPaint(gradient);
                    g2.fillRoundRect(0, 0, w, h, arc, arc);

                    g2.setColor(Color.WHITE);
                    g2.setStroke(new BasicStroke(3f));
                    g2.drawRoundRect(1, 1, w - 3, h - 3, arc, arc);

                    super.paintComponent(g);
                    g2.dispose();
                }
            };

            tiles[i].setFocusable(false);
            tiles[i].setContentAreaFilled(false);
            tiles[i].setOpaque(false);
            tiles[i].setBorder(BorderFactory.createEmptyBorder());
            tiles[i].setIcon(getScaledIcon(BACK_IMAGE));

            final int index = i;
            tiles[i].addActionListener(e -> handleTileClick(index));
            gridPanel.add(tiles[i]);
        }

        // Show frame first so layered pane sizes are correct for countdown overlay
        setVisible(true);

        // Start 3..2..1..Go countdown, then enable clicks and start the game timer
        startCountdown(() -> {
            allowClicks = true;
            timerLabel.setText(formatTime(remainingSeconds)); // reset label to 01:00
            startTimer();
        });
    }

    private String formatTime(int totalSeconds) {
        int minutes = totalSeconds / 60;
        int seconds = totalSeconds % 60;
        return String.format("Time : %02d:%02d", minutes, seconds);
    }

    private void pauseTimer() {
        if (gameTimer != null && gameTimer.isRunning()) {
            gameTimer.stop();
        }
    }

    // ---------- Countdown ----------
    private void startCountdown(Runnable onComplete) {
        JLayeredPane layeredPane = getLayeredPane();

        JLabel countdownLabel = new JLabel("", SwingConstants.CENTER);
        countdownLabel.setFont(new Font("Arial Black", Font.BOLD, 120));
        countdownLabel.setForeground(new Color(0x00008B)); // dark blue

        Dimension size = getContentPane().getSize();
        countdownLabel.setBounds(0, 0, size.width, size.height);
        countdownLabel.setHorizontalAlignment(SwingConstants.CENTER);
        countdownLabel.setVerticalAlignment(SwingConstants.CENTER);

        layeredPane.add(countdownLabel, JLayeredPane.POPUP_LAYER);
        layeredPane.revalidate();
        layeredPane.repaint();

        int[] count = {3};
        javax.swing.Timer countdownTimer = new javax.swing.Timer(1000, null);
        countdownTimer.addActionListener(e -> {
            if (count[0] > 0) {
                countdownLabel.setText(String.valueOf(count[0]));
                count[0]--;
            } else {
                countdownLabel.setText("Go!");
                ((javax.swing.Timer) e.getSource()).stop();

                javax.swing.Timer goTimer = new javax.swing.Timer(500, ev -> {
                    layeredPane.remove(countdownLabel);
                    layeredPane.revalidate();
                    layeredPane.repaint();
                    onComplete.run();
                });
                goTimer.setRepeats(false);
                goTimer.start();
            }
        });
        countdownTimer.start();
    }

    // ---------- Prepare 12 pairs + 3 special tiles ----------
    private String[] prepareRandomImages() {
        String[] availableImages = {
            "assets/tiles/img1.jpg",
            "assets/tiles/img2.png",
            "assets/tiles/img3.png",
            "assets/tiles/img4.jpeg",
            "assets/tiles/img5.jpg",
            "assets/tiles/img6.jpg",
            "assets/tiles/img7.png",
            "assets/tiles/img8.png",
            "assets/tiles/img9.jpeg",
            "assets/tiles/img10.png", // special tile
            "assets/tiles/img11.png",
            "assets/tiles/img12.jpg"
        };

        ArrayList<String> imagesList = new ArrayList<>();
        for (String img : availableImages) {
            if (!img.contains("img10")) {
                imagesList.add(img);
                imagesList.add(img); // duplicate pair
            }
        }

        // Add special tile (img10) three times to fill 25 total
        imagesList.add("assets/tiles/img10.png");
        imagesList.add("assets/tiles/img10.png");
        imagesList.add("assets/tiles/img10.png");

        Collections.shuffle(imagesList);
        return imagesList.toArray(new String[0]);
    }

    // ---------- Tile click handler ----------
    private void handleTileClick(int index) {
        if (!allowClicks) return;
        if (!tiles[index].isVisible()) return;
        if (firstSelected != null && secondSelected != null) return;

        tiles[index].setIcon(getScaledIcon(tileImages[index]));

        // Special bonus tile
        if (tileImages[index].contains("img10")) {
            tiles[index].setEnabled(false);

            remainingSeconds += 15;
            timerLabel.setText(formatTime(remainingSeconds));

            javax.swing.Timer bonusTimer = new javax.swing.Timer(600, e -> {
                tiles[index].setVisible(false);
                tiles[index].setEnabled(true);
            });
            bonusTimer.setRepeats(false);
            bonusTimer.start();
            return;
        }

        if (firstSelected == null) {
            firstSelected = tiles[index];
        } else if (firstSelected != tiles[index]) {
            secondSelected = tiles[index];

            String img1 = getTileImage(firstSelected);
            String img2 = getTileImage(secondSelected);

            if (img1.equals(img2)) {
                javax.swing.Timer removeTimer = new javax.swing.Timer(500, e -> {
                    firstSelected.setVisible(false);
                    secondSelected.setVisible(false);
                    firstSelected = null;
                    secondSelected = null;
                    matchedPairs++;

                    int specialCount = countOccurrencesOf("img10");
                    int totalPairsNeeded = (tiles.length - specialCount) / 2;

                    if (matchedPairs >= totalPairsNeeded) {
                        if (gameTimer != null) gameTimer.stop();

                        // 🎉 Show fireworks overlay
                        SwingUtilities.invokeLater(() -> {
                            AnimationManager.FireworksPanel fireworks = new AnimationManager.FireworksPanel();
                            JLayeredPane layeredPane = getLayeredPane();
                            fireworks.setBounds(0, 0, getWidth(), getHeight());
                            layeredPane.add(fireworks, JLayeredPane.DRAG_LAYER);

                            showWinDialog();

                            fireworks.stop();
                            layeredPane.remove(fireworks);
                            layeredPane.revalidate();
                            layeredPane.repaint();
                        });
                    }
                });
                removeTimer.setRepeats(false);
                removeTimer.start();
            } else {
                flipBackTimer = new javax.swing.Timer(800, e -> {
                    if (firstSelected != null) firstSelected.setIcon(getScaledIcon(BACK_IMAGE));
                    if (secondSelected != null) secondSelected.setIcon(getScaledIcon(BACK_IMAGE));
                    firstSelected = null;
                    secondSelected = null;
                });
                flipBackTimer.setRepeats(false);
                flipBackTimer.start();
            }
        }
    }

    private int countOccurrencesOf(String needle) {
        int count = 0;
        for (String s : tileImages) {
            if (s.contains(needle)) count++;
        }
        return count;
    }

    private String getTileImage(JButton button) {
        for (int i = 0; i < tiles.length; i++) {
            if (tiles[i] == button) return tileImages[i];
        }
        return "";
    }

    // ---------- High-quality scaling ----------
    private ImageIcon getScaledIcon(String imagePath) {
        ImageIcon icon = new ImageIcon(imagePath);
    
        // Make img10 same size as others (130) for clarity
        int size = 130;
        Image img = icon.getImage();
    
        BufferedImage resized = new BufferedImage(size, size, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g2 = resized.createGraphics();
        g2.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BICUBIC);
        g2.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2.drawImage(img, 0, 0, size, size, null);
        g2.dispose();
    
        return new ImageIcon(resized);
    }
    

    private JButton createCustomButton(String text) {
        JButton button = new JButton(text) {
            @Override
            protected void paintComponent(Graphics g) {
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

                int arc = 40;
                int w = getWidth();
                int h = getHeight();

                GradientPaint gradient = new GradientPaint(0, 0, new Color(0x001F4D),
                                                           w, h, new Color(0x001A3D));
                g2.setPaint(gradient);
                g2.fillRoundRect(0, 0, w, h, arc, arc);

                g2.setColor(new Color(0x00BFFF));
                g2.setStroke(new BasicStroke(3f));
                g2.drawRoundRect(2, 2, w - 5, h - 5, arc, arc);

                super.paintComponent(g);
                g2.dispose();
            }
        };

        button.setFont(new Font("Arial Black", Font.BOLD, 20));
        button.setForeground(Color.WHITE);
        button.setFocusPainted(false);
        button.setContentAreaFilled(false);
        button.setBorderPainted(false);
        button.setOpaque(false);

        return button;
    }

    // ---------- Timer ----------
    private void startTimer() {
        if (gameTimer != null && gameTimer.isRunning()) {
            gameTimer.stop();
        }

        timerLabel.setText(formatTime(remainingSeconds));

        gameTimer = new javax.swing.Timer(1000, e -> {
            remainingSeconds--;
            timerLabel.setText(formatTime(remainingSeconds));
            System.out.println("Timer tick: " + remainingSeconds); // debug

            if (remainingSeconds <= 0) {
                gameTimer.stop();
                showLoseDialog();
            }
        });
        gameTimer.setRepeats(true);
        gameTimer.start();
    }
    
    private void showWinDialog() {
        JDialog winDialog = new JDialog(this, "Congratulations!", true);
        winDialog.setSize(400, 200);
        winDialog.setLocationRelativeTo(this);
        winDialog.setResizable(false);
        
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBorder(new EmptyBorder(20, 20, 20, 20));
        panel.setBackground(new Color(240, 248, 255));
        
        // Title
        JLabel titleLabel = new JLabel("You Win!", SwingConstants.CENTER);
        titleLabel.setFont(new Font("Arial Black", Font.BOLD, 24));
        titleLabel.setForeground(new Color(0, 100, 0));
        panel.add(titleLabel, BorderLayout.NORTH);
        
        // Message
        JLabel messageLabel = new JLabel("Congratulations!", SwingConstants.CENTER);
        messageLabel.setFont(new Font("Arial", Font.PLAIN, 14));
        panel.add(messageLabel, BorderLayout.CENTER);
        
        // Buttons
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 10));
        buttonPanel.setOpaque(false);
        
        JButton replayButton = createDialogButton("Replay", new Color(34, 139, 34), e -> {
            winDialog.dispose();
            dispose();
            new AdvancedLevel(controller);
        });
        
        JButton backButton = createDialogButton("Main Menu", new Color(220, 20, 60), e -> {
            winDialog.dispose();
            dispose();
            new LevelSelectionScreen(controller);
        });
        
        buttonPanel.add(replayButton);
        buttonPanel.add(backButton);
        panel.add(buttonPanel, BorderLayout.SOUTH);
        
        winDialog.setContentPane(panel);
        winDialog.setVisible(true);
    }
    
    private void showLoseDialog() {
        JDialog loseDialog = new JDialog(this, "Game Over", true);
        loseDialog.setSize(400, 200);
        loseDialog.setLocationRelativeTo(this);
        loseDialog.setResizable(false);
        
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBorder(new EmptyBorder(20, 20, 20, 20));
        panel.setBackground(new Color(255, 240, 240));
        
        // Title
        JLabel titleLabel = new JLabel("Time's Up!", SwingConstants.CENTER);
        titleLabel.setFont(new Font("Arial Black", Font.BOLD, 24));
        titleLabel.setForeground(new Color(139, 0, 0));
        panel.add(titleLabel, BorderLayout.NORTH);
        
        // Message
        JLabel messageLabel = new JLabel("Time ran out!", SwingConstants.CENTER);
        messageLabel.setFont(new Font("Arial", Font.PLAIN, 14));
        panel.add(messageLabel, BorderLayout.CENTER);
        
        // Buttons
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 10));
        buttonPanel.setOpaque(false);
        
        JButton replayButton = createDialogButton("Replay", new Color(34, 139, 34), e -> {
            loseDialog.dispose();
            dispose();
            new AdvancedLevel(controller);
        });
        
        JButton backButton = createDialogButton("Main Menu", new Color(220, 20, 60), e -> {
            loseDialog.dispose();
            dispose();
            new LevelSelectionScreen(controller);
        });
        
        buttonPanel.add(replayButton);
        buttonPanel.add(backButton);
        panel.add(buttonPanel, BorderLayout.SOUTH);
        
        loseDialog.setContentPane(panel);
        loseDialog.setVisible(true);
    }
    
    private JButton createDialogButton(String text, Color color, java.awt.event.ActionListener action) {
        JButton button = new JButton(text);
        button.setFont(new Font("Arial", Font.BOLD, 14));
        button.setForeground(Color.BLACK);
        button.setBackground(color);
        button.setFocusPainted(false);
        button.setPreferredSize(new Dimension(150, 35));
        button.addActionListener(action);
        return button;
    }
}
