import java.util.ArrayList;
import java.util.Scanner;
public class Game {
    private Player p1;
    private Player p2;
    private Deck deck;
    private ArrayList<Card> loot;
    public Game() {
        String[] ranks = {"2", "3", "4", "5", "6", "7", "8", "9", "10", "J", "Q", "K", "A"};
        String[] suits = {"Hearts", "Clubs", "Diamonds", "Spades"};
        int[] values = {2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14};
        deck = new Deck(ranks, suits, values);
        loot = new ArrayList<Card>();
    }

    public void initPlayers() {
        Scanner input = new Scanner(System.in);

        System.out.println("Player 1 name:");
        String name1 = input.nextLine();
        p1 = new Player(name1);

        System.out.println("Player 2 name:");
        String name2 = input.nextLine();
        p2 = new Player(name2);
    }
    public void dealCards() {
        for (int i = 0; i < 26; i++) {
            p1.addCard(deck.deal());
            p2.addCard(deck.deal());
        }
    }

    public Player findWinner(Card p1Card, Card p2Card) {
        int p1Point = p1Card.getPoint();
        int p2Point = p2Card.getPoint();
        if (p1Point > p2Point) {
            if (p1Point == 14 && p2Point == 2) {
                return p2;
            }
            return p1;
        }
        if (p2Point > p1Point) {
            if (p2Point == 14 && p1Point == 2) {
                return p1;
            }
            return p2;
        }
        return null;
    }

    public void runTurn() {
        System.out.println(p1.getName() + " has " + p1.getNumCards() + " cards.");
        System.out.println(p2.getName() + " has " + p2.getNumCards() + " cards.");
        System.out.println(p1.getName() + "'s Top Card: " + p1.getTopCard());
        System.out.println(p2.getName() + "'s Top Card: " + p2.getTopCard());
        Player winner = findWinner(p1.getTopCard(), p2.getTopCard());
        loot.add(p1.removeTopCard());
        loot.add(p2.removeTopCard());
        while (winner == null) {
            System.out.println("War!");
            if (p1.getNumCards() == 0 || p2.getNumCards() == 0) {
                return;
            }
            for (int i = 0; i < 3; i++) {
                if (p1.getNumCards() > 1) {
                    Card p1Card = p1.removeTopCard();
                    loot.add(p1Card);
                    System.out.println(p1.getName() + " adds a " + p1Card + " to the loot pile.");
                }
                if (p2.getNumCards() > 1) {
                    Card p2Card = p2.removeTopCard();
                    loot.add(p2Card);
                    System.out.println(p2.getName() + " adds a " + p2Card + " to the loot pile.");
                }
            }
            System.out.println(p1.getName() + "'s Top Card: " + p1.getTopCard());
            System.out.println(p2.getName() + "'s Top Card: " + p2.getTopCard());
            winner = findWinner(p1.getTopCard(), p2.getTopCard());
            loot.add(p1.removeTopCard());
            loot.add(p2.removeTopCard());
        }
        System.out.println("Winner: " + winner.getName());
        for (Card card : loot) {
            winner.addCard(card);
        }
        loot = new ArrayList<Card>();
        System.out.println(p1.getName() + " now has " + p1.getNumCards() + " cards.");
        System.out.println(p2.getName() + " now has " + p2.getNumCards() + " cards.");
    }

    public void runGame() {
        Scanner input = new Scanner(System.in);
        initPlayers();
        deck.shuffle();
        dealCards();
        while (true) {
            runTurn();
            if (p1.getNumCards() <= 0) {
                System.out.println(p1.getName() + " is out of cards!");
                System.out.println(p2.getName() + " wins!");
                break;
            }
            if (p2.getNumCards() <= 0) {
                System.out.println(p2.getName() + " is out of cards!");
                System.out.println(p1.getName() + " wins!");
                break;
            }
            input.nextLine();
        }
    }
    public static void main(String[] args) {
        Game game1 = new Game();
        game1.runGame();
    }
}
