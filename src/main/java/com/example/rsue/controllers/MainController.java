package com.example.rsue.controllers;

import com.example.rsue.JavaFXTable;
import com.example.rsue.MainApplication;
import com.example.rsue.Row;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.StackPane;
import javafx.stage.FileChooser;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;

public class MainController {
    private JavaFXTable javaFXTable;
    private boolean isThereUnsavedChanges = true;
    private int saveAPI;

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
    public void openFileDialog(MouseEvent event) {
        fileChooserStart();
    }
    public void openMenuFileDialog(ActionEvent event) {
        fileChooserStart();
    }
    private void fileChooserStart(){
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Open Resource File");
        File excelFile = fileChooser.showOpenDialog(new Stage());
        javaFXTable = new JavaFXTable(excelFile);
        if (javaFXTable.isTableOpen())
            showRowsInFXTable();
    }

    /**
     * Выводит данные в таблицу JavaFX
     */
    private void showRowsInFXTable(){
        tableInfo.getColumns().clear();

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

    //Удаление записи
    public void removeRowButton(MouseEvent event){
        removeRow();
    }
    public void removeRowMenu(ActionEvent event){
        removeRow();
    }
    private void removeRow(){
        int selectedRow = tableInfo.getSelectionModel().getSelectedIndex();
        javaFXTable.deleteRow(tableInfo.getItems().get(selectedRow));
        tableInfo.getItems().remove(selectedRow);
        isThereUnsavedChanges = true;
    }

    //Сохранения файла
    public void saveFileButton(MouseEvent event){
        javaFXTable.save();
        isThereUnsavedChanges = false;
    }
    public void saveMenuButton(ActionEvent event){
        javaFXTable.save();
        isThereUnsavedChanges = false;
    }

    //Закртыие файла
    public void closeFile(ActionEvent event){
        exitProtection();
    }

    /**
     * Если есть неподтвержденые изменения, спросит о намерении их сохранить
     */
    private void exitProtection(){
        if (isThereUnsavedChanges){
            Parent root;
            try {
                FXMLLoader loader = new FXMLLoader(MainApplication.class.getResource("save-view.fxml"));
                Stage stage = new Stage();
                stage.setTitle("ExcelToJava");
                stage.initOwner(tableInfo.getScene().getWindow());
                stage.initModality(Modality.APPLICATION_MODAL);
                stage.setScene(new Scene((Parent) loader.load()));

                // showAndWait will block execution until the window closes...
                stage.showAndWait();

                SaveFormController controller = loader.getController();
                saveAPI = controller.getChoose();

                System.out.println(saveAPI);
                switch (saveAPI){
                    default:
                        break;
                    case 1:
                        tableInfo.getColumns().clear();
                        break;
                    case 2:
                        try
                        {
                            javaFXTable.save();
                            isThereUnsavedChanges = false;
                        } catch (Exception e) {
                            System.out.println(e);
                        }
                        break;
                }
            }
            catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public void setSaveAPI(int saveAPI) {
        this.saveAPI = saveAPI;
    }

    @FXML
    void initialize(){

    }



}
