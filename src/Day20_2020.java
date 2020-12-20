import java.io.IOException;
import java.math.BigInteger;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

public class Day20_2020 {
    public static void main(String[] args) throws IOException {
        long start = System.currentTimeMillis();
        // read input
        List<String> inputLines = Files.readAllLines(Path.of("resources/input_day220.txt"));
        int endRuleIndex = inputLines.indexOf("");

        ArrayList<Tile> tiles = new ArrayList<>();
        int id = 0;
        String[] content = {"."};
        int i = 0;
        for (String s : inputLines) {
            if (s.startsWith("Tile")) {
                id = Integer.parseInt(s.substring(s.indexOf(" ") + 1, s.indexOf(":")));
                content = new String[endRuleIndex - 1];
                i = 0;
            } else if (s.equals("")) {
                tiles.add(new Tile(id, content));
                //System.out.println(tiles.get(tiles.size() - 1));
            } else {
                content[i] = s;
                i++;
            }
        }
        tiles.add(new Tile(id, content));
        //System.out.println(tiles.get(tiles.size() - 1));

        //System.out.println(tiles.size());
        int[] result = new int[4];
        int found = 0;
        Tile startTile = null;
        for (Tile t : tiles) {
            if (nbMatching(t, tiles) == 2) {
                result[found] = t.getID();
                if (found == 0) {
                    startTile = t;
                }
                found++;
            }
        }

        BigInteger p1 = BigInteger.ONE;
        for (int nb : result) {
            //System.out.println(nb);
            p1 = p1.multiply(BigInteger.valueOf(nb));
        }
        System.out.println("Part 1: " + p1);


        boolean orientationOK = false;
        while (!orientationOK) {
            ArrayList<Tile> matchingTiles = getMatchingTiles(startTile, tiles);
            if ((startTile.getMatching(matchingTiles.get(0))[0] == 1) && (startTile.getMatching(matchingTiles.get(1))[0] == 2)) {
                orientationOK = true;
            } else if ((startTile.getMatching(matchingTiles.get(0))[0] == 2) && (startTile.getMatching(matchingTiles.get(1))[0] == 1)) {
                orientationOK = true;
            } else {
                startTile.rotateClockwise();
            }
        }
        Puzzle puzzle = new Puzzle(tiles.size());
        puzzle.setPiece(0, 0, startTile);
        tiles.remove(startTile);
        ArrayList<Tile> matchingTiles;
        for (int r = 0; r < puzzle.getSize(); r++) {
            for (int c = 0; c < puzzle.getSize(); c++) {
                if (c < (puzzle.getSize() - 1)) {
                    Tile currentTile = puzzle.getPiece(r, c);
                    if (currentTile == null)  {
                        System.out.println("ERROR " + r + ", " + c);
                    }
                    matchingTiles = getMatchingTiles(currentTile, tiles);
                    for (Tile t : matchingTiles) {
                        int[] mb = currentTile.getMatching(t);
                        if (mb[0] == Tile.RIGHT) {
                            if (puzzle.setRightPiece(r, c, t)) {
                                tiles.remove(t);
                                break;
                            }
                        }
                    }
                } else { // last column => set first piece in the
                    Tile currentTile = puzzle.getPiece(r, 0);
                    matchingTiles = getMatchingTiles(currentTile, tiles);
                    for (Tile t : matchingTiles) {
                        int[] mb = currentTile.getMatching(t);
                        if (mb[0] == Tile.LOWER) {
                            if (puzzle.setLowerPiece(r, 0, t)) {
                                tiles.remove(t);
                                break;
                            }
                        }
                    }
                }
            }
        }

        //System.out.println(puzzle);
        // 1705 ; 21 monsters

        // create image from puzzle
        Tile picture = puzzle.getImage();
        String[] monster = new String[] {"                  # ",
                "#    ##    ##    ###",
                " #  #  #  #  #  #   "};
        ArrayList<int[]> monsterPos = new ArrayList<>();
        for (int si = 0; si < monster.length; si++) {
            int xpos = monster[si].indexOf("#");
            while (xpos >= 0) {
                monsterPos.add(new int[]{si, xpos});
                xpos = monster[si].indexOf("#", xpos+1);
            }
        }

        int count = 0;
        // try rotating
        for (int n = 0; n < 4; n++) {
            count = Math.max(count, picture.getNbMonsters(monsterPos, monster[0].length(), monster.length));
            picture.rotateClockwise();
        }
        picture.flipHorizontal();
        for (int n = 0; n < 4; n++) {
            count = Math.max(count, picture.getNbMonsters(monsterPos, monster[0].length(), monster.length));
            picture.rotateClockwise();
        }
        picture.flipHorizontal();
        picture.flipVertical();
        for (int n = 0; n < 4; n++) {
            count = Math.max(count, picture.getNbMonsters(monsterPos, monster[0].length(), monster.length));
            picture.rotateClockwise();
        }

        int resultP2 = picture.getNbHashes() - (count * monsterPos.size());
        System.out.println("Part 2: " + resultP2);
        System.out.println("Total time: " + (System.currentTimeMillis()-start));
    }

    public static int nbMatching(Tile t, ArrayList<Tile> tiles) {
        return getMatchingTiles(t, tiles).size();
    }

    public static ArrayList<Tile> getMatchingTiles(Tile t, ArrayList<Tile> tiles) {
        ArrayList<Tile> result = new ArrayList<>();
        for (Tile ot : tiles) {
            if (t.checkMatching(ot)) { result.add(ot); }
        }
        return result;
    }
}
