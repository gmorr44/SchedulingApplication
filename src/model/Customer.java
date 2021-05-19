
package model;

/**
 *
 * @author gmorr
 */
public class Customer {
   
    private int customerId;
    private String customerName;
    private int addressId;
    private String address;
    private String address2;
    private int cityId;
    private String postalCode;
    private String phone;
    private String city;
    private int countryId;
    private String country;
    private static int userId;

    public Customer(int customerId, String customerName, int addressId, String 
    address, String address2, String phone, int cityId,String postalCode, String 
    city, int countryId, String country) {
        
        this.customerId = customerId;
        this.customerName = customerName;
        this.addressId = addressId;
        this.address = address;
        this.address2 = address2;
        this.cityId = cityId;
        this.postalCode = postalCode;
        this.phone = phone;
        this.city = city;
        this.countryId = countryId;
        this.country = country;
    }

    @Override
    public String toString(){
        return ( "Customer: " + customerName);
    }
    
    public static void setUserId(int userId){
        Customer.userId = userId;
    }
    
    public static int getUserId(){
        return userId;
    }
    
    public int getCustomerId() {
        return customerId;
    }

    public String getCustomerName() {
        return customerName;
    }

    public int getAddressId() {
        return addressId;
    }

    public String getAddress() {
        return address;
    }

    public String getAddress2() {
        return address2;
    }

    public int getCityId() {
        return cityId;
    }

    public String getPostalCode() {
        return postalCode;
    }
    
    public String getPhone() {
        return phone;
    }

    public String getCity() {
        return city;
    }
    
    public int getCountryId() {
        return countryId;
    }

    public String getCountry() {
        return country;
    }    
}
