package com.levina.controller;

import com.levina.entity.Category;
import com.levina.entity.Item;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;

import java.net.URL;
import java.util.ResourceBundle;

public class MainFormController implements Initializable {
    @FXML
    private TextField txtId;
    @FXML
    private TextField txtName;
    @FXML
    private ComboBox<Category> cmbxCategory;
    @FXML
    private DatePicker datepicker;
    @FXML
    private Button btnSave;
    @FXML
    private Button btnReset;
    @FXML
    private Button btnUpdate;
    @FXML
    private TableColumn colId;
    @FXML
    private TableColumn colName;
    @FXML
    private TableColumn colCategory;
    @FXML
    private TableColumn colED;
    @FXML
    private TableView<Item> tableItem;
    @FXML
    private ObservableList<Item> items;

    @FXML
    private void saveAction(ActionEvent actionEvent) {
        Alert a = new Alert(Alert.AlertType.ERROR);
        if(txtId.getText()== null || txtName.getText()==null)
        {
            a.setContentText("Please fill Id/Name");
            a.showAndWait();
        }
        else if(cmbxCategory.getValue()==null)
        {
            a.setContentText("Please fill category name");
        }
        else {
            int count = (int) items.stream().filter(p -> p.getName().equalsIgnoreCase(txtName.getText())).count();
            if (count >= 1) {
                a.setContentText("Duplicate Name");
                a.showAndWait();
            } else {
                Item i = new Item();
                i.setId(Integer.parseInt(txtId.getText().trim()));
                i.setName(txtName.getText().trim());
                i.setCategory(cmbxCategory.getSelectionModel().getSelectedItem());
                i.setDate(datepicker.getValue());
                getItems().add(i);
            }
        }


    }

    @FXML
    private void resetAction(ActionEvent actionEvent) {
        btnUpdate.setDisable(true);
        btnSave.setDisable(false);
        cmbxCategory.getSelectionModel().select(-1);
        datepicker.getEditor().clear();
        txtId.clear();
        txtName.clear();


    }

    @FXML
    private void updateAction(ActionEvent actionEvent) {
        Item i = tableItem.getSelectionModel().getSelectedItem();
        i.setId(Integer.parseInt(txtId.getText()));
        i.setName(txtName.getText());
        i.setCategory(cmbxCategory.getSelectionModel().getSelectedItem());
        i.setDate(datepicker.getValue());
        tableItem.refresh();


    }

    public ObservableList<Item> getItems() {
        if (items == null)
        {
            items = FXCollections.observableArrayList();
        }
        return items;
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }

    @FXML
    private void tableClick(MouseEvent mouseEvent) {
        Item i = tableItem.getSelectionModel().getSelectedItem();
        txtId.setId(String.valueOf(i.getId()));
        txtName.setText(i.getName());
        cmbxCategory.setValue(i.getCategory());


    }
}
