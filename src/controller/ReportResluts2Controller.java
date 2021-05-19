
package controller;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import model.Customer;
import utils.DisplayFilter;

/**
 * FXML Controller class
 *
 * @author gmorr
 */
public class ReportResluts2Controller implements Initializable {

    @FXML
    private Text titleTXT;

    @FXML
    private TableView<Customer> reportTable;

    @FXML
    private TableColumn<Customer, String> city;

    @FXML
    private TableColumn<Customer, String> customerName;

    @FXML
    void backDashboardBTN(ActionEvent event) throws IOException {
        Stage stage = (Stage)((Button)event.getSource()).getScene().getWindow();
        Object scene = FXMLLoader.load(getClass().getResource("/view/Reports.fxml"));
        stage.setScene(new Scene((Parent) scene));
        stage.show();
    }
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
            
        try{
           
            //Sets all of the customer objects from the database.
            reportTable.setItems(DisplayFilter.getCustomerByCity());
            // Populates the table columns.
            customerName.setCellValueFactory(new PropertyValueFactory<>("customerName"));
            city.setCellValueFactory(new PropertyValueFactory<>("city"));
        }
        
        catch (SQLException ex){
            //ex.printStackTrace();
        }
    }        
}
