public class BattleSimulator {

    private static final int MIN_ATTACK_ARMIES = 1;

    private final PlayerStats attackerStats;
    private final PlayerStats defenderStats;
    private final int attackerArmies;
    private final int defenderArmies;

    public BattleSimulator(int attackerArmies, int defenderArmies, PlayerStats attackerStats, PlayerStats defenderStats) {
        this.attackerArmies = attackerArmies;
        this.defenderArmies = defenderArmies;
        this.attackerStats = attackerStats;
        this.defenderStats = defenderStats;
    }

    public void simulate() {

        int remainingAttackerArmies = attackerArmies;
        int remainingDefenderArmies = defenderArmies;

        while (remainingAttackerArmies > MIN_ATTACK_ARMIES && remainingDefenderArmies > 0) {
            int attackWith = remainingAttackerArmies - 1 < 3 ? remainingAttackerArmies - 1 : 3;
            int defendWith = remainingDefenderArmies < 2 ? remainingDefenderArmies : 2;

            Fight fight = new Fight(attackWith, defendWith);
            fight.attack();

            remainingAttackerArmies-= fight.getAttackerLoses();
            remainingDefenderArmies-= fight.getDefenderLoses();

            attackerStats.lostArmies(fight.getAttackerLoses());
            defenderStats.lostArmies(fight.getDefenderLoses());
        }

        if (remainingAttackerArmies > 1 && remainingDefenderArmies == 0) {
            attackerStats.won();
        } else if (remainingAttackerArmies == 1 && remainingDefenderArmies > 0) {
            defenderStats.won();
        }
//        else {
//            System.err.print(
//                    String.format("INVALID BATTLE OUTCOME: attacker armies %d, defender armies %d"
//                            , remainingAttackerArmies
//                            , remainingDefenderArmies));
//        }

    }

}
