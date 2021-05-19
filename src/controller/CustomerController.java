
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
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import model.Customer;

/**
 * FXML Controller class
 *
 * @author gmorr
 */
public class CustomerController implements Initializable {
    
    @FXML
    private TableView<Customer> customerTable;

    @FXML
    private TableColumn<Customer, String> name;

    @FXML
    private TableColumn<Customer, String> address;
    
    @FXML
    private TableColumn<Customer, String> city;

    @FXML
    private TableColumn<Customer, String> zip;

    @FXML
    private TableColumn<Customer, String> phoneNumber;

    @FXML
    void addCustomerBTN(ActionEvent event) throws IOException, SQLException { 
        
        Stage stage = (Stage)((Button)event.getSource()).getScene().getWindow();
        Object scene = FXMLLoader.load(getClass().getResource("/view/NewCustomer.fxml"));
        stage.setScene(new Scene((Parent) scene));
        stage.show();
    }

    @FXML
    void backDashboardBTN(ActionEvent event) throws IOException {
        
        Stage stage = (Stage)((Button)event.getSource()).getScene().getWindow();
        Object scene = FXMLLoader.load(getClass().getResource("/view/HomeScreen.fxml"));
        stage.setScene(new Scene((Parent) scene));
        stage.show();
    }

    @FXML
    void deleteCustomerBTN(ActionEvent event) throws SQLException {
        
        try{
            int customerId = customerTable.getSelectionModel().getSelectedItem().getCustomerId();
            int addressId = customerTable.getSelectionModel().getSelectedItem().getAddressId();
            int cityId = customerTable.getSelectionModel().getSelectedItem().getCityId();
            int countryId = customerTable.getSelectionModel().getSelectedItem().getCountryId();
        
            // Warning to the user about deleting a customer record.
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Select OK to permanently delete this customer");
            Optional<ButtonType> result = alert.showAndWait();
            if(result.isPresent() && result.get() == ButtonType.OK){
            //execute the delete     
            CustomerDAO.deleteCustomer(customerId, addressId, cityId, countryId);
            //refresh the table view after the delete.
            customerTable.setItems(CustomerDAO.getAllCustomers());
            } 
        }
        catch(NullPointerException e){
            
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Error Dialog");
            alert.setContentText("Please select a customer to delete.");
            alert.showAndWait();
        }
        catch(SQLException ex){
            //ex.printStackTrace();    
        }
    }

    @FXML
    void updateCustomerBTN(ActionEvent event) throws IOException{
        
        try{
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("/view/UpdateCustomer.fxml"));
            loader.load();
            
            //Sends customer object to UpdateCustomerController.
            UpdateCustomerController MPIController = loader.getController();
            MPIController.sendCustomer(customerTable.getSelectionModel().getSelectedItem());
        
            Stage stage = (Stage)((Button)event.getSource()).getScene().getWindow();
            Parent scene = loader.getRoot();
            stage.setScene(new Scene((Parent) scene));
            stage.show();
        }
        
        catch(NullPointerException e){
            
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Error Dialog");
            alert.setContentText("Please select a customer to update.");
            alert.showAndWait();
        }
    }
    
    @Override
    public void initialize(URL url, ResourceBundle rb){    
        
        try{
            //Sets all of the customer objects from the database.
            customerTable.setItems(CustomerDAO.getAllCustomers());
            
            // Populates the table columns.
            name.setCellValueFactory(new PropertyValueFactory<>("customerName"));
            address.setCellValueFactory(new PropertyValueFactory<>("address"));
            city.setCellValueFactory(new PropertyValueFactory<>("city"));
            zip.setCellValueFactory(new PropertyValueFactory<>("postalCode"));
            phoneNumber.setCellValueFactory(new PropertyValueFactory<>("phone"));
        } 
        catch (SQLException ex){
            //ex.printStackTrace();
        }
    }       
}
