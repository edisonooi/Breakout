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

    private int durability;
    private int remainingDurability;

    private Powerup powerup;

    public Brick(int width, int height, String type) {
        super(width, height);

        initializeDurablity(type);
        this.setFill(colorMap.get(this.durability));

        initializePowerup(type);
        setPowerupStyle(powerup);
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



}
