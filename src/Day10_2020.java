import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.*;

public class Day10_2020 {
    public static void main(String[] args) throws IOException {
        // read input
        List<String> inputLines = Files.readAllLines(Path.of("resources\\input_day210.txt"));

        SortedSet<Integer> adapters = new TreeSet<>();
        for(String s : inputLines) {
            adapters.add(Integer.valueOf(s));
        }

        int oneDiff = 0;
        int threeDiff = 1; // one as your device is 3 higher then the highest
        int prevAdapter = 0;
        for (int adapter : adapters) {
            if ((adapter - prevAdapter) == 1) {
                oneDiff++;
            } else if ((adapter - prevAdapter) == 3) {
                threeDiff++;
            } else {
                System.out.println("Other diff: " + adapter + ", " + prevAdapter);
            }
            prevAdapter = adapter;
        }
        System.out.println("Part 1: " + oneDiff * threeDiff + " (" + oneDiff + " * " + threeDiff + ")");

        long[] combinations = new long[adapters.size()];
        combinations[0] = 1;
        combinations[1] = 1; // as 0 is not an element of adapter list
        ArrayList<Integer> adapterList = new ArrayList<>(adapters);
        for (int i = 0; i < adapterList.size(); i++) {
            for (int j = i + 1; j < adapterList.size(); j++) {
                if (adapterList.get(j) - adapterList.get(i) > 3) {
                    break; // you can only make combinations as long as the difference is max 3
                }
                combinations[j] += combinations[i];
            }
        }
        System.out.println("Part 2: " + combinations[combinations.length - 1]);
    }
}
