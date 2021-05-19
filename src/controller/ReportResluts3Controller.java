
package controller;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
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
import model.Appointment;
import model.Reports;
import utils.DisplayFilter;

/**
 * FXML Controller class
 *
 * @author gmorr
 */
public class ReportResluts3Controller implements Initializable {
      @FXML
    private Text titleTXT;

    @FXML
    private TableView<Reports> reportTable;
    
    @FXML
    private TableColumn<Reports, String> monthCol;

    @FXML
    private TableColumn<Reports, Integer> inOfficeCol;

    @FXML
    private TableColumn<Reports, Integer> offSiteCol;

    @FXML
    private TableColumn<Reports, Integer> phoneCol;

    @FXML
    private TableColumn<Reports, Integer> webCol;

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
                
                int web = 0;//variable will hold the value of the type count for each month.
                int phone = 0;//variable will hold the value of the type count for each month.
                int offSite = 0;//variable will hold the value of the type count for each month.
                int inOffice = 0;//variable will hold the value of the type count for each month.
                String holder = LocalDate.now().getMonth().toString();//This will be the holder off last month name.
                ObservableList<Reports> apptByType = FXCollections.observableArrayList();
                
                for(int i = 0; i < DisplayFilter.getAppointmentByMonthAndType().size(); i++){
                    
                    Appointment a = DisplayFilter.getAppointmentByMonthAndType().get(i);
                    String start = a.getLd().getMonth().toString();// The starting month 
 
                    if(i == 0){//Will account fot the first object in the Observable list.
                        holder = start;// Initializes the holder and start in the for the first iteration.
                        switch(a.getType()){ //Will add the appropriate appointment type to the count.
                        case "Web":
                            web++;
                        break;
                        case "Phone":
                            phone++;
                        break;
                        case "Off-Site":
                            offSite++;
                        break;
                        case "In-Office":
                            inOffice++;
                        break;
                        }     
                    } 
                
                    else if(start.equals(holder)){
                        switch(a.getType()){//Will add the appropriate appointment type to the count.
                            case "Web":
                            web++;
                            break;
                            case "Phone":
                            phone++;
                            break;
                            case "Off-Site":
                            offSite++;
                            break;
                            case "In-Office":
                            inOffice++;
                            break;
                    }
                        
                    if(i == DisplayFilter.getAppointmentByMonthAndType().size()-1 ){
                        
                        Reports x = new Reports (start , web, phone, inOffice, offSite);// new object.
                        apptByType.add(x);// Add new object into the Observable List.
                        holder = start;// updates the placeholder month with the index month.
                        }
                    }
                  
                    else {
                       
                        Reports z = new Reports (holder , web, phone, inOffice, offSite);//new object.
                        apptByType.add(z);// Adds a new object into the Observable List.
                        
                        web = 0;// Resets the variables for another count of a new month.     
                        phone = 0;// Resets the variables for another count of a new month.
                        offSite = 0;// Resets the variables for another count of a new month.
                        inOffice = 0;// Resets the variables for another count of a new month.

                        switch(a.getType()){//Will add the appropriate appointment type to the count.
                            case "Web":
                            web++;
                             break;
                            case "Phone":
                            phone++;
                            break;
                            case "Off-Site":
                            offSite++;
                            break;
                            case "In-Office":
                            inOffice++;
                            break;
                        }
                        //Will acount for the last object in the Observable List.
                        if(i == DisplayFilter.getAppointmentByMonthAndType().size()-1 ){
                            Reports t = new Reports (start , web, phone, inOffice, offSite);
                            apptByType.add(t);
                        }
                    
                        holder = start;// updates the placeholder month with the index month.    
                    }         
                }

            reportTable.setItems(apptByType);//Sets all of the customer objects from the database.
            // Populates the table columns.
            monthCol.setCellValueFactory(new PropertyValueFactory<>("month"));
            inOfficeCol.setCellValueFactory(new PropertyValueFactory<>("inOffice"));
            offSiteCol.setCellValueFactory(new PropertyValueFactory<>("offSite"));
            phoneCol.setCellValueFactory(new PropertyValueFactory<>("phone"));
            webCol.setCellValueFactory(new PropertyValueFactory<>("web"));
            }
            
            catch(SQLException ex){
            }    
    }
}    

   
