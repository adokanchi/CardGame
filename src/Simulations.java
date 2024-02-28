public class Simulations {
    public static void main(String[] args) {
        int highestRound = 0;
        for (int i = 0; i < 1000; i++) {
            Game g = new Game("Tony","bbbbbb");
            int newNumRounds = g.playGame();
            System.out.println("Game " + (i + 1) + " finished.");
            if (newNumRounds > highestRound) {
                highestRound = newNumRounds;
            }
        }
        System.out.println("Highest number of rounds: " + highestRound);
    }
}
