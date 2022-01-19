package breakout;

import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.input.KeyCode;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Shape;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;

/**
 * The class that manages the entire game, including dealing with player
 * interactions and setting up visuals.
 *
 * @author Edison Ooi
 */
public class Breakout {
    // Game-wide constants
    public static final int BRICK_PADDING = 5;
    public static final int INVIS_PADDLE_DURATION = 2000;
    public static final int FAST_PADDLE_DURATION = 5000;
    public static final int SLOW_BALL_DURATION = 5000;
    public static final int SCOREBOARD_HEIGHT = 100;

    // UI members for this instance
    private Scene mainScene;
    private Group root;
    private Scoreboard scoreboard;

    // Variables to track current level being played
    private Level currentLevel;
    private int currentLevelNum;
    
    // Store initial values of scene's width and height to use consistently throughout lifespan of game
    private int sceneWidth;
    private int sceneHeight;

    /**
     * Creates main scene and Group in which all UI components will live. Also initializes first level and scoreboard,
     * effectively starting gameplay.
     *
     * This should be the first method called after initialization of Breakout object.
     *
     * @param width width, in pixels, of playable region of Breakout game
     * @param height height, in pixels, of playable region of Breakout game
     * @param background background color of Breakout game
     * @return Scene object encapsulating all UI components to be rendered in Main
     */
    public Scene setupGame(int width, int height, Paint background) {
        // Initialize width and height of game
        // We don't take these values from Scene object because it changes upon window resizing, which messes up calculations
        sceneWidth = width;
        sceneHeight = height;

        // Top level collection that encapsulates all subviews in scene
        root = new Group();

        // Setup scoreboard
        scoreboard = new Scoreboard(width, SCOREBOARD_HEIGHT);
        scoreboard.setX(0);
        scoreboard.setY(0);
        root.getChildren().add(scoreboard);

        // Initialize first level
        currentLevelNum = 1;
        Level level1 = new NormalLevel(1, 3, root, sceneWidth, sceneHeight, scoreboard);
        currentLevel = level1;

        // Create main scene
        Scene scene = new Scene(root, sceneWidth, sceneHeight + SCOREBOARD_HEIGHT, background);

        // Respond to input
        scene.setOnKeyPressed(e -> handleKeyInput(e.getCode()));
        mainScene = scene;

        return scene;
    }

    /**
     * Recalculate all properties of current level and checks if the level is either finished or failed.
     * This method assumes setupGame() has already been called or currentLevel has been initialized otherwise.
     *
     * @param elapsedTime amount of time since last update
     */
    public void step(double elapsedTime) {
        currentLevel.step(elapsedTime);

        if(currentLevel.isFinished()) {
            if(currentLevelNum >= 3) {
                showEndScreen(true);
            } else {
                currentLevelNum++;
                goToLevel(currentLevelNum);
            }
        } else if (currentLevel.didFail()) {
            showEndScreen(false);
        }
    }

    /**
     * Performs action for global cheat keys, or calls current level's key handler if key was not a global cheat key.
     * This method assumes setupGame() has already been called or currentLevel has been initialized otherwise.
     *
     * @param code KeyCode of key that was pressed
     */
    public void handleKeyInput(KeyCode code) {
        switch (code) {
            case DIGIT1 -> goToLevel(1);
            case DIGIT2 -> goToLevel(2);
            case DIGIT3 -> goToLevel(3);
            case DIGIT4 -> goToLevel(3);
            case DIGIT5 -> goToLevel(3);
            case DIGIT6 -> goToLevel(3);
            case DIGIT7 -> goToLevel(3);
            case DIGIT8 -> goToLevel(3);
            case DIGIT9 -> goToLevel(3);
            case R -> currentLevel.reset();
            case C -> {
                if (currentLevelNum >= 3) {
                    showEndScreen(true);
                } else {
                    goToLevel(++currentLevelNum);
                }
            }
            default -> currentLevel.handleKeyInput(code);
        }
    }

    // Skips to level of given number, or final level if given level is higher than the highest level.
    private void goToLevel(int level) {
        System.out.println(sceneWidth);
        System.out.println(sceneHeight);

        currentLevel.clear();
        currentLevelNum = level;

        switch(currentLevelNum) {
            case 1 -> currentLevel = new NormalLevel(1, 3, root, sceneWidth, sceneHeight, scoreboard);
            case 2 -> currentLevel = new NormalLevel(2, 3, root, sceneWidth, sceneHeight, scoreboard);
            default -> currentLevel = new ExtremeLevel(3, 5, root, sceneWidth, sceneHeight, scoreboard);
        }
    }

    // Shows end screen with result and final score, indicating end of game.
    private void showEndScreen(boolean didWin) {
        mainScene.setOnKeyPressed(null);

        root.getChildren().clear();

        Text endScreenText = new Text(sceneWidth, sceneHeight, "");
        String endScreenMessage = String.format("You %s\nScore: %d", didWin ? "Win" : "Lose", scoreboard.getScore());
        endScreenText.setText(endScreenMessage);
        endScreenText.setFont(new Font(50));
        endScreenText.setTextAlignment(TextAlignment.CENTER);
        endScreenText.setFill(didWin ? Color.LIMEGREEN : Color.RED);
        endScreenText.setX(sceneWidth / 2.0 - endScreenText.getBoundsInLocal().getWidth() / 2);
        endScreenText.setY((sceneHeight + SCOREBOARD_HEIGHT) / 2.0 - endScreenText.getBoundsInLocal().getHeight() / 2);

        root.getChildren().add(endScreenText);
    }

    /**
     * Utility method to determine if two Shapes are intersecting.
     *
     * @param a first Shape object
     * @param b second Shape object
     * @return boolean indicating if the bounds of the two Shapes are intersecting
     */
    public static boolean isIntersecting(Shape a, Shape b) {
        return b.getBoundsInLocal().intersects(a.getBoundsInLocal());
    }
}
