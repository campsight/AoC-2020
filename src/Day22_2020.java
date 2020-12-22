import java.io.IOException;
import java.math.BigInteger;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

public class Day22_2020 {
    public static void main(String[] args) throws IOException {
        // read input
        List<String> inputLines = Files.readAllLines(Path.of("resources/input_day222.txt"));
        int playerSepIndex = inputLines.indexOf("");
        Deck p1Deck = new Deck();
        Deck p2Deck = new Deck();

        for (int i = 1; i < playerSepIndex; i++) {
            p1Deck.addCardBottom(new Card(Integer.parseInt(inputLines.get(i))));
        }
        for (int i = playerSepIndex+2; i < inputLines.size(); i++) {
            p2Deck.addCardBottom(new Card(Integer.parseInt(inputLines.get(i))));
        }

        System.out.println(p1Deck);
        System.out.println(p2Deck);

        boolean finished;
        do {
            //System.out.println("Round " + roundCount);
            playRound(p1Deck, p2Deck);
            finished = ((p1Deck.getSize() == 0) || (p2Deck.getSize() == 0));
            //System.out.println(p1Deck);
            //System.out.println(p2Deck);
        } while (!finished);

        System.out.println(p1Deck);
        System.out.println(p2Deck);

        BigInteger result = (p1Deck.getSize() > 0) ? p1Deck.calculateValue() : p2Deck.calculateValue();
        System.out.println("Part 1: " + result);
    }

    public static void playRound(Deck dp1, Deck dp2) {
            Card c1 = dp1.getTopCard();
            Card c2 = dp2.getTopCard();
            if (c1.getValue() > c2.getValue()) {
                dp1.addCardBottom(c1);
                dp1.addCardBottom(c2);
            } else {
                dp2.addCardBottom(c2);
                dp2.addCardBottom(c1);
            }
    }
}
