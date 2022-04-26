package com.example.rsue;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class MainApplication extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(MainApplication.class.getResource("table-view.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 1360, 768);
        stage.setTitle("ExcelToJava");
        stage.setScene(scene);
        stage.show();

    }

    public static void main(String[] args) throws IOException {
        Table table = new Table(new String("D:\\RSUE\\Java+VBA\\project.xlsx"));
        System.out.println(table.getRows(4));
        launch();
    }
}