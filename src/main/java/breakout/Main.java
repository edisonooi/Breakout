package breakout;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.stage.Stage;
import javafx.util.Duration;

/**
 * Landing point for program initialization.
 *
 * @author Edison Ooi
 */
public class Main extends Application {
    // Constants for rendering game
    public static final String TITLE = "Breakout Game";
    public static final int GAME_HEIGHT = 600;
    public static final int GAME_WIDTH = 600;
    public static final Paint BACKGROUND_COLOR = Color.BLACK;
    public static final int FRAMES_PER_SECOND = 60;
    public static final double SECOND_DELAY = 1.0 / FRAMES_PER_SECOND;

    // Instance of Breakout game
    private Breakout myBreakout;

    /**
     * Create an instance of Breakout game and play it.
     */
    @Override
    public void start (Stage stage) {
        // Create instance of Breakout game
        myBreakout = new Breakout();

        // Attach scene to the stage and display it
        Scene scene = myBreakout.setupGame(GAME_WIDTH, GAME_HEIGHT, BACKGROUND_COLOR);
        stage.setScene(scene);
        stage.setTitle(TITLE);
        stage.show();

        // Attach game loop by calling step repeatedly
        Timeline animation = new Timeline();
        animation.setCycleCount(Timeline.INDEFINITE);
        animation.getKeyFrames().add(new KeyFrame(Duration.seconds(SECOND_DELAY), e -> myBreakout.step(SECOND_DELAY)));
        animation.play();
    }
}
