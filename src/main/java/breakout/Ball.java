package breakout;

import javafx.scene.paint.Paint;
import javafx.scene.shape.Circle;

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

}
