package breakout;

import javafx.scene.Group;
import javafx.scene.input.KeyCode;
import javafx.scene.paint.Color;

import java.io.FileNotFoundException;

public class NormalLevel extends Level {

    private Paddle myPaddle;
    private Ball myBall;
    private Ball myExtraBall;

    private boolean extraBallIsActivated;

    private Group levelRoot;

    public NormalLevel(int levelNumber, int lives, Group root, int sceneWidth, int sceneHeight) {
        super(levelNumber, lives, root, sceneWidth, sceneHeight);

    }

    @Override
    public void setupChildNodes(Group root, int sceneWidth, int sceneHeight) {
        myPaddle = new Paddle(sceneWidth / 5.0, sceneHeight / 20.0, true);
        myPaddle.setX(sceneWidth * 0.5 - myPaddle.getWidth() / 2);
        myPaddle.setY(sceneHeight * 0.9);

        myBall = new Ball(150, 100);
        myBall.setCenterX(sceneWidth * 0.5);
        myBall.setCenterY(sceneHeight * 0.625);

        //myExtraBall should be invisible and stationary until it is activated
        myExtraBall = new Ball(0, 0);
        myExtraBall.setCenterX(sceneWidth * 0.5);
        myExtraBall.setCenterY(sceneHeight * 0.625);
        myExtraBall.setFill(Color.GOLD);
        myExtraBall.setOpacity(0);
        extraBallIsActivated = false;

        root.getChildren().add(myPaddle);
        root.getChildren().add(myBall);
        root.getChildren().add(myExtraBall);

        setupBricks(root, 0, 0, sceneWidth, sceneHeight / 2);

        levelRoot = root;
    }

    @Override
    public void handleKeyInput(KeyCode code) {
        myPaddle.move(code);
    }

    @Override
    public void step(double elapsedTime) {
        moveBalls(elapsedTime);
        checkPaddleCollisions();

        checkBrickCollisions(myBall);
        checkWallCollisions(myBall);
        if(extraBallIsActivated) {
            checkBrickCollisions(myExtraBall);
            checkWallCollisions(myExtraBall);
        }
    }

    private void moveBalls(double elapsedTime) {
        myBall.move(elapsedTime);

        if(extraBallIsActivated) {
            myExtraBall.move(elapsedTime);
        }
    }

    private void checkPaddleCollisions() {
        if(Breakout.isIntersecting(myPaddle, myBall)) {
            myBall.bounce(myPaddle);
        }

        if(Breakout.isIntersecting(myPaddle, myExtraBall)) {
            myExtraBall.bounce(myPaddle);
        }
    }
}
