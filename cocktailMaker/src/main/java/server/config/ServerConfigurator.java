package server.config;

import org.apache.log4j.Logger;
import server.db.DAL;
import server.db.entities.Dispenser;
import server.dispensers.DispenserConfig;
import server.dispensers.DispenserControllerManager;

import java.util.*;
import java.util.stream.Collectors;

/**
 * Created by B06514A on 6/15/2017.
 */
public class ServerConfigurator {

    private static final Logger logger = Logger.getLogger(ServerConfigurator.class.getName());
    private static final int DISPENSERS_MAX_COUNT = 10;
    private static final String DISPENSER_PIN_IN_PROPERTY = "dispenser.%d.pin";

    private static Properties properties;

    public static void init(Properties properties) {
        ServerConfigurator.properties = properties;
    }

    public static void configure() {
        logger.info("Initialize server configuration");

        DAL.init();

        Map<Integer, DispenserConfig> dispenserMap = initDispenserMap();
        DispenserControllerManager.init(dispenserMap);
        createInitialData(dispenserMap);

    }

    private static void createInitialData(Map<Integer, DispenserConfig> dispenserMap) {
        logger.info("Persisting dispensers in the DB");
        Set<Integer> presentDispensersIDs = new HashSet<>();
        presentDispensersIDs.addAll(DAL.getAll(Dispenser.class).stream().map(Dispenser::getId).collect(Collectors.toList()));

        for (DispenserConfig dispenserConfig : dispenserMap.values()) {
            if (!presentDispensersIDs.contains(dispenserConfig.getId())) {
                DAL.persist(new Dispenser(dispenserConfig.getId(), false));
            }
        }

    }

    private static Map<Integer, DispenserConfig> initDispenserMap() {
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

}
