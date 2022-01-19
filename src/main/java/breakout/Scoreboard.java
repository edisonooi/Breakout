package breakout;

import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;

import java.util.*;

/**
 * This class is a modified Text object that displays the current level number, game score, number of lives remaining,
 * and a list of active powerups. This class also keeps track of the total score in a particular Breakout instance.
 *
 * A Level object must exist in order for the Scoreboard to display useful information. Once the Scoreboard object has
 * been instantiated, the current Level being played should call refreshText() each frame.
 *
 * @author Edison Ooi
 */
public class Scoreboard extends Text {
    // Total cumulative score of Breakout game
    private int score;

    // Text that is to be displayed on the scoreboard
    private String scoreboardText;

    /**
     * Class constructor. Initializes all instance variables and sets up desired properties of overarching Text object
     *
     * @param width width, in pixels, of scoreboard region on main scene
     * @param height height, in pixels, of scoreboard region on main scene
     */
    public Scoreboard(int width, int height) {
        super(width, height, "Placeholder");
        this.score = 0;
        this.scoreboardText = "\nLevel:\nScore:\nLives:\nActive Powerups:\n";
        setText(scoreboardText);
        setWrappingWidth(width);
        setFont(new Font(15));
        setFill(Color.WHITE);
    }

    /**
     * Updates this Scoreboard's text based on the information of a level
     *
     * @param currentLevel the current level being played
     */
    public void refreshText(Level currentLevel) {
        // Build a String representing all active powerups in current level
        List<String> activePowerups = currentLevel.getActivePowerups();

        StringBuilder powerupString = new StringBuilder("");

        for(String powerup : activePowerups) {
            powerupString.append(powerup);
            powerupString.append(", ");
        }

        scoreboardText = String.format("\nLevel: %d\nScore: %d\nLives: %d\nActive Powerups: %s\n",
                currentLevel.levelNumber, score, currentLevel.numRemainingLives, powerupString);
        setText(scoreboardText);
    }

    /**
     * Adds a certain number of points to the current game score
     *
     * @param pointsToAdd
     */
    public void updateScore(int pointsToAdd) {
        score += pointsToAdd;
    }

    /**
     * @return Number of cumulative points scored in current Breakout game
     */
    public int getScore() {
        return this.score;
    }
}
