
package utils;

import DAO.AppointmentDAO;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.Optional;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import model.Appointment;

/**
 *
 * @author gmorr
 */
public class Time {
    
    public static void upcomingApptWarning(int userId) throws SQLException{
        
        for(int i = 0; i < AppointmentDAO.getAllAppointments().size(); i++){
            
            Appointment a = AppointmentDAO.getAllAppointments().get(i);
            String apptTime = a.getStart();
            String apptDate = a.getDate();
            LocalTime appTime = Time.stringToTime(apptDate, apptTime);
            LocalDate appDate = Time.stringToDate(apptDate, apptTime);            
            LocalTime now = LocalTime.now();
            String consultant = a.getUserName();
            
            if(appDate.equals (LocalDate.now()) ){
                
                LocalTime apTime = appTime;
                long minutesBetween = ChronoUnit.MINUTES.between(now.minusMinutes(1),appTime);  
                    
                if(minutesBetween <= 15 && minutesBetween >= 0){
                    
                    Alert alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setTitle("Appointment Alert!");
                    alert.setContentText("Consultant: " + consultant + "\nHas an appointment in " + minutesBetween + " minutes.");
                    alert.showAndWait();
                }
            }                    
        }
    }

    //seperates date-time to date only.
    public static LocalDate dateOnly(LocalDateTime dateTime){
        LocalDate date = dateTime.toLocalDate();
        return date;
    }
    
    //seperates date-time to time only.
    public static LocalTime timeOnly(LocalDateTime dateTime){
        LocalTime time = dateTime.toLocalTime();
        return time;
    }
    
    //Will change time from default time to UTC for database insertion.
    public static Timestamp timeIntoDB(LocalDateTime ldt){
        
        ZonedDateTime zdt = ldt.atZone(ZoneId.of(ZoneId.systemDefault().toString()));
        ZonedDateTime utcZDT = zdt.withZoneSameInstant(ZoneId.of("UTC"));
        LocalDateTime utcLDT = utcZDT.toLocalDateTime();
       
        return Timestamp.valueOf(utcLDT);        
    }
    
    //Will change the time from UTC to system default for database extraction.
    public static LocalDateTime timeOutDB(Timestamp ts){
        
        LocalDateTime ldt = ts.toLocalDateTime();
        ZonedDateTime zdt = ldt.atZone(ZoneId.of("UTC"));
        ZonedDateTime sysZDT = zdt.withZoneSameInstant(ZoneId.of(ZoneId.systemDefault().toString()));
        
        return sysZDT.toLocalDateTime();
    }

    //convert table string representation into LocalDate object.
    public static LocalDate stringToDate(String sd, String st){
        
        String cdt = sd + " " + st + ":00";
        DateTimeFormatter dtf =  DateTimeFormatter.ofPattern("MM/dd/yyyy HH:mm:ss");    
        LocalDateTime ldt = LocalDateTime.parse(cdt, dtf);
        LocalDate ld = ldt.toLocalDate();
        
        return ld;
    }  
    
    //convert table string representation into LocalTime object.
    public static LocalTime stringToTime(String sd, String st){
        
        String cdt = sd + " " + st + ":00";
        DateTimeFormatter dtf =  DateTimeFormatter.ofPattern("MM/dd/yyyy HH:mm:ss");       
        LocalDateTime ldt = LocalDateTime.parse(cdt, dtf);
        LocalTime lt = ldt.toLocalTime();
        
        return lt;
    }  
    
    //This method will prevent an appointment from being scheduled outside normal business days.
    public static Boolean openDateChecks (LocalDate ld){
        
        if(ld.getDayOfWeek() == DayOfWeek.SATURDAY || ld.getDayOfWeek() == DayOfWeek.SUNDAY){
            
            Alert alert = new Alert(Alert.AlertType.WARNING, "Your selected date is outside normal business hours. Please select a date between Monday-Friday.");
            Optional<ButtonType> result = alert.showAndWait();
            return true;
        }
        
        else if(ld.isBefore(LocalDate.now())){
            
            Alert alert = new Alert(Alert.AlertType.WARNING, "Your selected date has already passed. Please select a date from today or later.");
            Optional<ButtonType> result = alert.showAndWait();
            return true;    
        }
        
        else
            return false;
    }
    
    //This method will prevent an appointment from being scheduled with a start time before an end time.
    public static Boolean appointmenInversionCheck(LocalTime start, LocalTime end){
        
        if (start.isAfter(end) || start.equals(end)){
            
            Alert alert = new Alert(Alert.AlertType.WARNING, "The end time selected is less than or equal to the start time. Please review time selection.");
            Optional<ButtonType> result = alert.showAndWait(); 
            return true;
        } 
        
        else
            return false;
    }
    
    // This method will prevent any overlapping conflict while scheduling an appointment with a specific user and customer.
    public static Boolean appointmentConflict(LocalDate ld, LocalTime ltStart, LocalTime ltEnd,int user,int apptId, int customer ) throws SQLException{
        
        for(int i = 0; i < AppointmentDAO.getAllAppointments().size(); i++){
            
            Appointment selected = AppointmentDAO.getAllAppointments().get(i);
            LocalDate selDate = Time.stringToDate(selected.getDate(), selected.getStart());
            LocalTime selStart = Time.stringToTime(selected.getDate(), selected.getStart());
            LocalTime selEnd = Time.stringToTime(selected.getDate(), selected.getEnd());
            LocalDateTime selLDT = LocalDateTime.of(ld, ltStart);
            
            if(selLDT.isAfter(LocalDateTime.now())){
                if((selDate.equals(ld) && user == selected.getUserId()) || (selDate.equals(ld) && customer == selected.getCustomerId()) ){
                    if((ltStart.isBefore(selStart) && ltEnd.isAfter(selStart)) ||   //1st condition
                    (ltStart.isBefore(selEnd) && ltEnd.isAfter(selEnd)) ||          //2nd condition
                    (ltStart.isAfter(selStart) && ltEnd.isBefore(selEnd)) ||        //3rd condition
                    (ltStart.equals(selStart) && ltEnd.equals(selEnd)) ||           //4th condition
                    (ltStart.equals(selStart) && ltEnd.isBefore(selEnd)) ||         //5th condition
                    (ltStart.isAfter(selStart) && ltEnd.equals(selEnd)) ||          //6th condition
                    (ltStart.equals(selStart) && ltEnd.isAfter(selEnd)) ||          //7th condition
                    (ltStart.isBefore(selStart) && ltEnd.isAfter(selEnd)) ||        //8th condition
                    (ltStart.isBefore(selStart) && ltEnd.equals(selEnd))){          //9th condition 
                        
                        if(!((selected.getUserId() == user && selected.getAppointmentId()== apptId) || (selected.getCustomerId() == customer && selected.getAppointmentId()== apptId))){
                            Alert alert = new Alert(Alert.AlertType.WARNING);
                            alert.setTitle("Scheduling Conflict");
                            alert.setContentText("There is a scheduling conflict. Please try another date or time.");
                            alert.showAndWait();
                            return true;                                 
                        }
                    }
                }
            }  
            
            else{
                
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Error Dialog");
            alert.setContentText("The selected appointment time has already passed.Please select a future date and time.");
            alert.showAndWait();
            return true;        
            }            
        } 
        return false;
    }
}
