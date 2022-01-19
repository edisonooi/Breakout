package breakout;

import javafx.application.Platform;
import javafx.scene.Group;
import javafx.scene.input.KeyCode;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;

/**
 * This class represents an abstraction of a level in Breakout.
 *
 * @author Edison Ooi
 */
public abstract class Level {
    // Statistics about level
    public int levelNumber;
    public int numLives;
    public int numRemainingLives;
    public int pointsAccumulated;

    // Indicates whether level has been failed
    public boolean failed;

    // Keep track of which powerups are currently active
    public boolean fastPaddleCheatHasBeenUsed;
    public boolean fastPaddleCheatIsActive;
    public boolean slowBallCheatIsActive;
    public boolean invisiblePaddleIsActive;
    public boolean extraBallIsActive;
    public boolean longPaddleIsActive;

    // Variables needed to render UI components for level
    public String blockConfigFile;
    public Set<Brick> bricks;
    public Group levelRoot;
    public Scoreboard scoreboard;

    // Store width and height of playable region for game
    public int sceneWidth;
    public int sceneHeight;

    /**
     * Class constructor. Initializes all instance variables and sets up UI elements for level.
     *
     * @param levelNumber cardinal number representing current level being created
     * @param lives number of lives the player starts with
     * @param root Group object that holds all Nodes rendered by Breakout game
     * @param sceneWidth width, in pixels, of playable region of Breakout game
     * @param sceneHeight height, in pixels, of playable region of Breakout game
     * @param scoreboard Scoreboard object used to display game statistics to player
     */
    public Level(int levelNumber, int lives, Group root, int sceneWidth, int sceneHeight, Scoreboard scoreboard) {
        this.levelNumber = levelNumber;
        this.numLives = this.numRemainingLives = lives;
        this.pointsAccumulated = 0;

        // Brick configuration text file name must match this format
        this.blockConfigFile = "src/main/resources/level" + levelNumber + "config.txt";
        this.bricks = new HashSet<>();

        this.levelRoot = root;
        this.scoreboard = scoreboard;

        this.sceneWidth = sceneWidth;
        this.sceneHeight = sceneHeight;

        this.failed = false;

        this.fastPaddleCheatHasBeenUsed = false;
        this.fastPaddleCheatIsActive = false;
        this.slowBallCheatIsActive = false;
        this.extraBallIsActive = false;
        this.longPaddleIsActive = false;
        this.invisiblePaddleIsActive = false;

        setupChildNodes(root, sceneWidth, sceneHeight);
    }

    /**
     * Sets up all Nodes needed for level, including bricks, balls, and paddles.
     *
     * @param root Group object that holds all Nodes for Breakout game
     * @param sceneWidth width, in pixels, of playable region of Breakout game
     * @param sceneHeight height, in pixels, of playable region of Breakout game
     */
    public abstract void setupChildNodes(Group root, int sceneWidth, int sceneHeight);

    /**
     * Sets initial properties of all balls in level.
     */
    public abstract void setupBalls();

    /**
     * Responds to certain keystrokes such as movement controls or cheat keys.
     *
     * @param code KeyCode of key that was pressed
     */
    public abstract void handleKeyInput(KeyCode code);

    /**
     * Performs specific actions when powerup is activated upon brick breaking.
     *
     * @param powerup Powerup that was activated
     */
    public abstract void handlePowerup(Powerup powerup);

    /**
     * Recalculate all properties of level and its children Nodes.
     *
     * @param elapsedTime amount of time since last update
     */
    public abstract void step(double elapsedTime);

