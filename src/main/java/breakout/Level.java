package breakout;

import javafx.scene.Group;
import javafx.scene.input.KeyCode;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;

public abstract class Level {
    public int levelNumber;
    public int numLives;
    public int numRemainingLives;
    public int pointsAccumulated;

    public boolean failed;

    public boolean fastPaddleCheatHasBeenUsed;
    public boolean fastPaddleCheatIsActive;
    public boolean slowBallCheatIsActive;
    public boolean invisiblePaddleIsActive;
    public boolean extraBallIsActive;
    public boolean longPaddleIsActive;

    public String blockConfigFile;
    public Set<Brick> bricks;
    public Group levelRoot;
    public Scoreboard scoreboard;

    public int sceneWidth;
    public int sceneHeight;

    public Level(int levelNumber, int lives, Group root, int sceneWidth, int sceneHeight, Scoreboard scoreboard) {
        this.levelNumber = levelNumber;
        this.numLives = this.numRemainingLives = lives;
        this.pointsAccumulated = 0;

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

    public abstract void handleKeyInput(KeyCode code);
    public abstract void step(double elapsedTime);

    public abstract void setupChildNodes(Group root, int sceneWidth, int sceneHeight);

    public abstract void setupBalls();

    public abstract void handlePowerup(Powerup powerup);

    public void setupBricks(Group root, int startX, int startY, int endX, int endY) {
        Scanner sc;
        File file;

        try {
            file = new File(blockConfigFile);
            sc = new Scanner(file);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
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

    public void checkBrickCollisions(Ball ball) {
        for(Brick brick : bricks) {
            if(Breakout.isIntersecting(brick, ball)) {
                Powerup p = ball.bounce(brick, levelRoot, bricks);

                if(p != null) {
                    handlePowerup(p);
                    scoreboard.updateScore(brick.getDurability());
                    pointsAccumulated += brick.getDurability();
                }

                return;
            }
        }
    }

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

    public void loseLife() {
        this.numRemainingLives--;

        if(this.numRemainingLives == 0) {
            this.failed = true;
            return;
        }

        setupBalls();
    }

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

    public void clear() {
        levelRoot.getChildren().removeIf(child -> !(child instanceof Scoreboard));
    }

    public boolean didFail() {
        return this.failed;
    }

    public boolean isFinished() {
        return this.bricks.isEmpty();
    }
}
