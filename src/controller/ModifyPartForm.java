package controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import model.InHouse;
import model.Inventory;
import model.OutSourced;
import model.Part;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

/** This is the controller class for the ModifyPartForm scene. */
public class ModifyPartForm implements Initializable {

    /** The "Machine ID/Company Name" label in the ModifyPartForm scene. */
    public Label ModifyPartCompanyMachineLabel;
    /** The InHouse radio button. */
    public RadioButton ModifyPartInhouseRadioButton;
    /** The Name field */
    public TextField ModifyPartFormNameField;
    /** The Inventory field */
    public TextField ModifyPartFormInvField;
    /** The Price Field */
    public TextField ModifyPartFormPriceField;
    /** The Max field */
    public TextField ModifyPartFormMaxField;
    /** The Machine ID/Company Name Field */
    public TextField ModifyPartFormMaCoField;
    /** The Min field */
    public TextField ModifyPartFormMinField;
    /** The ID field */
    public TextField ModifyPartFormIdField;
    /** Part to be passed from main screen */
    public Part modifiedPart;
    /** The OutSourced Radio Button */
    public RadioButton ModifyPartOutsourcedRadioButton;

    /**
     * Initializes the screen.
     * @param url
     * @param resourceBundle
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
    }

    /**
     * Changes the label {@link ModifyPartForm#ModifyPartCompanyMachineLabel} to "Machine ID".
     * @param actionEvent
     */
    public void OnModifyPartInhouseRadioAction(ActionEvent actionEvent) {
        ModifyPartCompanyMachineLabel.setText("Machine ID");
    }

    /**
     * Changes the label {@link ModifyPartForm#ModifyPartCompanyMachineLabel} to "Company Name".
     * @param actionEvent
     */
    public void OnModifyPartOutsourcedRadioAction(ActionEvent actionEvent) {
        ModifyPartCompanyMachineLabel.setText("Company Name");
    }

    /**
     * Obtains the Part data from the main screen selection.
     * Sets the fields to that part data.
     *
     * RUNTIME_ERROR: During the implementation of the "Save Modified Part" function I came across a NullPointerException Error
     * that occurred consistently while attempting to save the new Part data. I used breakpoints and the debug feature in IntelliJ
     * to step through the program to identify where exactly the program was crashing. I identified that the Part ID was not being
     * parsed correctly after being passed to the Modify part form. It turned out that the ID field was pointing to the wrong
     * text field in the fxml file. I corrected the field names and it functioned correctly after that.
     * @param selection
     */
    public void showPart(Part selection){
        modifiedPart = selection;
        ModifyPartFormIdField.setText(Integer.toString(modifiedPart.getId()));
        ModifyPartFormNameField.setText(modifiedPart.getName());
        ModifyPartFormInvField.setText(Integer.toString(modifiedPart.getStock()));
        ModifyPartFormPriceField.setText(Double.toString(modifiedPart.getPrice()));
        ModifyPartFormMaxField.setText(Integer.toString(modifiedPart.getMax()));
        ModifyPartFormMinField.setText(Integer.toString(modifiedPart.getMin()));

        if(selection instanceof InHouse){
            ModifyPartInhouseRadioButton.setSelected(true);
            ModifyPartCompanyMachineLabel.setText("Machine ID");
            ModifyPartFormMaCoField.setText(Integer.toString(((InHouse) selection).getMachineId()));
        } else{
            ModifyPartOutsourcedRadioButton.setSelected(true);
            ModifyPartCompanyMachineLabel.setText("Company Name");
            ModifyPartFormMaCoField.setText(((OutSourced) selection).getCompanyName());
        }
    }

    /**
     * Modifies an existing part.
     * Retains the ID number and allows all other fields to be edited. Also checks to ensure the inventory number is
     * between max and min. And that min is less than max. The user must also confirm to save the part.
     * @param actionEvent
     * @throws IOException
     */
    public void OnModifyPartSaveButtonAction(ActionEvent actionEvent) throws IOException {
        try{
            String name = ModifyPartFormNameField.getText();
            int inv = Integer.parseInt(ModifyPartFormInvField.getText());
            double price = Double.parseDouble(ModifyPartFormPriceField.getText());
            int max = Integer.parseInt(ModifyPartFormMaxField.getText());
            int min = Integer.parseInt(ModifyPartFormMinField.getText());
            int index = Inventory.getAllParts().indexOf(Inventory.lookupPart(modifiedPart.getId()));
            if (MainForm.confirmDialogBox("Save Part?", "Save Part", "Would you like to modify this part?")) {
                if (max < min) {
                    MainForm.errorDialogBox("Input Error", "Max/Min", "Max cannot be less than min. ");
                } else if (inv < min || inv > max) {
                    MainForm.errorDialogBox("Input Error", "Inv/Min", "Inventory can not be less that min or greater than max. ");
                } else {
                    if (ModifyPartInhouseRadioButton.isSelected()) {
                        int machineId = Integer.parseInt(ModifyPartFormMaCoField.getText());
                        Part tempPart = new InHouse(modifiedPart.getId(), name, price, inv, min, max, machineId);
                        Inventory.updatePart(index, tempPart);
                    } else {
                        String companyName = ModifyPartFormMaCoField.getText();
                        Part tempPart = new OutSourced(modifiedPart.getId(), name, price, inv, min, max, companyName);
                        Inventory.updatePart(index, tempPart);
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
     * Redirects to main screen.
     * @param actionEvent
     * @throws IOException
     */
    public void OnModifyPartCancelButtonAction(ActionEvent actionEvent) throws IOException{
        Parent root = FXMLLoader.load(getClass().getResource("/view/MainForm.fxml"));
        Stage stage = (Stage)((Button)actionEvent.getSource()).getScene().getWindow();
        Scene scene = new Scene(root);
        stage.setTitle("Main Form");
        stage.setScene(scene);
        stage.show();
    }
}
