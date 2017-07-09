package server.dispensers;

import java.util.Map;
import java.util.logging.Logger;

/**
 * Created by B06514A on 6/18/2017.
 */
public class DispenserManager {
    private static final Logger logger = Logger.getLogger(DispenserManager.class.getName());

    private static Map<Integer, DispenserConfig> dispenserMap;

    public static void init(Map<Integer, DispenserConfig> dispenserMap) {
        logger.info("Initializing DispenserManager");
        DispenserManager.dispenserMap = dispenserMap;
    }

}
