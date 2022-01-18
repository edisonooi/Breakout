package breakout;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.scene.Group;
import javafx.scene.input.KeyCode;
import javafx.scene.paint.Color;
import javafx.util.Duration;

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
        myPaddle = new Paddle(sceneWidth / 5.0, sceneHeight / 25.0, true);
        myPaddle.setX(sceneWidth * 0.5 - myPaddle.getWidth() / 2);
        myPaddle.setY(sceneHeight * 0.9);

        myBall = new Ball(150, 100);
        myExtraBall = new Ball(0, 0);
        setupBalls();

        root.getChildren().add(myPaddle);
        root.getChildren().add(myBall);
        root.getChildren().add(myExtraBall);

        setupBricks(root, 0, 0, sceneWidth, sceneHeight / 2);

        levelRoot = root;
    }

    @Override
    public void handleKeyInput(KeyCode code) {
        if(code == KeyCode.L) {
            this.numRemainingLives++;
        } else if (code == KeyCode.T && !fastPaddleCheatHasBeenUsed) {
            fastPaddleCheatHasBeenUsed = true;

            myPaddle.setSpeed(myPaddle.getSpeed() * 2);
            Timeline timeline =
                    new Timeline(new KeyFrame(Duration.millis(Breakout.FAST_PADDLE_DURATION),
                            e -> myPaddle.setSpeed(myPaddle.getSpeed() / 2)));
            timeline.setCycleCount(1);
            timeline.play();
        } else if (code == KeyCode.S && !slowBallCheatIsActive) {
            slowBallCheatIsActive = true;

            myBall.setxVelocity(myBall.getxVelocity() / 2);
            myBall.setyVelocity(myBall.getyVelocity() / 2);
            myExtraBall.setxVelocity(myExtraBall.getxVelocity() / 2);
            myExtraBall.setyVelocity(myExtraBall.getyVelocity() / 2);

            Timeline timeline =
                    new Timeline(new KeyFrame(Duration.millis(Breakout.SLOW_BALL_DURATION),
                            e -> {
                                myBall.setxVelocity(myBall.getxVelocity() * 2);
                                myBall.setyVelocity(myBall.getyVelocity() * 2);
                                myExtraBall.setxVelocity(myExtraBall.getxVelocity() * 2);
                                myExtraBall.setyVelocity(myExtraBall.getyVelocity() * 2);
                                slowBallCheatIsActive = false;
                            }));
            timeline.setCycleCount(1);
            timeline.play();
        }
        myPaddle.move(code);
    }

    @Override
    public void step(double elapsedTime) {
        checkPaddleCollisions();
        checkBrickCollisions(myBall);
        checkWallCollisions(myBall);

        checkPaddleWarping();

        if(extraBallIsActivated) {
            checkBrickCollisions(myExtraBall);
            checkWallCollisions(myExtraBall);
        }

        moveBalls(elapsedTime);
    }

    private void moveBalls(double elapsedTime) {
        myBall.move(elapsedTime);

        if(extraBallIsActivated) {
            myExtraBall.move(elapsedTime);
        }
    }

    @Override
    public void handlePowerup(Powerup powerup) {
        if(powerup == Powerup.NONE) {
            return;
        }

        if(powerup == Powerup.EXTRA_BALL) {
            activateExtraBall();
        } else if (powerup == Powerup.INVISIBLE_PADDLE) {
            myPaddle.setOpacity(0.5);
            Timeline timeline =
                    new Timeline(new KeyFrame(Duration.millis(Breakout.INVIS_PADDLE_DURATION), e -> myPaddle.setOpacity(1)));
            timeline.setCycleCount(1);
            timeline.play();
        } else if (powerup == Powerup.LONG_PADDLE) {
            myPaddle.setWidth(myPaddle.getWidth() * 1.5);
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

    public void checkWallCollisions(Ball ball) {
        if(ball.getCenterY() >= sceneHeight && ball != myExtraBall) {
            loseLife();
        }

        if(ball.getCenterX() - ball.getRadius() <= 0 ||
                ball.getCenterX() + ball.getRadius() >= sceneWidth) {
            ball.setxVelocity(ball.getxVelocity() * -1);
        }

        if(ball.getCenterY() - ball.getRadius() <= 0) {
            ball.setyVelocity(ball.getyVelocity() * -1);
        }
    }

    private void checkPaddleWarping() {
        if(myPaddle.getX() >= sceneWidth) {
            myPaddle.setX(0 - myPaddle.getWidth() / 2);
        } else if(myPaddle.getX() + myPaddle.getWidth() <= 0) {
            myPaddle.setX(sceneWidth - myPaddle.getWidth() / 2);
        }
    }

    @Override
    public void setupBalls() {
        myBall.setCenterX(sceneWidth * 0.5);
        myBall.setCenterY(sceneHeight * 0.625);
        myBall.setxVelocity(150);
        myBall.setyVelocity(100);

        //myExtraBall should be invisible and stationary until it is activated
        myExtraBall.setCenterX(sceneWidth * 0.5);
        myExtraBall.setCenterY(sceneHeight * 0.625);
        myExtraBall.setFill(Color.GOLD);
        myExtraBall.setOpacity(0);
        myExtraBall.setxVelocity(0);
        myExtraBall.setyVelocity(0);
        extraBallIsActivated = false;
    }

    private void activateExtraBall() {
        myExtraBall.setOpacity(1);
        myExtraBall.setxVelocity(-160);
        myExtraBall.setyVelocity(100);
        extraBallIsActivated = true;
    }
}
