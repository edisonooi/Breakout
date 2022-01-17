package breakout;

import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.input.KeyCode;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;
import java.util.Set;

public abstract class Level {
    public int levelNumber;
    public int numLives;

    public String blockConfigFile;
    public Set<Brick> bricks;

    public Level(int levelNumber, int lives, String blockConfigFile) {
        this.levelNumber = levelNumber;
        this.numLives = lives;
        this.blockConfigFile = blockConfigFile;
    }

    public abstract void handleKeyInput(KeyCode code);

    public abstract void setupChildNodes(Group root, int sceneWidth, int sceneHeight) throws FileNotFoundException;

    public void setupBricks(Group root, int startX, int startY, int endX, int endY) throws FileNotFoundException {
        File file = new File(blockConfigFile);
        Scanner sc = new Scanner(file);

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

        sc = new Scanner(file);
        while(sc.hasNextLine()) {
            bricksInCurrentLine = sc.nextLine().split(" ");

            for(String brickType : bricksInCurrentLine) {
                Brick brick = new Brick(brickWidth, brickHeight, brickType);
                brick.setX(currentX);
                brick.setY(currentY);
                root.getChildren().add(brick);
                this.bricks.add(brick);

                currentX += brick.getWidth() + Breakout.BRICK_PADDING;
            }

            // Reset position trackers
            currentX = startX + Breakout.BRICK_PADDING;
            currentY += brickHeight + Breakout.BRICK_PADDING;
        }

        sc.close();
    }

    public void clear(Group root) {
        root.getChildren().removeIf(child -> !(child instanceof Scoreboard));
    }

    public Set<Brick> getBricks() {
        return this.bricks;
    }




}
