import java.util.LinkedList;
import java.util.Objects;

public class Tegel {
    public static final int WHITE = 0;
    public static final int BLACK = 1;
    private int color = WHITE;

    public static final String NE = "ne";
    public static final String NW = "nw";
    public static final String SE = "se";
    public static final String SW = "sw";
    public static final String E = "e";
    public static final String W = "w";
    public static final int INE = 0;
    public static final int INW = 1;
    public static final int ISE = 2;
    public static final int ISW = 3;
    public static final int IE = 4;
    public static final int IW = 5;
    //public static final int[] REV_DIRS = new int[]{ISW, ISE, INW, INE, IW, IE};
    public static final int[] DIR_COL_DIFF = new int[]{1, -1, 1, -1, 2, -2};
    public static final int[] DIR_ROW_DIFF = new int[]{1, 1, -1, -1, 0, 0};

    private Tegel[] neighbours = new Tegel[6];

    private int r, c;

    public Tegel(int row, int col) {
        this.r = row;
        this.c = col;
    }

    public void flipColor() {
        color = 1-color;
    }

    public int getColor() {
        return color;
    }

    public int getNbBlackNeighbours() {
        int count = 0;
        for (int i = 0; i < 6; i++) {
            if ((neighbours[i] != null) && (neighbours[i].getColor() == BLACK)) count++;
        }
        return count;
    }

    public LinkedList<Tegel> getWhiteNeighbours(LinkedList<Tegel> tegelList) {
        LinkedList<Tegel> resultList = new LinkedList<>();
        for (int i = 0 ; i < 6; i++) {
            Tegel t = neighbours[i];
            if (t != null) {
                if (t.getColor() == WHITE) resultList.add(t);
            } else { // if no neighbour exists yet, create a white one
                t = new Tegel(r + DIR_ROW_DIFF[i], c + DIR_COL_DIFF[i]);
                neighbours[i] = t;
                t.checkSurroundingTegels(tegelList);
                resultList.add(t);
                tegelList.add(t);
            }
        }
        return resultList;
    }

    public void checkSurroundingTegels(LinkedList<Tegel> tegelList) {
        if (tegelList == null) tegelList = new LinkedList<>();
        boolean alreadyNB;
        for (Tegel t : tegelList) {
            alreadyNB = false;
            for (int i = 0; i < 6; i++) {
                if ((!(neighbours[i] == null)) && neighbours[i].equals(t)) {
                    alreadyNB = true;
                    break;
                }
            }
            if (! alreadyNB) {
                int row = t.getRow();
                int col = t.getCol();
                if (row == r) {
                    if (col == (c-2)) { // t West of this
                        neighbours[IW] = t;
                        t.setNeighbour(this, IE);
                    } else if (col == (c+2)) { // t East of this
                        neighbours[IE] = t;
                        t.setNeighbour(this, IW);
                    }
                } else if (row == (r+1)) {
                    if (col == (c-1)) { // t NW of this
                        neighbours[INW] = t;
                        t.setNeighbour(this, ISE);
                    } else if (col == (c+1)) { // t NE of this
                        neighbours[INE] = t;
                        t.setNeighbour(this, ISW);
                    }
                } else if (row == (r-1)) {
                    if (col == (c-1)) { // t SW of this
                        neighbours[ISW] = t;
                        t.setNeighbour(this, INE);
                    } else if (col == (c+1)) { // t SE of this
                        neighbours[ISE] = t;
                        t.setNeighbour(this, INW);
                    }
                }
            }
        }
    }

    public Tegel createNeighbour(String direction, LinkedList<Tegel> tegelList) {
        int row = 0;
        int col = 0;
        int dir = 0;
        Tegel resultTegel;
        switch (direction) {
            case SE -> {
                row = r - 1;
                col = c + 1;
                dir = ISE;
            }
            case SW -> {
                row = r - 1;
                col = c - 1;
                dir = ISW;
            }
            case E -> {
                row = r;
                col = c + 2;
                dir = IE;
            }
            case W -> {
                row = r;
                col = c - 2;
                dir = IW;
            }
            case NE -> {
                row = r + 1;
                col = c + 1;
                dir = INE;
            }
            case NW -> {
                row = r + 1;
                col = c - 1;
                dir = INW;
            }
        }
        resultTegel = neighbours[dir];
        if (resultTegel == null) {
            resultTegel = new Tegel(row, col);
            neighbours[dir] = resultTegel;
            resultTegel.checkSurroundingTegels(tegelList);
        }
        if ((!(tegelList == null)) && (!tegelList.contains(resultTegel))) tegelList.add(resultTegel);

        return resultTegel;
    }

    public int getRow() {
        return r;
    }

    public int getCol() {
        return c;
    }

    public void setNeighbour(Tegel tile, int direction) {
        neighbours[direction] = tile;
    }

    @Override
    public String toString() {
        return "r=" + r +
                ", c=" + c + "|" + (color==BLACK) + "||";
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Tegel tegel = (Tegel) o;
        return r == tegel.r && c == tegel.c;
    }

    @Override
    public int hashCode() {
        return Objects.hash(r, c);
    }
}
