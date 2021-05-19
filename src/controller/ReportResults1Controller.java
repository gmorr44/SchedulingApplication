
package controller;

import DAO.AppointmentDAO;
import DAO.UserDAO;
import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import model.Appointment;
import model.User;

/**
 * FXML Controller class
 *
 * @author gmorr
 */
public class ReportResults1Controller implements Initializable {

    @FXML
    private Text titleTXT;

    @FXML
    private TableView<Appointment> reportTable;

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
    void backReportsBTN(ActionEvent event) throws IOException {
        
        Stage stage = (Stage)((Button)event.getSource()).getScene().getWindow();
        Object scene = FXMLLoader.load(getClass().getResource("/view/Reports.fxml"));
        stage.setScene(new Scene((Parent) scene));
        stage.show();
    }

    @FXML
    void pickUserBTN(ActionEvent event) throws SQLException {
        
        try{
            
            //Set all of the appointment objects in the table.
            User u = pickUser.getSelectionModel().getSelectedItem();
            titleTXT.setText("Viewing Schedule For Consultant: " + u.getUserName());
            reportTable.setItems(AppointmentDAO.getUserAppointments(u));
        
            date.setCellValueFactory(new PropertyValueFactory<>("date"));
            startTime.setCellValueFactory(new PropertyValueFactory<>("start"));
            endTime.setCellValueFactory(new PropertyValueFactory<>("end"));
            customerName.setCellValueFactory(new PropertyValueFactory<>("customerName"));
            appoinmtnetType.setCellValueFactory(new PropertyValueFactory<>("type"));
        }
        
        catch(NullPointerException e){   
        }
    }
    
    @FXML
    void allBTN(ActionEvent event) throws SQLException {//Clears filter and displays all appointments.
        try{
        
            reportTable.setItems(AppointmentDAO.getAllAppointments());
            pickUser.setItems(UserDAO.getAllUsers());
            titleTXT.setText("Viewing Schedule For All Consultants");
        }
        catch(NullPointerException e){ 
        }
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        
        try{
            titleTXT.setText("Viewing Schedule For All Consultants");
            pickUser.setItems(UserDAO.getAllUsers());
    
            reportTable.setItems(AppointmentDAO.getAllAppointments());
        
            date.setCellValueFactory(new PropertyValueFactory<>("date"));
            startTime.setCellValueFactory(new PropertyValueFactory<>("start"));
            endTime.setCellValueFactory(new PropertyValueFactory<>("end"));
            customerName.setCellValueFactory(new PropertyValueFactory<>("customerName"));
            appoinmtnetType.setCellValueFactory(new PropertyValueFactory<>("type"));
        }
        
        catch (SQLException ex){
             //ex.printStackTrace();
        }
    }
}
