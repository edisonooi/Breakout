package breakout;

import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.Shape;

import java.util.HashSet;
import java.util.Set;

/**
 * The class that manages the entire game, including dealing with player
 * interactions and setting up visuals
 *
 * @author Edison Ooi
 */
public class Breakout {
    //Default settings for balls
    public static final int BALL_SIZE = 12;
    public static final Paint BALL_COLOR = Color.WHITE;
    public static final int BALL_X_SPEED = 150;
    public static final int BALL_Y_SPEED = 100;

    //Default settings for paddle
    public static final int PADDLE_WIDTH = 80;
    public static final int PADDLE_HEIGHT = 20;
    public static final Paint PADDLE_COLOR = Color.WHITE;
    public static final int PADDLE_SPEED = 10;

    //Default settings for brick
    public static final int BRICK_WIDTH = 100;
    public static final int BRICK_HEIGHT = 100;

    //Main ball
    private Ball myBall;

    //Main paddle
    private Paddle myPaddle;

    //Dummy brick
    private Brick myDummyBrick;

    private Set<Brick> myBricks;

    private Group root;


    private int sceneWidth;
    private int sceneHeight;


    public Scene setupGame(int width, int height, Paint background) {
        //Top level collection that encapsulates all subviews in scene
        root = new Group();

        //Initialize ball in center of screen
        myBall = new Ball(BALL_SIZE, BALL_X_SPEED, BALL_Y_SPEED, BALL_COLOR);
        myBall.setCenterX(width / 2.0);
        myBall.setCenterY(height / 2.0);

        //Initialize paddle toward bottom of screen
        myPaddle = new Paddle(PADDLE_WIDTH, PADDLE_HEIGHT, true, PADDLE_COLOR);
        myPaddle.setX(width / 2.0 - myPaddle.getWidth() / 2);
        myPaddle.setY(height - height / 8.0 - myPaddle.getHeight() / 2);

        //Display a dummy brick
        myDummyBrick = new Brick(BRICK_WIDTH, BRICK_HEIGHT, "5");
        myDummyBrick.setX(10);
        myDummyBrick.setY(10);

        myBricks = new HashSet<>();
        myBricks.add(myDummyBrick);

        //Add subviews to group
        root.getChildren().add(myBall);
        root.getChildren().add(myPaddle);
        root.getChildren().add(myDummyBrick);

        //Create main scene
        Scene scene = new Scene(root, width, height, background);
        //Respond to input
        scene.setOnKeyPressed(e -> handleKeyInput(e.getCode()));

        //Set width and height for this Breakout instance
        sceneWidth = width;
        sceneHeight = height;

        return scene;

    }

    // Update all properties after a certain time interval
    // This serves as a way to animate the objects in game
    public void step(double elapsedTime) {
        // Update ball position based on its x and y velocities
        myBall.setCenterX(myBall.getCenterX() + myBall.getxVelocity() * elapsedTime);
        myBall.setCenterY(myBall.getCenterY() + myBall.getyVelocity() * elapsedTime);

        // Test for wall collisions
        if(myBall.getCenterX() - myBall.getRadius() <= 0 ||
                myBall.getCenterX() + myBall.getRadius() >= sceneWidth) {
            myBall.setxVelocity(myBall.getxVelocity() * -1);
        }

        if(myBall.getCenterY() - myBall.getRadius() <= 0 ||
                myBall.getCenterY() + myBall.getRadius() >= sceneHeight) {
            myBall.setyVelocity(myBall.getyVelocity() * -1);
        }

        // Test for paddle and ball collision
        if(isIntersecting(myPaddle, myBall)) {
            myBall.bounce(myPaddle);
        }

        // Test for ball and brick collision
        if(isIntersecting(myBall, myDummyBrick)) {
            myBall.bounce(myDummyBrick, root, myBricks);
        }

        if(myPaddle.getX() >= sceneWidth) {
            myPaddle.setX(0 - myPaddle.getWidth() / 2);
        } else if(myPaddle.getX() + myPaddle.getWidth() <= 0) {
            myPaddle.setX(sceneWidth - myPaddle.getWidth() / 2);
        }


    }

    // What to do each time a key is pressed
    private void handleKeyInput (KeyCode code) {
        myPaddle.move(code);
//        switch (code) {
//            case RIGHT -> myPaddle.setX(myPaddle.getX() + PADDLE_SPEED);
//            case LEFT -> myPaddle.setX(myPaddle.getX() - PADDLE_SPEED);
//        }
    }

    private boolean isIntersecting(Shape a, Shape b) {
        //If the bounds of both shapes intersect, return true
        return b.getBoundsInParent().intersects(a.getBoundsInParent());
    }
}
