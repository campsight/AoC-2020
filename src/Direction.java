import java.util.HashMap;
import java.util.Map;

public enum Direction {
    UP("U", 0, 1),
    DOWN("D", 0, -1),
    RIGHT("R", 1, 0),
    LEFT("L", -1, 0);

    private static final Map<String, Direction> BY_LABEL = new HashMap<>();
    private static final Map<Integer, Direction> BY_X_EFFECT = new HashMap<>();
    private static final Map<Integer, Direction> BY_Y_EFFECT = new HashMap<>();

    static {
        for (Direction e : values()) {
            BY_LABEL.put(e.label, e);
            BY_X_EFFECT.put(e.x, e);
            BY_Y_EFFECT.put(e.y, e);
        }
    }

    public final String label;
    public final int x;
    public final int y;

    Direction(String label, int x, int y) {
        this.label = label;
        this.x = x;
        this.y = y;
    }

    public static Direction valueOfLabel(String label) {
        return BY_LABEL.get(label);
    }

    public static Direction valueOfX(int x) {
        return BY_X_EFFECT.get(x);
    }

    public static Direction valueOfY(int y) {
        return BY_Y_EFFECT.get(y);
    }
}
