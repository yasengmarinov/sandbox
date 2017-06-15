package server;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class ServerLauncher extends Application {

    private static Class clazz;

    private static Stage stage;

    @Override
    public void start(Stage primaryStage) throws Exception{
        stage = primaryStage;
        Parent root = FXMLLoader.load(getClass().getClassLoader().getResource("views/login.fxml"));
        primaryStage.setTitle("Cocktail Maker");
        primaryStage.setScene(new Scene(root, 800, 480));
        primaryStage.show();
    }

    public static void navigateTo(String page) {
        try {
            String url = "views/" + page + ".fxml";
            Parent root = FXMLLoader.load(clazz.getClassLoader().getResource(url));
            stage.setScene(new Scene(root, 800, 480));
        } catch (Exception ex) {
            System.err.println(ex);
        }
    }


    public static void main(String[] args) {
        clazz = new ServerLauncher().getClass();
        launch(args);
    }
}
