package breakout;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.scene.Group;
import javafx.scene.input.KeyCode;
import javafx.scene.paint.Color;
import javafx.util.Duration;

/**
 * This class represents a Breakout Level that consists of one paddle at the bottom
 * of the screen moving horizontally, bricks in a rectangular region at the top of the
 * screen, and either one or two balls bouncing around. A life is lost when the
 * main ball goes past the bottom of the screen.
 *
 * @author Edison Ooi
 */
public class NormalLevel extends Level {
    // Game elements specific to this level
    private Paddle myPaddle;
    private Ball myBall;
    private Ball myExtraBall;

    /**
     * Class constructor. Calls constructor of superclass Level.
     *
     * @param levelNumber cardinal number representing current level being created
     * @param lives number of lives the player starts with
     * @param root Group object that holds all Nodes rendered by Breakout game
     * @param sceneWidth width, in pixels, of playable region of Breakout game
     * @param sceneHeight height, in pixels, of playable region of Breakout game
     * @param scoreboard Scoreboard object used to display game statistics to player
     */
    public NormalLevel(int levelNumber, int lives, Group root, int sceneWidth, int sceneHeight, Scoreboard scoreboard) {
        super(levelNumber, lives, root, sceneWidth, sceneHeight, scoreboard);
    }

    /**
     * @param root Group object that holds all Nodes for Breakout game
     * @param sceneWidth width, in pixels, of playable region of Breakout game
     * @param sceneHeight height, in pixels, of playable region of Breakout game
     */
    @Override
    public void setupChildNodes(Group root, int sceneWidth, int sceneHeight) {
        myPaddle = new Paddle(sceneWidth / 5.0, sceneHeight / 25.0, true);
        myPaddle.setX(sceneWidth * 0.5 - myPaddle.getWidth() / 2);
        myPaddle.setY(sceneHeight * 0.9 + Breakout.SCOREBOARD_HEIGHT);

        myBall = new Ball(150, 100);
        myExtraBall = new Ball(0, 0);
        setupBalls();

        root.getChildren().add(myPaddle);
        root.getChildren().add(myBall);
        root.getChildren().add(myExtraBall);

        setupBricks(root, 0, Breakout.SCOREBOARD_HEIGHT, sceneWidth, sceneHeight / 2 + Breakout.SCOREBOARD_HEIGHT);

        levelRoot = root;
    }

    /**
     * Sets initial properties of all balls in level.
     */
    @Override
    public void setupBalls() {
        myBall.setCenterX(sceneWidth * 0.5);
        myBall.setCenterY(sceneHeight * 0.625 + Breakout.SCOREBOARD_HEIGHT);
        myBall.setxVelocity(150);
        myBall.setyVelocity(100);

        //myExtraBall should be invisible and stationary until it is activated
        myExtraBall.setCenterX(sceneWidth * 0.5);
        myExtraBall.setCenterY(sceneHeight * 0.625 + Breakout.SCOREBOARD_HEIGHT);
        myExtraBall.setFill(Color.GOLD);
        myExtraBall.setOpacity(0);
        myExtraBall.setxVelocity(0);
        myExtraBall.setyVelocity(0);
        extraBallIsActive = false;
    }

