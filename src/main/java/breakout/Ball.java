package breakout;

import javafx.scene.Group;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;

import java.util.Set;

public class Ball extends Circle {
    private double radius;

    private int xVelocity;
    private int yVelocity;

    private Paint color;

    public Ball(double radius, int xVelocity, int yVelocity, Paint color) {
        this.radius = radius;
        this.xVelocity = xVelocity;
        this.yVelocity = yVelocity;
        this.color = color;
    }

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

    public void bounce(Paddle paddle) {
        updateVelocities(paddle);
    }

    private void updateVelocities(Rectangle rect) {

    }



}
