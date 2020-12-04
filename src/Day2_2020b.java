import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

public class Day2_2020b {
    private static final String SPACE_DELIMITER = " ";
    private static final String HYPHEN_DELIMITER = "-";

    public static boolean isValid(String line) {
        String[] parts = line.split(SPACE_DELIMITER);
        String[] numbers = parts[0].split(HYPHEN_DELIMITER);
        int minValue = Integer.parseInt(numbers[0]);
        int maxValue = Integer.parseInt(numbers[1]);
        char letter = parts[1].charAt(0);
        int count = (int) parts[2].chars().filter(ch -> ch == letter).count();
        return ((count >= minValue) && (count <= maxValue));
    }

    public static boolean isValid2(String line) {
        String[] parts = line.split(SPACE_DELIMITER);
        String[] numbers = parts[0].split(HYPHEN_DELIMITER);
        int p1 = Integer.parseInt(numbers[0]);
        int p2 = Integer.parseInt(numbers[1]);
        char letter = parts[1].charAt(0);
        return ((parts[2].charAt(p1-1) == letter) ^ (parts[2].charAt(p2-1) == letter));
    }

    public static void main(String[] args) throws IOException {
        List<String> inputLines = Files.readAllLines(Path.of("resources\\input_day202.txt"));
        long sum = inputLines.stream().filter(Day2_2020b::isValid).count();
        long sum2 = inputLines.stream().filter(Day2_2020b::isValid2).count();

        System.out.println(isValid2("1-3 a: abcde"));
        System.out.println(isValid2("1-3 b: cdefg"));
        System.out.println(isValid2("2-9 c: ccccccccc"));

        System.out.println(sum);
        System.out.println(sum2);
    }
}
