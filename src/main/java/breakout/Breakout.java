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
    private int currentLevelIndex;
    private Level[] levels;


    private int sceneWidth;
    private int sceneHeight;

    public Scene setupGame(int width, int height, Paint background) {
        //Top level collection that encapsulates all subviews in scene
        root = new Group();

        Level level1 = new NormalLevel(2, 3);
        try {
            level1.setupChildNodes(root, width, height);
        } catch (FileNotFoundException e) {
            System.out.println(e);
            return null;
        }

        currentLevel = level1;


        //Create main scene
        Scene scene = new Scene(root, width, height, background);
        //Respond to input
        scene.setOnKeyPressed(e -> currentLevel.handleKeyInput(e.getCode()));

        //Set width and height for this Breakout instance
        sceneWidth = width;
        sceneHeight = height;

        return scene;

    }

    // Update all properties after a certain time interval
    // This serves as a way to animate the objects in game
    public void step(double elapsedTime) {
        // Update ball position based on its x and y velocities
//        myBall.setCenterX(myBall.getCenterX() + myBall.getxVelocity() * elapsedTime);
//        myBall.setCenterY(myBall.getCenterY() + myBall.getyVelocity() * elapsedTime);
//
//        // Test for wall collisions
//        if(myBall.getCenterX() - myBall.getRadius() <= 0 ||
//                myBall.getCenterX() + myBall.getRadius() >= sceneWidth) {
//            myBall.setxVelocity(myBall.getxVelocity() * -1);
//        }
//
//        if(myBall.getCenterY() - myBall.getRadius() <= 0 ||
//                myBall.getCenterY() + myBall.getRadius() >= sceneHeight) {
//            myBall.setyVelocity(myBall.getyVelocity() * -1);
//        }
//
//        // Test for paddle and ball collision
//        if(isIntersecting(myPaddle, myBall)) {
//            myBall.bounce(myPaddle);
//        }
//
//        // Test for ball and brick collision
//        if(isIntersecting(myBall, myDummyBrick)) {
//            myBall.bounce(myDummyBrick, root, myBricks);
//        }
//
//        if(myPaddle.getX() >= sceneWidth) {
//            myPaddle.setX(0 - myPaddle.getWidth() / 2);
//        } else if(myPaddle.getX() + myPaddle.getWidth() <= 0) {
//            myPaddle.setX(sceneWidth - myPaddle.getWidth() / 2);
//        }
//        if(level.brick.isempty) {
//            level.clear;
//            levelCount++
//
//
//        }

    }

    private boolean isIntersecting(Shape a, Shape b) {
        //If the bounds of both shapes intersect, return true
        return b.getBoundsInParent().intersects(a.getBoundsInParent());
    }
}
