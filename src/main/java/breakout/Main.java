package breakout;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.stage.Stage;
import javafx.scene.shape.Circle;
import javafx.util.Duration;


/**
 * Feel free to completely change this code or delete it entirely.
 *
 * @author YOUR NAME HERE
 */
public class Main extends Application {
    // useful names for constant values used
    public static final String TITLE = "Example JavaFX Animation";
    public static final int SIZE = 400;
    public static final Paint BACKGROUND_COLOR = Color.BLACK;
    public static final int FRAMES_PER_SECOND = 60;
    public static final double SECOND_DELAY = 1.0 / FRAMES_PER_SECOND;

    // instance variables
    private Breakout myBreakout;


    /**
     * Initialize what will be displayed.
     */
    @Override
    public void start (Stage stage) {
        // Create instance of Breakout game
        myBreakout = new Breakout();

        // Attach scene to the stage and display it
        Scene scene = myBreakout.setupGame(SIZE, SIZE, BACKGROUND_COLOR);
        stage.setScene(scene);
        stage.setTitle(TITLE);
        stage.show();

        // Attach game loop by calling step repeatedly
        Timeline animation = new Timeline();
        animation.setCycleCount(Timeline.INDEFINITE);
        animation.getKeyFrames().add(new KeyFrame(Duration.seconds(SECOND_DELAY), e -> myBreakout.step(SECOND_DELAY)));
        animation.play();


//        Circle shape = new Circle(190, 190, 20);
//        shape.setFill(Color.LIGHTSTEELBLUE);
//
//        Group root = new Group();
//        root.getChildren().add(shape);
//
//        Scene scene = new Scene(root, SIZE, SIZE, Color.DARKBLUE);
//        stage.setScene(scene);
//
//        stage.setTitle(TITLE);
//        stage.show();
    }
}
