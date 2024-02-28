import javax.swing.*;
import java.awt.*;

public class WarViewer extends JFrame {
    private Game war;
    public static final int WINDOW_WIDTH = 1000;
    public static final int WINDOW_HEIGHT = 600;
    public static boolean dispInstructions = false;
    public String[] instructions;

    public WarViewer(Game war) {
        // Initial window properties
        this.setTitle("WAR!");
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setSize(WINDOW_WIDTH, WINDOW_HEIGHT);
        this.setVisible(true);
        this.war = war;
        dispInstructions = false;
        instructions = new String[]{"Both players draw their first card from their hand.",
                                    "Whoever has the higher card wins and adds both cards to their hand.",
                                    "Aces beat everything except for twos. Twos beat aces.",
                                    "If you tie, you start a War. Along with the cards that tied, each player" +
                                            "places their next three cards in the loot pile. The winner of the next round collects the loot.",
                                    "The player who runs out of cards first loses; the other wins."};
    }
    public WarViewer(Game war, int width, int height) {
        // Initial window properties
        this.setTitle("WAR!");
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setSize(width, height);
        this.setVisible(true);
        this.war = war;
        dispInstructions = false;
    }


    // Clears whole window
    public void clearWindow(Graphics g) {
        g.setColor(Color.WHITE);
        g.fillRect(0,0,WINDOW_WIDTH, WINDOW_HEIGHT);
    }

    // Draws deck/information (deck image, card count, player names)
    public void drawDecks(Graphics g) {
        final int nameOffsetX = 250;
        final int nameOffsetY = 150;
        // Player names
        g.setColor(Color.RED);
        if (war.getP1() != null) {
            g.drawString(war.getP1().getName(), nameOffsetX, nameOffsetY);
            g.drawString(war.getP2().getName(), WINDOW_WIDTH - nameOffsetX, WINDOW_HEIGHT - nameOffsetY);
        }

        // Deck images
        final int deckOffset = 75;
        Image cardBack = new ImageIcon("Resources/Cards/back.png").getImage();
        g.drawImage(cardBack, deckOffset, deckOffset, Card.CARD_WIDTH, Card.CARD_HEIGHT, this);
        g.drawImage(cardBack, WINDOW_WIDTH - Card.CARD_WIDTH - deckOffset, WINDOW_HEIGHT - Card.CARD_HEIGHT - deckOffset, Card.CARD_WIDTH, Card.CARD_HEIGHT, this);

        // Deck numbers
        if (war.getP1() != null) {
            final int deckNumOffsetX = 250;
            final int deckNumOffsetY = 125;
            g.drawString(Integer.toString(war.getP1().getNumCards()),deckNumOffsetX,deckNumOffsetY);
            g.drawString(Integer.toString(war.getP2().getNumCards()),WINDOW_WIDTH - deckNumOffsetX,WINDOW_HEIGHT - deckNumOffsetY);
        }
    }

    // Draws one loot card in player 1 loot location
    public void drawP1WarLootCard(Card card, int cardNum, Graphics g) {
        final int warLocX = WINDOW_WIDTH / 2 + 200;
        final int warLocY = 200;
        final int perCardOffset = 50;
        card.draw(warLocX + cardNum*perCardOffset, warLocY, g);
    }

    // Draws one loot card in player 2 loot location
    public void drawP2WarLootCard(Card card, int cardNum, Graphics g) {
        final int warLocX = 100;
        final int warLocY = 400;
        final int perCardOffset = 50;
        card.draw(warLocX + cardNum*perCardOffset, warLocY, g);
    }

    // Draws all war loot cards
    public void drawLootCards(Graphics g) {
        if (war.inWar) {
            for (int i = 0; i < 3; i++) {
                this.drawP1WarLootCard(war.getLoot().get(2*i+2),i,g);
                this.drawP2WarLootCard(war.getLoot().get(2*i+3),i,g);
            }
        }
    }

    // Draws the two competing cards
    public void drawPlayedCards(Graphics g) {
        if (war.getP1() != null && !war.getLoot().isEmpty()) {
            war.getLoot().get(war.getLoot().size() - 2).draw(400, 200, g);
            war.getLoot().get(war.getLoot().size() - 1).draw(400, 400, g);
        }
    }

    // Draws game instructions
    public void drawInstructions(Graphics g) {
        g.setColor(Color.RED);
        int xVal = 20;
        int yVal = 20;
        for (String line : instructions) {
            g.drawString(line,xVal,yVal);
            yVal += 50;
        }
        g.drawString("Press enter to continue.",xVal,yVal);
    }

    // Draws winner announcement when game ends
    public void drawGameOver(Graphics g) {
        g.setColor(Color.RED);
        String winner;
        if (war.getP1().getNumCards() == 0) {
            winner = war.getP2().getName();
        }
        else {
            winner = war.getP1().getName();
        }
        g.drawString("Game over!", WINDOW_WIDTH / 2,WINDOW_HEIGHT / 2);
        g.drawString(winner + " wins!",WINDOW_WIDTH / 2,WINDOW_HEIGHT / 2 + 20);

    }

    // Draws screen
    public void paint(Graphics g) {
        this.clearWindow(g);
        // Only displays once then is disabled forever
        if (!dispInstructions) {
            this.drawInstructions(g);
            dispInstructions = true;
            return;
        }
        this.drawDecks(g);
        this.drawPlayedCards(g);
        this.drawLootCards(g);
        if (war.gameOver) {
            this.drawGameOver(g);
        }
    }
}
