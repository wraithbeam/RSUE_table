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
import java.util.ArrayList;
import java.util.Objects;

public class MainController {
    private JavaFXTable javaFXTable;
    private boolean isThereUnsavedChanges = false;
    private int saveAPI;
    private boolean isEditTabOpen = true;
    private ObservableList<Row> rows;
    private ArrayList<Row> unsavedRows = new ArrayList<>();
    private int lastSelectedRow = -1;

    @FXML
    private TableView<Row> tableInfo;

    @FXML
    private Button addButton;

    @FXML
    private CheckBox chekboxJournal;

    @FXML
    private CheckBox chekboxPapper;

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
    private MenuItem menuEditUnselect;

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
    private TextField textfieldArticle;

    @FXML
    private TextField textfieldCompanyAdress;

    @FXML
    private TextField textfieldIzdatel;

    @FXML
    private TextField textfieldName;

    @FXML
    private TextField textfieldOtchestvo;

    @FXML
    private TextField textfieldPhysicAdress;

    @FXML
    private TextField textfieldPodpiskaAllTime;

    @FXML
    private TextField textfieldPodpiskaInYear;

    @FXML
    private TextField textfieldSurname;

    @FXML
    private TextField textfieldTitle;

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

        rows = javaFXTable.getRowsAll();
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
        try {
            removeRow();
        }catch (Exception e){
            System.out.println("Ошибка удаления | " + e);
        }
    }
    private void removeRow(){
        int selectedRow = tableInfo.getSelectionModel().getSelectedIndex();
        try {
            if (unsavedRows.contains(tableInfo.getItems().get(selectedRow)))
                unsavedRows.remove(unsavedRows.indexOf(tableInfo.getItems().get(selectedRow)));
        }catch (Exception e){
            System.out.println("Ошибка удаления файла из хранилища | " + e);
        }
        try {
            javaFXTable.deleteRow(tableInfo.getItems().get(selectedRow));
            tableInfo.getItems().remove(selectedRow);
        }
        catch (Exception e){
            System.out.println("Ошибка удаления файла из Экселя | " + e);
        }
        isThereUnsavedChanges = true;
    }

    //Сохранения файла
    public void saveFileButton(MouseEvent event){
        if (isThereUnsavedChanges)
            try {
                javaFXTable.save();
                addInExcel();
                isThereUnsavedChanges = false;
            }catch (Exception e){
                System.out.println(e);
            }
    }
    public void saveMenuButton(ActionEvent event){
        if (isThereUnsavedChanges)
            try {
                javaFXTable.save();
                addInExcel();
                isThereUnsavedChanges = false;
            }catch (Exception e){
                System.out.println(e);
            }
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
                            tableInfo.getColumns().clear();
                        } catch (Exception e) {
                            System.out.println(e);
                        }
                        break;
                }
            }
            catch (IOException e) {
                e.printStackTrace();
            }
        }else
            tableInfo.getColumns().clear();
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
            splitPlane.setDividerPosition(0, 0.78);
            isEditTabOpen = true;
        }
    }

    /**
     * По клику переносит данные в форму редактирования
     */
    public void fillEditForm(MouseEvent event){
        try {
            int indexSelectedRow = tableInfo.getSelectionModel().getSelectedIndex();
            if (indexSelectedRow == lastSelectedRow){
                unSelectItem(new ActionEvent());
                return;
            }
            lastSelectedRow = indexSelectedRow;

            Row selectedRow = tableInfo.getItems().get(indexSelectedRow);

            textfieldSurname.setText(selectedRow.getSurname());
            textfieldName.setText(selectedRow.getName());
            textfieldOtchestvo.setText(selectedRow.getOtchestvo());
            textfieldPhysicAdress.setText(selectedRow.getAddress());

            if(Objects.equals(selectedRow.getType(), "Журнал")) {
                chekboxJournal.setSelected(true);
                chekboxPapper.setSelected(false);
            }
            else {
                chekboxJournal.setSelected(false);
                chekboxPapper.setSelected(true);
            }

            textfieldIzdatel.setText(selectedRow.getIzdatel());
            textfieldTitle.setText(selectedRow.getTitle());
            textfieldArticle.setText(selectedRow.getArticle());
            textfieldCompanyAdress.setText(selectedRow.getAddressCompany());

            textfieldPodpiskaInYear.setText(selectedRow.getTimeInYear());
            textfieldPodpiskaAllTime.setText(selectedRow.getTimeAtAll());

        }catch (Exception e){
            System.out.println("Ошибка выбора строки | Ничего не выделено");
        }

    }

    /**
     * Добавляет запись в Ексель и таблицу
     */
    public void addRow(MouseEvent event){
        try{
            String type = "";
            if (chekboxPapper.isSelected())
                type = "Газета";
            if (chekboxJournal.isSelected())
                type = "Журнал";

            int index = Integer.parseInt(rows.get(rows.size()-1).getIndex()) + 1;


            Row newRow = new Row(
                    textfieldSurname.getText(), textfieldName.getText(),textfieldOtchestvo.getText(), textfieldPhysicAdress.getText(),  index + "",
                    textfieldIzdatel.getText(), textfieldTitle.getText(), textfieldCompanyAdress.getText(),
                    textfieldPodpiskaInYear.getText(), textfieldPodpiskaAllTime.getText(),
                    textfieldArticle.getText(), type);

            rows.add(newRow);
            tableInfo.setItems(rows);
            unsavedRows.add(newRow);
            unSelectItem(new ActionEvent());
            isThereUnsavedChanges = true;
        }catch (Exception e){
            System.out.println("Ошибка добавления | " + e);
        }
    }
    private void addInExcel(){
        for (Row unsavedRow:
             unsavedRows) {
            javaFXTable.addRow(unsavedRow);
        }
    }

    public void editRow(MouseEvent event){
        try{
            String type = "";
            if (chekboxPapper.isSelected())
                type = "Газета";
            if (chekboxJournal.isSelected())
                type = "Журнал";

            int indexInExcel = Integer.parseInt(tableInfo.getItems().get(tableInfo.getSelectionModel().getSelectedIndex()).getIndex());
            int index = tableInfo.getSelectionModel().getSelectedIndex();


            Row newRow = new Row(
                    textfieldSurname.getText(), textfieldName.getText(),textfieldOtchestvo.getText(), textfieldPhysicAdress.getText(),  indexInExcel + "",
                    textfieldIzdatel.getText(), textfieldTitle.getText(), textfieldCompanyAdress.getText(),
                    textfieldPodpiskaInYear.getText(), textfieldPodpiskaAllTime.getText(),
                    textfieldArticle.getText(), type);

            rows.remove(index);
            rows.add(index, newRow);
            tableInfo.setItems(rows);
            unSelectItem(new ActionEvent());
            isThereUnsavedChanges = true;
            javaFXTable.editRow(newRow);
        }catch (Exception e){
            System.out.println("Ошибка добавления | " + e);
        }
    }
    private void editInExcel(Row row){
        javaFXTable.editRow(row);
    }

    public void searchInTable(ActionEvent event){
        unSelectItem(new ActionEvent());
        try {
            tableInfo.requestFocus();
            String condition = menuFindCondition.getText();
            tableInfo.getSelectionModel().setSelectionMode(
                    SelectionMode.MULTIPLE
            );

            short i = 0;
            for (Row row: rows) {
                if (row.find(condition)) {
                    tableInfo.getSelectionModel().select(i);
                    tableInfo.getFocusModel().focus(i);
                }
                i++;
            }

        }
        catch (Exception e){
        }
    }

    public void unSelectItem(ActionEvent event){
        tableInfo.getSelectionModel().clearSelection();

        textfieldSurname.setText("");
        textfieldName.setText("");
        textfieldOtchestvo.setText("");
        textfieldPhysicAdress.setText("");

        chekboxJournal.setSelected(false);
        chekboxPapper.setSelected(false);


        textfieldIzdatel.setText("");
        textfieldTitle.setText("");
        textfieldArticle.setText("");
        textfieldCompanyAdress.setText("");

        textfieldPodpiskaInYear.setText("");
        textfieldPodpiskaAllTime.setText("");

    }

    @FXML
    void initialize(){
        menuEditDelete.setAccelerator(new KeyCodeCombination(KeyCode.DELETE));
        menuFileOpen.setAccelerator(new KeyCodeCombination(KeyCode.O, KeyCombination.CONTROL_DOWN));
        menuFileSave.setAccelerator(new KeyCodeCombination(KeyCode.S, KeyCombination.CONTROL_DOWN));
        menuEditAdd.setAccelerator(new KeyCodeCombination(KeyCode.A, KeyCombination.CONTROL_DOWN));
        menuEditChange.setAccelerator(new KeyCodeCombination(KeyCode.E, KeyCombination.CONTROL_DOWN));
        menuEditUnselect.setAccelerator(new KeyCodeCombination(KeyCode.D, KeyCombination.CONTROL_DOWN));

        splitPlane.setDividerPosition(0, 0.78);

        chekboxJournal.setAllowIndeterminate(false);
        chekboxPapper.setAllowIndeterminate(false);
    }

    @FXML
    public void exitApplication() {
        exitProtection();
    }
}
