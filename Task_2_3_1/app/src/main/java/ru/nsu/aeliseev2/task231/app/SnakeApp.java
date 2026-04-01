package ru.nsu.aeliseev2.task231.app;

import java.io.IOException;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class SnakeApp extends Application {

    /**
     * The entry point for the JavaFX application.
     *
     * @param stage The main stage.
     * @throws IOException Error while loading the FXML layout.
     */
    @Override
    public void start(Stage stage) throws IOException {
        stage.setTitle("Snake game");
        FXMLLoader loader = new FXMLLoader(getClass().getResource("Snake.fxml"));
        Scene scene = new Scene(loader.load(), 640, 480);
        SnakeController controller = loader.getController();
        scene.setOnKeyPressed(keyEvent -> controller.handleKeyPress(keyEvent.getCode()));
        stage.setScene(scene);
        stage.show();
    }

    /**
     * The entry point for the program.
     *
     * @param args Command line arguments.
     */
    public static void main(String[] args) {
        launch();
    }

}