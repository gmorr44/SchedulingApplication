
package model;

import java.time.LocalDate;
import java.time.LocalTime;

/**
 *
 * @author gmorr
 */
public class Appointment {
    
    private int appointmentId; 
    private int customerId; 
    private int userId; 
    private String title; 
    private String description;  
    private String location; 
    private String contact; 
    private String type; 
    private String url; 
    private String date;
    private String start; 
    private String end; 
    private String userName; 
    private String password;
    private String customerName;
    private LocalDate ld;
    private LocalTime lts;
    private LocalTime lte;

    public Appointment(int appointmentId, int customerId, int userId, String date, 
    String start, String end, String type, String userName, String password , String customerName){
        this.appointmentId = appointmentId;
        this.customerId = customerId;
        this.userId = userId;
        this.title = title;
        this.location = location;
        this.contact = contact;
        this.type = type;
        this.url = url;
        this.date = date;
        this.start = start;
        this.end = end;
        this.userName = userName;
        this.password = password;
        this.customerName = customerName;
    }
    public Appointment(int appointmentId, int customerId, int userId, LocalDate ld, 
    LocalTime lts, LocalTime lte, String type, String userName, String password , String customerName){
        this.appointmentId = appointmentId;
        this.customerId = customerId;
        this.userId = userId;
        this.title = title;
        this.location = location;
        this.contact = contact;
        this.type = type;
        this.url = url;
        this.ld = ld;
        this.lts = lts;
        this.lte = lte;
        this.userName = userName;
        this.password = password;
        this.customerName = customerName;
    }

    public LocalDate getLd() {
        return ld;
    }

    public void setLd(LocalDate ld) {
        this.ld = ld;
    }

    public LocalTime getLte() {
        return lte;
    }

    public void setLte(LocalTime lte) {
        this.lte = lte;
    }
    

    public void setAppointmentId(int appointmentId) {
        this.appointmentId = appointmentId;
    }

    public void setCustomerId(int customerId) {
        this.customerId = customerId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public void setContact(String contact) {
        this.contact = contact;
    }

    public void setType(String type) {
        this.type = type;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    
    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getStart() {
        return start;
    }

    public void setStart(String start) {
        this.start = start;
    }

    public String getEnd() {
        return end;
    }

    public void setEnd(String end) {
        this.end = end;
    }

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }
    
    public int getAppointmentId() {
        return appointmentId;
    }

    public int getCustomerId() {
        return customerId;
    }

    public int getUserId() {
        return userId;
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public String getLocation() {
        return location;
    }

    public String getContact() {
        return contact;
    }

    public String getType() {
        return type;
    }

    public String getUrl() {
        return url;
    }
    
    public String getUserName() {
        return userName;
    }

    public String getPassword() {
        return password;
    }
}
