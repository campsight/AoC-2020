import java.io.IOException;
import java.math.BigInteger;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.LinkedList;
import java.util.List;

public class Day22b_2020 {
    public static void main(String[] args) throws IOException {
        // read input
        List<String> inputLines = Files.readAllLines(Path.of("resources/input_day222.txt"));
        int playerSepIndex = inputLines.indexOf("");
        LinkedList<Integer> p1Deck = new LinkedList<>();
        LinkedList<Integer> p2Deck = new LinkedList<>();

        for (int i = 1; i < playerSepIndex; i++) {
            p1Deck.add(Integer.parseInt(inputLines.get(i)));
        }
        for (int i = playerSepIndex+2; i < inputLines.size(); i++) {
            p2Deck.add(Integer.parseInt(inputLines.get(i)));
        }

        System.out.println(p1Deck);
        System.out.println(p2Deck);

        int winner = playRecursiveRound(p1Deck, p2Deck);

        System.out.println(p1Deck);
        System.out.println(p2Deck);

        LinkedList<Integer> winningDeck = (winner == 1) ? p1Deck : p2Deck;

        BigInteger result = BigInteger.ZERO;
        int nbCards = winningDeck.size();
        for (int i=1; i<=nbCards;i++) {
            result = result.add(BigInteger.valueOf((long) winningDeck.removeLast() *i));
        }
        System.out.println("Part 2: " + result);
    }

    public static int playRecursiveRound(LinkedList<Integer> dp1, LinkedList<Integer> dp2) {
        LinkedList<String> seen = new LinkedList<>();
        while ((dp1.size() >0) && (dp2.size() > 0)) {
            // System.out.println(seen.toString() + dp1.toString() + dp2.toString());
            if (seen.contains(dp1.toString()) && seen.contains(dp2.toString())) { return 1; }
            seen.add(dp1.toString());
            seen.add(dp2.toString());

            int c1 = dp1.removeFirst();
            int c2 = dp2.removeFirst();

            int winner;
            if ((dp1.size() >= c1) && (dp2.size() >= c2)) {
                winner = playRecursiveRound(makeCopy(dp1, c1), makeCopy(dp2, c2));
            } else {
                winner = (c1 > c2) ? 1 : 2;
            }

            if (winner == 1) {
                dp1.add(c1);
                dp1.add(c2);
            } else {
                dp2.add(c2);
                dp2.add(c1);
            }
        }
        return (dp1.size() > 0) ? 1 : 2;
    }

    public static LinkedList<Integer> makeCopy(LinkedList<Integer> origList, int nb) {
        LinkedList<Integer> result = new LinkedList<>();
        for (int i = 0; i < nb; i++) {
            result.add(origList.get(i));
        }
        return result;
    }
}
