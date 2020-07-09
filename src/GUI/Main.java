package GUI;

import BusinessLogic.Flashcard;
import BusinessLogic.util.LinkedList;
import BusinessLogic.util.Map;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.time.Duration;
import java.time.Instant;
import java.util.HashMap;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception{
        Parent root = FXMLLoader.load(getClass().getResource("Home.fxml"));
        primaryStage.setTitle("Kioku - Inicio");
        primaryStage.setScene(new Scene(root, 415  , 440));
        primaryStage.show();

    }

    public static void main(String[] args) {
        launch(args);

    }

}
