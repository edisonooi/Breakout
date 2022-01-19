package breakout;

import javafx.scene.Node;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;

import java.util.*;

public class Scoreboard extends Text {
    private int score;
    private List<String> activePowerups;

    private String scoreboardText;

    public Scoreboard(int width, int height, String startingText) {
        super(width, height, startingText);
        this.score = 0;
        this.activePowerups = new ArrayList<>();
        this.scoreboardText = "\nLevel:\nScore:\nLives:\nActive Powerups:\n";
        setText(scoreboardText);
        setWrappingWidth(width);
        setFont(new Font(15));
        setFill(Color.WHITE);
    }

    public void refreshText(Level currentLevel) {
        activePowerups = currentLevel.getActivePowerups();

        StringBuilder powerupString = new StringBuilder("");

        for(String powerup : activePowerups) {
            powerupString.append(powerup);
            powerupString.append(", ");
        }

        scoreboardText = String.format("\nLevel: %d\nScore: %d\nLives: %d\nActive Powerups: %s\n",
                currentLevel.levelNumber, score, currentLevel.numRemainingLives, powerupString);
        setText(scoreboardText);
    }

    public void updateScore(int pointsToAdd) {
        score += pointsToAdd;
    }

    public int getScore() {
        return this.score;
    }
}
