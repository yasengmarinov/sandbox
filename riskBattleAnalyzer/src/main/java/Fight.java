import java.util.Arrays;

public class Fight {

    private final int attackerArmies;
    private final int defenderArmies;

    private int attackerLoses;
    private int defenderLoses;

    public Fight(int attackerArmies, int defenderArmies) {
        this.attackerArmies = attackerArmies;
        this.defenderArmies = defenderArmies;
    }

    public void attack() {
        int[] attackerDies = new int[attackerArmies];
        int[] defenderDies = new int[defenderArmies];

        for (int i = 0; i < attackerDies.length; i++) {
            attackerDies[i] = DieRoller.roll();
        }
        for (int i = 0; i < defenderDies.length; i++) {
            defenderDies[i] = DieRoller.roll();
        }

        Arrays.sort(attackerDies);
        Arrays.sort(defenderDies);

        for (int i = 0; i < ((attackerArmies <= defenderArmies) ? attackerArmies : defenderArmies); i++) {
            if (attackerDies[attackerDies.length - 1 - i] <= defenderDies[defenderDies.length - 1 - i]) {
                attackerLoses++;
            } else {
                defenderLoses++;
            }
        }
    }

    public int getAttackerLoses() {
        return attackerLoses;
    }

    public int getDefenderLoses() {
        return defenderLoses;
    }
}
