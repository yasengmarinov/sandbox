public class AnalyzerLauncher {

    public static void main(String... args) {
        analyzeBattle(10, 10);
//        analyzeBattle(5,5);
//        analyzeBattle(10, 10);
//        analyzeBattle(15, 15);
//        analyzeBattle(20, 20);
//        analyzeBattle(30, 30);
    }

    private static void analyzeBattle(int attackingArmies, int defendingArmies) {
        BattleAnalyzer battle30vs30 = new BattleAnalyzer(attackingArmies, defendingArmies);
        battle30vs30.analyze();
        battle30vs30.print();
    }

}
