
package DAO;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import model.User;
import utils.DBConnection;

/**
 *
 * @author gmorr
 */
public class UserDAO {
    //This class is used for getting a list of all users to populate the combo boxes.
    public static ObservableList<User>getAllUsers() throws SQLException{
        ObservableList<User> userList = FXCollections.observableArrayList();
        
        try{
            String readStatement = "SELECT userId, userName, password, active FROM user" ;
            
            PreparedStatement ps = DBConnection.getConnection().prepareStatement(readStatement);           
            ResultSet rs = ps.executeQuery(); 
            
            while(rs.next()){
                int userId = rs.getInt("userId");
                String userName = rs.getString("userName");
                String password = rs.getString("password");
                int active = rs.getInt("active");
               
                User u = new User(userId, userName, password, active);
                userList.add(u);
            }
        }    
        catch(SQLException ex){
            //ex.printStackTrace();
        }      
        return userList;
    }    
}