    /**
     * Instantiates and renders all bricks for level by extracting brick configuration from given text file and placing
     * them in given region.
     *
     * Should only be called from within setupChildNodes().
     *
     * @param root Group object that holds all Nodes for Breakout game
     * @param startX x-coordinate of starting point of rectangular region for bricks
     * @param startY y-coordinate of starting point of rectangular region for bricks
     * @param endX x-coordinate of ending point of rectangular region for bricks
     * @param endY y-coordinate of ending point of rectangular region for bricks
     */
    public void setupBricks(Group root, int startX, int startY, int endX, int endY) {
        // Attempt to open blockConfigFile, or kill program if file is not found
        Scanner sc = null;
        File file = null;
        try {
            file = new File(blockConfigFile);
            sc = new Scanner(file);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            Platform.exit();
            return;
        }

        // Calculate dimensions of available space for bricks
        int availableWidth = endX - startX;
        int availableHeight = endY - startY;

        // Figure out how many bricks to create in each direction in order to properly size bricks in scene
        int bricksInXDirection = 0;
        int bricksInYDirection = 0;

        while(sc.hasNextLine()) {
            bricksInYDirection++;

            String currentLine = sc.nextLine();

            if(bricksInXDirection == 0) {
                bricksInXDirection = currentLine.split(" ").length;
            }
        }

        // Calculate desired brick dimensions
        double brickWidth = (availableWidth - ((bricksInXDirection + 1) * Breakout.BRICK_PADDING)) / (double)bricksInXDirection;
        double brickHeight = (availableHeight - ((bricksInYDirection + 1) * Breakout.BRICK_PADDING)) / (double)bricksInYDirection;

        // Create brick objects, set their locations, add to root and set of bricks to be rendered
        double currentX = startX + Breakout.BRICK_PADDING;
        double currentY = startY + Breakout.BRICK_PADDING;

        String[] bricksInCurrentLine;

        try {
            sc = new Scanner(file);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            return;
        }

        while(sc.hasNextLine()) {
            bricksInCurrentLine = sc.nextLine().split(" ");

            for(String brickType : bricksInCurrentLine) {
                if(!brickType.equals("0")) {
                    Brick brick = new Brick(brickWidth, brickHeight, brickType);
                    brick.setX(currentX);
                    brick.setY(currentY);
                    root.getChildren().add(brick);
                    this.bricks.add(brick);
                }

                currentX += brickWidth + Breakout.BRICK_PADDING;
            }

            // Reset position trackers
            currentX = startX + Breakout.BRICK_PADDING;
            currentY += brickHeight + Breakout.BRICK_PADDING;
        }

        sc.close();
    }

    /**
     * Checks if ball has hit any remaining bricks, and performs the necessary actions if the ball break the brick.
     * Should only be called within step().
     *
     * @param ball
     */
    public void checkBrickCollisions(Ball ball) {
        for(Brick brick : bricks) {
            if(Breakout.isIntersecting(brick, ball)) {
                Powerup p = ball.bounce(brick, levelRoot, bricks);

                // If bounce results in the brick breaking it returns the corresponding powerup dropped (could be NONE)
                if(p != null) {
                    handlePowerup(p);
                    scoreboard.updateScore(brick.getDurability());
                    pointsAccumulated += brick.getDurability();
                }

                return;
            }
        }
    }

    /**
     * @return List of active powerups in level
     */
    public List<String> getActivePowerups() {
        List<String> activePowerups = new ArrayList<>();

        if(fastPaddleCheatIsActive) {
            activePowerups.add("Fast Paddle");
        }

        if(slowBallCheatIsActive) {
            activePowerups.add("Slow Ball");
        }

        if(extraBallIsActive) {
            activePowerups.add("Extra Ball");
        }

        if(longPaddleIsActive) {
            activePowerups.add("Long Paddle");
        }

        if(invisiblePaddleIsActive) {
            activePowerups.add("Invisible Paddle");
        }

        return activePowerups;
    }

    /**
     * Decreases number of remaining lives and resets ball positions when ball goes off screen.
     */
    public void loseLife() {
        this.numRemainingLives--;

        if(this.numRemainingLives == 0) {
            this.failed = true;
            return;
        }

        setupBalls();
    }

    /**
     * Resets entire level, including points scored from this level.
     */
    public void reset() {
        clear();
        setupChildNodes(levelRoot, sceneWidth, sceneHeight);

        scoreboard.updateScore(-1 * pointsAccumulated);
        this.pointsAccumulated = 0;
        this.numRemainingLives = this.numLives;


        this.fastPaddleCheatHasBeenUsed = false;
        this.slowBallCheatIsActive = false;
        this.longPaddleIsActive = false;
        this.extraBallIsActive = false;
        this.invisiblePaddleIsActive = false;
        this.fastPaddleCheatIsActive = false;
    }

    /**
     * Clears all Nodes created by this level.
     */
    public void clear() {
        levelRoot.getChildren().removeIf(child -> !(child instanceof Scoreboard));
    }

    /**
     * @return boolean indicating if player failed the level by losing all of their lives
     */
    public boolean didFail() {
        return this.failed;
    }

    /**
     * @return boolean indicating if player beat the level by breaking all the bricks
     */
    public boolean isFinished() {
        return this.bricks.isEmpty();
    }
}
