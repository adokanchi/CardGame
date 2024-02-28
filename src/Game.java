import java.util.ArrayList;
import java.util.Scanner;
public class Game {
    private Player p1;
    private Player p2;
    private Deck deck;
    private ArrayList<Card> loot;
    private WarViewer window;
    private int numRounds;
    public boolean inWar;
    public boolean gameOver;
    Scanner input;
    public Game() {
        numRounds = 0;
        inWar = false;
        String[] ranks = {"2", "3", "4", "5", "6", "7", "8", "9", "10", "J", "Q", "K", "A"};
        String[] suits = {"Hearts", "Clubs", "Diamonds", "Spades"};
        int[] values = {2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14};
        loot = new ArrayList<Card>();
        input = new Scanner(System.in);
    }
    public Game(String name1, String name2) {
        numRounds = 0;
        inWar = false;
        String[] ranks = {"2", "3", "4", "5", "6", "7", "8", "9", "10", "J", "Q", "K", "A"};
        String[] suits = {"Hearts", "Clubs", "Diamonds", "Spades"};
        int[] values = {2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14};
        loot = new ArrayList<Card>();
        p1 = new Player(name1);
        p2 = new Player(name2);
        input = new Scanner(System.in);
    }
    public void initPlayers() {
        Scanner input = new Scanner(System.in);

        System.out.println("Player 1 name:");
        String name1 = input.nextLine();
        p1 = new Player(name1);

        System.out.println("Player 2 name:");
        String name2 = input.nextLine();
        p2 = new Player(name2);

        gameOver = false;
    }

    public Player getP1() {
        return p1;
    }

    public Player getP2() {
        return p2;
    }

    public ArrayList<Card> getLoot() {
        return loot;
    }
    public void dealCards() {
        String[] ranks = {"2", "3", "4", "5", "6", "7", "8", "9", "10", "J", "Q", "K", "A"};
        String[] suits = {"Hearts", "Clubs", "Diamonds", "Spades"};
        int[] values = {2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14};
        this.deck = new Deck(ranks, suits, values, window);
        deck.shuffle();
        deck.shuffle();
        deck.shuffle();
        for (int i = 0; i < 26; i++) {
            p1.addCard(deck.deal());
            p2.addCard(deck.deal());
        }
    }

    // Evaluates the winner given the cards each player played, returns the winning player
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

    // Runs one turn cycle
    public void runTurn() {
        inWar = false;
        input.nextLine();
        System.out.println("Round " + (++numRounds));
        System.out.println(p1.getName() + " has " + p1.getNumCards() + " cards.");
        System.out.println(p2.getName() + " has " + p2.getNumCards() + " cards.");
        System.out.println(p1.getName() + "'s Top Card: " + p1.getTopCard());
        System.out.println(p2.getName() + "'s Top Card: " + p2.getTopCard());
        Player winner = findWinner(p1.getTopCard(), p2.getTopCard());
        loot.add(p1.removeTopCard());
        loot.add(p2.removeTopCard());
        window.repaint();
        input.nextLine();
        while (winner == null) {
            inWar = true;
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
            window.repaint();
            input.nextLine();
        }
        System.out.println("Winner: " + winner.getName());
        for (Card card : loot) {
            winner.addCard(card);
        }
        loot = new ArrayList<Card>();
        System.out.println(p1.getName() + " now has " + p1.getNumCards() + " cards.");
        System.out.println(p2.getName() + " now has " + p2.getNumCards() + " cards.");
        inWar = false;
        window.repaint();
        input.nextLine();
    }

    public void printInstructions() {
        System.out.println("Both players draw their first card from their hand.");
        System.out.println("Whoever has the higher card wins and adds both cards to their hand.");
        System.out.println("Aces beat everything except for twos. Twos beat aces.");
        System.out.println("If you tie, you start a War. Along with the cards that tied, each player");
        System.out.println("places their next three cards in the loot pile. The winner of the next round collects the loot.");
        System.out.println("The player who runs out of cards first loses; the other wins.");
    }
    public int playGame() {
        this.window = new WarViewer(this);
        printInstructions();
        initPlayers();
        dealCards();
        System.out.println("Press enter to play each round.");
        while (true) {
            runTurn();
            if (p1.getNumCards() <= 0) {
                System.out.println(p1.getName() + " is out of cards!");
                System.out.println(p2.getName() + " wins!");
                gameOver = true;
                window.repaint();
                break;
            }
            if (p2.getNumCards() <= 0) {
                System.out.println(p2.getName() + " is out of cards!");
                System.out.println(p1.getName() + " wins!");
                gameOver = true;
                window.repaint();
                break;
            }
        }
        window.repaint();
        input.nextLine();
        return numRounds;
    }

    public static void main(String[] args) {
        Game game1 = new Game();
        game1.playGame();
    }
}
