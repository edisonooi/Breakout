package breakout;

import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;

/**
 * The class that manages the entire game, including dealing with player
 * interactions and setting up visuals
 *
 * @author Edison Ooi
 */
public class Breakout {
    //Default settings for balls
    public static final int BALL_SIZE = 30;
    public static final Paint BALL_COLOR = Color.WHITE;
    public static final int BALL_X_SPEED = 48;
    public static final int BALL_Y_SPEED = 30;

    //Default settings for paddle
    public static final int PADDLE_WIDTH = 50;
    public static final int PADDLE_HEIGHT = 10;
    public static final Paint PADDLE_COLOR = Color.WHITE;

    //Main ball
    private Circle myBall;
    private int ballXDirection = 1;
    private int ballYDirection = 1;

    //Main paddle
    private Rectangle myPaddle;

    private int sceneWidth;
    private int sceneHeight;


    public Scene setupGame(int width, int height, Paint background) {
        //Top level collection that encapsulates all subviews in scene
        Group root = new Group();

        //Initialize ball in center of screen
        myBall = new Circle(BALL_SIZE, BALL_COLOR);
        myBall.setCenterX(width / 2.0);
        myBall.setCenterY(height / 2.0);

        //Initialize paddle toward bottom of screen
        myPaddle = new Rectangle(PADDLE_WIDTH, PADDLE_HEIGHT, PADDLE_COLOR);
        myPaddle.setX(width / 2.0 - myPaddle.getWidth() / 2);
        myPaddle.setY(height - height / 8.0 - myPaddle.getHeight() / 2);

        //Add subviews to group
        root.getChildren().add(myBall);
        root.getChildren().add(myPaddle);

        //Create main scene and set instance width and height
        Scene scene = new Scene(root, width, height, background);
        sceneWidth = width;
        sceneHeight = height;

        return scene;

    }

    // Update all properties after a certain time interval
    // This serves as a way to animate the objects in game
    public void step(double elapsedTime) {
        // Update ball position based on its x and y velocities
        myBall.setCenterX(myBall.getCenterX() + ballXDirection * BALL_X_SPEED * elapsedTime);
        myBall.setCenterY(myBall.getCenterY() + ballYDirection * BALL_Y_SPEED * elapsedTime);

        // Test for wall collisions
        if(myBall.getCenterX() - myBall.getRadius() <= 0 ||
                myBall.getCenterX() + myBall.getRadius() >= sceneWidth) {
            ballXDirection *= -1;
        }

        if(myBall.getCenterY() - myBall.getRadius() <= 0 ||
                myBall.getCenterY() + myBall.getRadius() >= sceneHeight) {
            ballYDirection *= -1;
        }

    }





}
