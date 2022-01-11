package breakout;

import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Circle;

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
    public static final int BALL_X_VELOCITY = 16;
    public static final int BALL_Y_VELOCITY = 10;

    //Default settings for paddle
    public static final int PADDLE_WIDTH = 50;
    public static final int PADDLE_HEIGHT = 10;
    public static final Paint PADDLE_COLOR = Color.WHITE;

    private Circle myBall;


    public Scene setupGame(int width, int height, Paint background) {
        //Top level collection that encapsulates all subviews in scene
        Group root = new Group();

        //Initialize ball in center of screen
        myBall = new Circle(BALL_SIZE, BALL_COLOR);
        myBall.setCenterX(width / 2);
        myBall.setCenterY(height / 2);

        //Add subviews to group
        root.getChildren().add(myBall);

        //Create main scene
        Scene scene = new Scene(root, width, height, background);

        return scene;

    }



}
