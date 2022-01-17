package breakout;

import javafx.scene.Group;
import javafx.scene.input.KeyCode;

public class ExtremeLevel extends Level {
    private Paddle leftPaddle;
    private Paddle rightPaddle;
    private Paddle topPaddle;
    private Paddle bottomPaddle;

    private Ball myBall;

    public ExtremeLevel(int levelNumber, int lives, String blockConfigFile) {
        super(levelNumber, lives, blockConfigFile);
    }

    @Override
    public void setupChildNodes(Group root, int sceneWidth, int sceneHeight) {
        leftPaddle = new Paddle(sceneWidth / 20.0, sceneHeight / 5.0, false);
        rightPaddle = new Paddle(sceneWidth / 20.0, sceneHeight / 5.0, false);
        topPaddle = new Paddle(sceneWidth / 5.0, sceneHeight / 20.0, true);
        bottomPaddle = new Paddle(sceneWidth / 5.0, sceneHeight / 20.0, true);
    }

    @Override
    public void handleKeyInput(KeyCode code) {

    }
}
