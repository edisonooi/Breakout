package breakout;

import javafx.scene.paint.Paint;
import javafx.scene.shape.Rectangle;
import javafx.scene.input.KeyCode;

public class Paddle extends Rectangle {
    // Default paddle speed
    public static final int PADDLE_SPEED = 20;

    // Instance variables
    private boolean isHorizontal;

    /**
     * Class constructor
     * @param width Width of paddle
     * @param height Height of paddle
     * @param isHorizontal Boolean indicating if paddle moves along x-axis
     * @param color Color of paddle
     */
    public Paddle(double width, double height, boolean isHorizontal, Paint color) {
        super(width, height, color);
        this.isHorizontal = isHorizontal;
    }

    /**
     * Updates paddle position based on key pressed
     * @param code KeyCode of key that is pressed
     */
    public void move(KeyCode code) {
        if(isHorizontal) {
            if(code == KeyCode.LEFT) {
                this.setX(this.getX() - PADDLE_SPEED);
            } else if(code == KeyCode.RIGHT) {
                this.setX(this.getX() + PADDLE_SPEED);
            }
        } else {
            if(code == KeyCode.UP) {
                this.setY(this.getY() - PADDLE_SPEED);
            } else if(code == KeyCode.DOWN) {
                this.setY(this.getY() + PADDLE_SPEED);
            }
        }
    }


}
