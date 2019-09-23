package com.levina.controller;

import com.levina.entity.Category;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;

import java.net.URL;
import java.util.ResourceBundle;
// Author by : Anastasia Levina 1772020

public class CategoryFormController implements Initializable {
    @FXML
    private TextField txtIdCategory;
    @FXML
    private TextField txtNameCategory;
    @FXML
    private Button btnSaveCategory;
    @FXML
    private TableColumn <Category,String> colIdCategory;
    @FXML
    private TableColumn <Category,String> colCategoryName;
    @FXML
    private TableView<Category> tableCategory;
    @FXML
    private MainFormController mainformController;
    private Integer c;
    private ObservableList<Category> categoriesss;

    @FXML
    public void setMainformController(MainFormController mainformController){
        this.mainformController = mainformController;
        tableCategory.setItems(mainformController.getCategories());
    }


    @FXML
    private void savecategoryAction(ActionEvent actionEvent) {
        categoriesss = mainformController.getCategories();
        Category c = new Category();
        boolean ketemu = false;
        c.setId(Integer.valueOf(txtIdCategory.getText()));
        c.setName(txtNameCategory.getText());
        Alert error = new Alert(Alert.AlertType.ERROR);

        if (txtIdCategory.getText().isEmpty() && txtNameCategory.getText().isEmpty()){
            error.setContentText("Please Fill ID/Name Category !");
            error.showAndWait();

        }
        else {
            for (Category i : categoriesss){
                if (i.getId() == c.getId()) {
                    ketemu = true;
                    error.setContentText("ID is duplicate");
                    error.showAndWait();
                    break;
                }
            }
            if (!ketemu){
                categoriesss.add(c);
            }
            txtIdCategory.clear();
            txtNameCategory.clear();

        }
    }


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        colIdCategory.setCellValueFactory(data-> {
            Category c = data.getValue();

            return new SimpleStringProperty(String.valueOf(c.getId()));
        });
        colCategoryName.setCellValueFactory(data-> {
            Category c = data.getValue();

            return new SimpleStringProperty(c.getName());
        });
    }

}
