import java.io.IOException;
import java.util.HashMap;

public class Day15_2020 {
    private static HashMap<Long, Long> memory = new HashMap<>();

    public static void main(String[] args) throws IOException {
        int[] initialList = {9,12,1,4,17,0,18};
        long next = 0;
        for (int i = 1; i <= initialList.length; i++) {
            next = addToRow(initialList[i-1], i);
        }
        for (int i = (initialList.length + 1); i < 2020; i++) {
            next = addToRow(next, i);
        }
        System.out.println("part 1: " + next);
        for (int i = 2020; i < 30000000; i++) {
            next = addToRow(next, i);
        }
        System.out.println("Part 2: " + next);
    }

    public static long addToRow(long number, long index) {
        if (memory.containsKey(number)) {
            long previousIndex= memory.get(number);
            memory.put(number, index);
            return (index - previousIndex);
        } else {
            memory.put(number, index);
            return 0;
        }
    }
}
