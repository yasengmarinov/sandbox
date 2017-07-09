package server.config;

import server.dispensers.DispenserConfig;
import server.dispensers.DispenserManager;
import server.db.DAL;
import server.db.entities.Dispenser;

import java.util.*;
import java.util.logging.Logger;
import java.util.stream.Collectors;

/**
 * Created by B06514A on 6/15/2017.
 */
public class ServerConfigurator {

    private static final Logger logger = Logger.getLogger(ServerConfigurator.class.getName());
    private static final int DISPENSERS_MAX_COUNT = 10;
    private static final String DISPENSER_PIN_GROUND_PROPERTY = "dispenser.%d.pin.ground";
    private static final String DISPENSER_PIN_IN_PROPERTY = "dispenser.%d.pin.in";

    private static Properties properties;

    public static void init(Properties properties) {
        ServerConfigurator.properties = properties;
    }
    public static void configure() {
        logger.info("Initialize server configuration");

        DAL.init();

        Map<Integer, DispenserConfig> dispenserMap = initDispenserMap();
        DispenserManager.init(dispenserMap);
        createInitialData(dispenserMap);

    }

    private static void createInitialData(Map<Integer, DispenserConfig> dispenserMap) {
        Set<Integer> presentDispensersIDs = new HashSet<>();
        presentDispensersIDs.addAll(DAL.getAll(Dispenser.class).stream().map(Dispenser::getId).collect(Collectors.toList()));

        for (DispenserConfig dispenserConfig : dispenserMap.values()) {
            if (!presentDispensersIDs.contains(dispenserConfig.getId())) {
                DAL.persist(new Dispenser(dispenserConfig.getId(), false));
            }
        }

    }

    private static Map<Integer, DispenserConfig> initDispenserMap() {
        Map<Integer, DispenserConfig> dispenserMap = new HashMap<>();

        for (int i = 1; i <= DISPENSERS_MAX_COUNT; i++) {
            int dispenserGround = Integer.valueOf(
                    properties.getProperty(String.format(DISPENSER_PIN_GROUND_PROPERTY, i), "-1"));
            int dispenserIn = Integer.valueOf(
                    properties.getProperty(String.format(DISPENSER_PIN_IN_PROPERTY, i), "-1"));
            if (dispenserGround != -1 && dispenserIn != -1) {
                logger.info(String.format("Configuring dispenser: %d with ground: %d and in: %d",
                        i, dispenserGround, dispenserIn));
                dispenserMap.put(i, new DispenserConfig(i, dispenserGround, dispenserIn));
            }
        }

        return dispenserMap;
    }

}
