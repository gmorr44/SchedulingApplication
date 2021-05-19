
package controller;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import utils.SceneChange;

/**
 * FXML Controller class
 *
 * @author gmorr
 */
public class ReportsController implements Initializable {
    
    SceneChange change = (direct, event)->{    //This lamba reduces the large block of redundant code called for each scene change.
        Stage stage = (Stage)((Button)event.getSource()).getScene().getWindow();
        Object scene = FXMLLoader.load(getClass().getResource(direct));
        stage.setScene(new Scene((Parent) scene));
        stage.show();    
    };

    @FXML
    private Text titleTXT;
    
    @FXML
    void consultantScheduleBTN(ActionEvent event) throws IOException {
        
        change.sceneChange("/view/ReportResults1.fxml",event);//Calling the lambda
    }

    @FXML
    void apptByTypeBTN(ActionEvent event) throws IOException {
        
        change.sceneChange("/view/ReportResults3.fxml",event);//Calling the lambda
    }

    @FXML
    void backDashboardBTN(ActionEvent event) throws IOException {
        
        change.sceneChange("/view/HomeScreen.fxml",event);//Calling the lambda
    }

    @FXML
    void custByCityBTN(ActionEvent event) throws IOException {
        
        change.sceneChange("/view/ReportResults2.fxml",event);//Calling the lambda
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) 
    {
       
    }        
}
