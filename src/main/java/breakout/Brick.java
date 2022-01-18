package breakout;

import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Rectangle;

import java.util.Map;

public class Brick extends Rectangle {

    // Maps a brick's durability to its color
    private static final Map<Integer, Color> colorMap = Map.of(
            1, Color.WHITE,
            2, Color.YELLOW,
            3, Color.ORANGE,
            4, Color.RED,
            5, Color.PURPLE
    );

    // Instance variables
    private int durability;
    private int remainingDurability;

    private Powerup powerup;

    /**
     * Class constructor
     * @param width width of brick
     * @param height height of brick
     * @param type character representing type of brick
     */
    public Brick(double width, double height, String type) {
        super(width, height);

        initializeDurablity(type);
        this.setFill(colorMap.get(this.durability));

        initializePowerup(type);
        setPowerupStyle(powerup);
    }

    /**
     * Initialize original and remaining durability of brick based on its type
     * @param type character representing type of brick
     */
    private void initializeDurablity(String type) {
        switch (type) {
            case "1" -> durability = remainingDurability = 1;
            case "2" -> durability = remainingDurability = 2;
            case "3" -> durability = remainingDurability = 3;
            case "4" -> durability = remainingDurability = 4;
            case "5" -> durability = remainingDurability = 5;
            case "L" -> durability = remainingDurability = 1;
            case "I" -> durability = remainingDurability = 1;
            case "B" -> durability = remainingDurability = 1;
            default -> durability = remainingDurability = 1;
        }
    }

    /**
     * Assign a powerup to brick based on its type given by brick config file
     * @param type character representing type of brick
     */
    private void initializePowerup(String type) {
        switch (type) {
            case "L" -> powerup = Powerup.LONG_PADDLE;
            case "I" -> powerup = Powerup.INVISIBLE_PADDLE;
            case "B" -> powerup = Powerup.EXTRA_BALL;
            default -> powerup = Powerup.NONE;
        }
    }

    /**
     * Draw a stroke around brick if it is a powerup brick
     * @param powerup the powerup that this brick activates when broken
     */
    private void setPowerupStyle(Powerup powerup) {
        if(powerup == Powerup.NONE) {
            return;
        }

        switch (powerup) {
            case LONG_PADDLE -> this.setStroke(Color.GREEN);
            case INVISIBLE_PADDLE -> this.setStroke(Color.BROWN);
            case EXTRA_BALL -> this.setStroke(Color.BLUE);
        }

        this.setStrokeWidth(this.getWidth() / 10);
    }

    /**
     * Modify brick properties if it has been hit by ball
     * @return boolean indicating if brick has been broken
     */
    public boolean hit() {
        this.remainingDurability--;

        // Brick has been broken
        if(remainingDurability == 0) {
            return true;
        }

        this.setFill(colorMap.get(remainingDurability));
        return false;
    }

    /**
     * Get powerup assigned to this brick
     * @return brick's powerup
     */
    public Powerup getPowerup() {
        return this.powerup;
    }

    public int getDurability() {
        return this.durability;
    }
}
