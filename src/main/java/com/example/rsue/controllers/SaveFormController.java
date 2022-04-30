package com.example.rsue.controllers;

import com.example.rsue.MainApplication;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.stage.Stage;

import java.io.IOException;

public class SaveFormController {
    private int choose;

    @FXML
    private Button Cancel;

    @FXML
    private Button DontSaveButton;

    @FXML
    private Button saveButton;

    public void cancel(){
        Stage stage = (Stage) Cancel.getScene().getWindow();
        stage.close();

        choose = 0;
        //api(0);
    }

    public void exit(){
        Stage stage = (Stage) Cancel.getScene().getWindow();
        stage.close();
        choose = 1;
        //api(1);
    }

    public void save(){
        Stage stage = (Stage) Cancel.getScene().getWindow();
        stage.close();
        choose = 2;
        //api(2);
    }

    private void api(int choose) {
        try {
            FXMLLoader loader = new FXMLLoader(MainApplication.class.getResource("table-view.fxml"));
            Parent root = loader.load();
            MainController mainController = loader.getController();
            mainController.setSaveAPI(choose);

        }catch (Exception e){
            System.out.println(e);
        }
    }

    public int getChoose(){
        return choose;
    }
}
