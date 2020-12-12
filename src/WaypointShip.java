

public class WaypointShip {
    public static final String NORTH = "N";
    public static final String EAST = "E";
    public static final String SOUTH = "S";
    public static final String WEST = "W";
    public static final String LEFT = "L";
    public static final String RIGHT = "R";
    public static final String FORWARD = "F";
    public static final String[] DIRECTIONS = {NORTH, EAST, SOUTH, WEST};
    private int direction;
    private int wp_x;
    private int wp_y;
    private int x;
    private int y;

    public WaypointShip() {
        this.direction = 1;
        this.x = 0;
        this.y = 0;
        this.wp_x = 10;
        this.wp_y = 1;
    }

    public WaypointShip(int direction, int wp_x, int wp_y, int x, int y) {
        this.direction = direction;
        this.wp_x = wp_x;
        this.wp_y = wp_y;
        this.x = x;
        this.y = y;
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

    public int getWp_x() {
        return wp_x;
    }

    public void setWp_x(int wp_x) {
        this.wp_x = wp_x;
    }

    public int getWp_y() {
        return wp_y;
    }

    public void setWp_y(int wp_y) {
        this.wp_y = wp_y;
    }

    public int getManhattanDist() {
        return Math.abs(getX()) + Math.abs(getY());
    }

    public void navigate(String instruction) {
        String command = instruction.substring(0,1);
        int value = Integer.parseInt(instruction.substring(1));
        switch(command) {
            case NORTH:
                this.wp_y += value;
                break;
            case EAST:
                this.wp_x += value;
                break;
            case SOUTH:
                this.wp_y -= value;
                break;
            case WEST:
                this.wp_x -= value;
                break;
            case LEFT:
                turnLeft(value/90);
                break;
            case RIGHT:
                turnRight(value/90);
                break;
            case FORWARD:
                this.x += value * wp_x;
                this.y += value * wp_y;
                break;
            default:
                throw new IllegalStateException("Unexpected value: " + command);
        }
    }

    private void turnLeft(int angle) {
        int buffer = this.wp_x;
        switch (angle) {
            case 1 -> {
                this.wp_x = -this.wp_y;
                this.wp_y = buffer;
            }
            case 2 -> {
                this.wp_x = -this.wp_x;
                this.wp_y = -this.wp_y;
            }
            case 3 -> {
                this.wp_x = this.wp_y;
                this.wp_y = -buffer;
            }
        }
    }

    private void turnRight(int angle) {
        int buffer = this.wp_x;
        switch (angle) {
            case 1 -> {
                this.wp_x = this.wp_y;
                this.wp_y = -buffer;
            }
            case 2 -> {
                this.wp_x = -this.wp_x;
                this.wp_y = -this.wp_y;
            }
            case 3 -> {
                this.wp_x = -this.wp_y;
                this.wp_y = buffer;
            }
        }
    }

    @Override
    public String toString() {
        StringBuilder sb  = new StringBuilder();
        sb.append("Ship at position (").append(this.x).append(", ").append(this.y).append("), ");
        sb.append("WP at position (").append(this.wp_x).append(", ").append(this.wp_y).append(").");
        return sb.toString();
    }
}
