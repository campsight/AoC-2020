

public class Ship {
    public static final String NORTH = "N";
    public static final String EAST = "E";
    public static final String SOUTH = "S";
    public static final String WEST = "W";
    public static final String LEFT = "L";
    public static final String RIGHT = "R";
    public static final String FORWARD = "F";
    public static final String[] DIRECTIONS = {NORTH, EAST, SOUTH, WEST};
    private int direction;
    private int x;
    private int y;

    public Ship() {
        this.direction = 1;
        this.x = 0;
        this.y = 0;
    }

    public Ship(int direction) {
        this.direction = direction;
        this.x = 0;
        this.y = 0;
    }

    public int getDirection() {
        return direction;
    }

    public void setDirection(int direction) {
        this.direction = direction;
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    public int getManhattanDist() {
        return Math.abs(getX()) + Math.abs(getY());
    }

    public void navigate(String instruction) {
        String command = instruction.substring(0,1);
        int value = Integer.parseInt(instruction.substring(1));
        switch (command) {
            case NORTH -> this.y += value;
            case EAST -> this.x += value;
            case SOUTH -> this.y -= value;
            case WEST -> this.x -= value;
            case LEFT -> this.direction = (this.direction - value / 90 + 4) % 4;
            case RIGHT -> this.direction = (this.direction + value / 90) % 4;
            case FORWARD -> moveDirection(this.direction, value);
            default -> throw new IllegalStateException("Unexpected value: " + command);
        }
    }

    private void moveDirection(int dir, int dist) {
        switch (dir) {
            case 0 -> this.y += dist;
            case 1 -> this.x += dist;
            case 2 -> this.y -= dist;
            case 3 -> this.x -= dist;
        }
    }
}
