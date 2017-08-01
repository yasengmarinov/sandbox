package server.dispensers;

import org.apache.log4j.Logger;
import server.config.ServerConfigurator;
import server.db.entities.Dispenser;
import server.dispensers.controllers.MockPumpController;
import server.dispensers.controllers.PumpController;
import server.dispensers.interfaces.DispenserController;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by B06514A on 6/18/2017.
 */
public class DispenserControllerManager {
    private static final Logger logger = Logger.getLogger(DispenserControllerManager.class.getName());
    public static final String TEST_MODE = "testMode";

    private static Map<Integer, DispenserConfig> dispenserMap = new HashMap<>();
    private static Map<Integer, DispenserController> dispenserControllerMap = new HashMap<>();

    public static void init(Map<Integer, DispenserConfig> dispenserMap) {
        logger.info("Initializing Dispenser Controller Manager");
        DispenserControllerManager.dispenserMap = dispenserMap;

        for (DispenserConfig config : dispenserMap.values()) {
            DispenserController controller = ServerConfigurator.getProperty(TEST_MODE).equalsIgnoreCase("true") ?
                    new MockPumpController(config) : new PumpController(config);
            dispenserControllerMap.put(config.getId(), controller);
        }
    }

    public static DispenserController getDispenserController(Dispenser dispenser) {
        return dispenserControllerMap.get(dispenser.getId());
    }

}
