package breakout;

import java.util.*;

public class Scoreboard {
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
