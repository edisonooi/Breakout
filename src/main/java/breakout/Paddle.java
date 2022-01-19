package breakout;

import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.input.KeyCode;

/**
 * This class represents a paddle that can be used to hit balls in a Breakout game
 *
 * @author Edison Ooi
 */
public class Paddle extends Rectangle {
    // Default paddle speed
    private static final int PADDLE_SPEED = 35;

    // Instance variables
    private boolean isHorizontal;
    private int speed;

    /**
     * Class constructor
     *
     * @param width Width of paddle
     * @param height Height of paddle
     * @param isHorizontal Boolean indicating if paddle moves along x-axis
     */
    public Paddle(double width, double height, boolean isHorizontal) {
        super(width, height, Color.WHITE);
        this.speed = PADDLE_SPEED;
        this.isHorizontal = isHorizontal;
    }

    /**
     * Updates paddle position based on key pressed
     *
     * @param code KeyCode of key that is pressed
     */
    public void move(KeyCode code) {
        if(isHorizontal) {
            if(code == KeyCode.LEFT) {
                this.setX(this.getX() - speed);
            } else if(code == KeyCode.RIGHT) {
                this.setX(this.getX() + speed);
            }
        } else {
            if(code == KeyCode.UP) {
                this.setY(this.getY() - speed);
            } else if(code == KeyCode.DOWN) {
                this.setY(this.getY() + speed);
            }
        }
    }

    /**
     * @return Speed of this paddle, in pixels per keystroke
     */
    public int getSpeed() {
        return this.speed;
    }

    /**
     * @param speed desired speed of paddle in pixels per keystroke
     */
    public void setSpeed(int speed) {
        this.speed = speed;
    }
}
