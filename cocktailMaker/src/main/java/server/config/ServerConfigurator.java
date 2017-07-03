package server.config;

import server.PumpManager;
import server.db.DAL;
import server.db.entities.Pump;

import java.util.*;
import java.util.logging.Logger;
import java.util.stream.Collectors;

/**
 * Created by B06514A on 6/15/2017.
 */
public class ServerConfigurator {

    private static final Logger logger = Logger.getLogger(ServerConfigurator.class.getName());
    private static final int PUMPS_MAX_COUNT = 10;
    private static final String PUMP_PIN_GROUND_PROPERTY = "pump.%d.pin.ground";
    private static final String PUMP_PIN_IN_PROPERTY = "pump.%d.pin.in";

    private static Properties properties;

    public static void init(Properties properties) {
        ServerConfigurator.properties = properties;
    }
    public static void configure() {
        logger.info("Initialize server configuration");

        DAL.init();

        Map<Integer, PumpConfig> pumpMap = initPumpMap();
        PumpManager.init(pumpMap);
        createInitialData(pumpMap);

    }

    private static void createInitialData(Map<Integer, PumpConfig> pumpMap) {
        Set<Integer> presentPumpsIDs = new HashSet<>();
        presentPumpsIDs.addAll(DAL.Pumps.getPumps().stream().map(Pump::getId).collect(Collectors.toList()));

        for (PumpConfig pumpConfig : pumpMap.values()) {
            if (!presentPumpsIDs.contains(pumpConfig.getId())) {
                DAL.Pumps.addPump(new Pump(pumpConfig.getId(), false));
            }
        }

    }

    private static Map<Integer, PumpConfig> initPumpMap() {
        Map<Integer, PumpConfig> pumpMap = new HashMap<>();

        for (int i = 1; i <= PUMPS_MAX_COUNT; i++) {
            int pumpGround = Integer.valueOf(
                    properties.getProperty(String.format(PUMP_PIN_GROUND_PROPERTY, i), "-1"));
            int pumpIn = Integer.valueOf(
                    properties.getProperty(String.format(PUMP_PIN_IN_PROPERTY, i), "-1"));
            if (pumpGround != -1 && pumpIn != -1) {
                logger.info(String.format("Configuring pump: %d with ground: %d and in: %d",
                        i, pumpGround, pumpIn));
                pumpMap.put(i, new PumpConfig(i, pumpGround, pumpIn));
            }
        }

        return pumpMap;
    }

}
