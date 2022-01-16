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

    private Paint color;

    public Brick(int width, int height, String type) {
        this.width = width;
        this.height = height;

        switch (type) {
            case "1":
                durability = 1;
                break;
            case "2":
                durability = 2;
                break;
            case "3":
                durability = 3;
                break;
            case "4":
                durability = 4;
                break;
            case "5":
                durability = 5;
                break;
            case "L":
                durability = 1;
                break;
            case "I":
                durability = 1;
                break;
            case "B":
                durability = 1;
                break;
            default:
                durability = 1;
        }

        this.color = colorMap.get(this.durability);
        this.remainingDurability = this.durability;


    }




}
