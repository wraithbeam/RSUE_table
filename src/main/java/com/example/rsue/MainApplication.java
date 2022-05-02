package com.example.rsue;

import com.example.rsue.controllers.MainController;
import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

import java.io.IOException;

public class MainApplication extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(MainApplication.class.getResource("table-view.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 1360, 768);
        stage.setTitle("ExcelToJava");
        stage.setScene(scene);

        MainController controller = fxmlLoader.getController();
        stage.setOnCloseRequest(e -> controller.exitApplication());

        stage.show();

    }

    public static void main(String[] args) throws IOException {
        launch();
    }
}