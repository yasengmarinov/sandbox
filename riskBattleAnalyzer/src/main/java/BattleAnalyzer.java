public class BattleAnalyzer {

    private static final int RUNS_COUNT = 5000;

    private final int attackerArmies;
    private final int defenderArmies;
    private  PlayerStats attackerStats = new PlayerStats();
    private PlayerStats defenderStats = new PlayerStats();


    public BattleAnalyzer(int attackerArmies, int defenderArmies) {
        this.attackerArmies = attackerArmies;
        this.defenderArmies = defenderArmies;
    }

    public void analyze() {
        for (int i = 0; i < RUNS_COUNT; i++) {
            BattleSimulator battleSimulator = new BattleSimulator(attackerArmies, defenderArmies, attackerStats, defenderStats);
            battleSimulator.simulate();
        }
    }

    public void print() {
        new ResultsPrinter(attackerArmies, defenderArmies, attackerStats, defenderStats, RUNS_COUNT)
        .printResults();
    }

}
