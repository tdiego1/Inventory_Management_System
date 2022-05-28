package controller;

import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import model.*;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import static javafx.collections.FXCollections.observableArrayList;

/** This is the controller class for the AddProductFrom scene. */
public class AddProductForm implements Initializable {
    /** ID Text Field */
    public TextField AddProductIdField;
    /** Name Text Field */
    public TextField AddProductNameField;
    /** Inventory Text Field */
    public TextField AddProductInvField;
    /** Price Text Field */
    public TextField AddProductPriceField;
    /** Max Text Field */
    public TextField AddProductMaxField;
    /** Min Text Field */
    public TextField AddProductMinField;
    /** TableView A */
    public TableView AddProductTableA;
    /** TableView A ID column */
    public TableColumn AddProductTableAId;
    /** TableView A Name column */
    public TableColumn AddProductTableAName;
    /** TableView A Inventory column */
    public TableColumn AddProductTableAInv;
    /** TableView A Price column */
    public TableColumn AddProductTableAPrice;
    /** TableView B */
    public TableView AddProductTableB;
    /** TableView B ID column */
    public TableColumn AddProductTableBId;
    /** TableView B Name column */
    public TableColumn AddProductTableBName;
    /** TableView B Inventory column */
    public TableColumn AddProductTableBInv;
    /** TableView B Price column */
    public TableColumn AddProductTableBPrice;
    /** Search Text Field */
    public TextField AddProductSearchField;
    /** List of all Parts from inventory */
    private ObservableList<Part> allParts = observableArrayList();
    /** List of associated Parts */
    private ObservableList<Part> associatedParts = observableArrayList();
    /** New ID */
    public static int newId = 999;

    /**
     * Adds all data to both TableViews on the screen.
     * @param url
     * @param resourceBundle
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        allParts = Inventory.getAllParts();
        AddProductTableA.setItems(allParts);
        AddProductTableAId.setCellValueFactory(new PropertyValueFactory<>("id"));
        AddProductTableAName.setCellValueFactory(new PropertyValueFactory<>("name"));
        AddProductTableAInv.setCellValueFactory(new PropertyValueFactory<>("stock"));
        AddProductTableAPrice.setCellValueFactory(new PropertyValueFactory<>("price"));

        AddProductTableB.setItems(associatedParts);
        AddProductTableBId.setCellValueFactory(new PropertyValueFactory<>("id"));
        AddProductTableBName.setCellValueFactory(new PropertyValueFactory<>("name"));
        AddProductTableBInv.setCellValueFactory(new PropertyValueFactory<>("stock"));
        AddProductTableBPrice.setCellValueFactory(new PropertyValueFactory<>("price"));

    }

    /**
     * Adds parts from the main parts inventory to TableView B.
     * @param actionEvent
     */
    public void OnAddProductAddButtonAction(ActionEvent actionEvent) {
        Part selection = (Part) AddProductTableA.getSelectionModel().getSelectedItem();
        if(selection == null)
            return;
        try{
            associatedParts.add(selection);
        } catch (Exception e){
            MainForm.errorDialogBox("Error", "Error Adding Part", "Could not add part!");
        }
    }

    /**
     * Removes a part from the associated parts in TableView B.
     * @param actionEvent
     */
    public void OnAddProductRemoveButtonAction(ActionEvent actionEvent) {
        if(MainForm.confirmDialogBox("Remove Part", "Remove Part?", "Are you sure you want to remove this part?")) {
            Part selection = (Part) AddProductTableB.getSelectionModel().getSelectedItem();
            if (selection == null)
                return;
            try {
                associatedParts.remove(selection);
            } catch (Exception e) {
                MainForm.errorDialogBox("Error", "Error", "No part removed!");
            }
        }
    }

    /**
     * Creates new product and adds it to the inventory.
     * Adds all basic info and associated parts to the new product.
     * @param actionEvent
     * @throws IOException
     */
    public void OnAddProductSaveButtonAction(ActionEvent actionEvent) throws IOException {
        try {
            String name = AddProductNameField.getText();
            int inv = Integer.parseInt(AddProductInvField.getText());
            double price = Double.parseDouble(AddProductPriceField.getText());
            int max = Integer.parseInt(AddProductMaxField.getText());
            int min = Integer.parseInt(AddProductMinField.getText());
            if (MainForm.confirmDialogBox("Save Product?", "Save Product?", "Would you like to add this product?")) {
                if (max < min) {
                    MainForm.errorDialogBox("Input Error", "Max/Min", "Max cannot be less than min. ");
                } else if (inv < min || inv > max) {
                    MainForm.errorDialogBox("Input Error", "Inv/Min", "Inventory can not be less that min or greater than max. ");
                } else {
                    Product tempProduct = new Product(generateNewId(), name, price, inv, min, max);
                    for(Part p : associatedParts) {
                        tempProduct.addAssociatedPart(p);
                    }
                    Inventory.addProduct(tempProduct);

                    Parent root = FXMLLoader.load(getClass().getResource("/view/MainForm.fxml"));
                    Stage stage = (Stage) ((Button) actionEvent.getSource()).getScene().getWindow();
                    Scene scene = new Scene(root);
                    stage.setTitle("Main Form");
                    stage.setScene(scene);
                    stage.show();
                }
            }
        }
        catch(Exception e){
            MainForm.errorDialogBox("Input Error", "Could not add part!", "One or more fields was incorrect/empty");
        }
    }

    /**
     * Cancels the Add Product function and goes back to main screen.
     * @param actionEvent
     * @throws IOException
     */
    public void OnAddProductCancelButtonAction(ActionEvent actionEvent) throws IOException{
        Parent root = FXMLLoader.load(getClass().getResource("/view/MainForm.fxml"));
        Stage stage = (Stage)((Button)actionEvent.getSource()).getScene().getWindow();
        Scene scene = new Scene(root);
        stage.setTitle("Main Form");
        stage.setScene(scene);
        stage.show();
    }

    /**
     * Displays list of parts based on name/ID.
     * @param actionEvent
     */
    public void OnAddProductSearchAction(ActionEvent actionEvent) {
        String q = AddProductSearchField.getText();
        ObservableList<Part> parts = Inventory.lookupPart(q);

        try{
            int id = Integer.parseInt(q);
            Part p = Inventory.lookupPart(id);
            if(p != null)
                parts.add(p);
        }
        catch(NumberFormatException e){
            // ignore
        }
        if(parts.size() == 0){
            MainForm.errorDialogBox("Error", "Not Found", "No Part name/ID found!");
        }
        else
            AddProductTableA.setItems(parts);
    }

    /**
     * Generates unique ID.
     * @return
     */
    public static int generateNewId(){
        newId = newId + 1;
        return newId;
    }
}
