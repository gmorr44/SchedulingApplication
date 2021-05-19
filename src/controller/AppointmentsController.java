
package controller;

import DAO.AppointmentDAO;
import DAO.UserDAO;
import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.time.LocalDate;
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
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.ToggleGroup;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import model.Appointment;
import model.User;
import utils.DisplayFilter;
import utils.SceneChange;
import utils.Warning;
/**
 * FXML Controller class
 *
 * @author gmorr
 */
public class AppointmentsController implements Initializable {
    @FXML
    private Text titleTXT;
    
    @FXML
    private TableView<Appointment> appointmentTable;

    @FXML
    private TableColumn<Appointment, LocalDate> date;

    @FXML
    private TableColumn<Appointment, LocalTime> startTime;

    @FXML
    private TableColumn<Appointment, LocalTime> endTime;

    @FXML
    private TableColumn<Appointment, String> customerName;

    @FXML
    private TableColumn<Appointment, String> appoinmtnetType;
    
    @FXML
    private ComboBox<User> pickUser;

    @FXML
    private ToggleGroup calendarView;
    
    @FXML
    private RadioButton all;
    
    @FXML
    private RadioButton day;

    @FXML
    private RadioButton week;

    @FXML
    private RadioButton month;
    
    @FXML
    private Label loggedInTXT;
    
    SceneChange change = (direct, event)->{    //This lamba reduces the large block of redundant code called for each scene change.
        Stage stage = (Stage)((Button)event.getSource()).getScene().getWindow();
        Object scene = FXMLLoader.load(getClass().getResource(direct));
        stage.setScene(new Scene((Parent) scene));
        stage.show();    
    };
    
    Warning confirmation = (warningText)->{    ////This lamba reduces the large block of redundant code called for each warning alert.
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setTitle("Error Dialog");
        alert.setContentText(warningText);
        alert.showAndWait();           
    };
    
    @FXML
    void allView(ActionEvent event) throws SQLException {
        //Set all of the appointment objects in the table.
        User u = pickUser.getSelectionModel().getSelectedItem();
        appointmentTable.setItems(AppointmentDAO.getUserAppointments(u));
        titleTXT.setText("Scheduled Appointments-All");
    }
    
    @FXML
    void today(ActionEvent event) throws SQLException {
        //Set all of the appointment objects in the table.
        User u = pickUser.getSelectionModel().getSelectedItem();
        appointmentTable.setItems(DisplayFilter.getAppointmentByDay(u));
        titleTXT.setText("Scheduled Appointments-Today");
    }
    
    @FXML
    void weekView(ActionEvent event) throws SQLException {
        //Set all of the appointment objects in the table.
        User u = pickUser.getSelectionModel().getSelectedItem();
        appointmentTable.setItems(DisplayFilter.getAppointmentByWeek(u));
        titleTXT.setText("Scheduled Appointments-This Week");
    }
    
    @FXML
    void monthView(ActionEvent event) throws SQLException { 
        //Set all of the appointment objects in the table.
        User u = pickUser.getSelectionModel().getSelectedItem();
        appointmentTable.setItems(DisplayFilter.getAppointmentByMonth(u));
        titleTXT.setText("Scheduled Appointments-This Month");
    }
    
    @FXML
    void pickUserBTN(ActionEvent event) throws SQLException {
        User u = pickUser.getSelectionModel().getSelectedItem();
                
        if(all.isSelected()){
            appointmentTable.setItems(AppointmentDAO.getUserAppointments(u));
        }
        else if(week.isSelected()){
            appointmentTable.setItems(DisplayFilter.getAppointmentByWeek(u));
        }
        else if(month.isSelected()){
            appointmentTable.setItems(DisplayFilter.getAppointmentByMonth(u));
        }
        else if(day.isSelected()){
            appointmentTable.setItems(DisplayFilter.getAppointmentByDay(u));
        }
    }
 
    @FXML
    void addAppointmentBTN(ActionEvent event) throws IOException{        
        change.sceneChange("/view/NewAppointment.fxml",event);//Calling the lambda 
    }

    @FXML
    void backDashboardBTN(ActionEvent event) throws IOException{
        change.sceneChange("/view/HomeScreen.fxml",event);//calling the lamba
    }

    @FXML
    void deleteAppointmentBTN(ActionEvent event) throws IOException, SQLException{
        
        try{ 
            
        int customerId = appointmentTable.getSelectionModel().getSelectedItem().getCustomerId();
        int userId = appointmentTable.getSelectionModel().getSelectedItem().getUserId();
        int appointmentId = appointmentTable.getSelectionModel().getSelectedItem().getAppointmentId();
        
        
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Select OK to permanently delete this appointment");
            Optional<ButtonType> result = alert.showAndWait();
        
            if(result.isPresent() && result.get() == ButtonType.OK){    
                AppointmentDAO.deleteAppointment(appointmentId,customerId, userId);
                User u = pickUser.getSelectionModel().getSelectedItem();
                
                if(all.isSelected()){
                  appointmentTable.setItems(AppointmentDAO.getUserAppointments(u));
                }
                else if(week.isSelected()){
                    appointmentTable.setItems(DisplayFilter.getAppointmentByWeek(u));
                }
                else if(month.isSelected()){
                    appointmentTable.setItems(DisplayFilter.getAppointmentByMonth(u));
                }
                else if(day.isSelected()){
                    appointmentTable.setItems(DisplayFilter.getAppointmentByDay(u));
                }
            }   
        }
        catch(NullPointerException e){
            confirmation.message("Please select an appointment to delete.");//calling the confirmation lambda.
        }
    }

    @FXML
    void updateAppointmentBTN(ActionEvent event) throws IOException{
        try{
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("/view/UpdateAppointment.fxml"));
            loader.load();
            
            //Sends object to the UpdateAppointmentController.
            UpdateAppointmentController MPIController = loader.getController();
            MPIController.sendAppointment(appointmentTable.getSelectionModel().getSelectedItem());
             
            Stage stage = (Stage)((Button)event.getSource()).getScene().getWindow();
            Parent scene = loader.getRoot();
            stage.setScene(new Scene((Parent) scene));
            stage.show(); 
        }        
        catch(NullPointerException e){
            confirmation.message("Please select an appointment to update.");
        }
    }
    
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {

        try{
            //Sets the logged in user to personalize the page view.
             int loggedIn = User.getUserLoggedIn();
             User u = UserDAO.getAllUsers().get(loggedIn);
             pickUser.setItems(UserDAO.getAllUsers());
             pickUser.setValue(u);// pre-selects the logged in user for initial view.
             
             //Sets loginText next to user selection combo box.
             loggedInTXT.setText("Logged in as:    " + u.getUserName());
             
             //Set all of the appointment objects in the table.
             appointmentTable.setItems(AppointmentDAO.getUserAppointments(u));
             
             //Populate the table columns             
             date.setCellValueFactory(new PropertyValueFactory<>("date"));
             startTime.setCellValueFactory(new PropertyValueFactory<>("start"));
             endTime.setCellValueFactory(new PropertyValueFactory<>("end"));
             customerName.setCellValueFactory(new PropertyValueFactory<>("customerName"));
             appoinmtnetType.setCellValueFactory(new PropertyValueFactory<>("type"));
        } 
         catch (SQLException ex){
        }       
    }        
}
