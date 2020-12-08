import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class Day8_2020 {
    private static String COMMA_DELIMITER = ",";

    public static void main(String[] args) throws IOException {
        // read input
        List<String> inputLines = Files.readAllLines(Path.of("resources\\input_day208.txt"));
        Iterator<String> lineIterator = inputLines.iterator();

        // part 1
        ArrayList<Operation> operations = new ArrayList<>();
        while (lineIterator.hasNext()) {
            String[] line = lineIterator.next().split(" ");
            operations.add(new Operation(line[0], Integer.parseInt(line[1])));
        }
        Program myProgram = new Program(operations);

        myProgram.runProgram();
        System.out.println("Part 1: " + myProgram.getAccumulator());

        // part 2
        for (int i = 0; i < operations.size(); i++) {
            if (i > 0)  { operations.get(i-1).swapOperation(); }
            operations.get(i).swapOperation();
            Program testProgram = new Program(operations);
            int test = testProgram.runProgram();
            if (test == Program.RETURN_OK) {
                System.out.println("Part 2: " + testProgram.getAccumulator());
                break;
            }
        }

    }
}
