package server;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.stage.Stage;
import org.apache.log4j.Logger;
import server.config.ServerConfigurator;

import java.util.Properties;

public class ServerLauncher extends Application {

    private static final Logger logger = Logger.getLogger(ServerLauncher.class.getName());

    private static Stage stage;

    @Override
    public void start(Stage primaryStage) throws Exception{
        stage = primaryStage;
//        stage.setFullScreen(true);
        PageNavigator.init(stage);
        primaryStage.setTitle("Cocktail Maker");
        PageNavigator.navigateTo(PageNavigator.PAGE_LOGIN);
        stage.show();
    }

    @Override
    public void stop() {
        Platform.exit();
        System.exit(0);
    }


    public static void main(String[] args) {

        logger.info("Starting application");
        Properties serverProperties = Utils.loadPropertiesFile("dispencer_config.properties");

        logger.info("Starting initial configuration");

        ServerConfigurator.init(serverProperties);
        ServerConfigurator.configure();

        logger.info( "Launching application");
        launch(args);

    }
}
