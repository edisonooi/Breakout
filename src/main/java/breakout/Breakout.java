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
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;

import java.io.FileNotFoundException;
import java.util.HashSet;
import java.util.Set;

/**
 * The class that manages the entire game, including dealing with player
 * interactions and setting up visuals
 *
 * @author Edison Ooi
 */
public class Breakout {
    // How much space to put between each brick
    public static final int BRICK_PADDING = 5;
    public static final int INVIS_PADDLE_DURATION = 2000;
    public static final int FAST_PADDLE_DURATION = 5000;
    public static final int SLOW_BALL_DURATION = 5000;
    public static final int SCOREBOARD_HEIGHT = 100;

    private Scene mainScene;
    private Group root;
    private Scoreboard scoreboard;

    private Level currentLevel;
    private int currentLevelNum;

    private int sceneWidth;
    private int sceneHeight;

    public Scene setupGame(int width, int height, Paint background) {
        sceneWidth = width;
        sceneHeight = height;

        //Top level collection that encapsulates all subviews in scene
        root = new Group();

        //Setup scoreboard
        scoreboard = new Scoreboard(sceneWidth, SCOREBOARD_HEIGHT, "Hello");
        scoreboard.setX(0);
        scoreboard.setY(0);
        root.getChildren().add(scoreboard);

        //Initialize first level
        currentLevelNum = 1;
        Level level1 = new NormalLevel(1, 3, root, width, height, scoreboard);
        currentLevel = level1;


        //Create main scene
        Scene scene = new Scene(root, sceneWidth, sceneHeight + SCOREBOARD_HEIGHT, background);
        //Respond to input
        scene.setOnKeyPressed(e -> handleKeyInput(e.getCode()));
        mainScene = scene;

        return scene;
    }

    // Update all properties after a certain time interval
    // This serves as a way to animate the objects in game
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

    private void goToLevel(int level) {
        currentLevel.clear();
        currentLevelNum = level;

        switch(currentLevelNum) {
            case 1 -> currentLevel = new NormalLevel(1, 3, root, sceneWidth, sceneHeight, scoreboard);
            case 2 -> currentLevel = new NormalLevel(2, 3, root, sceneWidth, sceneHeight, scoreboard);
            default -> currentLevel = new ExtremeLevel(3, 5, root, sceneWidth, sceneHeight, scoreboard);
        }
    }

    private void showEndScreen(boolean didWin) {
        mainScene.setOnKeyPressed(null);

        root.getChildren().clear();

        Text endScreenText = new Text(sceneWidth, sceneHeight, "");
        String endScreenMessage = String.format("You %s\nScore: %d", didWin ? "Win" : "Lose", scoreboard.getScore());
        endScreenText.setText(endScreenMessage);
        endScreenText.setFont(new Font(50));
        endScreenText.setTextAlignment(TextAlignment.CENTER);
        endScreenText.setFill(didWin ? Color.LIMEGREEN : Color.RED);
        endScreenText.setX(sceneWidth / 2 - endScreenText.getBoundsInLocal().getWidth() / 2);
        endScreenText.setY((sceneHeight + SCOREBOARD_HEIGHT) / 2 - endScreenText.getBoundsInLocal().getHeight() / 2);

        root.getChildren().add(endScreenText);
    }

    public static boolean isIntersecting(Shape a, Shape b) {
        //If the bounds of both shapes intersect, return true
        return b.getBoundsInLocal().intersects(a.getBoundsInLocal());
    }
}
