package main;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 * Main class containing the main and start methods.
 * FUTURE ENHANCEMENT: If this program were to be updated a future enhancement that would extend its functionality could
 * include a number of parts field for the associated parts of each product. You would be able to add the number of parts
 * in the "add part" form instead of adding multiples of the same part. This would reduce the number of duplicates and
 * simplify the TableView of associated parts.
 */
public class Main extends Application{
    /**
     * Override method starting the Javafx application.
     * @param primaryStage
     * @throws Exception
     */
    @Override
    public void start(Stage primaryStage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("/view/MainForm.fxml"));
        primaryStage.setTitle("Inventory Management System");
        primaryStage.setScene(new Scene(root));
        primaryStage.show();
    }

    /**
     * JAVADOC LOCATION: /IdeaProjects/Inventory_Management_System/Javadoc
     * @param args
     */
    public static void main(String[] args){
        launch(args);
    }
}

