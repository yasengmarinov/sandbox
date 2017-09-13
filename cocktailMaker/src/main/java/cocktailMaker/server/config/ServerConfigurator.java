package cocktailMaker.server.config;

import cocktailMaker.server.card.CardSwipeDispatcher;
import cocktailMaker.server.PageNavigator;
import javafx.stage.Stage;
import org.apache.log4j.Logger;
import cocktailMaker.server.db.DAO;
import cocktailMaker.server.db.entities.Dispenser;
import cocktailMaker.server.dispensers.DispenserConfig;
import cocktailMaker.server.dispensers.DispenserControllerManager;

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
    private static Stage stage;

    public static void init(Properties properties) {
        ServerConfigurator.properties = properties;
    }

    public static void configure() {
        logger.info("Initialize cocktailMaker.server configuration");

        DAO.init();

        Map<Integer, DispenserConfig> dispenserMap = initDispenserMap();
        DispenserControllerManager.init(dispenserMap);
        createInitialData(dispenserMap);
        CardSwipeDispatcher.getInstance().init(stage, properties);

        initPageNavigator();

    }

    private static void createInitialData(Map<Integer, DispenserConfig> dispenserMap) {
        logger.info("Persisting dispensers in the DB");
        Set<Integer> presentDispensersIDs = new HashSet<>();
        presentDispensersIDs.addAll(DAO.getAll(Dispenser.class).stream().map(Dispenser::getId).collect(Collectors.toList()));

        for (DispenserConfig dispenserConfig : dispenserMap.values()) {
            if (!presentDispensersIDs.contains(dispenserConfig.getId())) {
                DAO.persist(new Dispenser(dispenserConfig.getId(), false));
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

    public static final String getProperty(String property) {
        return properties.getProperty(property, "");
    }

    public static void setStage(Stage stage) {
        ServerConfigurator.stage = stage;
    }

    public static Stage getStage() {
        return stage;
    }

    private static void initPageNavigator() {
        PageNavigator.init(stage);
        stage.setTitle("Cocktail Maker");
        PageNavigator.navigateTo(PageNavigator.PAGE_LOGIN);
        stage.show();
    }
}
