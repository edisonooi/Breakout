package breakout;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.scene.Group;
import javafx.scene.input.KeyCode;
import javafx.util.Duration;

public class ExtremeLevel extends Level {
    private Paddle leftPaddle;
    private Paddle rightPaddle;
    private Paddle topPaddle;
    private Paddle bottomPaddle;

    private Ball myBall;

    public ExtremeLevel(int levelNumber, int lives, Group root, int sceneWidth, int sceneHeight, Scoreboard scoreboard) {
        super(levelNumber, lives, root, sceneWidth, sceneHeight, scoreboard);
    }

    @Override
    public void setupChildNodes(Group root, int sceneWidth, int sceneHeight) {
        leftPaddle = new Paddle(sceneWidth / 25.0, sceneHeight / 5.0, false);
        leftPaddle.setX(sceneWidth * 0.05 - leftPaddle.getWidth());
        leftPaddle.setY(sceneHeight * 0.5 - leftPaddle.getHeight() / 2 + Breakout.SCOREBOARD_HEIGHT);

        rightPaddle = new Paddle(sceneWidth / 25.0, sceneHeight / 5.0, false);
        rightPaddle.setX(sceneWidth * 0.95);
        rightPaddle.setY(sceneHeight * 0.5 - rightPaddle.getHeight() / 2 + Breakout.SCOREBOARD_HEIGHT);

        topPaddle = new Paddle(sceneWidth / 5.0, sceneHeight / 25.0, true);
        topPaddle.setX(sceneWidth * 0.5 - topPaddle.getWidth() / 2);
        topPaddle.setY(sceneHeight * 0.05 - topPaddle.getHeight() + Breakout.SCOREBOARD_HEIGHT);

        bottomPaddle = new Paddle(sceneWidth / 5.0, sceneHeight / 25.0, true);
        bottomPaddle.setX(sceneWidth * 0.5 - bottomPaddle.getWidth() / 2);
        bottomPaddle.setY(sceneHeight * 0.95 + Breakout.SCOREBOARD_HEIGHT);

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

        if(code == KeyCode.L) {
            this.numRemainingLives++;
        } else if (code == KeyCode.T && !fastPaddleCheatHasBeenUsed) {
            activateFastPaddleCheat();
        } else if (code == KeyCode.S && !slowBallCheatIsActive) {
            slowBallCheatIsActive = true;

            myBall.setxVelocity(myBall.getxVelocity() / 2);
            myBall.setyVelocity(myBall.getyVelocity() / 2);

            Timeline timeline =
                    new Timeline(new KeyFrame(Duration.millis(Breakout.SLOW_BALL_DURATION),
                            e -> {
                                myBall.setxVelocity(myBall.getxVelocity() * 2);
                                myBall.setyVelocity(myBall.getyVelocity() * 2);
                                slowBallCheatIsActive = false;
                            }));
            timeline.setCycleCount(1);
            timeline.play();
        }
    }

    private void activateFastPaddleCheat() {
        fastPaddleCheatHasBeenUsed = true;

        leftPaddle.setSpeed(leftPaddle.getSpeed() * 2);
        rightPaddle.setSpeed(rightPaddle.getSpeed() * 2);
        topPaddle.setSpeed(topPaddle.getSpeed() * 2);
        bottomPaddle.setSpeed(bottomPaddle.getSpeed() * 2);

        Timeline timeline =
                new Timeline(new KeyFrame(Duration.millis(Breakout.INVIS_PADDLE_DURATION),
                        e -> resetPaddleSpeeds()));
        timeline.setCycleCount(1);
        timeline.play();
    }

    private void resetPaddleSpeeds() {
        leftPaddle.setSpeed(leftPaddle.getSpeed() / 2);
        rightPaddle.setSpeed(rightPaddle.getSpeed() / 2);
        topPaddle.setSpeed(topPaddle.getSpeed() / 2);
        bottomPaddle.setSpeed(bottomPaddle.getSpeed() / 2);
    }

    @Override
    public void step(double elapsedTime) {
        checkPaddleCollisions();
        checkBrickCollisions(myBall);
        checkWallCollisions(myBall);
        checkPaddleWarping();
        myBall.move(elapsedTime);
        scoreboard.refreshText(this);
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
                || ball.getCenterY() <= Breakout.SCOREBOARD_HEIGHT
                || ball.getCenterY() >= sceneHeight + Breakout.SCOREBOARD_HEIGHT) {
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

        if(leftPaddle.getY() >= sceneHeight + Breakout.SCOREBOARD_HEIGHT) {
            leftPaddle.setY(Breakout.SCOREBOARD_HEIGHT - leftPaddle.getHeight() / 2);
        } else if (leftPaddle.getY() + leftPaddle.getHeight() <= Breakout.SCOREBOARD_HEIGHT) {
            leftPaddle.setY(sceneHeight + Breakout.SCOREBOARD_HEIGHT - leftPaddle.getHeight() / 2);
        }
        rightPaddle.setY(leftPaddle.getY());
    }

    @Override
    public void setupBalls() {
        myBall.setCenterX(sceneWidth * 0.5);
        myBall.setCenterY(sceneHeight * 0.1 + Breakout.SCOREBOARD_HEIGHT);
        myBall.setxVelocity(150);
        myBall.setyVelocity(100);
    }

    @Override
    public void handlePowerup(Powerup powerup) {
        if(powerup == Powerup.NONE) {
            return;
        }

        if(powerup == Powerup.INVISIBLE_PADDLE) {
            topPaddle.setOpacity(0);
            bottomPaddle.setOpacity(0);
            leftPaddle.setOpacity(0);
            rightPaddle.setOpacity(0);

            Timeline timeline =
                    new Timeline(new KeyFrame(Duration.millis(Breakout.INVIS_PADDLE_DURATION), e -> makePaddlesVisible()));
            timeline.setCycleCount(1);
            timeline.play();
        } else if (powerup == Powerup.LONG_PADDLE) {
            topPaddle.setWidth(topPaddle.getWidth() * 1.5);
            bottomPaddle.setWidth(bottomPaddle.getWidth() * 1.5);
            leftPaddle.setHeight(leftPaddle.getHeight() * 1.5);
            rightPaddle.setHeight(rightPaddle.getHeight() * 1.5);
        }
    }

    private void makePaddlesVisible() {
        topPaddle.setOpacity(1);
        bottomPaddle.setOpacity(1);
        leftPaddle.setOpacity(1);
        rightPaddle.setOpacity(1);
    }
}