    /**
     * Responds to the following cheat keys or moves paddle according to key:
     * L - Gives player an extra life for this level.
     * T - Doubles paddle speed for certain amount of time.
     * S - Halves ball speed for certain amount of time.
     *
     * @param code KeyCode of key that was pressed
     */
    @Override
    public void handleKeyInput(KeyCode code) {
        if(code == KeyCode.L) {
            this.numRemainingLives++;
        } else if (code == KeyCode.T && !fastPaddleCheatHasBeenUsed) {
            fastPaddleCheatHasBeenUsed = true;
            fastPaddleCheatIsActive = true;

            myPaddle.setSpeed(myPaddle.getSpeed() * 2);
            Timeline timeline =
                    new Timeline(new KeyFrame(Duration.millis(Breakout.FAST_PADDLE_DURATION),
                            e -> {
                                myPaddle.setSpeed(myPaddle.getSpeed() / 2);
                                fastPaddleCheatIsActive = false;
                            }));
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

    /**
     * Check collisions, update ball and paddle positions, and refresh scoreboard.
     *
     * @param elapsedTime amount of time since last update
     */
    @Override
    public void step(double elapsedTime) {
        checkPaddleCollisions();
        checkBrickCollisions(myBall);
        checkWallCollisions(myBall);

        checkPaddleWarping();

        if(extraBallIsActive) {
            checkBrickCollisions(myExtraBall);
            checkWallCollisions(myExtraBall);
        }

        moveBalls(elapsedTime);
        scoreboard.refreshText(this);
    }

    // Helper method for step() to move balls a certain amount based on time
    // since last update.
    private void moveBalls(double elapsedTime) {
        myBall.move(elapsedTime);

        if(extraBallIsActive) {
            myExtraBall.move(elapsedTime);
        }
    }

    // Helper method for step() to check if any ball has hit the paddle
    // and bounce accordingly.
    private void checkPaddleCollisions() {
        if(Breakout.isIntersecting(myPaddle, myBall)) {
            myBall.bounce(myPaddle);
        }

        if(Breakout.isIntersecting(myPaddle, myExtraBall)) {
            myExtraBall.bounce(myPaddle);
        }
    }

    // Helper method for step() to check if ball is hitting or going past a wall
    // and bounce/lose life accordingly.
    private void checkWallCollisions(Ball ball) {
        if(ball.getCenterY() >= sceneHeight + Breakout.SCOREBOARD_HEIGHT) {
            if(ball == myExtraBall) {
                extraBallIsActive = false;
                myExtraBall.setOpacity(0);
            } else {
                loseLife();
            }
        }

        if(ball.getCenterX() - ball.getRadius() <= 0 ||
                ball.getCenterX() + ball.getRadius() >= sceneWidth) {
            ball.setxVelocity(ball.getxVelocity() * -1);
        }

        if(ball.getCenterY() - ball.getRadius() <= Breakout.SCOREBOARD_HEIGHT) {
            ball.setyVelocity(ball.getyVelocity() * -1);
        }
    }

    // Helper method for step() to check if paddle has gone off screen and warp
    // it to the opposite side.
    private void checkPaddleWarping() {
        if(myPaddle.getX() >= sceneWidth) {
            myPaddle.setX(0 - myPaddle.getWidth() / 2);
        } else if(myPaddle.getX() + myPaddle.getWidth() <= 0) {
            myPaddle.setX(sceneWidth - myPaddle.getWidth() / 2);
        }
    }

    /**
     * Perform necessary action to activate powerup obtained from breaking a brick.
     *
     * @param powerup Powerup that was activated
     */
    @Override
    public void handlePowerup(Powerup powerup) {
        if(powerup == Powerup.NONE) {
            return;
        }

        if(powerup == Powerup.EXTRA_BALL) {
            activateExtraBall();
        } else if (powerup == Powerup.INVISIBLE_PADDLE) {
            myPaddle.setOpacity(0);
            invisiblePaddleIsActive = true;
            Timeline timeline =
                    new Timeline(new KeyFrame(Duration.millis(Breakout.INVIS_PADDLE_DURATION), e ->
                    {
                        myPaddle.setOpacity(1);
                        invisiblePaddleIsActive = false;
                    }));
            timeline.setCycleCount(1);
            timeline.play();
        } else if (powerup == Powerup.LONG_PADDLE) {
            longPaddleIsActive = true;
            myPaddle.setWidth(myPaddle.getWidth() * 1.5);
        }
    }

    // Helper method for handlePowerup() to make extra ball visible and start moving.
    private void activateExtraBall() {
        myExtraBall.setOpacity(1);
        myExtraBall.setxVelocity(-160);
        myExtraBall.setyVelocity(100);
        extraBallIsActive = true;
    }
}
