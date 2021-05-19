
package utils;

import com.mysql.jdbc.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

/**
 *
 * @author gmorr
 */
public class DBQuery {
    
    private static PreparedStatement statement;
    
    public static void setPreparedStatement(Connection conn, String sqlStatement) throws SQLException{
        statement = conn.prepareStatement(sqlStatement);
    }
    
    public static PreparedStatement getPreparedStatement(){
        return statement;
    }   
}
