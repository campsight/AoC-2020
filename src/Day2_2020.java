import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class Day2_2020 {
    private static String SPACE_DELIMITER = " ";
    private static String HYPHEN_DELIMITER = "-";

    private static int processLine(String line) {
        int result;
        try (Scanner rowScanner = new Scanner(line)) {
            rowScanner.useDelimiter(SPACE_DELIMITER);
            int minValue;
            int maxValue;
            try (Scanner timesScanner = new Scanner(rowScanner.next())) {
                timesScanner.useDelimiter(HYPHEN_DELIMITER);
                minValue = timesScanner.nextInt();
                maxValue = timesScanner.nextInt();
            }
            char letter = rowScanner.next().charAt(0);
            String password = rowScanner.next();
            int count = (int) password.chars().filter(ch -> ch == letter).count();
            result = ((count >= minValue) && (count <= maxValue)) ? 1 : 0;
        }
        return result;
    }

    private static int processLineB(String line) {
        int result;
        try (Scanner rowScanner = new Scanner(line)) {
            rowScanner.useDelimiter(SPACE_DELIMITER);
            int p1;
            int p2;
            try (Scanner timesScanner = new Scanner(rowScanner.next())) {
                timesScanner.useDelimiter(HYPHEN_DELIMITER);
                p1 = timesScanner.nextInt();
                p2 = timesScanner.nextInt();
            }
            char letter = rowScanner.next().charAt(0);
            String password = rowScanner.next();
            result = ((password.charAt(p1-1) == letter) ^ (password.charAt(p2-1) == letter)) ? 1 : 0;
        }
        return result;
    }

    public static void main(String[] args) throws FileNotFoundException {
        Scanner input = new Scanner(new File("resources\\input_day202.txt"));
        int sum = 0;
        int sumB = 0;
        while (input.hasNext()) {
            String line = input.nextLine();
            sum += processLine(line);
            sumB += processLineB(line);
        }

        System.out.println(processLineB("1-3 a: abcde"));
        System.out.println(processLineB("1-3 b: cdefg"));
        System.out.println(processLineB("2-9 c: ccccccccc"));

        System.out.println(sum);
        System.out.println(sumB);
    }
}
