import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

public class Day24_2020 {
    private static LinkedList<BWTile> tiles = new LinkedList<>();
    private static BWTile startTile;

    public static void main(String[] args) throws IOException {
        // read input
        List<String> inputLines = Files.readAllLines(Path.of("resources/input_day224.txt"));
        //List<String> inputLines = Files.readAllLines(Path.of("resources/nele_day24.txt"));

        startTile = new BWTile(0,0);
        tiles.add(startTile);
        for(String line : inputLines) {
            flipTile(line);
        }

        //int count = blackTileCount();

        System.out.println("Part 1: " + tiles.stream().filter(x -> x.getColor() == BWTile.BLACK).count());

        // only keep the black tiles
        tiles = tiles.stream().filter(x -> x.getColor() == BWTile.BLACK).collect(Collectors.toCollection(LinkedList::new));

        //System.out.println(tiles);
        for (int i = 0; i <100; i++) {
            processDay();
            System.out.println("Day " + (i+1) + ": " + tiles.size());
        }

        System.out.println("Part 2: " + tiles.size());


    }

    private static void processDay() {
        LinkedList<BWTile> newTiles = new LinkedList<>();
        LinkedList<BWTile> whiteTileList = new LinkedList<>();
        for (BWTile tile : tiles) {
            // determine if black tile stays black
            int nbAdj = nbAdjacentBlackTiles(tile);
            if ((nbAdj == 1) || (nbAdj == 2)) {
                newTiles.add(tile);
            }

            // add white tiles (which were not added yet)
            int rb = tile.getRow();
            int cb = tile.getCol();
            BWTile whiteTile, existingBlack, existingWhite;
            whiteTile = new BWTile(rb+1, cb-1); // NW
            existingBlack = getTile(whiteTile, tiles);
            if (existingBlack.equals(whiteTile)) {
                existingWhite = getTile(whiteTile, whiteTileList);
                if (existingWhite.equals(whiteTile)) {
                    whiteTileList.add(existingWhite);
                }
            }
            whiteTile = new BWTile(rb+1, cb+1); // NE
            existingBlack = getTile(whiteTile, tiles);
            if (existingBlack.equals(whiteTile)) {
                existingWhite = getTile(whiteTile, whiteTileList);
                if (existingWhite.equals(whiteTile)) {
                    whiteTileList.add(existingWhite);
                }
            }
            whiteTile = new BWTile(rb, cb-2); // W
            existingBlack = getTile(whiteTile, tiles);
            if (existingBlack.equals(whiteTile)) {
                existingWhite = getTile(whiteTile, whiteTileList);
                if (existingWhite.equals(whiteTile)) {
                    whiteTileList.add(existingWhite);
                }
            }
            whiteTile = new BWTile(rb, cb+2); // E
            existingBlack = getTile(whiteTile, tiles);
            if (existingBlack.equals(whiteTile)) {
                existingWhite = getTile(whiteTile, whiteTileList);
                if (existingWhite.equals(whiteTile)) {
                    whiteTileList.add(existingWhite);
                }
            }
            whiteTile = new BWTile(rb-1, cb-1); // SW
            existingBlack = getTile(whiteTile, tiles);
            if (existingBlack.equals(whiteTile)) {
                existingWhite = getTile(whiteTile, whiteTileList);
                if (existingWhite.equals(whiteTile)) {
                    whiteTileList.add(existingWhite);
                }
            }
            whiteTile = new BWTile(rb-1, cb+1); // SE
            existingBlack = getTile(whiteTile, tiles);
            if (existingBlack.equals(whiteTile)) {
                existingWhite = getTile(whiteTile, whiteTileList);
                if (existingWhite.equals(whiteTile)) {
                    whiteTileList.add(existingWhite);
                }
            }

        }
        //System.out.println(newTiles.size());
        for (BWTile tile : whiteTileList) {
            int nbAdj = nbAdjacentBlackTiles(tile);
            if (nbAdj == 2) {
                tile.flipColor();
                newTiles.add(tile);
            }
        }
        tiles = newTiles;
    }

    private static int nbAdjacentBlackTiles(BWTile tile) {
        int row = tile.getRow();
        int col = tile.getCol();
        int count = 0;
        for (BWTile t : tiles) {
            int r = t.getRow();
            int c = t.getCol();
            if ((r == row) && ((c == col-2) || (c == col+2))) count++;
            if (((r == row+1) || (r == row-1)) && ((c == col-1) || (c == col+1))) count++;
        }
        return count;
    }

    private static void flipTile(String line) {
        ArrayList<String> instructions = splitInstructions(line);
        //System.out.println("Instruction: " + instructions);
        instructions = simplifyInstructions(instructions);
        //System.out.println("Simplified instruction: " + instructions);

        BWTile previousTile = startTile;
        for (String instruction : instructions) {
            previousTile = previousTile.createNeighbour(instruction);
        }
        BWTile flipTile = getTile(previousTile, tiles);
        flipTile.flipColor();
        System.out.println("flipped tile [" + flipTile.getRow() + ", " + flipTile.getCol() + "] to " + flipTile.getColor());
        if (flipTile.equals(previousTile)) {
            tiles.add(flipTile);
        }
    }

    public static ArrayList<String> simplifyInstructions(ArrayList<String> instructions) {

        ArrayList<String> result = new ArrayList<>(instructions);
        long nb1, nb2;
        String[] firstElement = new String[]{BWTile.E, BWTile.SE, BWTile.SW}; // e cancels w, SE cancels NW, SW cancels NE
        String[] secondElement = new String[]{BWTile.W, BWTile.NW, BWTile.NE};

        for (int i = 0; i<3; i++) {
            int finalI = i;
            nb1 = instructions.stream().filter(x -> x.equals(firstElement[finalI])).count();
            nb2 = instructions.stream().filter(x -> x.equals(secondElement[finalI])).count();
            int min = (int) Math.min(nb1, nb2);
            for (int k = 0; k < min; k++) {
                result.remove(firstElement[i]);
                result.remove(secondElement[i]);
            }
        }
        return result;
    }

    private static BWTile getTile(BWTile searchTile, LinkedList<BWTile> searchList) {
        for (BWTile tile : searchList) {
            if ((tile.getCol() == searchTile.getCol()) && (tile.getRow() == searchTile.getRow())) return tile;
        }
        return searchTile;
    }

    private static ArrayList<String> splitInstructions(String line) {
        int i = 0;
        ArrayList<String> result = new ArrayList<>();
        String nxtInstruction;
        while (i < line.length()) {
            if (line.substring(i).startsWith("s") || line.substring(i).startsWith("n")) {
                nxtInstruction = line.substring(i, i+2);
                i += 2;
            } else {
                nxtInstruction = line.substring(i, i+1);
                i++;
            }
            result.add(nxtInstruction);
        }
        return result;
    }

}
