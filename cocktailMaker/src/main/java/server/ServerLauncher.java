package server;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class ServerLauncher extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception{
        Parent root = FXMLLoader.load(getClass().getClassLoader().getResource("views/home_admin.fxml"));
        primaryStage.setTitle("Cocktail Maker");
        primaryStage.setScene(new Scene(root, 800, 480));
        primaryStage.show();
    }


    public static void main(String[] args) {
        launch(args);
    }
}
