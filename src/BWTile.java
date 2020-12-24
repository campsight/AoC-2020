public class BWTile {
    public static final int WHITE = 0;
    public static final int BLACK = 1;
    private int color = WHITE;

    public static final String NE = "ne";
    public static final String NW = "nw";
    public static final String SE = "se";
    public static final String SW = "sw";
    public static final String E = "e";
    public static final String W = "w";

    private int r, c;

    public BWTile(int row, int col) {
        this.r = row;
        this.c = col;
    }

    public void flipColor() {
        color = 1-color;
    }

    public int getColor() {
        return color;
    }

    public BWTile createNeighbour(String direction) {
        int row = 0;
        int col = 0;
        switch (direction) {
            case SE -> {
                row = r - 1;
                col = c + 1;
            }
            case SW -> {
                row = r - 1;
                col = c - 1;
            }
            case E -> {
                row = r;
                col = c + 2;
            }
            case W -> {
                row = r;
                col = c - 2;
            }
            case NE -> {
                row = r + 1;
                col = c + 1;
            }
            case NW -> {
                row = r + 1;
                col = c - 1;
            }
        }
        return new BWTile(row, col);
    }

    public int getRow() {
        return r;
    }

    public int getCol() {
        return c;
    }

    @Override
    public String toString() {
        return "r=" + r +
                ", c=" + c + "|";
    }
}
