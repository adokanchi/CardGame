import javax.swing.*;
import java.awt.*;
public class Card {
    private String rank;
    private String suit;
    private int point;
    private Image image;
    private WarViewer window;
    public static final int CARD_WIDTH = 107;
    public static final int CARD_HEIGHT = 150;
    public Card(String rank, String suit, int point, WarViewer window) {
        this.rank = rank;
        this.suit = suit;
        this.point = point;
        this.window = window;
        // Use rank/suit value to find which image to use
        int imgNum = 4 * (point - 1);
        if (rank.equals("A")) {
            imgNum = 0;
        }
        switch (suit) {
            case "Spades":
                imgNum += 1;
                break;
            case "Hearts":
                imgNum += 2;
                break;
            case "Diamonds":
                imgNum += 3;
                break;
            case "Clubs":
                imgNum += 4;
                break;
        }
        image = new ImageIcon("Resources/Cards/" + String.valueOf(imgNum) + ".png").getImage();
    }
    public String getRank() {
        return rank;
    }
    public String getSuit() {
        return suit;
    }
    public int getPoint() {
        return point;
    }
    public void setRank(String rank) {
        this.rank = rank;
    }
    public void setSuit(String suit) {
        this.suit = suit;
    }
    public void setPoint(int point) {
        this.point = point;
    }
    public String toString() {
        return rank + " of " + suit;
    }

    // Draws card at coords x,y
    public void draw(int x, int y, Graphics g) {
        g.drawImage(image, x, y, CARD_WIDTH, CARD_HEIGHT, window);
    }
}
