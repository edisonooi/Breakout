package breakout;

import javafx.scene.Group;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;
import javafx.scene.shape.Rectangle;

import java.util.Set;

public class Ball extends Circle {
    // Instance variables
    private double xVelocity;
    private double yVelocity;

    /**
     * Class constructor
     * @param radius radius of ball, in pixels
     * @param xVelocity velocity of ball in x direction
     * @param yVelocity velocity of ball in x direction
     * @param color color of ball
     */
    public Ball(double radius, double xVelocity, double yVelocity, Paint color) {
        super(radius, color);
        this.xVelocity = xVelocity;
        this.yVelocity = yVelocity;
    }

    /**
     * Updates properties of ball and brick upon collision
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
     * Updates properties of ball upon colliding with paddle
     * @param paddle Paddle that ball collides with
     */
    public void bounce(Paddle paddle) {
        updateVelocities(paddle);
    }

    /**
     * Updates velocities of ball depending on which side of rectangle it bounces off of
     * @param rect Rectangle that ball collides with
     */
    private void updateVelocities(Rectangle rect) {
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
        }
        if(isIntersectingLine(topLine) || isIntersectingLine(bottomLine)) {
            this.yVelocity *= -1;
        }
    }

    /**
     * Helper method for updateVelocities(), checks if ball is intersecting a given line
     * @param line line to check ball intersection with
     * @return boolean that indicates if ball and line are intersecting
     */
    private boolean isIntersectingLine(Line line) {
        return this.getBoundsInParent().intersects(line.getBoundsInParent());
    }

    public double getxVelocity() {
        return xVelocity;
    }

    public double getyVelocity() {
        return yVelocity;
    }

    public void setxVelocity(double xVelocity) {
        this.xVelocity = xVelocity;
    }

    public void setyVelocity(double yVelocity) {
        this.yVelocity = yVelocity;
    }
}
