package view;

import controller.GameController;
import controller.LevelSelectionScreen;
import util.SoundManager;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.Collections;

public class BeginnerLevel extends JFrame {
    private final int GRID_SIZE = 4; // 4x4 grid
    private final JButton[] tiles = new JButton[GRID_SIZE * GRID_SIZE];
    private final String BACK_IMAGE = "assets/tiles_back.png"; // face-down image
    private String[] tileImages; // holds the randomized images
    private JButton firstSelected = null;
    private JButton secondSelected = null;
    private javax.swing.Timer flipBackTimer;
    private int matchedPairs = 0;

    private final GameController controller; // Needed to pass to LevelSelectionScreen

    public BeginnerLevel(GameController controller) {
        this.controller = controller;

        setTitle("Beginner Level - Twin Match Quest");
        setSize(700, 700);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        // Top control buttons panel
        JPanel controlPanel = new JPanel();
        controlPanel.setOpaque(false);

        JButton pauseBtn = createCustomButton("Pause");
        JButton backBtn = createCustomButton("Back to Level Selection");

        pauseBtn.setPreferredSize(new Dimension(320, 80));
        backBtn.setPreferredSize(new Dimension(320, 80));

        pauseBtn.addActionListener(e -> {
            SoundManager.playButtonClickSound();
            JOptionPane.showMessageDialog(this, "Game Paused");
        });

        backBtn.addActionListener(e -> {
            SoundManager.playButtonClickSound();
            dispose();
            // Redirect back to LevelSelectionScreen
            new LevelSelectionScreen(controller);
        });

        controlPanel.add(pauseBtn);
        controlPanel.add(backBtn);
        add(controlPanel, BorderLayout.NORTH);

        // Game grid panel
        JPanel gridPanel = new JPanel(new GridLayout(GRID_SIZE, GRID_SIZE, 5, 5));
        add(gridPanel, BorderLayout.CENTER);

        // Prepare randomized tiles
        tileImages = prepareRandomImages();

        for (int i = 0; i < tiles.length; i++) {
            tiles[i] = new JButton();
            tiles[i].setFocusable(false);
            tiles[i].setBorder(BorderFactory.createLineBorder(Color.BLACK, 2));
            tiles[i].setIcon(getScaledIcon(BACK_IMAGE));

            final int index = i;
            tiles[i].addActionListener(e -> handleTileClick(index));
            gridPanel.add(tiles[i]);
        }

        setVisible(true);
    }

    private String[] prepareRandomImages() {
        String[] availableImages = {
            "assets/tiles_img1.jpg",
            "assets/tiles_img2.jpg",
            "assets/tiles_img3.jpg",
            "assets/tiles_img4.jpg",
            "assets/tiles_img5.jpg",
            "assets/tiles_img6.png",
            "assets/tiles_img7.png",
            "assets/tiles_img8.jpg"
        };

        ArrayList<String> imagesList = new ArrayList<>();
        for (int i = 0; i < 8; i++) { // 8 pairs for 16 tiles
            imagesList.add(availableImages[i]);
            imagesList.add(availableImages[i]);
        }

        Collections.shuffle(imagesList);
        return imagesList.toArray(new String[0]);
    }

    private void handleTileClick(int index) {
        if (firstSelected != null && secondSelected != null) return; // already two selected

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
                        JOptionPane.showMessageDialog(BeginnerLevel.this, "You win!");
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

    private String getTileImage(JButton button) {
        for (int i = 0; i < tiles.length; i++) {
            if (tiles[i] == button) {
                return tileImages[i];
            }
        }
        return "";
    }

    private ImageIcon getScaledIcon(String imagePath) {
        ImageIcon icon = new ImageIcon(imagePath);
        Image scaled = icon.getImage().getScaledInstance(150, 150, Image.SCALE_SMOOTH);
        return new ImageIcon(scaled);
    }

    /**
     * Creates a custom glowing button style: dark blue fill, light blue border,
     * white text, rounded corners.
     */
    private JButton createCustomButton(String text) {
        JButton button = new JButton(text) {
            @Override
            protected void paintComponent(Graphics g) {
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

                int arc = 50;
                int w = getWidth();
                int h = getHeight();

                // Dark blue gradient fill
                GradientPaint gradient = new GradientPaint(0, 0, new Color(0x001F4D),
                                                           w, h, new Color(0x001A3D));
                g2.setPaint(gradient);
                g2.fillRoundRect(0, 0, w, h, arc, arc);

                // Light blue border
                g2.setColor(new Color(0x00BFFF));
                g2.setStroke(new BasicStroke(4f));
                g2.drawRoundRect(2, 2, w - 5, h - 5, arc, arc);

                super.paintComponent(g);
                g2.dispose();
            }
        };

        button.setFont(new Font("Arial Black", Font.BOLD, 24));
        button.setForeground(Color.WHITE);
        button.setFocusPainted(false);
        button.setContentAreaFilled(false);
        button.setBorderPainted(false);
        button.setOpaque(false);

        return button;
    }
}
