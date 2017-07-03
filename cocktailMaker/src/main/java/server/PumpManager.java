package server;

import server.config.PumpConfig;

import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.logging.Logger;

/**
 * Created by B06514A on 6/18/2017.
 */
public class PumpManager {
    private static final Logger logger = Logger.getLogger(PumpManager.class.getName());

    private static Map<Integer, PumpConfig> pumpMap;

    public static void init(Map<Integer, PumpConfig> pumpMap) {
        logger.info("Initializing PumpManager");
        PumpManager.pumpMap = pumpMap;
    }

}
