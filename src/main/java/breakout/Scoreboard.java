package breakout;

import javafx.scene.Node;
import java.util.*;

public class Scoreboard extends Node {
    private int score;
    private int livesRemaining;
    private int levelNumber;
    private Set<String> activePowerups;

    public Scoreboard() {
        this.score = 0;
        this.livesRemaining = 3;
        this.levelNumber = 1;
        this.activePowerups = new HashSet<>();
    }






}
