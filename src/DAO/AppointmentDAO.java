
package DAO;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import model.Appointment;
import model.User;
import utils.DBConnection;
import utils.Time;

/**
 *
 * @author gmorr
 */
public class AppointmentDAO {
    // Returns a list of appointment types to use in the combo box.
     public static ObservableList<String>getAllAppointmentTypes() throws SQLException{
        ObservableList<String> apptTypeList = FXCollections.observableArrayList();
        apptTypeList.add("Web");
        apptTypeList.add("Phone");
        apptTypeList.add("In-Office");
        apptTypeList.add("Off-Site");    
        return apptTypeList;
    }  
    /* Returns a list of all appointments.This Method is not used in the application
     right now but I added it for later use when I want to view appointments that have passed.*/
    public static ObservableList<Appointment>getAllAppointments() throws SQLException{
        ObservableList<Appointment> appointmentList = FXCollections.observableArrayList();
        try{
            //SQL statement.
            String readStatement = "SELECT appointmentId, appointment.customerId, appointment.userId,\n"+
            " title, description, location, contact, type, url, start, end, userName, password,customerName\n" +
            "FROM customer, appointment, user\n" +
            "WHERE user.userId = appointment.userId AND appointment.customerId = customer.customerId\n" +
            "ORDER BY start ASC;";        
            
            PreparedStatement ps = DBConnection.getConnection().prepareStatement(readStatement);            
            ResultSet rs = ps.executeQuery(); 
            
            while(rs.next()){
                int appointmentId = rs.getInt("appointmentId");
                int customerId = rs.getInt("customerId");
                int userId = rs.getInt("userId");
                String type = rs.getString("type");
                Timestamp startTS = rs.getTimestamp("start");
                Timestamp endTS = rs.getTimestamp("end");
                String userName = rs.getString("userName");
                String password = rs.getString("password");
                String customerName = rs.getString("customerName");
                
                String date = Time.timeOutDB(startTS).format(DateTimeFormatter.ofPattern("MM/dd/yyyy"));
                String start = Time.timeOutDB(startTS).format(DateTimeFormatter.ofPattern("HH:mm"));
                String end = Time.timeOutDB(endTS).format(DateTimeFormatter.ofPattern("HH:mm"));
                LocalTime lte = Time.stringToTime(date, end);
                LocalDate ld = Time.stringToDate(date, start);
                LocalDateTime ldt = LocalDateTime.of(ld, lte);
                
                if(ldt.equals(LocalDateTime.now()) || ldt.isAfter(LocalDateTime.now())){//Filters old appointments from the list.    
                Appointment a = new Appointment(appointmentId, customerId, userId, date, start, end, type, userName, password , customerName);
                appointmentList.add(a);
                }
            }
        }
        catch(SQLException ex){
            //ex.printStackTrace();
        }
        
        return appointmentList;
    }
    
    public static ObservableList<Appointment>getUserAppointments(User u) throws SQLException{
        
        ObservableList<Appointment> userAppointmentList = FXCollections.observableArrayList();
        
        try{
            //SQL statement.
            String readStatement = "SELECT appointmentId, appointment.customerId, appointment.userId,\n"+
            " title, description, location, contact, type, url, start, end, userName, password,customerName\n" +
            "FROM customer, appointment, user\n" +
            "WHERE user.userId = appointment.userId AND appointment.customerId = customer.customerId\n" +
            "ORDER BY start ASC;";        
            
            PreparedStatement ps = DBConnection.getConnection().prepareStatement(readStatement);            
            ResultSet rs = ps.executeQuery(); 
            
            while(rs.next()){
                int appointmentId = rs.getInt("appointmentId");
                int customerId = rs.getInt("customerId");
                int userId = rs.getInt("userId");
                String type = rs.getString("type");
                Timestamp startTS = rs.getTimestamp("start");
                Timestamp endTS = rs.getTimestamp("end");
                String userName = rs.getString("userName");
                String password = rs.getString("password");
                String customerName = rs.getString("customerName");
                
                String date = Time.timeOutDB(startTS).format(DateTimeFormatter.ofPattern("MM/dd/yyyy"));
                String start = Time.timeOutDB(startTS).format(DateTimeFormatter.ofPattern("HH:mm"));
                String end = Time.timeOutDB(endTS).format(DateTimeFormatter.ofPattern("HH:mm"));
                LocalTime lte = Time.stringToTime(date, end);
                LocalDate ld = Time.stringToDate(date, start);
                LocalDateTime ldt = LocalDateTime.of(ld, lte);
                
                if(ldt.equals(LocalDateTime.now()) || ldt.isAfter(LocalDateTime.now())){//Filters old appointments from the list.
                    if(u.getUserId() == userId){
                        Appointment a = new Appointment(appointmentId, customerId, userId, date, start, end, type, userName, password , customerName);
                        userAppointmentList.add(a);
                    }
                }
            }
        }
        catch(SQLException ex){
            //ex.printStackTrace();
        }
        
        return userAppointmentList;
    }

    public static void createAppointment( int customerId,int userId,
    String title,String description,String location,String contact,String type,String url,
    Timestamp start,Timestamp end,String userName,String password ,int active){
        
        try{   
            //The following block of code will insert data into the ***appointment*** table within the database.            
            String createAppointment = "INSERT INTO appointment VALUES(null,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
            PreparedStatement psb = DBConnection.getConnection().prepareStatement(createAppointment);     
            psb.setInt(1,customerId);
            psb.setInt(2,userId);
            psb.setString(3,"title");
            psb.setString(4,"description");
            psb.setString(5,"location");  
            psb.setString(6,"contact");
            psb.setString(7,type);
            psb.setString(8,"www.customer.com");
            psb.setTimestamp(9,start); 
            psb.setTimestamp(10,end);
            psb.setTimestamp(11,Time.timeIntoDB(LocalDateTime.now()));
            psb.setString(12, "admin");
            psb.setString(13, null);
            psb.setString(14, "admin");
            psb.execute();
        }       
        catch(SQLException ex){
            //ex.printStackTrace();
        }
    }
    public static void updateAppointment(int customerId,int userId,int appointmentId, String type,
    Timestamp start,Timestamp end) throws SQLException{
        try{
            String updateAppt = "UPDATE appointment SET userId = ? , customerId = ?, start = ?, end = ?, type = ?  WHERE appointment.appointmentId = ?";
            PreparedStatement psa = DBConnection.getConnection().prepareStatement(updateAppt);
            psa.setInt(1, userId);
            psa.setInt(2, customerId);
            psa.setTimestamp(3, start);
            psa.setTimestamp(4, end);
            psa.setString(5, type);
            psa.setInt(6, appointmentId);
            psa.execute();
        }
        catch(SQLException ex){
            //ex.printStackTrace();
        }
    }
    
    public static void deleteAppointment(int appointmentId,int customerId, int userId){
        try{
            String deleteAppointment = "DELETE FROM appointment WHERE appointmentId = ? AND customerId = ? AND userId = ?";
            PreparedStatement psa = DBConnection.getConnection().prepareStatement(deleteAppointment);
            psa.setInt(1, appointmentId);
            psa.setInt(2, customerId);
            psa.setInt(3, userId);
            psa.execute();       
        }
        catch(SQLException ex){
            //ex.printStackTrace();
        }
    }
}
