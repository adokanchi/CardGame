import java.util.ArrayList;

public class Player {
    private String name;
    private ArrayList<Card> hand;
    private int points;

    public Player() {
        this.name = "unnamed";
        this.hand = new ArrayList<Card>();
        this.points = 0;
    }

    public Player(String name) {
        this.name = name;
        this.hand = new ArrayList<Card>();
        this.points = 0;
    }

    public Player(String name, ArrayList<Card> hand) {
        this.name = name;
        this.hand = new ArrayList<Card>();
        for (Card card : hand) {
            this.hand.add(card);
        }
        this.points = 0;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public ArrayList<Card> getHand() {
        return hand;
    }
    public void setHand(ArrayList<Card> hand) {
        this.hand = new ArrayList<Card>();
        for (Card card : hand) {
            this.hand.add(card);
        }
    }
    public int getPoints() {
        return points;
    }

    public void setPoints(int points) {
        this.points = points;
    }

    public void addPoints(int newPoints) {
        points += newPoints;
    }

    public void addCard(Card newCard) {
        hand.add(newCard);
    }

    public Card getTopCard() {
        return hand.get(0);
    }

    public Card removeTopCard() {
        return hand.remove(0);
    }

    public int getNumCards() {
        return hand.size();
    }

    public boolean hasCards() {
        return (this.hand.isEmpty());
    }

    public String toString() {
        String str = name + " has " + points + " points\n" + name + "'s cards: ";
        for (Card card : hand) {
            str += card + " | ";
        }
        return str;
    }
}
