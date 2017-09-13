package cocktailMaker.server;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.scene.input.KeyEvent;
import javafx.stage.Stage;
import org.apache.log4j.Logger;
import cocktailMaker.server.config.ServerConfigurator;

import java.util.Properties;

public class ServerLauncher extends Application {

    private static final Logger logger = Logger.getLogger(ServerLauncher.class.getName());

    private static Stage stage;

    public static void main(String[] args) {

        logger.info("Starting application");
        Properties serverProperties = Utils.loadPropertiesFile("config/server_config.properties");

        logger.info("Starting initial configuration");

        ServerConfigurator.init(serverProperties);

        logger.info("Launching application");
        launch(args);

    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        ServerConfigurator.setStage(primaryStage);
        ServerConfigurator.configure();
    }

    @Override
    public void stop() {
        Platform.exit();
        System.exit(0);
    }
}
