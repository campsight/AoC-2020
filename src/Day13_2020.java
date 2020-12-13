import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.List;

public class Day13_2020 {
    public static void main(String[] args) throws IOException {
        // read input
        List<String> inputLines = Files.readAllLines(Path.of("resources\\input_day213.txt"));
        long earliestDepartTime = Long.parseLong(inputLines.get(0));
        String[] busses = inputLines.get(1).split(",");
        long earliest = Long.MAX_VALUE;
        long earliestID = -1;
        for (String bus : busses) {
            if (!bus.equalsIgnoreCase("x")) {
                long busID = Long.parseLong(bus);
                long departs = busID - (earliestDepartTime % busID);
                if (departs < earliest) { earliest = departs; earliestID = busID; }
            }
        }

        System.out.println("Part 1: " + (earliestID * (earliest)) + " (" + earliestID + ", " + earliest + ")");

        int size = (int) Arrays.stream(busses).filter(x -> (!(x.equalsIgnoreCase("x")))).count();
        int[] num = new int[size];
        int[] rem = new int[size];
        int p = 0;
        for (int i = 0; i < busses.length; i++) {
            if (!busses[i].equalsIgnoreCase("x")) {
                num[p] = Integer.parseInt(busses[i]);
                rem[p] = num[p] - i;
                p++;
            }
        }

        System.out.println("Part 2: " + findMinX(num, rem));

        // alternative (got this code from https://www.reddit.com/user/jacobpake/
        // this is a very cool solution to the problem in my opinion, so I left it in
        // comments are from me (I only use code I can understand)
        //long t = 100000000000000L; can also be used (but doesn't make a lot of difference
        long t = 0; // use this one for the examples
        int i = 0;
        long s = 1; // s denotes the 'step' you take to increase the time t you are going to check next
        while (i < busses.length) {
            if (busses[i].equalsIgnoreCase("x")) { i++; }
            else {
                int theBus = Integer.parseInt(busses[i]);
                if ((t + i) % theBus == 0) {
                    // if you have a solution for bus b, you can 'add' this to the steps you take
                    s *= theBus; // this is really smart: it makes you quickly increase the step only to valid future timestamps
                    i++; // go find a solution that also fits the next bus
                } else {
                    t += s;
                }
            }
        }
        System.out.println("Part 2 (alt.): " + t);
    }

    // Returns modulo inverse of a
    // with respect to m using extended
    // Euclid Algorithm. Refer below post for details:
    // https://www.geeksforgeeks.org/multiplicative-inverse-under-modulo-m/
    static long inv(long a, long m)
    {
        long m0 = m, t, q;
        long x0 = 0, x1 = 1;

        if (m == 1)
            return 0;

        // Apply extended Euclid Algorithm
        while (a > 1)
        {
            // q is quotient
            q = a / m;

            t = m;

            // m is remainder now, process
            // same as euclid's algo
            m = a % m;a = t;

            t = x0;

            x0 = x1 - q * x0;

            x1 = t;
        }

        // Make x1 positive
        if (x1 < 0)
            x1 += m0;

        return x1;
    }

    // k is size of num[] and rem[].
    // Returns the smallest number
    // x such that:
    // x % num[0] = rem[0],
    // x % num[1] = rem[1],
    // ..................
    // x % num[k-2] = rem[k-1]
    // Assumption: Numbers in num[] are pairwise
    // coprime (gcd for every pair is 1)
    static long findMinX(int[] num, int[] rem)
    {
        int k = num.length;
        // Compute product of all numbers
        long prod = 1;
        for (int j : num) {
            prod *= j;
        }

        // Initialize result
        long result = 0;

        // Apply above formula
        for (int i = 0; i < k; i++)
        {
            long pp = prod / num[i];
            result += rem[i] * inv(pp, num[i]) * pp;
        }

        return result % prod;
    }
}
