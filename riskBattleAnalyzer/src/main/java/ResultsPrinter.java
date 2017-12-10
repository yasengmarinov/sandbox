public class ResultsPrinter {

    private StringBuilder builder = new StringBuilder();

    private int attackerArmies;
    private int defenderArmies;
    private PlayerStats attackerStats;
    private PlayerStats defenderStats;
    private int totalFights;

    public ResultsPrinter(int attackerArmies, int defenderArmies, PlayerStats attackerStats, PlayerStats defenderStats, int totalFights) {
        this.attackerArmies = attackerArmies;
        this.defenderArmies = defenderArmies;
        this.attackerStats = attackerStats;
        this.defenderStats = defenderStats;
        this.totalFights = totalFights;
    }

    public void printResults() {
        builder.append("------- Battle result -------\n");
        builder.append("Attacker armies: ").append(attackerArmies).append("\n");
        builder.append("Defender armies: ").append(defenderArmies).append("\n\n");
        builder.append("Attacker results:\n");
        playerStats(attackerStats);
        builder.append("Defender results:\n");
        playerStats(defenderStats);
        builder.append("-----------------------------\n");
        System.out.print(builder.toString());
    }

    private void playerStats(PlayerStats playerStats) {
        builder
                .append("Total wins: ").append(playerStats.getWins()).append("\n")
                .append("Percent wins: ").append(playerStats.getWins() * 100 / totalFights).append("%\n")
        .append("Average lost armies: ").append(playerStats.getLostArmies() / totalFights).append("\n");
    }
}
