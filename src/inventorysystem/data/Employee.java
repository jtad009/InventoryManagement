/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package inventorysystem.data;

import java.util.logging.Logger;

/**
 *
 * @author Epic
 */
public abstract class Employee {
    private String fullname;
    private String username;
    private int employeeId;
    private String gender;
    private String dateOfBirth;
    private String address;
/**
 * Employee Object Constructor
 * @param fullname
 * @param username
 * @param employeeId
 * @param gender
 * @param dateOfBirth
 * @param address 
 */
    public Employee(String fullname, String username, int employeeId, String gender, String dateOfBirth, String address) {
        this.fullname = fullname;
        this.username = username;
        this.employeeId = employeeId;
        this.gender = gender;
        this.dateOfBirth = dateOfBirth;
        this.address = address;
    }
    private static final Logger LOG = Logger.getLogger(Employee.class.getName());

    public String getFullname() {
        return fullname;
    }

    public void setFullname(String fullname) {
        this.fullname = fullname;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public int getEmployeeId() {
        return employeeId;
    }

    public void setEmployeeId(int employeeId) {
        this.employeeId = employeeId;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(String dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }
    
    
    abstract public String getEmployeeType();
}
