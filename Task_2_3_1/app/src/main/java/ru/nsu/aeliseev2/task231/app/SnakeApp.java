package ru.nsu.aeliseev2.task231.app;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URL;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class SnakeApp extends Application {

    @Override
    public void start(Stage stage) {
        stage.setTitle("Snake game");
        Parent root;
        try {
            URL location = getClass().getResource("Snake.fxml");
            if (location == null) {
                throw new FileNotFoundException("Snake.fxml missing");
            }
            root = FXMLLoader.load(location);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        Scene scene = new Scene(root, 640, 480);
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }

}