package controller;

import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import model.Inventory;
import model.Part;
import model.Product;

import java.io.IOException;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;

import static javafx.collections.FXCollections.observableArrayList;

/** This is the controller class for the MainForm scene. */
public class MainForm implements Initializable{

    /** Parts TableView */
    public TableView MainFormPartsTable;
    /** Parts Tableview ID column */
    public TableColumn MainFormPartsTableID;
    /** Parts Tableview Name column */
    public TableColumn MainFormPartsTableName;
    /** Parts Tableview Inventory column */
    public TableColumn MainFormPartsTableInv;
    /** Parts Tableview Price column */
    public TableColumn MainFormPartsTablePrice;
    /** Products Tableview */
    public TableView MainFormProductsTable;
    /** Products Tableview ID column */
    public TableColumn MainFormProductsTableID;
    /** Products Tableview Name column */
    public TableColumn MainFormProductsTableName;
    /** Products Tableview Inventory column */
    public TableColumn MainFormProductsTableInv;
    /** Products Tableview Price column */
    public TableColumn MainFormProductsTablePrice;
    /** Parts search Text Field */
    public TextField MainFormPartsSearchField;
    /** Product search Text Field */
    public TextField MainFormProductSearchField;
    /** List of all Parts from inventory */
    private ObservableList<Part> allParts = observableArrayList();
    /** List of all Products from inventory */
    private ObservableList<Product> allProducts = observableArrayList();

    /**
     * Displays data in both TableViews.
     * @param url
     * @param resourceBundle
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        allParts = Inventory.getAllParts();
        allProducts = Inventory.getAllProducts();
        MainFormPartsTable.setItems(allParts);
        MainFormProductsTable.setItems(allProducts);

        MainFormPartsTableID.setCellValueFactory(new PropertyValueFactory<>("id"));
        MainFormPartsTableName.setCellValueFactory(new PropertyValueFactory<>("name"));
        MainFormPartsTableInv.setCellValueFactory(new PropertyValueFactory<>("stock"));
        MainFormPartsTablePrice.setCellValueFactory(new PropertyValueFactory<>("price"));

        MainFormProductsTableID.setCellValueFactory(new PropertyValueFactory<>("id"));
        MainFormProductsTableName.setCellValueFactory(new PropertyValueFactory<>("name"));
        MainFormProductsTableInv.setCellValueFactory(new PropertyValueFactory<>("stock"));
        MainFormProductsTablePrice.setCellValueFactory(new PropertyValueFactory<>("price"));

    }

    /**
     * Redirects to Add Part screen.
     * @param actionEvent
     * @throws IOException
     */
    public void OnMainPartsAddButtonAction(ActionEvent actionEvent) throws IOException{
        Parent root = FXMLLoader.load(getClass().getResource("/view/AddPartForm.fxml"));
        Stage stage = (Stage)((Button)actionEvent.getSource()).getScene().getWindow();
        Scene scene = new Scene(root);
        stage.setTitle("Add Part");
        stage.setScene(scene);
        stage.show();
    }

    /**
     * Redirects to Modify Part screen.
     * Checks to endure a part is selected and there were no errors while retrieving it.
     * @param actionEvent
     * @throws IOException
     */
    public void OnMainPartsModifyButtonAction(ActionEvent actionEvent) throws IOException{
        try{
            Part selection = (Part) MainFormPartsTable.getSelectionModel().getSelectedItem();

            if (selection == null)
                return;

            Stage stage = (Stage)((Button)actionEvent.getSource()).getScene().getWindow();
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/ModifyPartForm.fxml"));
            Parent root = loader.load();
            ModifyPartForm mp = loader.getController();
            mp.showPart(selection);

            stage.setTitle("Modify Part");
            stage.setScene(new Scene(root));
            stage.show();
        } catch (Exception e){
            MainForm.errorDialogBox("Error", "Part Not modified!", "Please select a part to modify.");
        }
    }

    /**
     * Deletes Parts from the Parts table.
     * Shows Error message if no Part was deleted.
     * @param actionEvent
     */
    public void OnMainPartsDeleteButtonAction(ActionEvent actionEvent){
        if(confirmDialogBox("Delete Part", "Delete?", "Are you sure you want to delete this part?")) {
            Part selection = (Part) MainFormPartsTable.getSelectionModel().getSelectedItem();

            if (selection == null)
                return;
            try {
                allParts.remove(selection);
            } catch (Exception e) {
                MainForm.errorDialogBox("Error", "Error", "No part deleted!");
            }
        }
    }

