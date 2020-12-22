import java.math.BigInteger;
import java.util.LinkedList;

public class Deck {
    LinkedList<Card> cards = new LinkedList<>();

    public Deck() {
    }

    public void addCardBottom(Card card) {
        cards.add(card);
    }

    public int getSize() {
        return cards.size();
    }

    public Card getTopCard() {
        return cards.removeFirst();
    }

    @Override
    public String toString() {
        return "Deck{" +
                "cards=" + cards +
                '}';
    }

    public BigInteger calculateValue() {
        BigInteger result = BigInteger.ZERO;
        int nbCards = getSize();
        for (int i=1; i<=nbCards;i++) {
            result = result.add(BigInteger.valueOf((long) cards.removeLast().getValue() *i));
        }
        return result;
    }
}
