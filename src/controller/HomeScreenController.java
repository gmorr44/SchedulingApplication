
package controller;

import DAO.UserDAO;
import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
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
import javafx.scene.control.Label;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import model.User;
import utils.DisplayFilter;
import utils.Time;

public class HomeScreenController implements Initializable {
    
    @FXML
    private Label userName;
    @FXML
    private Text numAppointments;
    @FXML
    private Text greeting;
    @FXML
    private Label remainingTXT;
    
    @FXML
    void CustomerBTN(ActionEvent event) throws IOException, SQLException {
        
        Stage stage = (Stage)((Button)event.getSource()).getScene().getWindow();
        Object scene = FXMLLoader.load(getClass().getResource("/view/Customer.fxml"));
        stage.setScene(new Scene((Parent) scene));
        stage.show();
    }

    @FXML
    void appointmentBTN(ActionEvent event) throws IOException, SQLException{
         
        Stage stage = (Stage)((Button)event.getSource()).getScene().getWindow();
        Object scene = FXMLLoader.load(getClass().getResource("/view/Appointments.fxml"));
        stage.setScene(new Scene((Parent) scene));
        stage.show();
    }

    @FXML
    void reportBTN(ActionEvent event) throws IOException{
        
        Stage stage = (Stage)((Button)event.getSource()).getScene().getWindow();
        Object scene = FXMLLoader.load(getClass().getResource("/view/Reports.fxml"));
        stage.setScene(new Scene((Parent) scene));
        stage.show();
    }
    
    @FXML
    void exitBTN(ActionEvent event) {//This will close the application.
        
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Select OK to confirm Signing-Out and closing the application.");
            Optional<ButtonType> result = alert.showAndWait();
        
        if(result.isPresent() && result.get() == ButtonType.OK){    
            System.exit (0);
        }
    }
    //I built this method but didn't end up using it. I left for later use.
    public void sendUser(User user) throws SQLException
    {  
        //userName.setText(user.getUserName());
        //customerId = appointment.getCustomerId();
        //userId = appointment.getUserId();
        //consultantName.getSelectionModel().select(appointment.getUserId()-1);
        //customerName.getSelectionModel().select(appointment.getCustomerId()-1);
    }
 
    @Override
    public void initialize(URL url, ResourceBundle rb) {

        try{
            int loggedIn = User.getUserLoggedIn();
            User u = UserDAO.getAllUsers().get(loggedIn);
            userName.setText(u.getUserName());
            
            //The following code block will determine the time of day and change the greeting message on the home page.
            LocalTime noon = Time.stringToTime(LocalDate.now().format(DateTimeFormatter.ofPattern("MM/dd/yyyy")), "12:00");
            LocalTime five = Time.stringToTime(LocalDate.now().format(DateTimeFormatter.ofPattern("MM/dd/yyyy")), "17:00");
            
            // The following if-else statements will change greeting depending on the time of the day.
            if(LocalTime.now().isBefore(noon)){
                greeting.setText("Good Morning!");
            }
            else if(LocalTime.now().isBefore(five) ){
                greeting.setText("Good Afternoon!");
            }
            else
                greeting.setText("Good Evening!");
            
            /* The following block determines how many appointments are remaining for the day
            for the user that logged in */
            int appointmentsToday = DisplayFilter.getAppointmentByDay(u).size();
            String size = Integer.toString(appointmentsToday);
            numAppointments.setText(size); 
            if(appointmentsToday > 1 || appointmentsToday < 1){
                remainingTXT.setText("Appointments\nRemaining\nToday!");
            }
        }
        catch(SQLException ex){
        }
    }        
}
