
package utils;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.Month;
import java.time.format.DateTimeFormatter;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import model.Appointment;
import model.Customer;
import model.User;

/**
 *
 * @author gmorr
 */
public class DisplayFilter {
    
    public static ObservableList<Appointment>getAppointmentByMonth(User u) throws SQLException {
        ObservableList<Appointment> monthViewAppointments = FXCollections.observableArrayList();
        
        try{
            
            String readStatement = "SELECT appointmentId, appointment.customerId, appointment.userId,\n" +
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
                LocalDate ld = Time.stringToDate(date, start);
                LocalTime lte = Time.stringToTime(date, end);
                Month toOrder = Time.stringToDate(date, start).getMonth();
                LocalDateTime ldt = LocalDateTime.of(ld, lte);
                
                if(ldt.equals(LocalDateTime.now()) || ldt.isAfter(LocalDateTime.now())){
                    if(toOrder.equals(LocalDateTime.now().getMonth())){
                        if(u.getUserId() == userId){
                            Appointment a = new Appointment(appointmentId, customerId, userId, date, start, end, type, userName, password , customerName);
                            monthViewAppointments.add(a); 
                        }
                    }
                }
            }
        }
        
        catch(SQLException ex){
            //ex.printStackTrace();
        } 
        
        return monthViewAppointments;
        } 
    
    public static ObservableList<Appointment>getAppointmentByWeek(User u) throws SQLException {
        
        ObservableList<Appointment> weekViewAppointments = FXCollections.observableArrayList();
        
        try{
            
            String readStatement = "SELECT appointmentId, appointment.customerId, appointment.userId,\n" +
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
                
                LocalDate ld = Time.stringToDate(date, start);
                LocalTime lt = Time.stringToTime(date, start);
                LocalTime lte = Time.stringToTime(date, end);
                LocalDateTime check = LocalDateTime.of(ld, lt);
                LocalDateTime week = LocalDateTime.now().plusWeeks(1);
                LocalDateTime ldt = LocalDateTime.of(ld, lte);
                
                if(ldt.equals(LocalDateTime.now()) || ldt.isAfter(LocalDateTime.now())){
                    if(check.isBefore(week.plusHours(8))){
                        if(u.getUserId() == userId){
                            Appointment a = new Appointment(appointmentId, customerId, userId, date, start, end, type, userName, password , customerName);
                            weekViewAppointments.add(a);
                        }
                    }
                }                
            }
        } 
        
        catch(SQLException ex){
            //ex.printStackTrace();
        } 
        
        return weekViewAppointments;
        } 
    
    public static ObservableList<Appointment>getAppointmentByDay(User u) throws SQLException {
        
        ObservableList<Appointment> weekDayAppointments = FXCollections.observableArrayList();
        
        try{
            
            String readStatement = "SELECT appointmentId, appointment.customerId, appointment.userId,\n" +
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
                
                LocalDate ld = Time.stringToDate(date, start);
                LocalTime lt = Time.stringToTime(date, start);
                LocalTime lte = Time.stringToTime(date, end);
                LocalDateTime check = LocalDateTime.of(ld, lt);
                LocalDateTime day = LocalDateTime.now().plusHours(11);
                
                if(ld.equals(LocalDate.now()) && lte.isAfter(LocalTime.now())){
                    if(u.getUserId() == userId){
                        
                    Appointment a = new Appointment(appointmentId, customerId, userId, date, start, end, type, userName, password , customerName);
                    weekDayAppointments.add(a);
                    }
                }                
            }
        }
        
        catch(SQLException ex){
            //ex.printStackTrace();
        } 
        
        return weekDayAppointments;
        }  
    
    public static ObservableList<Customer>getCustomerByCity() throws SQLException{
        
        ObservableList<Customer> customerList = FXCollections.observableArrayList();
        
        try{
            
            String readStatement = "SELECT customerId, customerName, customer.addressID, address, address2, phone, address.cityId, postalCode, city, city.countryId, country\n" +
            "FROM customer, address, city, country\n" +
            "WHERE customer.addressId = address.addressId AND address.cityId = city.cityId AND city.countryId = country.countryId\n" +
            "ORDER BY city ASC;";
            
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
    
    public static ObservableList<Appointment>getAppointmentByMonthAndType() throws SQLException {
        
        /*Since this is a reporting tool, it will count the sum of all appointments. This includes 
        appointments that have already passed. This allows the ability to look back at prior appointments for analytics.*/
        
        ObservableList<Appointment> monthViewAppointments = FXCollections.observableArrayList();
        
        try{
            
            String readStatement = "SELECT appointmentId, appointment.customerId, appointment.userId,\n" +
            " title, description, location, contact, type, url, start, end, userName, password,customerName\n" +
            "FROM customer, appointment, user\n" +
            "WHERE user.userId = appointment.userId AND appointment.customerId = customer.customerId\n" + 
            "ORDER BY start,type ASC;";
            
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
                
                LocalDate date = Time.timeOutDB(startTS).toLocalDate();
                LocalTime start = Time.timeOutDB(startTS).toLocalTime();
                LocalTime end = Time.timeOutDB(endTS).toLocalTime();
                
                Appointment a = new Appointment(appointmentId, customerId, userId, date, start, end, type, userName, password , customerName);
                monthViewAppointments.add(a); 
            }
        }
        
        catch(SQLException ex){
            //ex.printStackTrace();
        } 
        
        return monthViewAppointments;
    } 
    
}
 