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

/** This is the controller class for the ModifyProductFrom scene. */
public class ModifyProductForm implements Initializable {

    /** ID Text Field */
    public TextField ModifyProductIdField;
    /** Name Text Field */
    public TextField ModifyProductNameField;
    /** Inventory Text Field */
    public TextField ModifyProductInvField;
    /** Price Field */
    public TextField ModifyProductPriceField;
    /** Max Text Field */
    public TextField ModifyProductMaxField;
    /** Min Text Field */
    public TextField ModifyProductMinField;
    /** TableView A */
    public TableView ModifyProductTableA;
    /** Table A ID Column*/
    public TableColumn ModifyProductTableAId;
    /** Table A Name Column*/
    public TableColumn ModifyProductTableAName;
    /** Table A Inventory Column*/
    public TableColumn ModifyProductTableAInv;
    /** Table A Price Column*/
    public TableColumn ModifyProductTableAPrice;
    /** TableView B */
    public TableView ModifyProductTableB;
    /** Table B ID Column*/
    public TableColumn ModifyProductTableBId;
    /** Table B Name Column*/
    public TableColumn ModifyProductTableBName;
    /** Table B Inventory Column*/
    public TableColumn ModifyProductTableBInv;
    /** Table B Price Column*/
    public TableColumn ModifyProductTableBPrice;
    /** Search Text Field */
    public TextField ModifyProductSearchField;
    /** Product passed from Main Screen */
    Product modifiedProduct;
    /** List of all Parts from inventory */
    private ObservableList<Part> allParts = observableArrayList();
    /** Associated Parts */
    private ObservableList<Part> associatedParts = observableArrayList();

    /**
     * Initializes the scene and populates both tables.
     * @param url
     * @param resourceBundle
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        allParts = Inventory.getAllParts();
        ModifyProductTableA.setItems(allParts);
        ModifyProductTableAId.setCellValueFactory(new PropertyValueFactory<>("id"));
        ModifyProductTableAName.setCellValueFactory(new PropertyValueFactory<>("name"));
        ModifyProductTableAInv.setCellValueFactory(new PropertyValueFactory<>("stock"));
        ModifyProductTableAPrice.setCellValueFactory(new PropertyValueFactory<>("price"));

        ModifyProductTableB.setItems(associatedParts);
        ModifyProductTableBId.setCellValueFactory(new PropertyValueFactory<>("id"));
        ModifyProductTableBName.setCellValueFactory(new PropertyValueFactory<>("name"));
        ModifyProductTableBInv.setCellValueFactory(new PropertyValueFactory<>("stock"));
        ModifyProductTableBPrice.setCellValueFactory(new PropertyValueFactory<>("price"));

    }

    /**
     * Populates the text in each field and the associated parts table.
     * @param selection data passed from main screen.
     */
    public void showProduct(Product selection){
        modifiedProduct = selection;
        associatedParts = modifiedProduct.getAllAssociatedParts();
        ModifyProductIdField.setText(Integer.toString(modifiedProduct.getId()));
        ModifyProductNameField.setText(modifiedProduct.getName());
        ModifyProductInvField.setText(Integer.toString(modifiedProduct.getStock()));
        ModifyProductPriceField.setText(Double.toString(modifiedProduct.getPrice()));
        ModifyProductMaxField.setText(Integer.toString(modifiedProduct.getMax()));
        ModifyProductMinField.setText(Integer.toString(modifiedProduct.getMin()));

        ModifyProductTableB.setItems(associatedParts);
        ModifyProductTableBId.setCellValueFactory(new PropertyValueFactory<>("id"));
        ModifyProductTableBName.setCellValueFactory(new PropertyValueFactory<>("name"));
        ModifyProductTableBInv.setCellValueFactory(new PropertyValueFactory<>("stock"));
        ModifyProductTableBPrice.setCellValueFactory(new PropertyValueFactory<>("price"));
    }

    /**
     * Saves the modified product data.
     * Redirects back to the main screen. Ensures the inventory is between min and max and that min is not greater than
     * max. The user must also confirm to save the product.
     * @param actionEvent
     * @throws IOException
     */
    public void OnModifyProductSaveButtonAction(ActionEvent actionEvent) throws IOException{
        try{
            String name = ModifyProductNameField.getText();
            int inv = Integer.parseInt(ModifyProductInvField.getText());
            double price = Double.parseDouble(ModifyProductPriceField.getText());
            int max = Integer.parseInt(ModifyProductMaxField.getText());
            int min = Integer.parseInt(ModifyProductMinField.getText());
            int index = Inventory.getAllProducts().indexOf(Inventory.lookupProduct(modifiedProduct.getId()));
            if (MainForm.confirmDialogBox("Save Product?", "Save Product", "Would you like to modify this product?")) {
                if (max < min) {
                    MainForm.errorDialogBox("Input Error", "Max/Min", "Max cannot be less than min. ");
                } else if (inv < min || inv > max) {
                    MainForm.errorDialogBox("Input Error", "Inv/Min", "Inventory can not be less that min or greater than max. ");
                } else {
                    Product tempProduct = new Product(modifiedProduct.getId(), name, price, inv, min, max);
                    for(Part p : associatedParts){
                        tempProduct.addAssociatedPart(p);
                    }
                    Inventory.deleteProduct(modifiedProduct);
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
            MainForm.errorDialogBox("Input Error", "Could not add product!", "One or more fields was incorrect/empty");
        }
    }

    /**
     * Cancels the modify product action and redirects to the main screen.
     * @param actionEvent
     * @throws IOException
     */
    public void OnModifyProductCancelButtonAction(ActionEvent actionEvent) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/view/MainForm.fxml"));
        Stage stage = (Stage)((Button)actionEvent.getSource()).getScene().getWindow();
        Scene scene = new Scene(root);
        stage.setTitle("Main Form");
        stage.setScene(scene);
        stage.show();
    }

    /**
     * Displays a list of parts based on name/ID.
     * Shows all parts based if search box is empty. Displays error if nothing was found.
     * @param actionEvent
     */
    public void OnModifyProductSearchAction(ActionEvent actionEvent) {
        String q = ModifyProductSearchField.getText();
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
            ModifyProductTableA.setItems(parts);
    }

    /**
     * Adds part from top to bottom table.
     * Displays error if part cannot be added.
     * @param actionEvent
     */
    public void OnModifyProductAddAction(ActionEvent actionEvent) {
        Part selection = (Part) ModifyProductTableA.getSelectionModel().getSelectedItem();
        if(selection == null)
            return;
        try{
            associatedParts.add(selection);
        } catch (Exception e){
            MainForm.errorDialogBox("Error", "Error Adding Part", "Could not add part!");
        }
    }

    /**
     * Removes part from bottom table.
     * Displays error if part cannot be removed.
     * @param actionEvent
     */
    public void OnModifyProductRemoveAction(ActionEvent actionEvent) {
        if(MainForm.confirmDialogBox("Remove Part", "Remove Part?", "Are you sure you want to remove this part?")) {
            Part selection = (Part) ModifyProductTableB.getSelectionModel().getSelectedItem();
            if (selection == null)
                return;
            try {
                associatedParts.remove(selection);
            } catch (Exception e) {
                MainForm.errorDialogBox("Error", "Error", "No part removed!");
            }
        }
    }
}
