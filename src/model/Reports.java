
package model;

/**
 *
 * @author gmorr
 */
public class Reports{
    
    private String month;
    private int web;
    private int phone;
    private int inOffice;
    private int offSite;
    
    //This will construct an object to use in the number of appointment types by month report.
    public Reports(String month, int web, int phone, int inOffice, int offSite){
        this.month = month;
        this.web = web;
        this.phone = phone;
        this.inOffice = inOffice;
        this.offSite = offSite;
    }

    public String getMonth() {
        return month;
    }

    public void setMonth(String month) {
        this.month = month;
    }

    public int getWeb() {
        return web;
    }

    public void setWeb(int web) {
        this.web = web;
    }

    public int getPhone() {
        return phone;
    }

    public void setPhone(int phone) {
        this.phone = phone;
    }

    public int getInOffice() {
        return inOffice;
    }

    public void setInOffice(int inOffice) {
        this.inOffice = inOffice;
    }

    public int getOffSite() {
        return offSite;
    }

    public void setOffSite(int offSite) {
        this.offSite = offSite;
    }
}