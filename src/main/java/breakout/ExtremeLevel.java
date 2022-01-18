package breakout;

import javafx.scene.Group;
import javafx.scene.input.KeyCode;

public class ExtremeLevel extends Level {
    private Paddle leftPaddle;
    private Paddle rightPaddle;
    private Paddle topPaddle;
    private Paddle bottomPaddle;

    private Ball myBall;

    public ExtremeLevel(int levelNumber, int lives, Group root, int sceneWidth, int sceneHeight) {
        super(levelNumber, lives, root, sceneWidth, sceneHeight);
    }

    @Override
    public void setupChildNodes(Group root, int sceneWidth, int sceneHeight) {
        leftPaddle = new Paddle(sceneWidth / 25.0, sceneHeight / 5.0, false);
        leftPaddle.setX(sceneWidth * 0.05 - leftPaddle.getWidth());
        leftPaddle.setY(sceneHeight * 0.5 - leftPaddle.getHeight() / 2);

        rightPaddle = new Paddle(sceneWidth / 25.0, sceneHeight / 5.0, false);
        rightPaddle.setX(sceneWidth * 0.95);
        rightPaddle.setY(sceneHeight * 0.5 - rightPaddle.getHeight() / 2);

        topPaddle = new Paddle(sceneWidth / 5.0, sceneHeight / 25.0, true);
        topPaddle.setX(sceneWidth * 0.5 - topPaddle.getWidth() / 2);
        topPaddle.setY(sceneHeight * 0.05 - topPaddle.getHeight());

        bottomPaddle = new Paddle(sceneWidth / 5.0, sceneHeight / 25.0, true);
        bottomPaddle.setX(sceneWidth * 0.5 - bottomPaddle.getWidth() / 2);
        bottomPaddle.setY(sceneHeight * 0.95);

        myBall = new Ball(150, 100);
        setupBalls();

        root.getChildren().add(leftPaddle);
        root.getChildren().add(rightPaddle);
        root.getChildren().add(topPaddle);
        root.getChildren().add(bottomPaddle);
        root.getChildren().add(myBall);

        setupBricks(root, sceneWidth / 4, sceneHeight / 4,
                sceneWidth - sceneWidth / 4, sceneHeight - sceneHeight / 4);
    }

    @Override
    public void handleKeyInput(KeyCode code) {
        leftPaddle.move(code);
        rightPaddle.move(code);
        topPaddle.move(code);
        bottomPaddle.move(code);
    }

    @Override
    public void step(double elapsedTime) {
        checkPaddleCollisions();
        checkBrickCollisions(myBall);
        checkWallCollisions(myBall);
        checkPaddleWarping();
        myBall.move(elapsedTime);
    }

    private void checkPaddleCollisions() {
        if(Breakout.isIntersecting(leftPaddle, myBall)) {
            myBall.bounce(leftPaddle);
        } else if (Breakout.isIntersecting(rightPaddle, myBall)) {
            myBall.bounce(rightPaddle);
        } else if (Breakout.isIntersecting(topPaddle, myBall)) {
            myBall.bounce(topPaddle);
        } else if (Breakout.isIntersecting(bottomPaddle, myBall)) {
            myBall.bounce(bottomPaddle);
        }
    }

    public void checkWallCollisions(Ball ball) {
        if(ball.getCenterX() <= 0
                || ball.getCenterX() >= sceneWidth
                || ball.getCenterY() <= 0
                || ball.getCenterY() >= sceneHeight) {
            loseLife();
        }
    }

    private void checkPaddleWarping() {
        if(topPaddle.getX() >= sceneWidth) {
            topPaddle.setX(0 - topPaddle.getWidth() / 2);
        } else if (topPaddle.getX() + topPaddle.getWidth() <= 0) {
            topPaddle.setX(sceneWidth - topPaddle.getWidth() / 2);
        }
        bottomPaddle.setX(topPaddle.getX());

        if(leftPaddle.getY() >= sceneHeight) {
            leftPaddle.setY(0 - leftPaddle.getHeight() / 2);
        } else if (leftPaddle.getY() + leftPaddle.getHeight() <= 0) {
            leftPaddle.setY(sceneHeight - leftPaddle.getHeight() / 2);
        }
        rightPaddle.setY(leftPaddle.getY());
    }

    @Override
    public void setupBalls() {
        myBall.setCenterX(sceneWidth * 0.5);
        myBall.setCenterY(sceneHeight * 0.2);
        myBall.setxVelocity(150);
        myBall.setyVelocity(100);
    }

    @Override
    public void handlePowerup(Powerup powerup) {
        if(powerup == Powerup.INVISIBLE_PADDLE) {
            topPaddle.setOpacity(0);
            bottomPaddle.setOpacity(0);
            leftPaddle.setOpacity(0);
            rightPaddle.setOpacity(0);
        } else if (powerup == Powerup.LONG_PADDLE) {
            topPaddle.setWidth(topPaddle.getWidth() * 1.5);
            bottomPaddle.setWidth(bottomPaddle.getWidth() * 1.5);
            leftPaddle.setHeight(leftPaddle.getHeight() * 1.5);
            rightPaddle.setHeight(rightPaddle.getHeight() * 1.5);
        }
    }
}
