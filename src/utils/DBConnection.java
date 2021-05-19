
package utils;

import com.mysql.jdbc.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 *
 * @author gmorr
 */
public class DBConnection {
   
    //JBDC URL Parts
    private static final String protocol = "jdbc";
    private static final String vendorName = ":mysql:";
    private static final String ipAddress = "";//DB ip address to update
    
    private static final String jdbcURL = protocol + vendorName + ipAddress; 
    
    //Driver interface reference
    private static final String MYSQLJDBCDriver = "com.mysql.jdbc.Driver";
    private static Connection conn = null;
    
    private static final String username = "";//DB username to update 
    private static String password = "";//DB password to update
    
    public static Connection startConnection(){
        
        try{
            Class.forName(MYSQLJDBCDriver);
            conn = (Connection)DriverManager.getConnection(jdbcURL, username, password);
        }
        
        catch(ClassNotFoundException e){
            //e.printStackTrace();
        } 
        
        catch(SQLException ex){
            //ex.printStackTrace();
        }
        
        return conn;
    }

    public static Connection getConnection() {
        return conn;
    }

    public static void closeConnection(){
        try{
            conn.close();
        }
        
        catch(SQLException ex){
            //ex.printStackTrace();  
        }                  
    }          
}
