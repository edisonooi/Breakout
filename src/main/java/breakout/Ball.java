package breakout;

import javafx.scene.Group;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;
import javafx.scene.shape.Rectangle;

import java.util.Set;

public class Ball extends Circle {
    // Constants
    public static final int DEFAULT_RADIUS = 10;
    private static final Paint DEFAULT_COLOR = Color.WHITE;

    // Velocity components for this ball
    private double xVelocity;
    private double yVelocity;

    /**
     * Class constructor.
     *
     * @param xVelocity velocity of ball in x direction
     * @param yVelocity velocity of ball in x direction
     */
    public Ball(double xVelocity, double yVelocity) {
        super(DEFAULT_RADIUS, DEFAULT_COLOR);
        this.xVelocity = xVelocity;
        this.yVelocity = yVelocity;
    }

    /**
     * Updates location of ball based on its velocity and elapsed time.
     *
     * @param elapsedTime amount of time that has passed since last game step
     */
    public void move(double elapsedTime) {
        setCenterX(getCenterX() + xVelocity * elapsedTime);
        setCenterY(getCenterY() + yVelocity * elapsedTime);
    }

    /**
     * Updates properties of ball and brick upon collision.
     *
     * @param brick brick that is colliding with ball
     * @param root Group of Nodes being rendered by scene
     * @param bricks Set of remaining bricks in current level
     * @return powerup assigned to brick if it breaks, or null if it doesn't break
     */
    public Powerup bounce(Brick brick, Group root, Set<Brick> bricks) {
        updateVelocities(brick);

        // Brick was broken
        if(brick.hit()) {
            bricks.remove(brick);
            root.getChildren().remove(brick);

            return brick.getPowerup();
        }

        return null;
    }

    /**
     * Updates properties of ball upon colliding with paddle.
     *
     * @param paddle Paddle that ball collides with
     */
    public void bounce(Paddle paddle) {
        updateVelocities(paddle);
    }


    // Updates velocities of ball depending on which side of rectangle it bounces off of.
    // We assume full conservation of momentum for the ball in all directions.
    private void updateVelocities(Rectangle rect) {
        // Create lines representing all four sides of rectangle, and compare bounds of ball with each line
        double leftX = rect.getX();
        double rightX = rect.getX() + rect.getWidth();
        double topY = rect.getY();
        double bottomY = rect.getY() + rect.getHeight();

        Line leftLine = new Line(leftX, topY, leftX, bottomY);
        Line rightLine = new Line(rightX, topY, rightX, bottomY);
        Line topLine = new Line(leftX, topY, rightX, topY);
        Line bottomLine = new Line(leftX, bottomY, rightX, bottomY);

        if(isIntersectingLine(leftLine) || isIntersectingLine((rightLine))) {
            this.xVelocity *= -1;
        } else if (isIntersectingLine(topLine) || isIntersectingLine(bottomLine)) {
            this.yVelocity *= -1;
        }
    }


    // Helper method for updateVelocities(), checks if ball is intersecting a given line.
    private boolean isIntersectingLine(Line line) {
        return this.getBoundsInLocal().intersects(line.getBoundsInLocal());
    }

    /**
     * @return velocity of ball in x-direction
     */
    public double getxVelocity() {
        return xVelocity;
    }

    /**
     * @return velocity of ball in y-direction
     */
    public double getyVelocity() {
        return yVelocity;
    }

    /**
     * Set the x-velocity of this ball.
     *
     * @param xVelocity
     */
    public void setxVelocity(double xVelocity) {
        this.xVelocity = xVelocity;
    }

    /**
     * Set the y-velocity of this ball.
     *
     * @param yVelocity
     */
    public void setyVelocity(double yVelocity) {
        this.yVelocity = yVelocity;
    }
}
