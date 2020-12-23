import java.io.IOException;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;

public class Day23b_2020 {
    private static Map<Integer, CircularListNode<Integer>> cups = new HashMap<>();
    public static int pointer;
    public static int nbCups;

    // credits to VeeArr, as his comments were the inspiration to this solution
    public static void main(String[] args) {
        String input = "974618352"; // JOHAN
        //String input = "389125467"; // exemple

        CircularListNode<Integer> previousCup = null;
        int firstCupNumber = -1;
        for (int i = 0; i < input.length(); i++) {
            int currentCupNumber = Integer.valueOf(input.substring(i, i+1));
            CircularListNode<Integer> currentCup = new CircularListNode<>(currentCupNumber);
            if (previousCup == null) {
                firstCupNumber = currentCupNumber;
            } else {
                previousCup.insertAfter(currentCup);
            }
            previousCup = currentCup;
            cups.put(currentCupNumber, currentCup);
        }

        for (int i = 10; i <= 1_000_000; i++) {
            CircularListNode<Integer> currentCup = new CircularListNode<>(i);
            previousCup.insertAfter(currentCup);
            previousCup = currentCup;
            cups.put(i, currentCup);
        }

        int maxVal = cups.keySet().stream().mapToInt(Integer::valueOf).max().getAsInt();

        int currentCupNumber = firstCupNumber;
        for (int i = 0; i < 10000000; i++) {
            //if (i % 100000 == 0) { System.out.println("i " + i); }

            CircularListNode<Integer> currentCup = cups.get(currentCupNumber);
            CircularListNode<Integer> cup1 = currentCup.next.remove();
            CircularListNode<Integer> cup2 = currentCup.next.remove();
            CircularListNode<Integer> cup3 = currentCup.next.remove();

            int nextCupNumber = currentCupNumber - 1;
            if (nextCupNumber < 1) { nextCupNumber = maxVal; }
            CircularListNode<Integer> nextCup = cups.get(nextCupNumber);
            while ((nextCup == cup1) || (nextCup == cup2) || (nextCup == cup3)) {
                nextCupNumber = nextCupNumber - 1;
                if (nextCupNumber < 1) { nextCupNumber = maxVal; }

                nextCup = cups.get(nextCupNumber);
            }

            nextCup.insertAfter(cup3);
            nextCup.insertAfter(cup2);
            nextCup.insertAfter(cup1);
            currentCupNumber = currentCup.next.value;
        }

        CircularListNode<Integer> solutionCup = cups.get(1).next;
        int solCup1 = solutionCup.value;
        int solCup2 = solutionCup.next.value;
        System.out.println("part 2: " + solCup1 + " * " + solCup2 + " = " + (BigInteger.valueOf(solCup1).multiply(BigInteger.valueOf(solCup2))));
    }

}
