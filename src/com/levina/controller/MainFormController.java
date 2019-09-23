package com.levina.controller;

import com.levina.Main;
import com.levina.entity.Category;
import com.levina.entity.Item;
import javafx.application.Platform;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
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
    private TableColumn <Item,String>colId;
    @FXML
    private TableColumn <Item,String>colName;
    @FXML
    private TableColumn <Item,String>colCategory;
    @FXML
    private TableColumn <Item,String> colED;
    @FXML
    private TableView<Item> tableItem;
    @FXML
    private ObservableList<Item> items;
    @FXML
    private ObservableList<Category> categories;
    @FXML
    private MainFormController mainformController;

    private void setMainController(MainFormController mainFormController) {
        this.mainformController = mainFormController;
        tableItem.setItems(mainFormController.getItems());
    }

    public ObservableList<Category> getCategories(){
        if (categories == null)
        {
            categories = FXCollections.observableArrayList();
        }
        return categories;
    }

    public ObservableList<Item> getItems() {
        if (items == null)
        {
            items = FXCollections.observableArrayList();
        }
        return items;
    }

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
                i.setDate(String.valueOf(datepicker.getValue()));
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
        Alert error = new Alert(Alert.AlertType.ERROR);
        Item i = tableItem.getSelectionModel().getSelectedItem();
        int c=0;
        String temp = i.getName();
        i.setId(Integer.valueOf(txtId.getText()));
        for (Item z : items)
        {
            if(z.getName().equals(txtId.getText()))
            {
                c+=1;
            }
        }

        if(c > 1)
        {
            error.setContentText("ID is duplicated");
            error.showAndWait();
            i.setName(temp);
        }
        else
        {
            i.setName(txtName.getText());
            i.setCategory(cmbxCategory.getValue());
            i.setDate(String.valueOf(datepicker.getValue()));
        }
        tableItem.refresh();
    }


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        categories = FXCollections.observableArrayList();
        items = FXCollections.observableArrayList();
        cmbxCategory.setItems(categories);
        tableItem.setItems(items);
        colId.setCellValueFactory(data ->{
            Item p = data.getValue();
            return  new SimpleStringProperty(String.valueOf(p.getId()));
        });
        colName.setCellValueFactory(data ->{
            Item p = data.getValue();
            return  new SimpleStringProperty(p.getName());
        });
        colED.setCellValueFactory(data ->{
            Item p = data.getValue();
            return  new SimpleStringProperty(String.valueOf(p.getDate()));
        });
        colCategory.setCellValueFactory(data ->{
            Item p = data.getValue();
            return  new SimpleStringProperty(p.getCategory().getName());
        });
    }

    @FXML
    private void tableClick(MouseEvent mouseEvent) {
        btnSave.setDisable(true);
        btnUpdate.setDisable(false);
        Item i = tableItem.getSelectionModel().getSelectedItem();
        txtId.setId(String.valueOf(i.getId()));
        txtName.setText(i.getName());
        cmbxCategory.setValue(i.getCategory());
        datepicker.setValue(LocalDate.parse(i.getDate()));


    }

    @FXML
    private void ShowCategoryAction(ActionEvent actionEvent) {
        try{
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(Main.class.getResource("view/CategoryForm.fxml"));
            BorderPane root = loader.load();
            CategoryFormController controller = loader.getController();
            controller.setMainformController(this);

            Stage mainStage = new Stage();
            mainStage.initModality(Modality.APPLICATION_MODAL);
            mainStage.setTitle("Category Form");
            mainStage.setScene(new Scene(root));
            mainStage.show();


        }
        catch (IOException e){
            e.printStackTrace();

        }
    }



    @FXML
    private void closeAction(ActionEvent actionEvent) {
        Platform.exit();
    }
}
