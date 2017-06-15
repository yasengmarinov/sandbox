package server;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import server.config.ServerConfigurator;

import java.io.FileReader;
import java.io.IOException;
import java.net.URL;
import java.util.Properties;

public class ServerLauncher extends Application {

    private static Stage stage;

    @Override
    public void start(Stage primaryStage) throws Exception{
        stage = primaryStage;
        Parent root = FXMLLoader.load(getClass().
                getClassLoader().getResource("views/login.fxml"));
        primaryStage.setTitle("Cocktail Maker");
        primaryStage.setScene(new Scene(root, 800, 480));
        primaryStage.show();
    }

    public static void navigateTo(String page) {
        try {
            String url = "views/" + page + ".fxml";
            Parent root = FXMLLoader.load(ServerLauncher.class.getClassLoader().getResource(url));
            stage.setScene(new Scene(root, 800, 480));
        } catch (Exception ex) {
            System.err.println(ex);
        }
    }


    public static void main(String[] args) {
        URL serverPropertiesURL = ServerLauncher.class.
                getClassLoader().getResource("server_config.properties");
        Properties serverProperties = new Properties();
        try {
            serverProperties.load(new FileReader(serverPropertiesURL.getFile()));
        } catch (IOException e) {
            e.printStackTrace();
        }
        ServerConfigurator serverConfigurator = ServerConfigurator.getInstance();
        serverConfigurator.setProperties(serverProperties);
        serverConfigurator.configure();

        launch(args);
    }
}
