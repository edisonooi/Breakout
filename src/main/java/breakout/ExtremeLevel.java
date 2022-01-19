package breakout;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.scene.Group;
import javafx.scene.input.KeyCode;
import javafx.util.Duration;

/**
 * This class represents a Breakout level that consists of four paddles, one on
 * each side of the screen, the top and bottom moving horizontally together and the left
 * and right moving vertically together. Bricks are placed in a rectangular
 * region in the center of the screen. A life is lost when the main ball goes
 * off any side of the screen.
 */
public class ExtremeLevel extends Level {
    // Game elements specific to this level
    private Paddle leftPaddle;
    private Paddle rightPaddle;
    private Paddle topPaddle;
    private Paddle bottomPaddle;
    private Ball myBall;

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
    public ExtremeLevel(int levelNumber, int lives, Group root, int sceneWidth, int sceneHeight, Scoreboard scoreboard) {
        super(levelNumber, lives, root, sceneWidth, sceneHeight, scoreboard);
    }

    /**
     * @param root Group object that holds all Nodes for Breakout game
     * @param sceneWidth width, in pixels, of playable region of Breakout game
     * @param sceneHeight height, in pixels, of playable region of Breakout game
     */
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

    /**
     * Sets initial properties of all balls in level.
     */
    @Override
    public void setupBalls() {
        myBall.setCenterX(sceneWidth * 0.5);
        myBall.setCenterY(sceneHeight * 0.1 + Breakout.SCOREBOARD_HEIGHT);
        myBall.setxVelocity(150);
        myBall.setyVelocity(100);
    }

    /**
     * Move all paddles according to key pressed.
     * @param code KeyCode of key that was pressed
     */
    @Override
    public void movePaddles(KeyCode code) {
        leftPaddle.move(code);
        rightPaddle.move(code);
        topPaddle.move(code);
        bottomPaddle.move(code);
    }

    /**
     * Helper method for handleKeyInput() to activate fast paddle cheat.
     */
    @Override
    public void activateFastPaddleCheat() {
        fastPaddleCheatHasBeenUsed = true;
        fastPaddleCheatIsActive = true;

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

    // Helper method for activateFastPaddleCheat() to reset paddle speeds to their original speed
    private void resetPaddleSpeeds() {
        leftPaddle.setSpeed(leftPaddle.getSpeed() / 2);
        rightPaddle.setSpeed(rightPaddle.getSpeed() / 2);
        topPaddle.setSpeed(topPaddle.getSpeed() / 2);
        bottomPaddle.setSpeed(bottomPaddle.getSpeed() / 2);
        fastPaddleCheatIsActive = false;
    }

    /**
     * Helper method for handleKeyInput() to activate slow ball cheat.
     */
    @Override
    public void activateSlowBallCheat() {
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
        myBall.move(elapsedTime);
        scoreboard.refreshText(this);
    }

    /**
     * Helper method for step() to move balls a certain amount based on time since last update.
     */
    @Override
    public void checkPaddleCollisions() {
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

    /**
     * Helper method for step() to check if ball is hitting or going past a wall and bounce/lose life accordingly.
     * @param ball
     */
    @Override
    public void checkWallCollisions(Ball ball) {
        if(ball.getCenterX() <= 0
                || ball.getCenterX() >= sceneWidth
                || ball.getCenterY() <= Breakout.SCOREBOARD_HEIGHT
                || ball.getCenterY() >= sceneHeight + Breakout.SCOREBOARD_HEIGHT) {
            loseLife();
        }
    }

    /**
     * Helper method for step() to check if paddle has gone off screen and warp it to the opposite side.
     */
    @Override
    public void checkPaddleWarping() {
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

        if(powerup == Powerup.INVISIBLE_PADDLE) {
            topPaddle.setOpacity(0);
            bottomPaddle.setOpacity(0);
            leftPaddle.setOpacity(0);
            rightPaddle.setOpacity(0);
            invisiblePaddleIsActive = true;

            Timeline timeline =
                    new Timeline(new KeyFrame(Duration.millis(Breakout.INVIS_PADDLE_DURATION), e -> makePaddlesVisible()));
            timeline.setCycleCount(1);
            timeline.play();
        } else if (powerup == Powerup.LONG_PADDLE) {
            topPaddle.setWidth(topPaddle.getWidth() * 1.5);
            bottomPaddle.setWidth(bottomPaddle.getWidth() * 1.5);
            leftPaddle.setHeight(leftPaddle.getHeight() * 1.5);
            rightPaddle.setHeight(rightPaddle.getHeight() * 1.5);
            longPaddleIsActive = true;
        }
    }

    private void makePaddlesVisible() {
        topPaddle.setOpacity(1);
        bottomPaddle.setOpacity(1);
        leftPaddle.setOpacity(1);
        rightPaddle.setOpacity(1);
        invisiblePaddleIsActive = false;
    }
}
