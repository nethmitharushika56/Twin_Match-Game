package view;

import controller.GameController;
import util.SoundManager;
import util.AnimationManager; // 🔥 Import fireworks panel

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.Collections;

public class IntermediateLevel extends JFrame {
    private final int GRID_SIZE = 4; // 4x4 grid
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

    // NEW: block clicks until countdown finishes
    private boolean allowClicks = false;

    public IntermediateLevel(GameController controller) {
        this.controller = controller;

        setTitle("Intermediate Level - Twin Match Quest");
        setSize(800, 850);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // ---------- Background Panel ----------
        JPanel backgroundPanel = new JPanel() {
            private Image bg = new ImageIcon("assets/intermediate_bg.png").getImage();

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

        // Timer label
        timerLabel = new JLabel("Time: 01:00");
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

        // Show window before countdown overlay
        setVisible(true);

        // Start countdown before allowing play
        startCountdown(() -> {
            allowClicks = true;
            startTimer();
        });
    }

    // ---------- Countdown ----------
    private void startCountdown(Runnable onComplete) {
        JLayeredPane layeredPane = getLayeredPane();

        JLabel countdownLabel = new JLabel("", SwingConstants.CENTER);
        countdownLabel.setFont(new Font("Arial Black", Font.BOLD, 120));
        countdownLabel.setForeground(new Color(0x00008B));

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
                countdownTimer.stop();

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

    // ---------- Prepare 8 pairs (16 tiles) ----------
    private String[] prepareRandomImages() {
        String[] availableImages = {
            "assets/tiles/img1.jpg",
            "assets/tiles/img2.png",
            "assets/tiles/img3.png",
            "assets/tiles/img4.jpeg",
            "assets/tiles/img5.jpg",
            "assets/tiles/img6.jpg",
            "assets/tiles/img7.png",
            "assets/tiles/img8.png"
        };

        ArrayList<String> imagesList = new ArrayList<>();
        for (String img : availableImages) {
            imagesList.add(img);
            imagesList.add(img);
        }
        Collections.shuffle(imagesList);
        return imagesList.toArray(new String[0]);
    }

    // ---------- Tile click handler ----------
    private void handleTileClick(int index) {
        if (!allowClicks) return; // block until countdown finishes
        if (firstSelected != null && secondSelected != null) return;

        tiles[index].setIcon(getScaledIcon(tileImages[index]));

        if (firstSelected == null) {
            firstSelected = tiles[index];
        } else if (firstSelected != tiles[index]) {
            secondSelected = tiles[index];

            if (getTileImage(firstSelected).equals(getTileImage(secondSelected))) {
                javax.swing.Timer removeTimer = new javax.swing.Timer(500, e -> {
                    firstSelected.setVisible(false);
                    secondSelected.setVisible(false);
                    firstSelected = null;
                    secondSelected = null;
                    matchedPairs++;
                    if (matchedPairs == 8) {
                        if (gameTimer != null) gameTimer.stop();
                        showWinAnimation(); // 🎆 show fireworks
                    }
                });
                removeTimer.setRepeats(false);
                removeTimer.start();
            } else {
                flipBackTimer = new javax.swing.Timer(800, e -> {
                    firstSelected.setIcon(getScaledIcon(BACK_IMAGE));
                    secondSelected.setIcon(getScaledIcon(BACK_IMAGE));
                    firstSelected = null;
                    secondSelected = null;
                });
                flipBackTimer.setRepeats(false);
                flipBackTimer.start();
            }
        }
    }

    // ---------- Fireworks on Win ----------
    private void showWinAnimation() {
        SwingUtilities.invokeLater(() -> {
            AnimationManager.FireworksPanel fireworks = new AnimationManager.FireworksPanel();
            JLayeredPane layeredPane = getLayeredPane();
            fireworks.setBounds(0, 0, getWidth(), getHeight());
            layeredPane.add(fireworks, JLayeredPane.DRAG_LAYER);

            JOptionPane.showMessageDialog(this, "🎉 You Win! 🎉");

            fireworks.stop();
            layeredPane.remove(fireworks);
            layeredPane.revalidate();
            layeredPane.repaint();
        });
    }

    private String getTileImage(JButton button) {
        for (int i = 0; i < tiles.length; i++) {
            if (tiles[i] == button) return tileImages[i];
        }
        return "";
    }

    private ImageIcon getScaledIcon(String imagePath) {
        ImageIcon icon = new ImageIcon(imagePath);
        Image scaled = icon.getImage().getScaledInstance(150, 150, Image.SCALE_SMOOTH);
        return new ImageIcon(scaled);
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
        gameTimer = new javax.swing.Timer(1000, e -> {
            remainingSeconds--;
            int minutes = remainingSeconds / 60;
            int seconds = remainingSeconds % 60;
            timerLabel.setText(String.format("Time: %02d:%02d", minutes, seconds));

            if (remainingSeconds <= 0) {
                gameTimer.stop();
                JOptionPane.showMessageDialog(this, "Time's up! You lose.");
                dispose();
                new LevelSelectionScreen(controller);
            }
        });
        gameTimer.start();
    }

    private void pauseTimer() {
        if (gameTimer != null) gameTimer.stop();
    }
}
