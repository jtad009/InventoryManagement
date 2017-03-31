/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package inventorysystem.data;

/**
 *
 * @author Epic
 */
public class AdminstrativeEmployee extends Employee {

    private String employeeType;

    public AdminstrativeEmployee(String fullname, String username, int employeeId, String gender, String dateOfBirth, String address) {
        super(fullname, username, employeeId, gender, dateOfBirth, address);
    }

    public void setEmployeeType(String type) {
        this.employeeType = type;
    }

    @Override
    public String getEmployeeType() {
        return this.employeeType;
    }

    @Override
    public String toString() {
        return "Employee Id: "+getEmployeeId()+
                " Username "+getUsername()+
                " fullname "+getFullname()+
                " usertype "+getEmployeeType();
    }

}
