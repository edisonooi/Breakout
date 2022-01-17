package breakout;

import javafx.scene.Group;

public class NormalLevel extends Level {

    private Paddle myPaddle;

    public NormalLevel(int levelNumber, int lives, String blockConfigFile) {
        super(levelNumber, lives, blockConfigFile);
    }


    @Override
    public void setupChildNodes(Group root, int sceneWidth, int sceneHeight) {

    }
}
