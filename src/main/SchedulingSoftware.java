
package main;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.SQLException;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import utils.DBConnection;

/**
 *
 * @author gmorr
 */
public class SchedulingSoftware extends Application {
    
    @Override
    public void start(Stage stage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("/view/Login.fxml"));
        
        Scene scene = new Scene(root);
        
        stage.setScene(scene);
        stage.show();
    }

    /**
     * @param args the command line arguments
     * @throws java.sql.SQLException
     */
    public static void main(String[] args) throws SQLException, FileNotFoundException, IOException {
        
        DBConnection.startConnection(); // Calls the DB startConnection and starts the connection.
 
        launch(args);
        
        DBConnection.closeConnection(); //Calls the DB closeConnection and ends the connection.                         
    }   
}
