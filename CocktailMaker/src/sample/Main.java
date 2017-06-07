package sample;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application {

    private static Stage stage;

    @Override
    public void start(Stage primaryStage) throws Exception{
        this.stage = primaryStage;
        Parent root = FXMLLoader.load(getClass().getResource("sample.fxml"));
        primaryStage.setTitle("Hello World");
        primaryStage.setScene(new Scene(root, 800, 480));
        primaryStage.show();
    }

    public static void navigateTo(String page) {
        try {
            Parent root = FXMLLoader.load(new Main().getClass().getResource(page + ".fxml"));
            stage.setScene(new Scene(root, 800, 480));
        } catch (Exception ex) {
            System.err.println(ex);
        }
    }


    public static void main(String[] args) {
        launch(args);
    }
}
