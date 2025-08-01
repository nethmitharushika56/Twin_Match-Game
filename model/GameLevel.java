package model;

public interface GameLevel {

    GameLevel BEGINNER = null;
    GameLevel INTERMEDIATE = null;
    GameLevel ADVANCED = null;
    String getDescription();
    static GameLevel[] values() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'values'");
    }
    String getDisplayName();

}
