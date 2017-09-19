package cocktailMaker.server.dispensers;

import cocktailMaker.server.db.DAO;
import org.apache.log4j.Logger;
import cocktailMaker.server.config.ServerConfigurator;
import cocktailMaker.server.db.entities.Dispenser;
import cocktailMaker.server.dispensers.controllers.MockPumpController;
import cocktailMaker.server.dispensers.controllers.PumpController;
import cocktailMaker.server.dispensers.interfaces.DispenserController;

import java.util.*;
import java.util.stream.Collectors;

/**
 * Created by B06514A on 6/18/2017.
 */
public class DispenserControllerManager {

    protected static DispenserControllerManager instance;
    protected final DAO dao;

    private static final Logger logger = Logger.getLogger(DispenserControllerManager.class.getName());
    private static final int DISPENSERS_MAX_COUNT = 10;
    private static final String DISPENSER_PIN_IN_PROPERTY = "dispenser.%d.pin";

    public static final String TEST_MODE = "testMode";

    private static Map<Integer, DispenserController> dispenserControllerMap = new HashMap<>();

    public DispenserControllerManager(DAO dao) {
        this.dao = dao;
    }

    public void init(Properties properties) {


        logger.info("Initializing Dispenser Controller Manager");

        Map<Integer, DispenserConfig> dispenserMap = initDispenserMap(properties);

        persistDispensersInDB(dispenserMap);

        for (DispenserConfig config : dispenserMap.values()) {
            DispenserController controller;
            if (isTestModeEnables(properties)) {
                controller = new MockPumpController(config);
            } else {
                controller = new PumpController(config);
            }
            dispenserControllerMap.put(config.getId(), controller);
        }
    }

    private boolean isTestModeEnables(Properties properties) {
        return properties.getProperty(TEST_MODE).equalsIgnoreCase("true");
    }

    public DispenserController getDispenserController(int dispenserId) {
        return dispenserControllerMap.get(dispenserId);
    }

    protected static Map<Integer, DispenserConfig> initDispenserMap(Properties properties) {
        logger.info("Begin building dispenser map");
        Map<Integer, DispenserConfig> dispenserMap = new HashMap<>();

        for (int i = 1; i <= DISPENSERS_MAX_COUNT; i++) {
            int dispenserIn = Integer.valueOf(
                    properties.getProperty(String.format(DISPENSER_PIN_IN_PROPERTY, i), "-1"));
            if (dispenserIn != -1) {
                logger.info(String.format("Add dispenser %d with pin: %d",
                        i, dispenserIn));
                dispenserMap.put(i, new DispenserConfig(i, dispenserIn));
            }
        }

        return dispenserMap;
    }

    protected void persistDispensersInDB(Map<Integer, DispenserConfig> dispenserMap) {
        logger.info("Persisting dispensers in the DB");
        Set<Integer> presentDispensersIDs = new HashSet<>();
        presentDispensersIDs.addAll(dao.getAll(Dispenser.class).stream().map(Dispenser::getId).collect(Collectors.toList()));

        for (DispenserConfig dispenserConfig : dispenserMap.values()) {
            if (!presentDispensersIDs.contains(dispenserConfig.getId())) {
                dao.persist(new Dispenser(dispenserConfig.getId(), false));
            }
        }

    }
}
