package server;

import javafx.application.Application;
import javafx.stage.Stage;
import server.config.ServerConfigurator;

import java.util.Properties;
import java.util.logging.Logger;

public class ServerLauncher extends Application {

    private static final Logger logger = Logger.getLogger(ServerLauncher.class.getName());

    private static Stage stage;

    @Override
    public void start(Stage primaryStage) throws Exception{
        stage = primaryStage;
        PageNavigator.init(stage);
        primaryStage.setTitle("Cocktail Maker");
        PageNavigator.navigateTo(PageNavigator.PAGE_LOGIN);
        stage.show();
    }


    public static void main(String[] args) {

        logger.info("Starting application");
        Properties serverProperties = Utils.loadPropertiesFile("server_config.properties");

        logger.info("Starting initial configuration");

        ServerConfigurator.init(serverProperties);
        ServerConfigurator.configure();

        logger.info( "Launching application");
        launch(args);

    }
}
