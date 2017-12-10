public class PlayerStats {

    private int lostArmies;
    private int wins;

    public void lostArmies(int lostArmies) {
        this.lostArmies+= lostArmies;
    }

    public void won() {
        wins++;
    }

    public int getLostArmies() {
        return lostArmies;
    }

    public int getWins() {
        return wins;
    }
}
