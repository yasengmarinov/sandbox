package cocktailMaker.server.config;

import cocktailMaker.guice.annotations.ServerProperties;
import cocktailMaker.server.card.CardSwipeDispatcher;
import cocktailMaker.server.PageNavigator;
import com.google.inject.Inject;
import com.google.inject.Injector;
import com.google.inject.Singleton;
import com.sun.security.ntlm.Server;
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
@Singleton
public class ServerConfigurator {

    private static final Logger logger = Logger.getLogger(ServerConfigurator.class.getName());

    private Properties properties;
    private PageNavigator pageNavigator;
    private Stage stage;
    private Injector injector;
    private DAO dao;

    @Inject
    public ServerConfigurator(@ServerProperties Properties properties, PageNavigator pageNavigator, DAO dao) {
        this.properties = properties;
        this.pageNavigator = pageNavigator;
        this.dao = dao;
    }

    public void configure() {
        logger.info("Initialize cocktailMaker.server configuration");

        dao.init();

        DispenserControllerManager.getInstance().init(properties);
        CardSwipeDispatcher.getInstance().init(stage, properties);

        initPageNavigator();

    }

    public void setStage(Stage stage) {
        this.stage = stage;
    }

    public void setInjector(Injector injector) {
        this.injector = injector;
    }

    private void initPageNavigator() {
        pageNavigator.setStage(stage);
        pageNavigator.setInjector(injector);
        stage.setTitle("Cocktail Maker");
        pageNavigator.navigateTo(PageNavigator.PAGE_LOGIN);
        stage.show();
    }
}
