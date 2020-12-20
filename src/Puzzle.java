import java.util.ArrayList;

public class Puzzle {
    private Tile[][] tiles;
    private int size;

    public Puzzle(int nbTiles) {
        size = (int) Math.sqrt(nbTiles);
        tiles = new Tile[size][size];
    }

    public void setPiece(int row, int column, Tile piece) {
        tiles[row][column] = piece;
    }

    public Tile getPiece(int row, int column) {
        return tiles[row][column];
    }

    public boolean setRightPiece(int row, int column, Tile piece) {
        Tile tile = tiles[row][column];
        int[] matches = tile.getMatching(piece);
        if (matches[0] != Tile.RIGHT) {
            System.out.println("ERROR Matching " + tile + " with " + piece);
            return false;
        }
        switch(matches[1]) {
            // case Tile.LEFT -> { tiles[row][column+1] = piece; } LEFT matches the RIGHT side of the piece next to it: already OK
            case Tile.LOWER -> { piece.rotateClockwise(); }
            case Tile.REVRIGHT -> { piece.rotateClockwise(); piece.rotateClockwise(); }
            case Tile.REVUPPER -> { piece.rotateClockwise(); piece.rotateClockwise(); piece.rotateClockwise(); }
            case Tile.REVLEFT -> { piece.flipVertical(); }
            case Tile.REVLOWER -> { piece.rotateClockwise(); piece.flipVertical();}
            case Tile.RIGHT -> { piece.rotateClockwise(); piece.rotateClockwise(); piece.flipVertical();}
            case Tile.UPPER -> { piece.rotateClockwise(); piece.rotateClockwise(); piece.rotateClockwise(); piece.flipVertical();}
        }
        tiles[row][column+1] = piece;
        if (row > 0) {
            Tile upperTile = tiles[row-1][column+1];
            if (upperTile != null) {
                int[] m = upperTile.getMatching(piece);
                if (m[0] != Tile.LOWER) {
                    System.out.println("ERROR setRightPiece (" + row + ", " + column + "): upper piece (" +
                            upperTile.getID() + ") not matching inserted piece (" + piece.getID() + ") on LOWER");
                    return false;
                } else if (m[1] != Tile.UPPER) {
                    System.out.println("ERROR setRightPiece (" + row + ", " + column + "): lower piece (" +
                            upperTile.getID() + ") not matching inserted piece (" + piece.getID() + ") on UPPER");
                    return false;
                }
            }
        }
        return true;
    }

    public boolean setLowerPiece(int row, int column, Tile piece) {
        Tile tile = tiles[row][column];
        int[] matches = tile.getMatching(piece);
        if (matches[0] != Tile.LOWER) {
            System.out.println("ERROR Matching " + tile + " with " + piece);
            return false;
        }
        switch(matches[1]) {
            // case Tile.UPPER -> { tiles[row][column+1] = piece; } UPPER matches the LOWER side of the piece above it: already OK
            case Tile.REVLEFT -> { piece.rotateClockwise(); }
            case Tile.REVLOWER -> { piece.rotateClockwise(); piece.rotateClockwise(); }
            case Tile.RIGHT -> { piece.rotateClockwise(); piece.rotateClockwise(); piece.rotateClockwise(); }
            case Tile.REVUPPER -> { piece.flipHorizontal(); }
            case Tile.LEFT -> { piece.rotateClockwise(); piece.flipHorizontal();}
            case Tile.LOWER -> { piece.rotateClockwise(); piece.rotateClockwise(); piece.flipHorizontal();}
            case Tile.REVRIGHT -> { piece.rotateClockwise(); piece.rotateClockwise(); piece.rotateClockwise(); piece.flipHorizontal();}
        }
        tiles[row+1][column] = piece;
        if (column > 0) {
            Tile leftTile = tiles[row+1][column-1];
            if (leftTile != null) {
                int[] m = leftTile.getMatching(piece);
                if (m[0] != Tile.RIGHT) {
                    System.out.println("ERROR setLowerPiece (" + row + ", " + column + "): left piece (" +
                            leftTile.getID() + ") not matching inserted piece (" + piece.getID() + ") on RIGHT");
                    return false;
                } else if (m[1] != Tile.LEFT) {
                    System.out.println("ERROR setLowerPiece (" + row + ", " + column + "): right piece (" +
                            leftTile.getID() + ") not matching inserted piece (" + piece.getID() + ") on LEFT");
                    return false;
                }
            }
        }
        return true;
    }

    public int getSize() {
        return size;
    }

    public Tile getImage() {
        int tileSize = tiles[0][0].getSize() - 2;
        String[] newContent = new String[tileSize * this.size];
        // outer loop: all rows of puzzle pieces
        for (int r = 0; r < this.size; r++) {
            StringBuilder[] sbArray = new StringBuilder[tileSize];
            for (int i = 0; i < sbArray.length; i++) { sbArray[i] = new StringBuilder(); }

            // inner loop: all columns within one row
            for (int c = 0; c < this.size; c++) {
                Tile t = tiles[r][c];
                for (int i = 0; i< tileSize; i++) {
                    sbArray[i].append(t.getInnerRow(i+1));
                }
            }

            for (int i = 0; i < sbArray.length; i++) {
                newContent[(r*tileSize)+i] = sbArray[i].toString();
            }
        }
        return new Tile(0, newContent);
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        for (int r = 0; r < size; r++) {
            for (int c = 0; c < size; c++) {
                if (tiles[r][c] != null) {
                    sb.append(tiles[r][c].getID()).append(" ");
                } else {
                    sb.append("     ");
                }
            }
            sb.append("\n");
        }
        return sb.toString();
    }
}
