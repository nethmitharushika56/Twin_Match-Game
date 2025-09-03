package model;

/**
 * Represents the difficulty levels of the game.
 */
public enum GameLevel {
    BEGINNER("Beginner", "4x4 grid, no time limit"),
    INTERMEDIATE("Intermediate", "6x6 grid, 3 minutes"),
    ADVANCED("Advanced", "8x8 grid, 2 minutes");

    private final String displayName;
    private final String description;

    GameLevel(String displayName, String description) {
        this.displayName = displayName;
        this.description = description;
    }

    /**
     * Get the display name of the level (for buttons/labels)
     */
    public String getDisplayName() {
        return displayName;
    }

    /**
     * Get the description of the level (for UI tooltips or labels)
     */
    public String getDescription() {
        return description;
    }

    /**
     * Returns all levels in order (same as values())
     */
    public static GameLevel[] getAllLevels() {
        return values();
    }
}
