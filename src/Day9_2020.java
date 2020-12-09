import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.*;

public class Day9_2020 {
    private static final int PREAMBLE_LENGTH = 25;
    private static LinkedList<Long> numberQueue = new LinkedList<>();

    public static void main(String[] args) throws IOException {
        // read input
        List<String> inputLines = Files.readAllLines(Path.of("resources\\input_day209.txt"));
        Iterator<String> lineIterator = inputLines.iterator();

        // part 1
        long wrongNumber = 0;
        int wrongIndex = 0;
        long[] allNumberQueue = new long[inputLines.size()];
        for (int i = 0; i < inputLines.size(); i++) {
            long next = Long.parseLong(lineIterator.next());
            allNumberQueue[i] = next;
            if (i < PREAMBLE_LENGTH) {
                numberQueue.add(next);
            } else {
                if (checkNext(next)) {
                    wrongNumber = next;
                    wrongIndex = i;
                    System.out.println("Part 1: " + wrongNumber);
                    break;
                }
            }
        }

        // part 2
        System.out.println("Part 2: " + subArraySum(allNumberQueue, wrongIndex, wrongNumber));
    }

    private static boolean checkNext(long number) {
        for (int i = 0; i < (PREAMBLE_LENGTH-1); i++) {
            long n1 = numberQueue.get(i);
            for (int j = 1; j < PREAMBLE_LENGTH; j++) {
                long n2 = numberQueue.get(j);
                if ((!(n1 == n2)) && (number == (n1+n2))) {
                    numberQueue.removeFirst();
                    numberQueue.add(number);
                    return false;
                }
            }
        }
        return true;
    }

    private static long subArraySum(long[] arr, int n, long sum)
    {
        long curr_sum;
        int i, j;

        // Pick a starting point
        for (i = 0; i < n; i++) {
            curr_sum = arr[i];

            // try all subarrays starting with 'i'
            for (j = i + 1; j <= n; j++) {
                if (curr_sum == sum) {
                    int p = j - 1;
                    System.out.println("Sum found between indexes " + i + " and " + p);
                    long[] resultArray = Arrays.copyOfRange(arr, i, p+1);
                    long min = Arrays.stream(resultArray).min().getAsLong();
                    long max = Arrays.stream(resultArray).max().getAsLong();
                    return (min + max);
                }
                if (curr_sum > sum || j == n) { break; }
                curr_sum = curr_sum + arr[j];
            }
        }
        System.out.println("No subarray found");
        return 0;
    }
}
