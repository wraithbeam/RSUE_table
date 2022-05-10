package com.example.rsue.controllers;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.stage.Stage;

public class SaveFormController {
    private short choose;

    @FXML
    private Button Cancel;

    public void cancel(){

        choose = 0;
        Stage stage = (Stage) Cancel.getScene().getWindow();
        stage.close();
        //api(0);
    }

    public void exit(){
        choose = 1;
        Stage stage = (Stage) Cancel.getScene().getWindow();
        stage.close();
        //api(1);
    }

    public void save(){
        choose = 2;
        Stage stage = (Stage) Cancel.getScene().getWindow();
        stage.close();
        //api(2);
    }

    public short getChoose(){
        return choose;
    }
}
