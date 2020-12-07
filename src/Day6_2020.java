import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.*;

public class Day6_2020 {

    public static void main(String[] args) throws IOException {
        // read input
        List<String> inputLines = Files.readAllLines(Path.of("resources\\input_day206.txt"));
        Iterator<String> lineIterator = inputLines.iterator();

        // 6612 3268
        int nbYes = 0;
        int nbAllYes = 0;
        StringBuilder answers = new StringBuilder();
        StringBuilder commonAnswers = new StringBuilder();
        boolean first = true;
        while(lineIterator.hasNext()) {
            String line = lineIterator.next();
            answers.append(line);
            if (first) {
                commonAnswers.append(line);
                first = false;
            } else if ((line == null) || line.length() < 1) {
                nbYes += answers.chars().distinct().count();
                nbAllYes += commonAnswers.chars().distinct().count();
                answers = new StringBuilder();
                commonAnswers = new StringBuilder();
                first = true;
            } else {
                commonAnswers = commonAnswers.chars().distinct()
                        .mapToObj(ch -> String.valueOf((char) ch))
                        .filter(line::contains)
                        .collect(StringBuilder::new, StringBuilder::append, StringBuilder::append);
            }
        }
        System.out.println("Part 1: " + nbYes);
        System.out.println("Part 2: " + nbAllYes);
    }
}
