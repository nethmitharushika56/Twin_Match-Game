package model;

import javax.swing.*;

/**
 * Represents a single tile in the memory game
 */
public class Tile {
    private int imageId;
    private boolean flipped;
    private boolean matched;
    private String imagePath;
    private ImageIcon imageIcon;
    private String displayName;

    public Tile(int imageId, String imagePath, String displayName) {
        this.imageId = imageId;
        this.flipped = false;
        this.matched = false;
        this.imagePath = imagePath;
        this.displayName = displayName;
        if (imagePath != null) {
            java.net.URL url = getClass().getResource(imagePath);
            if (url != null) {
                this.imageIcon = new ImageIcon(url);
            } else {
                System.err.println("Image not found: " + imagePath);
                this.imageIcon = null;
            }
        }
    }

    public int getImageId() {
        return imageId;
    }

    public boolean isFlipped() {
        return flipped;
    }

    public void flip() {
        this.flipped = !this.flipped;
    }

    public boolean isMatched() {
        return matched;
    }

    public void setMatched(boolean matched) {
        this.matched = matched;
    }

    public ImageIcon getImageIcon() {
        return imageIcon;
    }

    public String getDisplayName() {
        return displayName;
    }

    public String getImagePath() {
        return imagePath;
    }

    public String getDisplayText() {
        if (matched) {
            return "✓";
        } else if (flipped) {
            return displayName;
        } else {
            return "?";
        }
    }
} 