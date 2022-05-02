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
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyCodeCombination;
import javafx.scene.input.KeyCombination;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.StackPane;
import javafx.stage.FileChooser;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;

public class MainController {
    private JavaFXTable javaFXTable;
    private boolean isThereUnsavedChanges = false;
    private int saveAPI;
    private boolean isEditTabOpen = true;

    @FXML
    private TableView<Row> tableInfo;

    @FXML
    private Button addButton;

    @FXML
    private Button deleteButton;

    @FXML
    private Button editButton;

    @FXML
    private AnchorPane editFormPlane;

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
    private Menu openCloseEditTab;

    @FXML
    private Button saveButton;

    @FXML
    private SplitPane splitPlane;

    @FXML
    private AnchorPane tableAnchorPane;

    @FXML
    private ToolBar toolBar;


    //Выбор файла. Кнопки "Открыть"
    public void openFileDialog(MouseEvent event) {
        fileChooserStart();
    }
    public void openMenuFileDialog(ActionEvent event) {
        fileChooserStart();
    }
    private void fileChooserStart(){
        exitProtection();
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
        try {
            removeRow();
        }catch (Exception e){
            System.out.println("Ничего не выделено!" + e);
        }

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
        try {
            javaFXTable.save();
            isThereUnsavedChanges = false;
        }catch (Exception e){
            System.out.println(e);
        }

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
                        isThereUnsavedChanges = false;
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


    /**
     * Скрывает или открывает форму редактирования
     */
    public void showHideEditTab(MouseEvent event) {
        if (isEditTabOpen) {
            splitPlane.setDividerPosition(0, 1);
            isEditTabOpen = false;
        }
        else {
            splitPlane.setDividerPosition(0, 0.7);
            isEditTabOpen = true;
        }
    }

    @FXML
    void initialize(){
        menuEditDelete.setAccelerator(new KeyCodeCombination(KeyCode.DELETE));
        menuFileOpen.setAccelerator(new KeyCodeCombination(KeyCode.O, KeyCombination.CONTROL_DOWN));
        menuFileSave.setAccelerator(new KeyCodeCombination(KeyCode.S, KeyCombination.CONTROL_DOWN));
        menuEditAdd.setAccelerator(new KeyCodeCombination(KeyCode.A, KeyCombination.CONTROL_DOWN));
        menuEditChange.setAccelerator(new KeyCodeCombination(KeyCode.E, KeyCombination.CONTROL_DOWN));
    }

    @FXML
    public void exitApplication() {
        exitProtection();
    }
}
