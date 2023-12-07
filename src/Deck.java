import java.util.ArrayList;

public class Deck {
    private ArrayList<Card> cards;
    private int cardsLeft;

    public Deck(String[] ranks, String[] suits, int[] points) {
        cards = new ArrayList<Card>();
        cardsLeft = 0;

        for (int i = 0; i < ranks.length; i++) {
            for (String suit : suits) {
                Card card = new Card(ranks[i], suit, points[i]);
                cards.add(card);
                cardsLeft++;
            }
        }
    }

    public boolean isEmpty() {
        return cardsLeft == 0;
    }

    public int getCardsLeft() {
        return cardsLeft;
    }

    public Card deal() {
        if (isEmpty()) {
            return null;
        }
        return cards.get(cardsLeft-- - 1);
    }

    public void shuffle() {
        cardsLeft = cards.size();
        for (int i = cardsLeft - 1; i >= 0; i--) {
            int r = (int) (cardsLeft * Math.random());
            Card temp = cards.get(i);
            cards.set(i, cards.get(r));
            cards.set(r,temp);
        }
    }

    public void printDeck() {
        for (Card card : cards) {
            System.out.println(card);
        }
    }
}