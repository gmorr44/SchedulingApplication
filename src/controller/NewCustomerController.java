
package controller;

import DAO.CustomerDAO;
import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.Optional;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author gmorr
 */
public class NewCustomerController implements Initializable {

    @FXML
    private TextField nameTXT;

    @FXML
    private TextField addressTXT;

    @FXML
    private TextField cityTXT;

    @FXML
    private TextField zipTXT;

    @FXML
    private TextField phoneTXT;

    @FXML
    private TextField countryTXT;
    
    @FXML
    void cancelBTN(ActionEvent event) throws IOException {
        
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Select Ok to cancel. Any changes made will not be saved.");
        Optional<ButtonType> result = alert.showAndWait();
        
        if(result.isPresent() && result.get() == ButtonType.OK){
            Stage stage = (Stage)((Button)event.getSource()).getScene().getWindow();
            Object scene = FXMLLoader.load(getClass().getResource("/view/Customer.fxml"));
            stage.setScene(new Scene((Parent) scene));
            stage.show();
        }
    }

    @FXML
    void saveBTN(ActionEvent event) throws IOException, SQLException{ 
        
        String customerName = nameTXT.getText().trim();
        String address = addressTXT.getText().trim();
        String address2 = "";
        String postalCode = zipTXT.getText().trim();
        String phone = phoneTXT.getText().trim();
        String city = cityTXT.getText().trim();
        String country = countryTXT.getText().trim();
        
        if(customerName.isEmpty() || address.isEmpty() || postalCode.isEmpty() || phone.isEmpty() || city.isEmpty() || country.isEmpty()){
            
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Error Dialog");
            alert.setContentText("Please complete all text fields before saving new customer");
            alert.showAndWait();
        }
        
        else{
            
        CustomerDAO.createCustomer(0, 0, 0, 0, customerName, address, address2, phone, postalCode, city, country);
        
        Stage stage = (Stage)((Button)event.getSource()).getScene().getWindow();
        Object scene = FXMLLoader.load(getClass().getResource("/view/Customer.fxml"));
        stage.setScene(new Scene((Parent) scene));
        stage.show();
        }
    }

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }     
}
