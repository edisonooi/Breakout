package breakout;

import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Rectangle;

import java.util.Map;

public class Brick extends Rectangle {

    private static Map<Integer, Color> colorMap = Map.of(
            1, Color.WHITE,
            2, Color.YELLOW,
            3, Color.ORANGE,
            4, Color.RED,
            5, Color.PURPLE
    );

    private int width;
    private int height;

    private int durability;
    private int remainingDurability;

    private Powerup powerup;

    private Paint color;

    public Brick(int width, int height, String type) {
        this.width = width;
        this.height = height;

        initializeDurablity(type);
        initializePowerup(type);

        this.color = colorMap.get(this.durability);
    }

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

    private void initializePowerup(String type) {
        switch (type) {
            case "L" -> powerup = Powerup.LONG_PADDLE;
            case "I" -> powerup = Powerup.INVISIBLE_PADDLE;
            case "B" -> powerup = Powerup.EXTRA_BALL;
            default -> powerup = Powerup.NONE;
        }
    }


}
