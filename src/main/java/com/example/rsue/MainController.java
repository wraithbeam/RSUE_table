package com.example.rsue;

import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;
import java.util.ArrayList;

public class MainController {
    private JavaFXTable javaFXTable;

    @FXML
    private Button addButton;

    @FXML
    private Button deleteButton;

    @FXML
    private Button editButton;

    @FXML
    private Menu menuEdit;

    @FXML
    private MenuItem menuEditAdd;

    @FXML
    private MenuItem menuEditChange;

    @FXML
    private MenuItem menuEditDelete;

    @FXML
    private Menu menuFile;

    @FXML
    private MenuItem menuFileClose;

    @FXML
    private MenuItem menuFileOpen;

    @FXML
    private MenuItem menuFileSave;

    @FXML
    private Menu menuFind;

    @FXML
    private Button menuFindAcceptButton;

    @FXML
    private TextField menuFindCondition;

    @FXML
    private Button openButton;

    @FXML
    private Button saveButton;

    @FXML
    private TableView<Row> tableInfo;

    @FXML
    private CheckBox viewFormCheckBox;

    //Выбор файла. Кнопки "Открыть"
    @FXML
    void openFileDialog(MouseEvent event) {
        fileChooserStart();
    }
    @FXML
    void openMenuFileDialog(ActionEvent event) {
        fileChooserStart();
    }
    private void fileChooserStart(){
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Open Resource File");
        File excelFile = fileChooser.showOpenDialog(new Stage());
        javaFXTable = new JavaFXTable(excelFile);
        showRowsInFXTable();
    }

    private void showRowsInFXTable(){

        TableColumn<Row, String> columnFIO = new TableColumn<>("ФИО");
        TableColumn<Row, String> columnType = new TableColumn<>("Вид");
        TableColumn<Row, String> columnTitle = new TableColumn<>("Название");
        TableColumn<Row, String> columnDate = new TableColumn<>("Период подписки");

        ObservableList<Row> rows = javaFXTable.getRowsAll();
        tableInfo.setItems(rows);

        columnFIO.setCellValueFactory( new PropertyValueFactory<Row, String>("fio"));
        columnType.setCellValueFactory( new PropertyValueFactory<Row, String>("type"));
        columnTitle.setCellValueFactory( new PropertyValueFactory<Row, String>("title"));
        columnDate.setCellValueFactory( new PropertyValueFactory<Row, String>("time"));


        tableInfo.getColumns().add(columnFIO);
        tableInfo.getColumns().add(columnType);
        tableInfo.getColumns().add(columnTitle);
        tableInfo.getColumns().add(columnDate);
    }
    @FXML
    void initialize(){

    }



}
