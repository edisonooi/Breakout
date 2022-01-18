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

    private Group root;

    private Level currentLevel;
    private int currentLevelNum;

    private int sceneWidth;
    private int sceneHeight;

    public Scene setupGame(int width, int height, Paint background) {
        //Top level collection that encapsulates all subviews in scene
        root = new Group();

        //Initialize first level
        currentLevelNum = 1;
        Level level1 = new NormalLevel(1, 3, root, width, height);
        currentLevel = level1;

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
        currentLevel.step(elapsedTime);

        if(currentLevel.isFinished()) {
            if(currentLevelNum >= 3) {

                return;
            } else {
                currentLevelNum++;
                goToLevel(currentLevelNum);
            }
        } else if (currentLevel.didFail()) {
            //End game
            return;
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
            default -> currentLevel.handleKeyInput(code);
        }

    }

    private void goToLevel(int level) {
        currentLevel.clear();
        currentLevelNum = level;

        switch(currentLevelNum) {
            case 1 -> currentLevel = new NormalLevel(1, 3, root, sceneWidth, sceneHeight);
            case 2 -> currentLevel = new NormalLevel(2, 3, root, sceneWidth, sceneHeight);
            default -> currentLevel = new ExtremeLevel(3, 5, root, sceneWidth, sceneHeight);
        }
    }

    public static boolean isIntersecting(Shape a, Shape b) {
        //If the bounds of both shapes intersect, return true
        return b.getBoundsInLocal().intersects(a.getBoundsInLocal());
    }
}
