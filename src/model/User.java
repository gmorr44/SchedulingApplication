
package model;

/**
 *
 * @author gmorr
 */
public class User{
    
   private int userId;
   private String userName; 
   private String password; 
   private int active;
   private static int userLoggedIn;

    public User(int userId, String userName, String password, int active) {
        
        this.userId = userId;
        this.userName = userName;
        this.password = password;
        this.active = active;
    }
     
    @Override
    public String toString(){
        return ("Consultant: " + userName);
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public int getActive() {
        return active;
    }

    public void setActive(int active) {
        this.active = active;
    }

    public static int getUserLoggedIn() {
        return userLoggedIn;
    }

    public static void setUserLoggedIn(int userLoggedIn) {
        User.userLoggedIn = userLoggedIn;
    }
}
