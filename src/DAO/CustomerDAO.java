
package DAO;

import com.mysql.jdbc.Statement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.Optional;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import model.Customer;
import utils.Time;
import utils.DBConnection;

/**
 *
 * @author gmorr
 */
public class CustomerDAO {
    
    public static ObservableList<Customer>getAllCustomers() throws SQLException{
        
        ObservableList<Customer> customerList = FXCollections.observableArrayList();
        
        try{
            String readStatement = "SELECT customerId, customerName, customer.addressID, address, address2, phone, address.cityId, postalCode, city, city.countryId, country\n" +
            "FROM customer, address, city, country\n" +
            "WHERE customer.addressId = address.addressId AND address.cityId = city.cityId AND city.countryId = country.countryId";
            
            PreparedStatement ps = DBConnection.getConnection().prepareStatement(readStatement);           
            ResultSet rs = ps.executeQuery(); 
            
            while(rs.next()){
                int customerId = rs.getInt("customerId");
                String customerName = rs.getString("customerName");
                int addressId = rs.getInt("addressId");
                String address = rs.getString("address");
                String address2 = rs.getString("address2");
                String phone = rs.getString("phone");
                int cityId = rs.getInt("cityId");
                String postalCode = rs.getString("postalCode");
                String city = rs.getString("city");
                int countryId = rs.getInt("countryId");
                String country = rs.getString("country");
                
                Customer c = new Customer(customerId,customerName,addressId,address,address2, phone, cityId, postalCode, city, countryId, country);
                customerList.add(c);
            }
        }
        catch(SQLException ex){
            //ex.printStackTrace();
        }      
        return customerList;
    }
    
    public static void createCustomer (int customerId,int addressId,int cityId,int countryId, String customerName, 
            String address, String address2, String phone, String postalCode, String city, String country){
        
        try{
            
            //The following block of code will insert data into the ***country*** table within the database.
            String createCountry = "INSERT INTO country VALUES(null,?,?,?,?,?)";
            PreparedStatement psa = DBConnection.getConnection().prepareStatement(createCountry, Statement.RETURN_GENERATED_KEYS);
            psa.setString(1, country);
            psa.setTimestamp(2,Time.timeIntoDB(LocalDateTime.now()));
            psa.setString(3, "admin");
            psa.setString(4, null);
            psa.setString(5,"admin");          
            psa.execute();
            
            // The folling block of code will return the auto-generated key for use in the next insert
            ResultSet rsa = psa.getGeneratedKeys();
            rsa.next();
            countryId = rsa.getInt(1);
            
            //The following block of code will insert data into the ***city*** table within the database.            
            String createCity = "INSERT INTO city VALUES(null,?,?,?,?,?,?)";
            PreparedStatement psb = DBConnection.getConnection().prepareStatement(createCity, Statement.RETURN_GENERATED_KEYS);     
            psb.setString(1, city);
            psb.setInt(2,countryId);
            psb.setTimestamp(3,Time.timeIntoDB(LocalDateTime.now()));
            psb.setString(4, "admin");
            psb.setString(5, null);
            psb.setString(6, "admin");
            psb.execute();
            
            // The folling block of code will return the auto-generated key for use in the next insert
            ResultSet rsb = psb.getGeneratedKeys();
            rsb.next();
            cityId = rsb.getInt(1);
             
            //The following block of code will insert data into the ***address*** table within the database.
            String createAddress = "INSERT INTO address VALUES(null,?,?,?,?,?,?,?,?,?)";
            PreparedStatement psc = DBConnection.getConnection().prepareStatement(createAddress, Statement.RETURN_GENERATED_KEYS);           
            psc.setString(1, address);
            psc.setString(2, address2);
            psc.setInt(3, cityId);
            psc.setString(4, postalCode);
            psc.setString(5, phone);
            psc.setTimestamp(6,Time.timeIntoDB(LocalDateTime.now()));
            psc.setString(7,"admin");
            psc.setString(8,null);
            psc.setString(9,"admin");          
            psc.execute();
   
            // The folling block of code will return the auto-generated key for use in the next insert
            ResultSet rsc = psc.getGeneratedKeys();
            rsc.next();
            addressId = rsc.getInt(1);
            
            //The following block of code will insert data into the ***customer*** table within the database.
            String createCustomer = "INSERT INTO customer VALUES(null,?,?,?,?,?,?,?)";
            PreparedStatement psd = DBConnection.getConnection().prepareStatement(createCustomer);
            psd.setString(1, customerName);
            psd.setInt(2, addressId);
            psd.setInt(3, 1);
            psd.setTimestamp(4,Time.timeIntoDB(LocalDateTime.now()));
            psd.setString(5, "admin");
            psd.setString(6,null);
            psd.setString(7, "admin");           
            psd.execute();
        }
        
        catch(SQLException ex){
            //ex.printStackTrace();
        }   
    }
    
