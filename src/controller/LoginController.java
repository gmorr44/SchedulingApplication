
package controller;

import DAO.UserDAO;
import java.awt.event.KeyEvent;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.URL;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.Locale;
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
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import model.User;
import utils.Time;

/**
 *
 * @author gmorr
 */
public class LoginController implements Initializable 
{    
    @FXML
    private Text passwordLBL;
        
    @FXML
    private TextField UsernameTXT;
    
    @FXML
    private PasswordField PasswordTXT;

    @FXML
    private Button signInBTN;

    @FXML
    private Text usernameLBL;

    @FXML
    private Text signInLBL;

    @FXML
    private Text welcomeLBL;
    
    @FXML
    void signInButton(ActionEvent event) throws IOException, SQLException {    
        
        try{
        int userLength = UserDAO.getAllUsers().size();
        String user = UsernameTXT.getText();
        String pass = PasswordTXT.getText();
        
        int userIndex = 0;
        int access = 0;
        //This for block will check to make sure username and password match the database.
        for(int i = 0; i < userLength ; i++ ){
            
            String userKey = UserDAO.getAllUsers().get(i).getUserName();
            String passKey = UserDAO.getAllUsers().get(i).getPassword();
            
            if(user.equals(userKey)&& pass.equals(passKey)){   
               
                access = 1;
                userIndex = i;
                int loggedIn = UserDAO.getAllUsers().get(i).getUserId();
                User.setUserLoggedIn(loggedIn-1);
                
                //The following block will create a timestamp record of login activity. The file is loginRecord.txt
                Timestamp time = Time.timeIntoDB(LocalDateTime.now());
                String fileName = "src/files/loginRecord.txt";
                FileWriter fw = new FileWriter(fileName, true);
                PrintWriter pw = new PrintWriter(fw);
                pw.println("****Username: " + userKey + "   Log-In @: " + time + " UTC****");
                fw.close();  
            }    
        }  
        
        switch(access){ //The switch statement will check for access or no access. 
                        //If access is denied a warning will be displayed.
            case 0:
            ResourceBundle rb = ResourceBundle.getBundle("utils/Lan", Locale.getDefault());
        
            if(Locale.getDefault().getLanguage().equals("es") || Locale.getDefault().getLanguage().equals("en")
            || Locale.getDefault().getLanguage().equals("fr"))
            {
                Alert alert = new Alert(Alert.AlertType.WARNING, rb.getString("warning"));
                Optional<ButtonType> result = alert.showAndWait();
                PasswordTXT.setText("");
            }  
            break;
            
            case 1:
            
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("/view/HomeScreen.fxml"));
            loader.load();
            
            //Not used right now but left for later development. 
            HomeScreenController MPIController = loader.getController();
            MPIController.sendUser(UserDAO.getAllUsers().get(userIndex));
        
            Stage stage = (Stage)((Button)event.getSource()).getScene().getWindow();
            Parent scene = loader.getRoot();
            stage.setScene(new Scene((Parent) scene));
            stage.show();
            
            Time.upcomingApptWarning(User.getUserLoggedIn());// Method call with check if the current user has an appointment within the next 15 minutes
        
            break;
        }
    }                
        catch(Exception e){
            
        }
}    

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
        //The login screen will determine the user's machine default location and translate the language into either English, French, or Spanish.
        try{
            
            rb = ResourceBundle.getBundle("utils/Lan", Locale.getDefault());
        
            if(Locale.getDefault().getLanguage().equals("es") || Locale.getDefault().getLanguage().equals("en")
            || Locale.getDefault().getLanguage().equals("fr")){
            
                UsernameTXT.setPromptText(rb.getString("username"));
                PasswordTXT.setPromptText(rb.getString("password"));
                passwordLBL.setText(rb.getString("passwordLBL"));
                usernameLBL.setText(rb.getString("username"));
                signInBTN.setText(rb.getString("signin"));
                signInLBL.setText(rb.getString("signinLBL"));
                welcomeLBL.setText(rb.getString("welcome"));
            }
        }
        catch(Exception e){
        }       
    }       
}
