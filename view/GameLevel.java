package view;

import javax.swing.Icon;

public class GameLevel { // Removed "implements model.GameLevel" to fix the error

    public static final model.GameLevel BEGINNER = null;
    public static final model.GameLevel INTERMEDIATE = null;
    public static final model.GameLevel ADVANCED = null;

    public Icon getDisplayName() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'getDisplayName'");
    }

	public String getDescription() {
		// TODO: Provide a meaningful description for this GameLevel
		return "Game level description not yet implemented.";
	}

    public model.GameLevel toModelGameLevel() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'toModelGameLevel'");
    }

    public static GameLevel[] values() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'values'");
    }

}
