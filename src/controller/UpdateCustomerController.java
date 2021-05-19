
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
import model.Customer;

/**
 * FXML Controller class
 *
 * @author gmorr
 */
public class UpdateCustomerController implements Initializable {

    @FXML
    private TextField nameTXT;
    
    @FXML
    private TextField addressTXT;
    
    @FXML
    private TextField zipTXT;
    
    @FXML
    private TextField phoneTXT;
    
    @FXML
    private TextField countryTXT;
    
    @FXML
    private TextField cityTXT;

    public int customerId;
    public int addressId;
    public int cityId;
    public int countryId;

    @FXML
    private void saveBTN(ActionEvent event) throws IOException{
        
        try{
            
        String customerName = nameTXT.getText().trim();
        String address = addressTXT.getText().trim();
        String city = cityTXT.getText().trim();
        String postalCode = zipTXT.getText().trim();
        String phone = phoneTXT.getText().trim();
        String country = countryTXT.getText().trim();
        
            if(customerName.isEmpty() || address.isEmpty() || postalCode.isEmpty() || phone.isEmpty() || city.isEmpty() || country.isEmpty()){
            
                Alert alert = new Alert(Alert.AlertType.WARNING);
                alert.setTitle("Error Dialog");
                alert.setContentText("Please complete all text fields before updating new customer");
                alert.showAndWait();    
            }
        
            else{
            
                Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "This customer record will be updated. Select OK to continue.");
                Optional<ButtonType> result = alert.showAndWait();
            
                if(result.isPresent() && result.get() == ButtonType.OK){
                
                    CustomerDAO.updateCustomer(customerId, customerName, addressId, address, postalCode, phone, cityId, city, countryId,country);
                }
            
                Stage stage = (Stage)((Button)event.getSource()).getScene().getWindow();
                Object scene = FXMLLoader.load(getClass().getResource("/view/Customer.fxml"));
                stage.setScene(new Scene((Parent) scene));
                stage.show(); 
            }
        }
        
        catch(SQLException ex){
            //ex.printStackTrace();
        }
    }

    @FXML
    private void cancelBTN(ActionEvent event) throws IOException{
        
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Select Ok to cancel. Any changes made will not be saved.");
        Optional<ButtonType> result = alert.showAndWait();
        
        if(result.isPresent() && result.get() == ButtonType.OK){
            
            Stage stage = (Stage)((Button)event.getSource()).getScene().getWindow();
            Object scene = FXMLLoader.load(getClass().getResource("/view/Customer.fxml"));
            stage.setScene(new Scene((Parent) scene));
            stage.show();
        }
    }
    
    public void sendCustomer(Customer customer){ 
        
        customerId = customer.getCustomerId();
        addressId = customer.getAddressId();
        cityId = customer.getCityId();
        countryId = customer.getCountryId();
        nameTXT.setText(String.valueOf(customer.getCustomerName()));
        addressTXT.setText(String.valueOf(customer.getAddress()));
        zipTXT.setText(String.valueOf(customer.getPostalCode()));
        phoneTXT.setText(String.valueOf(customer.getPhone()));
        countryTXT.setText(String.valueOf(customer.getCountry()));
        cityTXT.setText(String.valueOf(customer.getCity()));
    }
    
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb){
        // TODO
    }        
}
