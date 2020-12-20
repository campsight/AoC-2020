import java.util.ArrayList;
import java.util.Arrays;

public class Tile {
    public static final int UPPER = 0;
    public static final int RIGHT = 1;
    public static final int LOWER = 2;
    public static final int LEFT = 3;
    public static final int REVUPPER = 4;
    public static final int REVRIGHT = 5;
    public static final int REVLOWER = 6;
    public static final int REVLEFT = 7;

    private int ID;
    private char[][] content;
    private String leftBorder, rightBorder, upperBorder, lowerBorder, rLeftBorder, rRightBorder, rUpperBorder, rLowerBorder;

    public Tile(int ID, String[] content) {
        this.ID = ID;
        int width = content[0].length();
        int height = content.length;
        this.content = new char[height][width];
        upperBorder = content[0];
        rUpperBorder = (new StringBuilder(upperBorder)).reverse().toString();
        lowerBorder = content[height-1];
        rLowerBorder = (new StringBuilder(lowerBorder)).reverse().toString();
        StringBuilder sbLeft = new StringBuilder();
        StringBuilder sbRight = new StringBuilder();
        for (int i = 0; i < height; i++) {
            sbLeft.append(content[i].substring(0,1));
            sbRight.append(content[i].substring(width-1));
            this.content[i] = content[i].toCharArray();
        }
        leftBorder = sbLeft.toString();
        rLeftBorder = sbLeft.reverse().toString();
        rightBorder = sbRight.toString();
        rRightBorder = sbRight.reverse().toString();
    }

    public String getInnerRow(int i) {
        if (i >= content.length) { return null; }
        return (new String(content[i])).substring(1, content.length-1);
    }

    public void resetBorders() {
        int s = content.length;
        upperBorder = new String(content[0]);
        rUpperBorder = (new StringBuilder(upperBorder)).reverse().toString();
        lowerBorder = new String(content[s-1]);
        rLowerBorder = (new StringBuilder(lowerBorder)).reverse().toString();
        StringBuilder sbLeft = new StringBuilder();
        StringBuilder sbRight = new StringBuilder();
        for (int i = 0; i < s; i++) {
            sbLeft.append(content[i][0]);
            sbRight.append(content[i][s-1]);
        }
        leftBorder = sbLeft.toString();
        rLeftBorder = sbLeft.reverse().toString();
        rightBorder = sbRight.toString();
        rRightBorder = sbRight.reverse().toString();
    }

    public boolean checkMatching(Tile otherTile) {
        if (this.equals(otherTile)) { return false; }
        return (getMatching(otherTile)[0] > -1) ? true : false;
    }

    public int[] getMatching(Tile otherTile) {
        String[] myBorders = getBorders();
        String[] otherBorders = otherTile.getBorders();
        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 8; j++) {
                if (myBorders[i].equals(otherBorders[j])) return new int[]{i, j};
            }
        }
        return new int[]{-1};
    }

    public void rotateClockwise() {
        char[][] rotated = new char[content.length][content.length];
        int z = content.length - 1;
        for (int i = 0; i < content.length; i++) {
            for (int j = 0; j < content.length; j++) {
                rotated[i][j] = content[z-j][i];
            }
        }
        content = rotated;
        resetBorders();
    }

    public void flipHorizontal() {
        // horizontal means left to right
        for (int i = 0; i < content.length; i++) {
            content[i] = (new StringBuilder(new String(content[i]))).reverse().toString().toCharArray();
        }
        resetBorders();
    }

    public void flipVertical() {
        // vertical means upper side to bottom
        int z = content.length - 1;
        char[][] flipped = new char[content.length][content.length];
        for (int i = 0; i < content.length; i++) {
            System.arraycopy(content[z - i], 0, flipped[i], 0, content.length);
        }
        content = flipped;
        resetBorders();
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder( "Tile{" +
                "ID=" + ID +
                ", leftBorder=" + leftBorder +
                ", rightBorder=" + rightBorder +
                ", upperBorder=" + upperBorder +
                ", lowerBorder=" + lowerBorder +
                ", rLeftBorder=" + rLeftBorder +
                ", rRightBorder=" + rRightBorder +
                ", rUpperBorder=" + rUpperBorder +
                ", rLowerBorder=" + rLowerBorder +
                "}\n");
        for (char[] chars : content) {
            sb.append(new String(chars));
            sb.append("\n");
        }
        return sb.toString();
    }

    public int getSize() {
        return content.length;
    }

    public boolean isSquare() {
        return content.length == content[0].length;
    }

    public int getNbMonsters(ArrayList<int[]> monster, int width, int height) {
        int count = 0;
        for (int r = 0; r <= (getSize() - height); r++) {
            for (int c = 0; c <= (getSize() - width); c++) {
                boolean monsterFound = true;
                for (int[] pos : monster) {
                    if (!(content[r+pos[0]][c+pos[1]] == '#')) {
                        monsterFound = false;
                        break;
                    }
                }
                if (monsterFound) { count++; }
            }
        }
        return count;
    }

    public int getNbHashes() {
        int count = 0;
        for (int r = 0; r < getSize(); r++) {
            for (int c = 0; c < getSize(); c++) {
                if (content[r][c] == '#') { count++; }
            }
        }
        return count;
    }

    public String[] getBorders() {
        String[] value = {upperBorder, rightBorder, lowerBorder, leftBorder,
                        rUpperBorder, rRightBorder, rLowerBorder, rLeftBorder};
        return value;
    }

    public int getID() {
        return ID;
    }

}