    /**
     * Displays list of parts based on name or ID.
     * Shows all parts if search field is blank and error if no part was found.
     * @param keyEvent
     */
    public void OnMainFormPartsSearch(ActionEvent keyEvent) {
        String q = MainFormPartsSearchField.getText();

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
            MainFormPartsTable.setItems(parts);
    }

    /**
     * Redirects to the Add Product screen.
     * @param actionEvent
     * @throws IOException
     */
    public void OnMainProductsAddButtonAction(ActionEvent actionEvent) throws IOException{
        Parent root = FXMLLoader.load(getClass().getResource("/view/AddProductForm.fxml"));
        Stage stage = (Stage)((Button)actionEvent.getSource()).getScene().getWindow();
        Scene scene = new Scene(root);
        stage.setTitle("Add Product");
        stage.setScene(scene);
        stage.show();
    }

    /**
     * Redirects to the Modify Product Screen.
     * Ensures that a product was selected and ensures there is no issue.
     * @param actionEvent
     * @throws IOException
     */
    public void OnMainProductsModifyButtonAction(ActionEvent actionEvent) throws IOException{
        try{
            Product selection = (Product) MainFormProductsTable.getSelectionModel().getSelectedItem();

            if (selection == null)
                return;

            Stage stage = (Stage)((Button)actionEvent.getSource()).getScene().getWindow();
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/ModifyProductForm.fxml"));
            Parent root = loader.load();
            ModifyProductForm mp = loader.getController();
            mp.showProduct(selection);

            stage.setTitle("Modify Product");
            stage.setScene(new Scene(root));
            stage.show();
        } catch (Exception e){
            MainForm.errorDialogBox("Error", "Product Not modified!", "Please select a product to modify.");
        }
    }

    /**
     * Deletes a selected product.
     * Ensures that the product does not have any associated parts and prompts the user to confirm product deletion.
     * @param actionEvent
     */
    public void OnMainProductsDeleteButtonAction(ActionEvent actionEvent) {
        if(confirmDialogBox("Delete Product", "Delete?", "Are you sure you want to delete this product?")) {
            Product selection = (Product) MainFormProductsTable.getSelectionModel().getSelectedItem();

            if (selection == null)
                return;
            try {
            if(selection.getAllAssociatedParts().isEmpty()) {
                allProducts.remove(selection);
            }
            else
                MainForm.errorDialogBox("Error", "Contains Parts", "Product has associated parts and" +
                        " cannot be deleted.");
            } catch (Exception e) {
                MainForm.errorDialogBox("Error", "Error", "Could not delete product!");
            }
        }
    }

    /**
     * Closes the program.
     * Prompts user to ensure they want to quit the program.
     * @param actionEvent
     * @throws IOException
     */
    public void OnMainExitAction(ActionEvent actionEvent) throws IOException {
        if(confirmDialogBox("Exit", "Exit", "Are you sure you want to exit?")) {
            Stage stage = (Stage) ((Button) actionEvent.getSource()).getScene().getWindow();
            stage.close();
        }
    }

    /**
     * Displays list of products based on name or ID.
     * Shows alert that no products were found and shows all products when search field is empty.
     * @param actionEvent
     */
    public void OnMainFormProductSearch(ActionEvent actionEvent) {
        String q = MainFormProductSearchField.getText();

        ObservableList<Product> products = Inventory.lookupProduct(q);

        try{
            int id = Integer.parseInt(q);
            Product pr = Inventory.lookupProduct(id);
            if(pr != null)
                products.add(pr);
        }
        catch(NumberFormatException e){
            // ignore
        }

        if(products.size() == 0){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setContentText("Product Name/ID not Found!");
            alert.showAndWait();
        }
        else
            MainFormProductsTable.setItems(products);
    }

    /**
     * Creates an Error dialog box.
     * @param title Title text
     * @param header Header text
     * @param content Content text
     */
    static void errorDialogBox(String title, String header, String content){
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(title);
        alert.setHeaderText(header);
        alert.setContentText(content);
        alert.showAndWait();
    }

    /**
     * Creates a Confirmation Box.
     * @param title Title text
     * @param header Header text
     * @param content Content text
     * @return
     */
    static boolean confirmDialogBox(String title, String header, String content){
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle(title);
        alert.setHeaderText(header);
        alert.setContentText(content);
        Optional<ButtonType> selection = alert.showAndWait();
        if (selection.get() == ButtonType.OK){
            return true;
        }
        else{
            return false;
        }
    }
}
