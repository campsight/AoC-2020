import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

public class Day24_2020_alt {
    private static LinkedList<Tegel> tiles;
    private static Tegel startTile;

    public static void main(String[] args) throws IOException {
        long start = System.currentTimeMillis();
        // read input
        List<String> inputLines = Files.readAllLines(Path.of("resources/input_day224.txt"));

        startTile = new Tegel(0,0);
        tiles = new LinkedList<>();
        tiles.add(startTile);
        for (String line : inputLines) {
            flipTileAlt(line);
        }
        System.out.println("Part 1 alt: " + tiles.stream().filter(x -> x.getColor() == Tegel.BLACK).count());
        for (int i = 0; i < 100; i++) {
            processDay();
            //System.out.println("Day " + (i+1) + ": " + tiles.stream().filter(x -> x.getColor() == Tegel.BLACK).count());
        }
        System.out.println("Part 2 alt: " + tiles.stream().filter(x -> x.getColor() == Tegel.BLACK).count());

        long end = System.currentTimeMillis();
        System.out.println("Time taken: " + (((double) (end-start))/1000) + " seconds.");
    }

    private static void processDay() {
        LinkedList<Tegel> newTiles = new LinkedList<>();
        LinkedList<Tegel> flipColorList = new LinkedList<>();
        LinkedList<Tegel> whiteTileList = tiles.stream().filter(x -> x.getColor() == Tegel.WHITE).collect(Collectors.toCollection(LinkedList::new));
        LinkedList<Tegel> blackTileList = tiles.stream().filter(x -> x.getColor() == Tegel.BLACK).collect(Collectors.toCollection(LinkedList::new));
        for (Tegel tile : blackTileList) {
            // determine if black tile flips to white
            int nbAdj = tile.getNbBlackNeighbours();
            if ((nbAdj == 0) || (nbAdj > 2)) {
                //tile.flipColor();
                flipColorList.add(tile);
            }
            newTiles.add(tile);
            // add white tiles (which were not added yet)
            for (Tegel t : tile.getWhiteNeighbours(tiles)) {
                if (!whiteTileList.contains(t)) whiteTileList.add(t);
            }
        }
        //System.out.println(newTiles.size());
        for (Tegel tile : whiteTileList) {
            if (tile.getNbBlackNeighbours() == 2) {
                //tile.flipColor();
                flipColorList.add(tile);
            }
            newTiles.add(tile);
        }
        for (Tegel t : flipColorList) { t.flipColor(); }
        tiles = newTiles;
    }



    private static void flipTileAlt(String line) {
        ArrayList<String> instructions = splitInstructions(line);
        //System.out.println("Instruction: " + instructions);
        instructions = simplifyInstructions(instructions);
        //System.out.println("Simplified instruction: " + instructions);

        Tegel previousTile = startTile;
        for (String instruction : instructions) {
            previousTile = previousTile.createNeighbour(instruction, tiles);
        }
        Tegel flipTile = previousTile; // getTile(previousTile, tiles);
        flipTile.flipColor();
        //System.out.println("flipped tile [" + flipTile.getRow() + ", " + flipTile.getCol() + "] to " + flipTile.getColor());
    }

    public static ArrayList<String> simplifyInstructions(ArrayList<String> instructions) {

        ArrayList<String> result = new ArrayList<>(instructions);
        long nb1, nb2;
        String[] firstElement = new String[]{Tegel.E, Tegel.SE, Tegel.SW}; // e cancels w, SE cancels NW, SW cancels NE
        String[] secondElement = new String[]{Tegel.W, Tegel.NW, Tegel.NE};

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
