package inventorysystem.data;

/**
 *
 * @author Epic
 */
public final class AdminstrativeEmployee extends Employee {

    private String employeeType;

    public AdminstrativeEmployee(String fullname, String username, int employeeId, String gender, String dateOfBirth, String address, String employeeType) {
        super(fullname, username, employeeId, gender, dateOfBirth, address);
        setEmployeeType(employeeType);
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
        return "Employee Id: " + getEmployeeId()
                + " Username " + getUsername()
                + " fullname " + getFullname()
                + " usertype " + getEmployeeType();
    }

}