    public static void updateCustomer(int customerId,String customerName, int addressId, 
    String address, String postalCode, String phone, int cityId, String city, int countryId,String country) throws SQLException{
        
        String updateCustomer = "UPDATE customer SET customerName = ?,lastUpdate = ? WHERE customerId = ?";
        PreparedStatement psa = DBConnection.getConnection().prepareStatement(updateCustomer);
        psa.setString(1, customerName);
        psa.setTimestamp(2,Time.timeIntoDB(LocalDateTime.now()));
        psa.setInt(3, customerId);
        psa.execute();
        
        String updateAddress = "UPDATE address SET address = ?, postalCode = ?, phone = ?, lastUpdate = ? WHERE addressId = ?";
        PreparedStatement psb = DBConnection.getConnection().prepareStatement(updateAddress);
        psb.setString(1, address);
        psb.setString(2, postalCode);
        psb.setString(3, phone);
        psb.setTimestamp(4,Time.timeIntoDB(LocalDateTime.now()));
        psb.setInt(5, addressId);
        
        psb.execute();
        
        String updateCity = "UPDATE city SET city = ?,lastUpdate = ? WHERE cityId = ?";
        PreparedStatement psc = DBConnection.getConnection().prepareStatement(updateCity);
        psc.setString(1, city);
        psc.setTimestamp(2,Time.timeIntoDB(LocalDateTime.now()));
        psc.setInt(3, cityId);
        
        psc.execute();
        
        String updateCountry = "UPDATE country SET country = ?, lastUpdate = ? WHERE countryId = ?";
        PreparedStatement psd = DBConnection.getConnection().prepareStatement(updateCountry);
        psd.setString(1, country);
        psd.setTimestamp(2,Time.timeIntoDB(LocalDateTime.now()));
        psd.setInt(3, countryId);
        
        psd.execute();
    }
    
    public static void deleteCustomer(int customerId, int addressId, int cityId, int countryId){
        try{
            
            String deleteCustomer = "DELETE FROM customer WHERE customerId = ?";
            PreparedStatement psa = DBConnection.getConnection().prepareStatement(deleteCustomer);
            psa.setInt(1, customerId);
            
            psa.execute();
            
            String deleteAddress = "DELETE FROM address WHERE addressId = ?";
            PreparedStatement psb = DBConnection.getConnection().prepareStatement(deleteAddress);
            psb.setInt(1, addressId);
            
            psb.execute();
            
            String deleteCity = "DELETE FROM city WHERE cityId = ?";
            PreparedStatement psc = DBConnection.getConnection().prepareStatement(deleteCity);
            psc.setInt(1, cityId);
            
            psc.execute();
            
            String deleteCountry = "DELETE FROM country WHERE countryId = ?";
            PreparedStatement psd = DBConnection.getConnection().prepareStatement(deleteCountry);
            psd.setInt(1, countryId);
            
            psd.execute();               
        }
        
        catch(SQLException ex){
            
            Alert alert = new Alert(Alert.AlertType.WARNING, "The deletion of this customer can not be completed until all associated appointments are removed first.");
            Optional<ButtonType> result = alert.showAndWait();
           
            //ex.printStackTrace();
        }   
    }    
}
