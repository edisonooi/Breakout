package breakout;

import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.input.KeyCode;

import java.util.Set;

public abstract class Level {
    private int levelNumber;
    private int numLives;

    private String blockConfigFile;
    private Set<Brick> bricks;

    public Level(int levelNumber, int lives, String blockConfigFile) {
        this.levelNumber = levelNumber;
        this.numLives = lives;
        this.blockConfigFile = blockConfigFile;
    }

    public abstract void setupChildNodes(Group root, int sceneWidth, int sceneHeight);

    public abstract void handleKeyInput(KeyCode code);

    public void clear(Group root) {
        root.getChildren().removeIf(child -> !(child instanceof Scoreboard));
    }

    public Set<Brick> getBricks() {
        return this.bricks;
    }




}
