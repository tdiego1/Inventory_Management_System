package controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;
import model.InHouse;
import model.Inventory;
import model.OutSourced;
import model.Part;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

/** This is the controller class for the AddPartFrom scene. */
public class AddPartForm implements Initializable {

    /** The "Machine ID/Company Name" label in the AddPartForm scene. */
    public Label AddPartCompanyMachineLabel;
    /** New ID */
    public static int newId = 0;
    /** Inventory Text Field */
    public TextField PartFormInvField;
    /** Name Text Field */
    public TextField PartFormNameField;
    /** Outsource Radio Button */
    public RadioButton AddPartOutsourceRadioButton;
    /** InHouse Radio Button */
    public RadioButton AddPartInhouseRadioButton;
    /** Price Text Field */
    public TextField PartFormPriceField;
    /** Max Text Field */
    public TextField PartFormMaxField;
    /** Machine ID/Comapny Name Text Field */
    public TextField PartFormMaCoField;
    /** Minimum Text Field */
    public TextField PartFormMinField;

    /**
     * Initializes the scene.
     * @param url
     * @param resourceBundle
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
    }

    /**
     * Changes the label {@link AddPartForm#AddPartCompanyMachineLabel} to "Machine ID".
     * @param actionEvent
     */
    public void OnAddPartInhouseRadioAction(ActionEvent actionEvent) {
        AddPartCompanyMachineLabel.setText("Machine ID");
    }

    /**
     * Changes the label {@link AddPartForm#AddPartCompanyMachineLabel} to "Company Name".
     * @param actionEvent
     */
    public void OnAddPartOutsourcedRadioAction(ActionEvent actionEvent) {
        AddPartCompanyMachineLabel.setText("Company Name");
    }

    /**
     * Adds a new part to the inventory.
     * Receives part name, inventory, price, maximum price, minimum price, and Machine ID/Company Name from user depending
     * on if it's an In-House or Outsourced part. Returns user to main screen.
     * @param actionEvent
     * @throws IOException
     */
    public void OnAddPartSaveButtonAction(ActionEvent actionEvent) throws IOException {
        try {
            String name = PartFormNameField.getText();
            int inv = Integer.parseInt(PartFormInvField.getText());
            double price = Double.parseDouble(PartFormPriceField.getText());
            int max = Integer.parseInt(PartFormMaxField.getText());
            int min = Integer.parseInt(PartFormMinField.getText());
            if (MainForm.confirmDialogBox("Save Part?", "Save Part", "Would you like to add this part?")) {
                if (max < min) {
                    MainForm.errorDialogBox("Input Error", "Max/Min", "Max cannot be less than min. ");
                } else if (inv < min || inv > max) {
                    MainForm.errorDialogBox("Input Error", "Inv/Min", "Inventory can not be less that min or greater than max. ");
                } else {
                    if (AddPartInhouseRadioButton.isSelected()) {
                        int machineId = Integer.parseInt(PartFormMaCoField.getText());
                        Part tempPart = new InHouse(generateNewId(), name, price, inv, min, max, machineId);
                        Inventory.addPart(tempPart);
                    } else {
                        String companyName = PartFormMaCoField.getText();
                        Part tempPart = new OutSourced(generateNewId(), name, price, inv, min, max, companyName);
                        Inventory.addPart(tempPart);
                    }
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
     * Cancels the add part functionality and redirects to the main screen.
     * @param actionEvent
     * @throws IOException
     */
    public void OnAddPartCancelButtonAction(ActionEvent actionEvent) throws IOException{
        Parent root = FXMLLoader.load(getClass().getResource("/view/MainForm.fxml"));
        Stage stage = (Stage)((Button)actionEvent.getSource()).getScene().getWindow();
        Scene scene = new Scene(root);
        stage.setTitle("Main Form");
        stage.setScene(scene);
        stage.show();
    }

    /**
     * Generates a new ID.
     * @return newId integer
     */
    public static int generateNewId(){
        newId = newId + 1;
        return newId;
    }
}
