package server.dispensers;

import server.db.entities.Dispenser;
import server.dispensers.controllers.MockPumpController;
import server.dispensers.interfaces.DispenserController;

import java.util.HashMap;
import java.util.Map;
import org.apache.log4j.Logger;

/**
 * Created by B06514A on 6/18/2017.
 */
public class DispenserControllerManager {
    private static final Logger logger = Logger.getLogger(DispenserControllerManager.class.getName());

    private static Map<Integer, DispenserConfig> dispenserMap = new HashMap<>();
    private static Map<Integer, DispenserController> dispenserControllerMap = new HashMap<>();

    public static void init(Map<Integer, DispenserConfig> dispenserMap) {
        logger.info("Initializing Dispenser Controller Manager");
        DispenserControllerManager.dispenserMap = dispenserMap;

        for(DispenserConfig config : dispenserMap.values()) {
            dispenserControllerMap.put(config.getId(), new MockPumpController(config));
        }
    }

    public static DispenserController getDispenserController(Dispenser dispenser) {
        return dispenserControllerMap.get(dispenser.getId());
    }

}
