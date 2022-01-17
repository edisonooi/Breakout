package breakout;

import javafx.scene.Group;
import javafx.scene.input.KeyCode;

import java.io.FileNotFoundException;

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
    public void setupChildNodes(Group root, int sceneWidth, int sceneHeight) throws FileNotFoundException {
        leftPaddle = new Paddle(sceneWidth / 5.0, sceneHeight / 20.0, true);
        leftPaddle.setX(sceneWidth * 0.1 - leftPaddle.getWidth());
        leftPaddle.setY(sceneHeight * 0.5 - leftPaddle.getHeight() / 2);

        rightPaddle = new Paddle(sceneWidth / 5.0, sceneHeight / 20.0, true);
        rightPaddle.setX(sceneWidth * 0.9);
        rightPaddle.setY(sceneHeight * 0.5 - bottomPaddle.getHeight() / 2);

        topPaddle = new Paddle(sceneWidth / 20.0, sceneHeight / 5.0, false);
        topPaddle.setX(sceneWidth * 0.5 - leftPaddle.getWidth() / 2);
        topPaddle.setY(sceneHeight * 0.1 - leftPaddle.getHeight());

        bottomPaddle = new Paddle(sceneWidth / 20.0, sceneHeight / 5.0, false);
        bottomPaddle.setX(sceneWidth * 0.5 - rightPaddle.getWidth() / 2);
        bottomPaddle.setY(sceneHeight * 0.9);

        myBall = new Ball(150, 100);
        myBall.setCenterX(sceneWidth * 0.5);
        myBall.setCenterY(sceneHeight * 0.2);

        setupBricks(root, sceneWidth / 4, sceneHeight / 4,
                sceneWidth - sceneWidth / 4, sceneHeight - sceneHeight / 4);
    }

    @Override
    public void handleKeyInput(KeyCode code) {

    }
}
