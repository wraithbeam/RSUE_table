package com.example.rsue.controllers;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

public class AcceptController {
    @FXML
    private Button saveButton;

    private short choose;

    @FXML
    void dontsave(MouseEvent event) {
        choose = 0;
        Stage stage = (Stage) saveButton.getScene().getWindow();
        stage.close();
    }

    @FXML
    void save(MouseEvent event) {
        choose = 1;
        Stage stage = (Stage) saveButton.getScene().getWindow();
        stage.close();
    }

    public short getChoose() {
        return choose;
    }
}
