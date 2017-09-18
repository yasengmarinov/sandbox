package cocktailMaker.server.dispensers;

import java.util.Map;

public class FakeDispenserControllerManager extends DispenserControllerManager {

    @Override
    protected void persistDispensersInDB(Map<Integer, DispenserConfig> dispenserMap) {
        System.out.println("Persisted");
    }
}
