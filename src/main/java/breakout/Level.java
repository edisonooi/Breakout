package breakout;

import javafx.scene.Group;
import javafx.scene.input.KeyCode;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.HashSet;
import java.util.Scanner;
import java.util.Set;

public abstract class Level {
    public int levelNumber;
    public int numLives;

    public String blockConfigFile;
    public Set<Brick> bricks;
    public Group levelRoot;

    public int sceneWidth;
    public int sceneHeight;

    public Level(int levelNumber, int lives, Group root, int sceneWidth, int sceneHeight) {
        this.levelNumber = levelNumber;
        this.numLives = lives;
        this.blockConfigFile = "src/main/resources/level" + levelNumber + "config.txt";
        this.bricks = new HashSet<>();
        this.levelRoot = root;
        this.sceneWidth = sceneWidth;
        this.sceneHeight = sceneHeight;

        setupChildNodes(root, sceneWidth, sceneHeight);
    }

    public abstract void handleKeyInput(KeyCode code);
    public abstract void step(double elapsedTime);

    public abstract void setupChildNodes(Group root, int sceneWidth, int sceneHeight);

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
                ball.bounce(brick, levelRoot, bricks);
                return;
            }
        }
    }

    public void checkWallCollisions(Ball ball) {
        if(ball.getCenterX() - ball.getRadius() <= 0 ||
                ball.getCenterX() + ball.getRadius() >= sceneWidth) {
            ball.setxVelocity(ball.getxVelocity() * -1);
        }

        if(ball.getCenterY() - ball.getRadius() <= 0 ||
                ball.getCenterY() + ball.getRadius() >= sceneHeight) {
            ball.setyVelocity(ball.getyVelocity() * -1);
        }
    }

    public void clear(Group root) {
        root.getChildren().removeIf(child -> !(child instanceof Scoreboard));
    }

    public Set<Brick> getBricks() {
        return this.bricks;
    }




}
