package breakout;

import javafx.scene.paint.Paint;
import javafx.scene.shape.Rectangle;

public class Paddle extends Rectangle {
    public static final int PADDLE_SPEED = 10;
    private int width;
    private int height;
    private Paint color;



    public Paddle(double width, double height, Paint color) {
        super(width, height, color);

    }




}
