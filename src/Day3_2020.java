import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Iterator;
import java.util.List;

public class Day3_2020 {
    private static final char TREE_CHAR = '#';

    private static long getNbTrees(List<String> map, int slope, int stepDown) {
        Iterator<String> lineIterator = map.iterator();
        int nbTrees = 0;
        int x_pos = 0;
        while (lineIterator.hasNext()) {
            String line = lineIterator.next();
            if (line.charAt(x_pos) == TREE_CHAR) nbTrees++;
            x_pos = (x_pos + slope) % line.length();
            int down = stepDown;
            while ((down > 0) && lineIterator.hasNext()) { down -= 1; lineIterator.next();}
        }
        return nbTrees;
    }

    public static void main(String[] args) throws IOException {
        List<String> inputLines = Files.readAllLines(Path.of("resources\\input_day203.txt"));

        long product = getNbTrees(inputLines, 1, 0);
        product = product * getNbTrees(inputLines, 3, 0);
        product = product * getNbTrees(inputLines, 5, 0);
        product = product * getNbTrees(inputLines, 7, 0);
        product = product * getNbTrees(inputLines, 1, 1);

        System.out.println(product);
    }
}
