
package controller;

import DAO.AppointmentDAO;
import DAO.CustomerDAO;
import DAO.UserDAO;
import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
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
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.stage.Stage;
import model.Customer;
import model.User;
import utils.Time;

/**
 * FXML Controller class
 *
 * @author gmorr
 */

public class NewAppointmentController implements Initializable {
    
    @FXML
    private ComboBox<String> apptType;

    @FXML
    private ComboBox<Customer> customerName;

    @FXML
    private ComboBox<LocalTime> startTime;

    @FXML
    private DatePicker date;

    @FXML
    private ComboBox<User> consultantName;

    @FXML
    private ComboBox<LocalTime> endTime;

    @FXML
    void saveBTN(ActionEvent event) throws IOException, SQLException{
        
        try{
            //retrieve data from new appointment form and assigns variables.
            int customer = customerName.getSelectionModel().getSelectedItem().getCustomerId();
            int user = consultantName.getSelectionModel().getSelectedItem().getUserId();
            LocalDate apptDate = date.getValue();
            LocalTime startLT = startTime.getValue();
            LocalTime endLT = endTime.getValue();
            String type = apptType.getValue();
        
            //creates localDateTime object from date and time classes for the start and end times.
            LocalDateTime startLDT = LocalDateTime.of(apptDate, startLT);
            LocalDateTime endLDT = LocalDateTime.of(apptDate, endLT);
        
            //calls the Time class method to convert system default time into "UTC".
            Timestamp start = Time.timeIntoDB(startLDT);
            Timestamp end = Time.timeIntoDB(endLDT);
        
            //Checking the date to make sure it is within normal Monday-Friday hours.
            if(Time.openDateChecks(apptDate) == false ){
                if(Time.appointmenInversionCheck(startLT, endLT) == false){
                    if(Time.appointmentConflict(apptDate, startLT, endLT,user,-1,customer)== false){
                        // Creating a new appointment into the database.
                        AppointmentDAO.createAppointment(customer, user, "title", "description", "location", "contact", type, "url", start, end, "userName", "password", 0);       
                        
                        //Scene change. 
                        Stage stage = (Stage)((Button)event.getSource()).getScene().getWindow();
                        Object scene = FXMLLoader.load(getClass().getResource("/view/Appointments.fxml"));
                        stage.setScene(new Scene((Parent) scene));
                        stage.show();
                    }
                }
            }
        }
        catch(Exception e){
            
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Error Dialog");
            alert.setContentText("Please complete all fields before saving new appointment");
            alert.showAndWait();
        }       
    }
    
    @FXML
    void cancelBTN(ActionEvent event) throws IOException {
        
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Select Ok to cancel. Any changes made will not be saved.");
        Optional<ButtonType> result = alert.showAndWait();
        
        if(result.isPresent() && result.get() == ButtonType.OK){ 
            
            Stage stage = (Stage)((Button)event.getSource()).getScene().getWindow();
            Object scene = FXMLLoader.load(getClass().getResource("/view/Appointments.fxml"));
            stage.setScene(new Scene((Parent) scene));
            stage.show();
        }
    }

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb){
        
        try{
            //Initializing the combo boxes with their associated objects.
            customerName.setItems(CustomerDAO.getAllCustomers());
            consultantName.setItems(UserDAO.getAllUsers());
            apptType.setItems(AppointmentDAO.getAllAppointmentTypes());
            
            //The following while loops populate the time combo boxes.
            LocalTime start = LocalTime.of(9, 0);
            LocalTime end = LocalTime.of(17, 0);
        
            while(start.isBefore(end.plusSeconds(1))){
                startTime.getItems().add(start);
                start = start.plusMinutes(30);
            }
        
            LocalTime start1 = LocalTime.of(9, 0);
            LocalTime end1 = LocalTime.of(17, 0);
            
            while(start1.isBefore(end1.plusSeconds(1))){
                endTime.getItems().add(start1);
                start1 = start1.plusMinutes(30);
            }
        }
        catch(SQLException ex){
            //ex.printStackTrace();
        } 
    }    
}
