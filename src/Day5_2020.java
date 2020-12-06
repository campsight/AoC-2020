import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.*;
import java.util.stream.Collectors;

public class Day5_2020 {
    private static final char ZERO_CHAR = 'F';
    private static final char ONE_CHAR = 'B';

    private static int getRow(String s) {
        String n = s.replaceAll("F", "0");
        n = n.replaceAll("B", "1");
        return Integer.parseInt(n, 2);
    }

    private static int getCol(String s) {
        String n = s.replaceAll("L", "0");
        n = n.replaceAll("R", "1");
        return Integer.parseInt(n, 2);
    }

    private static int getSeatID(String s) {
        return getRow(s.substring(0,7))*8+getCol(s.substring(7));
    }

    public static void main(String[] args) throws IOException {
        List<String> inputLines = Files.readAllLines(Path.of("resources\\input_day205.txt"));

        // part 1
        int max = 0;
        for (String line : inputLines) {
            max = Math.max(max, getSeatID(line));
        }
        System.out.println(max);

        // part 2
        HashMap<Integer, Integer> seats = new HashMap<>();
        for (String line : inputLines) {
            int row = getRow(line.substring(0,7));
            seats.put(row, seats.getOrDefault(row, 0) + 1);
        }
        TreeMap<Integer, Integer> sortedSeats = new TreeMap<>();
        sortedSeats.putAll(seats);
        List<Integer> nonFullRows = seats.entrySet().stream().filter(x -> x.getValue() == 7).map(x -> x.getKey()).collect(Collectors.toList());
        int myRow = nonFullRows.get(0);

        LinkedList<Integer> emptySeats = new LinkedList<>(Arrays.asList(0,1,2,3,4,5,6,7));
        for (String line : inputLines) {
            if (getRow(line.substring(0,7)) == myRow) {
                emptySeats.remove((Integer) getCol(line.substring(7)));
            }
        }
        System.out.println(myRow * 8 + emptySeats.get(0));



    }
}
